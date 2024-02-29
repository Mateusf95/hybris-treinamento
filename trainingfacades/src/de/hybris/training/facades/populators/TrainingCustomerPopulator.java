package de.hybris.training.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.springframework.util.Assert;

public class TrainingCustomerPopulator implements Populator<CustomerModel, CustomerData> {

    @Override
    public void populate(final CustomerModel source, final CustomerData target) throws ConversionException {
        Assert.notNull(source, "Parameter source cannot be null.");
        Assert.notNull(target, "Parameter target cannot be null.");

        target.setDocument(source.getDocument());
        target.setBirthdate(source.getBirthdate());
    }
}
