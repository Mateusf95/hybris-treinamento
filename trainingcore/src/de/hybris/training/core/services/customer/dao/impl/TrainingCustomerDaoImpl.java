package de.hybris.training.core.services.customer.dao.impl;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.training.core.services.customer.dao.TrainingCustomerDao;

public class TrainingCustomerDaoImpl extends AbstractItemDao implements TrainingCustomerDao {

    private final static String GET_CUSTOMER_BY_DOCUMENT_QUERY = "SELECT {" +CustomerModel.PK+"}" +
            "FROM {" + CustomerModel._TYPECODE + "} " +
            "WHERE {" + CustomerModel.DOCUMENT + "} LIKE ?document";//"SELECT {PK} FROM {Customer} LIKE ?document";

    @Override
    public CustomerModel findCustomerByDocument(final String document) {
        try {
            final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(GET_CUSTOMER_BY_DOCUMENT_QUERY);
            fQuery.addQueryParameter("document", document);

            return getFlexibleSearchService().searchUnique(fQuery);

        } catch (final Exception ex){
            return null;
        }
    }
}
