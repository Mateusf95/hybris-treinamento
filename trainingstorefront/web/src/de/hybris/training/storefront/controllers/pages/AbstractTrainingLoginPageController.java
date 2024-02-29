package de.hybris.training.storefront.controllers.pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractLoginPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractRegisterPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.ConsentForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.GuestForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.LoginForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.RegisterForm;
import de.hybris.platform.acceleratorstorefrontcommons.strategy.CustomerConsentDataStrategy;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.consent.data.AnonymousConsentData;
import de.hybris.platform.commercefacades.user.data.RegisterData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.training.facades.customer.TrainingCustomerFacade;
import de.hybris.training.storefront.forms.TrainingRegisterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static de.hybris.platform.commercefacades.constants.CommerceFacadesConstants.CONSENT_GIVEN;

public abstract class AbstractTrainingLoginPageController extends AbstractLoginPageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTrainingLoginPageController.class);

    private static final String FORM_GLOBAL_ERROR = "form.global.error";

    private static final String CONSENT_FORM_GLOBAL_ERROR = "consent.form.global.error";
    

    @Resource(name = "trainingRegistrationValidator")
    private Validator trainingRegistrationValidator;
    @Resource(name = "trainingCustomerFacade")
    private TrainingCustomerFacade trainingCustomerFacade;

    @Override
    protected String getDefaultLoginPage(final boolean loginError, final HttpSession session, final Model model) throws CMSItemNotFoundException {
        model.addAttribute(new TrainingRegisterForm());
        return super.getDefaultLoginPage(loginError, session, model);
    }

    @Override
    protected Validator getRegistrationValidator() {
        return trainingRegistrationValidator;
    }
    
    protected String processRegisterUserRequest(final String referer, final TrainingRegisterForm form,
                                                final BindingResult bindingResult, final Model model,
                                                final HttpServletRequest request, final HttpServletResponse response,
                                                final RedirectAttributes redirectModel)
            throws CMSItemNotFoundException {
        if (bindingResult.hasErrors())
        {
            return getRegistrationErrorPage(form, model);
        }

        final RegisterData data = new RegisterData();
        data.setFirstName(form.getFirstName());
        data.setLastName(form.getLastName());
        data.setLogin(form.getEmail());
        data.setPassword(form.getPwd());
        data.setTitleCode(form.getTitleCode());
        data.setDocument(form.getDocument());
        data.setBirthdate(form.getBirthdate());
        try
        {
            trainingCustomerFacade.register(data);
            getAutoLoginStrategy().login(form.getEmail().toLowerCase(), form.getPwd(), request, response);
            GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
                    "registration.confirmation.message.title");

        }
        catch (final DuplicateUidException e)
        {
            LOGGER.debug("registration failed.");
            bindingResult.rejectValue("email", "registration.error.account.exists.title");
            return getRegistrationErrorPage(form, model);
        }

        // Consent form data
        try
        {
            final ConsentForm consentForm = form.getConsentForm();
            if (consentForm != null && consentForm.getConsentGiven())
            {
                getConsentFacade().giveConsent(consentForm.getConsentTemplateId(), consentForm.getConsentTemplateVersion());
            }
        }
        catch (final Exception e)
        {
            LOGGER.error("Error occurred while creating consents during registration", e);
            GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, CONSENT_FORM_GLOBAL_ERROR);
        }

        // save anonymous-consent cookies as ConsentData
        final Cookie cookie = WebUtils.getCookie(request, WebConstants.ANONYMOUS_CONSENT_COOKIE);
        if (cookie != null)
        {
            try
            {
                final ObjectMapper mapper = new ObjectMapper();
                final List<AnonymousConsentData> anonymousConsentDataList = Arrays.asList(
                        mapper.readValue(URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8.displayName()), AnonymousConsentData[].class));

                anonymousConsentDataList.stream().filter(consentData -> CONSENT_GIVEN.equals(consentData.getConsentState()))
                        .forEach(consentData -> consentFacade.giveConsent(consentData.getTemplateCode(),
                                consentData.getTemplateVersion()));
            }
            catch (final UnsupportedEncodingException e) {
                LOGGER.error(String.format("Cookie Data could not be decoded : %s", cookie.getValue()), e);
            }
            catch (final IOException e) {
                LOGGER.error("Cookie Data could not be mapped into the Object", e);
            }
            catch (final Exception e) {
                LOGGER.error("Error occurred while creating Anonymous cookie consents", e);
            }
        }

        customerConsentDataStrategy.populateCustomerConsentDataInSession();

        return REDIRECT_PREFIX + getSuccessRedirect(request, response);
    }

    private String getRegistrationErrorPage(TrainingRegisterForm form, Model model) throws CMSItemNotFoundException {
        form.setTermsCheck(false);
        model.addAttribute(form);
        model.addAttribute(new LoginForm());
        model.addAttribute(new GuestForm());
        GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
        return handleRegistrationError(model);
    }
}
