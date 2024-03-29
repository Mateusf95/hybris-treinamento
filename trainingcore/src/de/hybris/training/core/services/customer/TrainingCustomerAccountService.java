package de.hybris.training.core.services.customer;

import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.Date;

public interface TrainingCustomerAccountService extends CustomerAccountService {

    void updateProfile(final CustomerModel customerModel,
                       final String titleCode,
                       final String name,
                       final String login,
                       final Date birthDate) throws DuplicateUidException;
}
