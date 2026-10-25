package com.krakedev.proyectos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.security.JwtUtil;
import com.krakedev.proyectos.services.TokenBlacklistService;
import com.krakedev.proyectos.services.UsuarioService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	//controlador para gestionar los roles (registrar y login)
	private final UsuarioService usuarioService;
	private final TokenBlacklistService blacklistService;

	public AuthController(UsuarioService usuarioService, TokenBlacklistService blacklistService) {
		this.usuarioService = usuarioService;
		this.blacklistService = blacklistService;
	}

	@PostMapping("/registrar")
	public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
		try {
			Usuario registrado = usuarioService.registrar(usuario);
			registrado.setPassword("*****");
			return ResponseEntity.ok(registrado);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Error al registrar: " + e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Usuario usuario) {
		try {
			Usuario encontrado = usuarioService.login(usuario.getUsername(), usuario.getPassword());
			//encontrado.setPassword("*****");
			//return ResponseEntity.ok(encontrado);
			String token = JwtUtil.generarToken(encontrado.getUsername(), encontrado.getRol());
            return ResponseEntity.ok("Bearer " + token);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Error en el login: " + e.getMessage());
		}
	}
	
	@PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body("Token no proporcionado o formato incorrecto. Use: Bearer <TOKEN>");
            }

            String token = authHeader.substring(7);
            JwtUtil.verificarToken(token);
            blacklistService.invalidarToken(token);

            return ResponseEntity.ok("Sesión cerrada correctamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al cerrar sesión: " + e.getMessage());
        }
    }
	
	@GetMapping("/perfil")
    public ResponseEntity<?> perfil(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body("Token no proporcionado o formato incorrecto.");
            }

            String token = authHeader.substring(7);

            if (blacklistService.estaInvalidado(token)) {
                return ResponseEntity.status(401).body("Token invalidado. Inicia sesión nuevamente.");
            }

            String username = JwtUtil.obtenerUsername(token);
            String rol = JwtUtil.obtenerRol(token);

            return ResponseEntity.ok("Usuario: " + username + " | Rol: " + rol);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener el perfil: " + e.getMessage());
        }
    }

}
