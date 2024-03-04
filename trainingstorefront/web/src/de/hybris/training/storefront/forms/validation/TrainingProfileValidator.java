package de.hybris.training.storefront.forms.validation;

import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.ProfileValidator;
import de.hybris.training.storefront.forms.TrainingUpdateProfileForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Date;

@Component("trainingProfileValidator")
public class TrainingProfileValidator extends ProfileValidator {
    @Override
    public boolean supports(final Class<?> aClass) {
        return TrainingUpdateProfileForm.class.equals(aClass);
    }

    @Override
    public void validate(final Object object, final Errors errors) {
        super.validate(object, errors);
        final TrainingUpdateProfileForm form = (TrainingUpdateProfileForm) object;

        final Date birthdate = form.getBirthdate();

        if (birthdate == null) {
            errors.rejectValue("birthdate", "profile.birthdate.invalid");
        }
    }
}
