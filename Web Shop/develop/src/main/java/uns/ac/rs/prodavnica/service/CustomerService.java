package uns.ac.rs.prodavnica.service;


import uns.ac.rs.prodavnica.entity.Customer;
import uns.ac.rs.prodavnica.entity.User;

public interface CustomerService {

    Customer findOne(Long id);

    void delete(Long id);
    Customer addNew(User user);

    Customer update(Customer customer) throws Exception;
}
