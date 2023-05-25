package br.com.isiflix.auth.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;

public class TokenConfig {

    //esse método faz a validação do token enviado na requisição, primeiramente criamoss um token
    //depois codificamos e por fim decotificamos e validamos se o token esta em conformidade com as regras
    public static Authentication codificarToken(HttpServletRequest request){
        if (request.getHeader("Authorization").equals("Bearer *paulo123")){//caso a requisição tenha cabeçalho correto,
            // aqui será gerado um token interno com as informações que for considerada relevantes.
            return new UsernamePasswordAuthenticationToken("user",//nome do usuario, pode ser userName
                    null,//quais as credencias que o usuario pode ter, usuario comum, admin
                    Collections.emptyList());//aqui podemos listar os endpoints que o usuario terá acesso

        }
        return null;


    }
}
