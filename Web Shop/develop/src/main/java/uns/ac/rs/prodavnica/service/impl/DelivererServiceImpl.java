package uns.ac.rs.prodavnica.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.prodavnica.entity.Deliverer;
import uns.ac.rs.prodavnica.entity.Role;
import uns.ac.rs.prodavnica.entity.User;
import uns.ac.rs.prodavnica.repository.DelivererRepository;
import uns.ac.rs.prodavnica.service.DelivererService;

@Service
public class DelivererServiceImpl implements DelivererService {
    @Autowired
    private DelivererRepository delivererRepository;

    @Override
    public void delete(Long id) {
        Deliverer deliverer = delivererRepository.getOne(id);
        delivererRepository.delete(deliverer);
    }


    @Override
    public Deliverer addNew(User user) {
        Deliverer deliverer = new Deliverer();
        deliverer.setRole(Role.DELIVERER);
        deliverer.setFirstName(user.getFirstName());
        deliverer.setLastName(user.getLastName());
        deliverer.setPassword(user.getPassword());
        deliverer.setTelephone(user.getTelephone());
        deliverer.setAddress(user.getAddress());
        deliverer.setEmail(user.getEmail());
        deliverer.setUsername(user.getUsername());
        return delivererRepository.save(deliverer);
    }

}
