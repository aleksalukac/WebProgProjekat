package uns.ac.rs.prodavnica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uns.ac.rs.prodavnica.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

	List<Article> findAllByName(String name);
	List<Article> findAll();
	List<Article> findAllByOnSale(Boolean sale);
}