package de.hybris.training.core.services.customer.dao;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.internal.dao.Dao;

public interface TrainingCustomerDao extends Dao {
    CustomerModel findCustomerByDocument(final String document);
}
