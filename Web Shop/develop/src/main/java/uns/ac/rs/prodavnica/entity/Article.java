package uns.ac.rs.prodavnica.entity;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


/*enum ArticleCategory {
    GRICKALICE, SLATKISI, SOKOVI
}*/


@Entity
public class Article implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartArticle> CartArticles = new HashSet<>();

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Double price;

    @Column
    private Boolean onSale;

    @Column
    private Integer amount;

    @Column
    private ArticleCategory category;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<User> favUser;


    public Article() {
        this.onSale = false;
        //imageLink = "http://aleksa.lukac.rs/photos/" + id.toString() + ".png";
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean sale) {
        this.onSale = sale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<CartArticle> getCartArticles() {
        return CartArticles;
    }

    public void setCartArticles(Set<CartArticle> CartArticles) {
        this.CartArticles = CartArticles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public ArticleCategory getCategory() {
        return category;
    }

    public void setCategory(ArticleCategory category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getFavUser() {
        return favUser;
    }

    public void setFavUser(Set<User> favUser) {
        this.favUser = favUser;
    }

    @Override
    public String toString() {
        return "Article [id=" + id + ", CartArticles=" + CartArticles + ", name=" + name + ", description=" + description
                + ", price=" + price + ", amount=" + amount + ", category=" + category + ", user=" + user + ", favUser="
                + favUser + "]";
    }
}
