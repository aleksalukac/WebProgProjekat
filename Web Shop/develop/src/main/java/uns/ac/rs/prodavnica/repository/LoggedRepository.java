package uns.ac.rs.prodavnica.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import uns.ac.rs.prodavnica.entity.Logged;

import java.util.List;

public interface LoggedRepository extends JpaRepository<Logged, Long> {


   // Logged findOne();
    List<Logged> findAll();

}
