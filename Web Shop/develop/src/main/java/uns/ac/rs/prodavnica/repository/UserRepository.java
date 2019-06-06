package uns.ac.rs.prodavnica.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uns.ac.rs.prodavnica.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAllByRoleOrderByFirstName(String role);
	
	List<User> findByFirstNameOrLastName(String firstName, String lastName);
	
	List<User> findByFirstNameIgnoreCase(String firstName);

	List<User> findByUsernameIgnoreCase(String username);
	
	User findByUsernameAndPassword(String username, String password);
	
	User findOneByUsername(String username);
	
	
}
