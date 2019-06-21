package uns.ac.rs.prodavnica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uns.ac.rs.prodavnica.entity.Deliverer;

public interface DelivererRepository extends JpaRepository<Deliverer, Long> {
}
