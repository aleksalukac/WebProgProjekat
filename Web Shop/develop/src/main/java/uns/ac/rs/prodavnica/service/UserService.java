package uns.ac.rs.prodavnica.service;

import java.util.List;

import uns.ac.rs.prodavnica.entity.User;


public interface UserService {

	User create(User user) throws Exception;

    User findOne(Long id);

    User findOne(String username);

    User update(User user) throws Exception;

    void delete(Long id);

    List<User> findAll();
    User login(String username, String password);
    User registration(User user);    
    User findOneByUsername(String username);

}