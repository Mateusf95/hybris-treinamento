package de.hybris.training.facades.populators;

import de.hybris.platform.commercefacades.product.converters.populator.StockPopulator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class TrainingStockPopulator implements Populator<ProductModel, ProductData> {

    @Override
    public void populate(final ProductModel source, final ProductData target) throws ConversionException {
        target.setStockMin(source.getStockMin() == null ? 0 : source.getStockMin());
    }
}
