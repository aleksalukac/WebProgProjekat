package uns.ac.rs.prodavnica.entity;

import java.io.Serializable;
//import java.util.HashSet;
//import java.util.Set;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.OneToMany;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/*public enum Role {
	ADMIN, DELIVERER, CUSTOMER
}*/

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class User implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column
    protected String username;

    @Column
    protected String password;
    @Column
    protected String firstName;

    @Column
    protected String lastName;
    
    @Column
    protected Role role;

    @Column
    protected String telephone;
    
    @Column
    protected String email;
    
    @Column
    protected String address;
    /*
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> historyItems = new HashSet<>();
    
    @OneToMany(mappedBy = "buyer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cart> buyerCarts = new HashSet<>();
    
    @OneToMany(mappedBy = "favUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> favoriteItems = new HashSet<>();
    
    @OneToMany(mappedBy = "deliver", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cart> deliverCarts = new HashSet<>();    
    */
    public User() {
    	
    }

	public User(Long id, String username, String password, String firstName, String lastName, Role role, String telephone, String email, String address) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.telephone = telephone;
		this.email = email;
		this.address = address;
	}

	public User(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.role = user.getRole();
		this.telephone = user.getTelephone();
		this.email = user.getEmail();
		this.address = user.getAddress();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	/*
	public Set<Item> getHistoryItems() {
		return historyItems;
	}

	public void setHistoryItems(Set<Item> historyItems) {
		this.historyItems = historyItems;
	}

	public Set<Cart> getBuyerCarts() {
		return buyerCarts;
	}

	public void setBuyerCarts(Set<Cart> buyerCarts) {
		this.buyerCarts = buyerCarts;
	}

	public Set<Item> getFavoriteItems() {
		return favoriteItems;
	}

	public void setFavoriteItems(Set<Item> favoriteItems) {
		this.favoriteItems = favoriteItems;
	}

	public Set<Cart> getDeliverCarts() {
		return deliverCarts;
	}

	public void setDeliverCarts(Set<Cart> deliverCarts) {
		this.deliverCarts = deliverCarts;
	}
*/

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", role=" + role +
				", telephone='" + telephone + '\'' +
				", email='" + email + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}