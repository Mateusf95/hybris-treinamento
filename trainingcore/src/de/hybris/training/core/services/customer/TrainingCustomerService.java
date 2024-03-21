package de.hybris.training.core.services.customer;

import de.hybris.platform.core.model.user.CustomerModel;

public interface TrainingCustomerService {

    CustomerModel getCustomerByDocument(final String document);
}
