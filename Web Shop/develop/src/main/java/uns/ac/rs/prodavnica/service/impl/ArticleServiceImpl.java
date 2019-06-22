package uns.ac.rs.prodavnica.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uns.ac.rs.prodavnica.entity.Article;

import uns.ac.rs.prodavnica.entity.User;
import uns.ac.rs.prodavnica.repository.ArticleRepository;
import uns.ac.rs.prodavnica.service.ArticleService;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Article> findAll() {
        List<Article> articles = articleRepository.findAll();
        return articles;
    }

    @Override
    public Article findOne(Long id) {
        Article article = articleRepository.getOne(id);
        return article;
    }

    @Override
    public List<Article> findAllOnSale() {
        List<Article> articlesOnSale = articleRepository.findAllByOnSale(true);
        return articlesOnSale;
    }

    @Override
    public Article update(Article article) throws Exception {
        Article articleToUpdate = articleRepository.getOne(article.getId());
        if (articleToUpdate == null) {
            throw new Exception("Article doesnt exist!");
        }

        articleToUpdate.setFavUser(article.getFavUser());
        articleToUpdate.setAmount(article.getAmount());
        articleToUpdate.setCarts(article.getCarts());
       // articleToUpdate.setCartArticles(article.getCartArticles());
        articleToUpdate.setOnSale(article.getOnSale());
        articleToUpdate.setUser(article.getUser());
        articleToUpdate.setDescription(article.getDescription());
        articleToUpdate.setName(article.getName());
        articleToUpdate.setPrice(article.getPrice());
        articleToUpdate.setCategory(article.getCategory());

        Article savedArticle = articleRepository.save(articleToUpdate);
        return savedArticle;
    }
}
