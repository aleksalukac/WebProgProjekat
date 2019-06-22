package uns.ac.rs.prodavnica.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Entity
public class Customer extends User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Customer(){
        //this.bonusPoints = 0;
        Cart cart = new Cart();
        this.carts.add(cart);
    }

    /*@Column
    protected int bonusPoints = 0;*/

    @ManyToMany(mappedBy = "favUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL) //omiljeni proizvodi - svaki kupac moze da ima vise proizovda, jedan proizvod moze biti omiljeni za vise kupaca
    private Set<Article> favoriteArticles = new HashSet<>();

    @ManyToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.ALL) //proizvodi trenutno u korpi
    private List<Article> articlesInCart = new ArrayList<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true) //prethodne kupovine - 1 kupac vise korpi
    private List<Cart> carts = new ArrayList<>();

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public Set<Article> getFavoriteArticles() {
        return favoriteArticles;
    }

    public void setFavoriteArticles(Set<Article> favoriteArticles) {
        this.favoriteArticles = favoriteArticles;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public List<Article> getArticlesInCart() {
        return articlesInCart;
    }

    public void setArticlesInCart(List<Article> articlesInCart) {
        this.articlesInCart = articlesInCart;
    }

    public void deleteArticlesInCart(){ this.articlesInCart.clear();}

    public void addCarts(Cart cart) { this.carts.add(cart);}

/*
    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }*/
}
