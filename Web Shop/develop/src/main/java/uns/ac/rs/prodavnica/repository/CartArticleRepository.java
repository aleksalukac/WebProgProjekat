package uns.ac.rs.prodavnica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uns.ac.rs.prodavnica.entity.CartArticle;


public interface CartArticleRepository extends JpaRepository<CartArticle, Long> {
	
}
