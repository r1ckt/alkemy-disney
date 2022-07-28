package com.alkemy.disney.auth.filter;

import com.alkemy.disney.auth.service.JwtUtils;
import com.alkemy.disney.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private UserDetailsCustomService userDetailsCustomService;

    private JwtUtils jwtUtil;


    @Autowired
    public JwtRequestFilter(UserDetailsCustomService userDetailsCustomService,
                            JwtUtils jwtUtil) {

        this.userDetailsCustomService = userDetailsCustomService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException,
                                                                    IOException {

        final String authenticationHeader = request.getHeader("Authentication");

        String username = null;
        String jwt = null;

        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {

            jwt = authenticationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);

        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsCustomService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authReq =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authReq.setDetails(authReq);
                SecurityContextHolder.getContext().setAuthentication(authReq);
            }
        }
        filterChain.doFilter(request, response);

    }


}