package br.csi.apitodolist.controller;

import br.csi.apitodolist.infra.security.TokenServiceJWT;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final TokenServiceJWT tokenServiceJWT;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenServiceJWT tokenServiceJWT) {
        this.authenticationManager = authenticationManager;
        this.tokenServiceJWT = tokenServiceJWT;
    }

    @PostMapping
    public ResponseEntity makeLogin(@RequestBody @Valid AuthenticationData authenticationData){
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationData.username(), authenticationData.password());
            Authentication at = authenticationManager.authenticate(authentication);

            User user = (User) at.getPrincipal();
            String token = this.tokenServiceJWT.getToken(user);

            return ResponseEntity.ok().body(new TokenJWTData(token));
        } catch (Exception error) {
            error.printStackTrace();
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    private record AuthenticationData(String username, String password) {}

    private record TokenJWTData (String token) {}
}