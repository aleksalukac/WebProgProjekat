package uns.ac.rs.prodavnica.service;


import uns.ac.rs.prodavnica.entity.Customer;

public interface CustomerService {

    Customer findOne(Long id);


    Customer update(Customer customer) throws Exception;
}
