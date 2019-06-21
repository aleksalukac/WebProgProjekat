package uns.ac.rs.prodavnica.service;


import uns.ac.rs.prodavnica.entity.Deliverer;
import uns.ac.rs.prodavnica.entity.User;

public interface DelivererService {

    void delete(Long id);
    Deliverer addNew(User user);
}
