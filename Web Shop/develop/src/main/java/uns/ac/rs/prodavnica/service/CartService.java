package uns.ac.rs.prodavnica.service;


import uns.ac.rs.prodavnica.entity.Cart;

import java.util.List;

public interface CartService {

    Cart addNew(Cart cart);

    Cart create(Cart cart) throws Exception;;

    Cart update(Cart cart) throws Exception;

    Cart findOne(Long id);

    List<Cart> findAll();

}
