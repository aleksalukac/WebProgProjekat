package uns.ac.rs.prodavnica.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import uns.ac.rs.prodavnica.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findOneById(long id);
}
