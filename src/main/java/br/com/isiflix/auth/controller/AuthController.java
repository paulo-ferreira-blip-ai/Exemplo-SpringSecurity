package br.com.isiflix.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {



    @GetMapping(path = "/free")
    public String sayFreeHello(){
        return "Este endpoint liberdado pela nossa API";
    }
    @GetMapping(path = "/auth")
    public String sayAuthHello(){
        return "Este é um endpoint que precisa de autenticação";
    }

}
