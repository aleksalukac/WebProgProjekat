package uns.ac.rs.prodavnica.service;


import uns.ac.rs.prodavnica.entity.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findAll();
    Article findOne(Long id);
}
