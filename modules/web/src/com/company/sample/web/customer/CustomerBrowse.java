package com.company.sample.web.customer;

import com.company.sample.entity.Customer;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class CustomerBrowse extends AbstractLookup {
    @Inject
    private DataGrid<Customer> customersDataGrid;
    @Inject
    private CollectionDatasource<Customer, UUID> customersDs;

    @Override
    public void init(Map<String, Object> params) {
        customersDataGrid.addAction(new CreateAction(customersDataGrid) {
            @Override
            public void actionPerform(Component component) {
                Customer newCustomer = metadata.create(Customer.class);
                customersDs.addItem(newCustomer);
                customersDataGrid.editItem(newCustomer.getId());
            }
        });

        customersDataGrid.addAction(new EditAction(customersDataGrid) {
            @Override
            public void actionPerform(Component component) {
                Customer selected = customersDataGrid.getSingleSelected();
                if (selected != null) {
                    customersDataGrid.editItem(selected.getId());
                } else {
                    showNotification("Item is not selected");
                }
            }
        });

        customersDataGrid.addEditorPostCommitListener(event -> customersDs.commit());
    }
}