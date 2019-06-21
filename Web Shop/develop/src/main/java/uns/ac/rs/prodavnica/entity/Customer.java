package uns.ac.rs.prodavnica.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
public class Customer extends User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cart> buyerCarts = new HashSet<>();

    @ManyToMany(mappedBy = "favUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Article> favoriteArticles = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Article> historyArticles = new HashSet<>();

    public Set<Article> getHistoryArticles() {
        return historyArticles;
    }

    public void setHistoryArticles(Set<Article> historyArticles) {
        this.historyArticles = historyArticles;
    }

    public Set<Cart> getCustomerCarts() {
        return buyerCarts;
    }

    public void setCustomerCarts(Set<Cart> buyerCarts) {
        this.buyerCarts = buyerCarts;
    }

    public Set<Article> getFavoriteArticles() {
        return favoriteArticles;
    }

    public void setFavoriteArticles(Set<Article> favoriteArticles) {
        this.favoriteArticles = favoriteArticles;
    }
}
