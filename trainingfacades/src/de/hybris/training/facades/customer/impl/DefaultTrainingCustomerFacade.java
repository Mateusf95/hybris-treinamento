package de.hybris.training.facades.customer.impl;

import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.RegisterData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.training.core.services.customer.TrainingCustomerAccountService;
import de.hybris.training.facades.customer.TrainingCustomerFacade;
import org.springframework.util.Assert;

public class DefaultTrainingCustomerFacade extends DefaultCustomerFacade implements TrainingCustomerFacade {


    private TrainingCustomerAccountService trainingCustomerAccountService;
    @Override
    protected void setCommonPropertiesForRegister(final RegisterData registerData,
                                                  final CustomerModel customerModel) {
        super.setCommonPropertiesForRegister(registerData, customerModel);
        customerModel.setDocument(registerData.getDocument());
        customerModel.setBirthdate(registerData.getBirthdate());
    }

    @Override
    public void updateProfile(CustomerData customerData) throws DuplicateUidException {
        validateDataBeforeUpdate(customerData);

        final String name = getCustomerNameStrategy().getName(customerData.getFirstName(), customerData.getLastName());
        final CustomerModel customer = getCurrentSessionCustomer();
        customer.setOriginalUid(customerData.getDisplayUid());

        trainingCustomerAccountService.updateProfile(customer, customerData.getTitleCode(),
                name, customerData.getUid(), customerData.getBirthdate());
    }

    @Override
    protected void validateDataBeforeUpdate(final CustomerData customerData) {
        super.validateDataBeforeUpdate(customerData);
        Assert.notNull(customerData.getBirthdate(), "The field [birthdate] cannot be empty");
    }

    public void setTrainingCustomerAccountService(TrainingCustomerAccountService trainingCustomerAccountService) {
        this.trainingCustomerAccountService = trainingCustomerAccountService;
    }
}
