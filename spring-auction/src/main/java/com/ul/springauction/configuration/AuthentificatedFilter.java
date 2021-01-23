package com.ul.springauction.configuration;

import com.ul.springauction.services.auth.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.catalina.connector.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class AuthentificatedFilter {

    private final JwtUtil jwtUtil;

    private static final String AUTHENTICATION = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Bearer";
    private static final String LOGIN_HEADER = "login";

    @Autowired
    public AuthentificatedFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Pointcut("@annotation(Authentificated)")
    private void AuthentificatedAnnotation() {
    }

    @Around("AuthentificatedAnnotation()")
    public Object doSomething(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();

        // Recuperation de l'entête d'authentification
        String authorizationHeader = request.getHeader(AUTHENTICATION);
        String login = request.getHeader(LOGIN_HEADER);

        // On extrait le token
        if (authorizationHeader != null && login != null) {
            String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
            // Puis on le verifie
            try {
                if (!jwtUtil.validateToken(token, login)) {
                    response.sendError(Response.SC_UNAUTHORIZED);
                }else{
                    return pjp.proceed();
                }
            } catch (Exception e) {
                response.sendError(Response.SC_UNAUTHORIZED);
            }
        } else {
            response.sendError(Response.SC_UNAUTHORIZED);
        }
        return null;
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return sra.getRequest();
    }

    private HttpServletResponse getResponse() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return sra.getResponse();
    }
}
