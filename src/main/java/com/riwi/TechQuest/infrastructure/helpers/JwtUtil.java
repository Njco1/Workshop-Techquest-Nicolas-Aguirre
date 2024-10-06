package com.riwi.TechQuest.infrastructure.helpers;

import com.riwi.TechQuest.domain.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey; // Clave secreta para firmar el JWT

    @Value("${jwt.expiration}")
    private Long jwtExpiration; // Tiempo de expiración del JWT

    // Método para obtener la clave de firma
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(this.secretKey.getBytes()); // Crear la clave HMAC a partir de la clave secreta
    }

    // Método para generar el token JWT
    public String generateToken(User user) {
        return Jwts.builder()
                .addClaims(Map.of(
                        "id", user.getId(),
                        "role", user.getRole(),
                        "name", user.getName()
                ))
                .setSubject(user.getEmail()) // Usar el email como sujeto
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Método para extraer el nombre de usuario del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Extraer el sujeto del token
    }

    // Método para validar el token
    public boolean validateToken(String token, String email) {
        return (email.equals(extractUsername(token)) && !isTokenExpired(token)); // Comprobar si el token es válido
    }

    // Método para comprobar si el token ha expirado
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date()); // Verificar la fecha de expiración
    }

    // Método genérico para extraer una reclamación del token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token)); // Aplicar el extractor a las reclamaciones
    }

    // Método para extraer todas las reclamaciones del token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey()) // Establecer la clave de firma
                .build()
                .parseClaimsJws(token) // Analizar el token
                .getBody(); // Devolver el cuerpo de las reclamaciones
    }
}
