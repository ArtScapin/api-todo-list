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
    public ResponseEntity<UserData> store(@RequestBody @Valid User user){
        user.setPermission("USER");
        this.service.create(user);
        return ResponseEntity.status(201).body(new UserData(user));
    }

    @GetMapping("/me")
    public ResponseEntity<UserData> findMyData() {
        UserData user = new UserData(this.service.getAuthenticatedUser());
        return ResponseEntity.status(200).body(user);
    }

    @GetMapping
    public ResponseEntity<List<UserData>>findAll(){
        return ResponseEntity.status(200).body(this.service.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserData> findById(@PathVariable Long id){
        UserData user = this.service.findUser(id);
        return ResponseEntity.status(200).body(user);
    }
}
