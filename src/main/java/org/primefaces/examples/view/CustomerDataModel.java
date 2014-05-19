/*
 * Copyright 2009-2011 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.examples.view;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.examples.domain.Customer;
import org.primefaces.model.SelectableDataModel;

public class CustomerDataModel extends ListDataModel<Customer> implements SelectableDataModel<Customer>, Serializable {  

    public CustomerDataModel() {
    }

    public CustomerDataModel(List<Customer> data) {
        super(data);
    }
    
    @Override
    public Customer getRowData(String rowKey) {
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data
        
        List<Customer> customers = (List<Customer>) getWrappedData();
        
        for(Customer customer : customers) {
            if(customer.getDescripcion().equals(rowKey))
                return customer;
        }
        
        return null;
    }

    @Override
    public Object getRowKey(Customer customer) {
        return customer.getDescripcion();
    }
}
