package de.hybris.training.core.services.customer.impl;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.training.core.services.customer.TrainingCustomerService;
import de.hybris.training.core.services.customer.dao.TrainingCustomerDao;

public class TrainingCustomerServiceImpl implements TrainingCustomerService {

    private TrainingCustomerDao trainingCustomerDao;
    @Override
    public CustomerModel getCustomerByDocument(final String document) {
       return this.trainingCustomerDao.findCustomerByDocument(document);
    }

    public void setTrainingCustomerDao(TrainingCustomerDao trainingCustomerDao) {
        this.trainingCustomerDao = trainingCustomerDao;
    }
}
