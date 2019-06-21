package uns.ac.rs.prodavnica.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uns.ac.rs.prodavnica.entity.Customer;
import uns.ac.rs.prodavnica.entity.Role;
import uns.ac.rs.prodavnica.entity.User;
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


    @Override
    public void delete(Long id) {
        Customer customer = customerRepository.getOne(id);
        customerRepository.delete(customer);
    }


    @Override
    public Customer addNew(User user) {
        Customer customer = new Customer();
        customer.setRole(Role.CUSTOMER);
        customer.setFirstName(user.getFirstName());
        customer.setLastName(user.getLastName());
        customer.setPassword(user.getPassword());
        customer.setTelephone(user.getTelephone());
        customer.setAddress(user.getAddress());
        customer.setEmail(user.getEmail());
        customer.setUsername(user.getUsername());
        return customerRepository.save(customer);
    }
}
