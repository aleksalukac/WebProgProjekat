package uns.ac.rs.prodavnica.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/*enum CartStatus {
	BOUGHT, SHIPPING, CANCELED, DELIVERED
}*/

@Entity
public class Cart implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CartArticle> CartArticles = new HashSet<>();

	@Column
	private Date datetime;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User customer;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User deliver;

	@Column
	private CartStatus status;

	public Cart() {

	}

	public Set<CartArticle> getCartArticles() {
		return CartArticles;
	}

	public void setCartArticles(Set<CartArticle> CartArticles) {
		this.CartArticles = CartArticles;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public User getBuyer() {
		return customer;
	}

	public void setBuyer(User customer) {
		this.customer = customer;
	}

	public User getDeliver() {
		return deliver;
	}

	public void setDeliver(User deliver) {
		this.deliver = deliver;
	}

	public CartStatus getStatus() {
		return status;
	}

	public void setStatus(CartStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Cart [CartArticles=" + CartArticles + ", datetime=" + datetime + ", customer=" + customer + ", deliver=" + deliver
				+ ", status=" + status + "]";
	}
}
