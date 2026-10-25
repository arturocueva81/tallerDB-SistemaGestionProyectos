package com.krakedev.proyectos.security;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {
	private static final String SECRET = "claveSecretaSuperSegura2026KrakeDev";
	private static final long EXPIRACION = 1000 * 60 * 60 * 8;

	public static String generarToken(String username, String rol) {
		try {
			Algorithm algoritmo = Algorithm.HMAC256(SECRET);
			return JWT.create().withSubject(username).withClaim("rol", rol).withIssuedAt(new Date())
					.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRACION)).sign(algoritmo);
		} catch (Exception e) {
			throw new RuntimeException("Error al generar el token: " + e.getMessage());
		}
	}

	public static DecodedJWT verificarToken(String token) {
		try {
			Algorithm algoritmo = Algorithm.HMAC256(SECRET);
			return JWT.require(algoritmo).build().verify(token);
		} catch (JWTVerificationException e) {
			throw new RuntimeException("Token inválido o expirado: " + e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException("Error al verificar el token: " + e.getMessage());
		}
	}

	public static String obtenerUsername(String token) {
		try {
			DecodedJWT jwt = verificarToken(token);
			return jwt.getSubject();
		} catch (Exception e) {
			throw new RuntimeException("Error al obtener el username del token: " + e.getMessage());
		}
	}

	public static String obtenerRol(String token) {
		try {
			DecodedJWT jwt = verificarToken(token);
			return jwt.getClaim("rol").asString();
		} catch (Exception e) {
			throw new RuntimeException("Error al obtener el rol del token: " + e.getMessage());
		}
	}
}
