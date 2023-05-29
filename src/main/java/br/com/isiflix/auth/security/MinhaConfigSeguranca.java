package br.com.isiflix.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MinhaConfigSeguranca {
    //@Bean: Esta anotação indica que o método configure está sendo usado para configurar um bean gerenciado pelo contêiner Spring.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Este é um método que configura as regras de segurança do Spring Security.
        // Ele recebe um objeto HttpSecurity que permite configurar a segurança HTTP.
        http.csrf().disable()
                .authorizeHttpRequests(authorize -> authorize//Este método configura as regras de autorização para as requisições HTTP.
                        .requestMatchers(HttpMethod.GET, "/free").permitAll()//aqui seleciona quais requisições serão liberadas
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .anyRequest().authenticated());//aqui as requisiçõe serão passeiveis de autenticação
        //nesse método abaixo estamos definindo a ordem dos filtros, podem haver um ou mais, então temos que especificar a ordem.
        http.addFilterBefore(new MeuFiltro(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }


//            //Versão 6 (a partir do Spring Security 6):
//            @Bean
//            public SecurityFilterChain config6(HttpSecurity http) throws Exception {
//                http.authorizeHttpRequests((authorize) -> authorize
//                                .requestMatchers("/").permitAll()
//                                .requestMatchers("/user/cadastro").hasAuthority("ADMIN")
//                                .anyRequest().authenticated()
//                ).formLogin( (form) -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/", true)
//                        .failureUrl("/login-error")
//                        .permitAll()
//                ).logout( (logout) -> logout
//                        .logoutSuccessUrl("/")
//                        .deleteCookies("JSESSIONID")
//                ).exceptionHandling( (ex) -> ex
//                        .accessDeniedPage("/negado")
//                );
//
//                return http.build();
}
   