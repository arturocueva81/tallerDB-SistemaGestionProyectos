package com.krakedev.proyectos.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.krakedev.proyectos.services.TokenBlacklistService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final TokenBlacklistService blacklistService;
	
	public JwtAuthenticationFilter(TokenBlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }
	
	@Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring(7);

            if (blacklistService.estaInvalidado(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token invalidado. Inicia sesión nuevamente.");
                return;
            }

            String username = JwtUtil.obtenerUsername(token);
            String rol = JwtUtil.obtenerRol(token);
            SimpleGrantedAuthority autoridad = new SimpleGrantedAuthority("ROLE_" + rol);

            UsernamePasswordAuthenticationToken autenticacion = new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(autoridad));
            SecurityContextHolder.getContext().setAuthentication(autenticacion);

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido o expirado: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
