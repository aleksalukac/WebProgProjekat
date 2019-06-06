package uns.ac.rs.prodavnica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.prodavnica.entity.Logged;
import uns.ac.rs.prodavnica.repository.LoggedRepository;

import java.util.List;


public interface LoggedService
{

    Logged findOne();

    Logged save(Logged cr);
    void deleteAll();
    public Logged getCurrentUser();

}
