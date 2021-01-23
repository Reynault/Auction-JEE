package com.ul.springauction.services.auth;

import com.ul.springauction.DAO.repository.UserRepository;
import com.ul.springauction.services.DeliveryService;
import com.ul.springauction.services.user.UserDetailService;
import com.ul.springauction.services.user.UserService;
import com.ul.springauction.shared.exception.BadRequestException;
import com.ul.springauction.shared.response.ErrorResponse;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import shared.ErrorMessageManager;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Component définit afin de trier les requêtes et de n'autoriser que les utilisateurs connectés
 * a communiquer avec les services
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String uri = request.getRequestURI();
        System.out.println(uri);

        String username = null, jwt = null;


        if (header != null && header.startsWith("Bearer ")){
            jwt = header.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                // a voir quoi mettre
                SecurityContextHolder.clearContext();
            }
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            User u = userRepository.findByLogin(username);
            UserDetails userDetails = null;
            try {
                userDetails = this.userDetailService.loadUserByUsername(username);
            } catch (Exception e){
                // a voir quoi mettre
                SecurityContextHolder.clearContext();
            }
            if (userDetails != null && u != null) {
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                } else {
                    // a voir quoi mettre
                    SecurityContextHolder.clearContext();
                }
            } else {
                // a voir quoi mettre
                SecurityContextHolder.clearContext();
            }
        }

        if (SecurityContextHolder.getContext() == null && !uri.equals("/auction/login") && !uri.equals("/auction/register")){
            // ne se déclanche pas tout le temps
            response.sendError(401, "Unauthorized User");
        }
        chain.doFilter(request, response); // va crée des erreurs 500 que j'ai gérer plus loin dans UserService en BadRequest missing value
    }
}