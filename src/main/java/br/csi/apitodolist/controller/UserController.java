package br.csi.apitodolist.controller;

import br.csi.apitodolist.model.user.User;
import br.csi.apitodolist.model.user.UserData;
import br.csi.apitodolist.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping
    @Transactional
    public ResponseEntity store(@RequestBody @Valid User user, UriComponentsBuilder uriComponentsBuilder){
        user.setPermission("USER");
        this.service.create(user);
        URI uri = uriComponentsBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @GetMapping("/{id}")
    public UserData findById(@PathVariable Long id){
        return this.service.findUser(id);
    }

    @GetMapping("/me")
    public UserData findById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.service.findUser(authentication.getName());
        return new UserData(user);
    }

    @GetMapping
    public List<UserData> findAll(){
        return this.service.findAllUsers();
    }
}
