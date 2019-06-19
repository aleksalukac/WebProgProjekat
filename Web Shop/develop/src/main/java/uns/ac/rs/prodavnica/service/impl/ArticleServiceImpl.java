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

}
