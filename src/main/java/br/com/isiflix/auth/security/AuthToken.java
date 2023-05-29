package br.com.isiflix.auth.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {

    private String token;

    public AuthToken(AuthToken authToken) {

    }
}



