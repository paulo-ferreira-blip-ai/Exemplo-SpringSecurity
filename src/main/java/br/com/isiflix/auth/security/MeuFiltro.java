package br.com.isiflix.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class MeuFiltro extends OncePerRequestFilter {

    //Esta classe é responsavel por configurar os parametros de segurança que as solicitações aos endpoints da APi irão receber
    //O Spring despacha cada solicitação para seu devido endpoint, antes disso, esse filtro fará
    //uma verificação nas credencias do usuario para liberar o acesso
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Debug Requisição passou pelo filtro");


        if (request.getHeader("Authorization") != null) {
            //validaremos se a requisção tem um cabeçalho, se houver, será colocado essa msg
            //dentro da requisição e será encaminhada
            Authentication auth = TokenConfig.decodificarToken(request);

            //cabeçalho de autorização existe, preciso saber se é valido
            if (auth != null) {
                //Se o Token for valido. a requisição será enviada para o endpoint indicado que esta autenticada
                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println("Debug Requisição passou pelo Token");
            } else {
                //Tratamento simples de erro, caso haja um token
                System.out.println("Error de Token");
                ErrorDto errorDto = new ErrorDto(401, "Usuario não autorizado para este sistema");
                response.setStatus(errorDto.getStatus());
                response.setContentType("application/json");
                ObjectMapper mapper = new ObjectMapper();
                response.getWriter().print(mapper.writeValueAsString(errorDto));
                response.getWriter().flush();
                return;
            }

        }
        //passa a requisição para frente
        filterChain.doFilter(request, response);
    }
}
