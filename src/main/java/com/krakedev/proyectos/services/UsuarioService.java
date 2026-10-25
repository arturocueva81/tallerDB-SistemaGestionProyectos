package com.krakedev.proyectos.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	private final UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public Usuario registrar(Usuario usuario) {
		try {
			String passwordCifrada = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
			usuario.setPassword(passwordCifrada);
			return usuarioRepository.save(usuario);
		} catch (Exception e) {
			throw new RuntimeException("Error al registrar el usuario: " + e.getMessage());
		}
	}

	public Usuario login(String username, String password) {
		try {
			Optional<Usuario> resultado = usuarioRepository.findByUsername(username);
			
			if (resultado.isPresent()) {
				Usuario usuario = resultado.get();
				
				if (BCrypt.checkpw(password, usuario.getPassword())) {
					return usuario;
				} else {
					throw new RuntimeException("Contraseña incorrecta");
				}
			} else {
				throw new RuntimeException("Usuario no encontrado: " + username);
			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Error en el login: " + e.getMessage());
		}
	}

}
