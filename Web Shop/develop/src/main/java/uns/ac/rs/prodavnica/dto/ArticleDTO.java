package uns.ac.rs.prodavnica.dto;

import uns.ac.rs.prodavnica.entity.Article;

public class ArticleDTO {

    private Article article;
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public ArticleDTO(Article article)
    {
        this.article = article;
        this.link = "http://aleksa.lukac.rs/photos/" + article.getId().toString() + ".png";
    }

}
