package uns.ac.rs.prodavnica.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uns.ac.rs.prodavnica.entity.Cart;
import uns.ac.rs.prodavnica.repository.CartRepository;
import uns.ac.rs.prodavnica.service.CartService;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;


    @Override
    public Cart create(Cart cart) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Cart findOne(Long id) {
        Cart cart = cartRepository.getOne(id);
        return cart;
    }

    @Override
    public List<Cart> findAll() {
        List<Cart> carts = cartRepository.findAll();
        return carts;
    }


    @Override
    public Cart addNew(Cart cart) {
        Cart c = new Cart();
        c.setDatetime(cart.getDatetime());
        c.setStatus(cart.getStatus());
        c.setCustomer(cart.getCustomer());
        c.setArticles(cart.getArticles());
        c.setDeliverer(cart.getDeliverer());
        return cartRepository.save(c);
    }

    @Override
    public Cart update(Cart cart) throws Exception {
        Cart cartToUpdate = cartRepository.getOne(cart.getId());
        if (cartToUpdate == null) {

            return create(cart);
            //throw new Exception("Cart doesnt exist!");
        }

        cartToUpdate.setArticles(cart.getArticles());
        cartToUpdate.setDatetime(cart.getDatetime());
        cartToUpdate.setStatus(cart.getStatus());
        cartToUpdate.setCustomer(cart.getCustomer());

        Cart savedCart = cartRepository.save(cartToUpdate);
        return savedCart;
    }
}
