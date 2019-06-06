package uns.ac.rs.prodavnica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uns.ac.rs.prodavnica.entity.Cart;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

}
