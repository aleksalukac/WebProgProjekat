package uns.ac.rs.prodavnica.entity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

//import javax.persistence.CascadeType;
//import javax.persistence.FetchType;

@Entity
public class Deliverer extends User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Deliverer()
    {

    }

    public Deliverer(long id)
    {
        this.id = id;
    }

    @OneToMany(mappedBy = "deliver", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cart> delivererCarts = new HashSet<>();

    public Set<Cart> getDeliverCarts() {
        return delivererCarts;
    }

    public void setDeliverCarts(Set<Cart> deliverCarts) {
        this.delivererCarts = deliverCarts;
    }
}
