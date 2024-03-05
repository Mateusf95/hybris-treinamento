package de.hybris.training.core.services.customer.impl;

import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.customer.impl.DefaultCustomerAccountService;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.training.core.services.customer.TrainingCustomerAccountService;

import java.util.Date;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

public class DefaultTrainingCustomerAccountServiceImpl extends DefaultCustomerAccountService implements TrainingCustomerAccountService {
    @Override
    public void updateProfile(final CustomerModel customerModel,
                              final String titleCode,
                              final String name,
                              final String login,
                              final Date birthDate) throws DuplicateUidException {
        validateParameterNotNullStandardMessage("customerModel", customerModel);

        customerModel.setBirthdate(birthDate);
        this.updateProfile(customerModel, titleCode, name, login);

    }
}
