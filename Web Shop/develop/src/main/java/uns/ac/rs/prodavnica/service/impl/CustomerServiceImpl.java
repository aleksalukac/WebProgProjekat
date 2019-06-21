package uns.ac.rs.prodavnica.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uns.ac.rs.prodavnica.entity.Customer;
import uns.ac.rs.prodavnica.repository.CustomerRepository;
import uns.ac.rs.prodavnica.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer findOne(Long id) {
        Customer customer = customerRepository.getOne(id);
        return customer;
    }

    @Override
    public Customer update(Customer customer) throws Exception {
        Customer customerToUpdate = customerRepository.getOne(customer.getId());
        if (customerToUpdate == null) {
            throw new Exception("Customer doesnt exist!");
        }

        customerToUpdate.setFavoriteArticles(customer.getFavoriteArticles());
        customerToUpdate.setHistoryArticles(customer.getHistoryArticles());
        customerToUpdate.setCustomerCarts(customer.getCustomerCarts());
        Customer savedCustomer = customerRepository.save(customerToUpdate);
        return savedCustomer;
    }

}
