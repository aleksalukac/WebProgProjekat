package uns.ac.rs.prodavnica.entity;
import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

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

	@Column
	private Date datetime;

	@Column
	private int price;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Customer customer;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Deliverer deliverer;

	@ManyToMany(mappedBy = "carts",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Article> articles = new ArrayList<>();

	@Column
	private CartStatus status;

	public Cart() {

	}

	public void generatePrice()
	{
		for(Article article : articles)
			this.price += article.getPrice();
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Deliverer getDeliverer() {
		return deliverer;
	}

	public void setDeliverer(Deliverer deliverer) {
		this.deliverer = deliverer;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public CartStatus getStatus() {
		return status;
	}

	public void setStatus(CartStatus status) {
		this.status = status;
	}
}
