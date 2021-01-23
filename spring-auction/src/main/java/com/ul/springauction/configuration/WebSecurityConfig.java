package com.ul.springauction.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration de sécurité de l'application
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    // Crée un bean pour le hachage du mot de passe de l'utilisateur
    @Bean
    public BCryptPasswordEncoder createBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    // Créé un bean pour l'authentification d'un utilisateur
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    // Configuration de la sécrutié
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // Configure Spring Security pour autorisé tout les cors
        http.cors();

        // On ajoute la sécurité de vérifier le token a chaque requête
        http.headers().frameOptions().disable();
    }
}
