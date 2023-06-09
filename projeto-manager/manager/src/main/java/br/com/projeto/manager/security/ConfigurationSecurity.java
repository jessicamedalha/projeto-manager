package br.com.projeto.manager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
*  CONFIGURAÇÕES PARA A AUTENTICAÇÃO DE USUÁRIOS
*
* */

@Configuration
@EnableWebSecurity
public class ConfigurationSecurity {

    /* Politicas de segurança: o http request é sujeito a permições de restrição de acesso*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/usuario/autenticar")
                .permitAll()
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/usuario/renovar-ticket")
                .permitAll()
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/pais/listar")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
