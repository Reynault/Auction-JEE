package com.ul.springauction.configuration;

import com.ul.springauction.services.auth.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration de sécurité de l'application
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtRequestFilter requestFilter;


    // Crée un bean pour le hachage du mot de passe de l'utilisateur
    @Bean
    public BCryptPasswordEncoder createBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    // Créé un bean pour l'authentification d'un utilisateur
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    // Configuration de la sécrutié
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors(); // Configure Spring Security pour autorisé tout les cors
        // Les utilisateurs non connecter ont seulement le droit a l'inscription et connexion
        // Toutes les autres routes sont bloquées s'il n'y a pas de token
        http.authorizeRequests().antMatchers("/register", "/login").permitAll()
                .antMatchers("/auction/articles/**", "/auction/participation/**").authenticated()
                .anyRequest().permitAll();

        // On ajoute la sécurité de vérifier le token a chaque requête
        http.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();
    }
}
