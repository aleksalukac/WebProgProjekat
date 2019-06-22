package uns.ac.rs.prodavnica.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uns.ac.rs.prodavnica.entity.Cart;
import uns.ac.rs.prodavnica.repository.CartRepository;
import uns.ac.rs.prodavnica.service.CartService;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;


    @Override
    public Cart update(Cart cart) throws Exception {
        Cart cartToUpdate = cartRepository.getOne(cart.getId());
        if (cartToUpdate == null) {
            throw new Exception("Cart doesnt exist!");
        }

        cartToUpdate.setArticles(cart.getArticles());
        cartToUpdate.setDatetime(cart.getDatetime());
        cartToUpdate.setStatus(cart.getStatus());
        cartToUpdate.setCustomer(cart.getCustomer());

        Cart savedCart = cartRepository.save(cartToUpdate);
        return savedCart;
    }
}
