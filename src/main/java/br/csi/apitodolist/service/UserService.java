package br.csi.apitodolist.service;

import br.csi.apitodolist.model.user.User;
import br.csi.apitodolist.model.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService (UserRepository repository){
        this.repository = repository;
    }

    public void create(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        this.repository.save(user);
    }
}
