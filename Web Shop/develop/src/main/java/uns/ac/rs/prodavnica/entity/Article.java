package uns.ac.rs.prodavnica.entity;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();

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
    private List<User> favUser;

    public List<User> getCart() {
        return cart;
    }

    public void setCart(List<User> cart) {
        this.cart = cart;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> cart;


    public Article() {
        this.onSale = false;
        //imageLink = "http://aleksa.lukac.rs/photos/" + id.toString() + ".png";
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void addCart(Cart cart) {
        this.carts.add(cart);
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
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

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
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

    public List<User> getFavUser() {
        return favUser;
    }

    public void setFavUser(List<User> favUser) {
        this.favUser = favUser;
    }
}
