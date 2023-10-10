package br.csi.apitodolist.service;

import br.csi.apitodolist.model.user.User;
import br.csi.apitodolist.model.user.UserData;
import br.csi.apitodolist.model.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.repository.findByUsername(authentication.getName());
        return user;
    }

    public List<User> findAllUsers(){
        return this.repository.findAll();
    }

    public UserData update(User user) {
        User authUser = this.getAuthenticatedUser();
        if(user.getName() != null) {
            authUser.setName(user.getName());
        }
        if(user.getPassword() != null) {
            authUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        this.repository.save(authUser);
        return new UserData(authUser);
    }

    public void delete() {
        User user = this.getAuthenticatedUser();
        this.repository.delete(user);
    }

    public UserData updatePermission(Long id) {
        User user = this.repository.findById(id).orElse(null);
        user.setPermission("ADM");
        return new UserData(user);
    }
}
