package uns.ac.rs.prodavnica.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.prodavnica.entity.Logged;
import uns.ac.rs.prodavnica.entity.User;
import uns.ac.rs.prodavnica.repository.LoggedRepository;
import uns.ac.rs.prodavnica.service.LoggedService;

import java.util.List;

@Service
public class LoggedImpl implements LoggedService {
    @Autowired
    public LoggedRepository loggedRepository;

    @Override
    public Logged save(Logged cr) {
        return loggedRepository.save(cr);

    }

    @Override
    public Logged findOne()
    {
        List<Logged> loggedList= loggedRepository.findAll();
        if ( loggedList.isEmpty()) {
            return null;
        }
        return loggedList.get(0);
    }

    @Override
    public void deleteAll() {
        loggedRepository.deleteAll();
    }

    public Logged getCurrentUser(){
        List<Logged> loggedList= loggedRepository.findAll();
        if ( loggedList.isEmpty()) {
            return null;
        }
        return loggedList.get(0);
    }
}
