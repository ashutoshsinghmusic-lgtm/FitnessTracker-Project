package com.project.fitness_project.security;

import com.project.fitness_project.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            String jwt = parseJwt(request);

            if(jwt != null && jwtUtils.validateToken(jwt)){
                System.out.println("Valid Jwt : " + jwt);

                String userId = jwtUtils.getUserIdFromToken(jwt);

                Claims claims = jwtUtils.getAllClaims(jwt);
                List<String> roles = claims.get("roles" , List.class);

                List<GrantedAuthority> authorities = List.of();
                if(roles != null){
                    authorities = roles.stream()
                                .map(role->(GrantedAuthority) new SimpleGrantedAuthority(role))
                                .toList();
                }

                CustomUserDetails customUserDetails = new CustomUserDetails(userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User Not Found"))
                );

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(customUserDetails ,
                                null,
                                authorities);

                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request,response);
    }

    public String parseJwt(HttpServletRequest request){
        return jwtUtils.getJwtTokenFromRequest(request);
    }
}
