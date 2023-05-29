package br.com.isiflix.auth.security;

import br.com.isiflix.auth.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Collections;
import java.util.Date;


public class TokenConfig {

    //esse método faz a validação do token enviado na requisição, primeiramente criamoss um token
    //depois codificamos e por fim decotificamos e validamos se o token esta em conformidade com as regras


    private static final String EMISSOR = "IsiFlix";
    private static final String TOKEN_HEADER = "Bearer";
    private static final String TOKEN_KEY = "01234567890123456789012345678901";
    private static final long UM_SEGUNDO = 1000;
    private static final long UM_MINUTO = 20 * UM_SEGUNDO;

    public static AuthToken codificarToken(Usuario usuario) {

        Key codificarToken = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes());

        String tokenJWT = Jwts.builder()
                .setSubject(usuario.getLogin())
                .setIssuer(EMISSOR)
                .setExpiration(new Date(System.currentTimeMillis() + UM_MINUTO))
                .signWith(codificarToken, SignatureAlgorithm.HS256)
                .compact();
        AuthToken authToken = new AuthToken(TOKEN_HEADER + tokenJWT);
        return authToken;

    }


    public static Authentication decodificarToken(HttpServletRequest request) {

        try {
            String jwtToken = request.getHeader("Authorization");
            jwtToken = jwtToken.replace(TOKEN_HEADER, "");

            //fará o leitura do token
            Jws<Claims> jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(TOKEN_KEY.getBytes())
                    .build().parseClaimsJws(jwtToken);

            //extraindo as informações do token
            String usuario = jwsClaims.getBody().getSubject();
            String emissor = jwsClaims.getBody().getIssuer();
            Date validade = jwsClaims.getBody().getExpiration();

            if (usuario.length() > 0 && emissor.equals(EMISSOR) && validade.after(new Date(System.currentTimeMillis()))) {

                //caso a requisição tenha cabeçalho correto,
                // aqui será gerado um token interno com as informações que for considerada relevantes.
                return new UsernamePasswordAuthenticationToken("user",//nome do usuario, pode ser userName
                        null,//quais as credencias que o usuario pode ter: usuario comum, admin
                        Collections.emptyList());//aqui podemos listar os endpoints que o usuario terá acesso

            }

        } catch (Exception e) {
            System.out.println("DEBUG - Erro ao decodificar Token");
        }
        return null;
    }
}
