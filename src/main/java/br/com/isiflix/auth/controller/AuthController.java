package br.com.isiflix.auth.controller;

import br.com.isiflix.auth.model.Usuario;
import br.com.isiflix.auth.security.AuthToken;
import br.com.isiflix.auth.security.TokenConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {


    @GetMapping(path = "/free")
    public String sayFreeHello() {
        return "Este endpoint liberdado pela nossa API";
    }

    @GetMapping(path = "/auth")
    public String sayAuthHello() {
        return "Este é um endpoint que precisa de autenticação";
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthToken> realizarLogin(@RequestBody Usuario usuario) {
        if (usuario.getLogin().equals("paulo123") && usuario.getSenha().equals("12345")) {
            return ResponseEntity.ok(TokenConfig.codificarToken(usuario));
        }
        return ResponseEntity.status(403).build();
    }


}
