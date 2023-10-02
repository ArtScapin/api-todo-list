package br.csi.apitodolist.service;

import br.csi.apitodolist.model.user.User;
import br.csi.apitodolist.model.user.UserData;
import br.csi.apitodolist.model.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public UserData findUser(Long id){
        User user = this.repository.getReferenceById(id);
        return new UserData(user);
    }

    public User findUser(String username){
        User user = this.repository.findByUsername(username);
        return user;
    }

    public List<UserData> findAllUsers(){
        return this.repository.findAll().stream().map(UserData::new).toList();
    }
}
