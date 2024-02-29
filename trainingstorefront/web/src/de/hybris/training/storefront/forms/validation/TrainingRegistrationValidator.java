package de.hybris.training.storefront.forms.validation;

import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.RegistrationValidator;
import de.hybris.training.storefront.forms.TrainingRegisterForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Date;

@Component("trainingRegistrationValidator")
public class TrainingRegistrationValidator extends RegistrationValidator {

    @Override
    public boolean supports(final Class<?> aClass) {
        return TrainingRegisterForm.class.equals(aClass);
    }

    @Override
    public void validate(final Object object, final Errors errors) {
        super.validate(object, errors);

        final TrainingRegisterForm form = (TrainingRegisterForm) object;

        final String document = form.getDocument();
        final Date birthdate = form.getBirthdate();

        if (StringUtils.isEmpty(document)) {
            errors.rejectValue("document", "register.document.invalid");
        }
        if (birthdate == null) {
            errors.rejectValue("birthdate", "register.birthdate.invalid");
        }
    }
}
