package uns.ac.rs.prodavnica.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import uns.ac.rs.prodavnica.entity.User;
import uns.ac.rs.prodavnica.repository.UserRepository;
import uns.ac.rs.prodavnica.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);


    }

    @Override
    public User findOne(Long id) {
        User user = userRepository.getOne(id);
        return user;
    }

    @Override
    public User findOne(String username) {
        List<User> users = userRepository.findByUsernameIgnoreCase(username);
        return users.get(0);
    }

    @Override
    public User update(User user) throws Exception {
        User userToUpdate = userRepository.getOne(user.getId());
        if (userToUpdate == null) {
            throw new Exception("Employee doesnt exist!");
        }

        userToUpdate.setFirstName(user.getFirstName());
        User savedUser = userRepository.save(userToUpdate);
        return savedUser;
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.getOne(id);
        userRepository.delete(user);
    }

    public User findOneByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public User create(User user) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public User registration(User user) {
        if (userRepository.findOneByUsername(user.getUsername()) != null) { //vec postoji u bazi
            return null;

        } else {
            return userRepository.save(user);
        }


    }
}
	
