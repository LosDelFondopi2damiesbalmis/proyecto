package auth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import peluposbd.TokenVerificado;
import peluposbd.TokenVerificadoJpaController;
import java.util.Date;
public class JwtUtil {

    // Clave secreta para firmar el JWT
    private static final SecretKey SECRET_KEY = generateSecretKey();
    
    private static final String PERSISTENCE_UNIT = "ApiRestPeluPosPU";

    // Generar la clave secreta HMAC-SHA256
    private static SecretKey generateSecretKey() {
        try {
            // Crear un generador de claves para HMAC-SHA256
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(256); // Tamaño de la clave
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generando la clave secreta", e);
        }
    }

    // Generar el token JWT con rol
    public static String generarToken(String idusuario, String rol) {
        // No vamos ha hacer que el token expire porque entonces necesitaríamos un
        // sistema de doble Token Access (Expitable a los 10 min) y Refresh sin expiración
        // y comprobable a través de una caché de tokens válidos.
         long tiempo = System.currentTimeMillis();
       //  final long EXPIRACION_EN_MILISEGUNDOS = 1000 * 60;
        
        String token = Jwts.builder()
                .subject(idusuario)
                .issuedAt(new Date(tiempo))
             //   .expiration(new Date(tiempo + EXPIRACION_EN_MILISEGUNDOS))
                .claim("rol", rol)
                .signWith(SECRET_KEY, Jwts.SIG.HS256)
                .compact();
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            TokenVerificadoJpaController dao = new TokenVerificadoJpaController(emf);
            TokenVerificado tokenBD = new TokenVerificado(token);
            TokenVerificado tokenBDEncontrado = dao.findTokenVerificado(token);
            if(tokenBDEncontrado != null)
            {
                return null;
            }
            else
            {
                dao.create(tokenBD);
            }
            
        } catch (Exception ex) {
            
        }
        return token;
    }

    // Verificar y obtener el subject del token (en este caso, el idusuario)
    public static String getIdUsuarioFromToken(String token) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TokenVerificadoJpaController dao = new TokenVerificadoJpaController(emf);
            if (dao.findTokenVerificado(token) == null) {
                return null; // Token invalidado
            }            
            return Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();  // Obtener el id de usuario
        } catch (JwtException ex) {
            return null;
        }
    }
    
    // Obtener el rol del token
    public static String getRolFromToken(String token) {
        try {
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TokenVerificadoJpaController dao = new TokenVerificadoJpaController(emf);
            if (dao.findTokenVerificado(token) == null) {
                return null; // Token invalidado
            }
            Claims claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("rol", String.class);
        } catch (JwtException ex) {
            return null;
        }
    }
    
    // Invalidar un token (logout)
    public static void invalidarToken(String token) {
        try {
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TokenVerificadoJpaController dao = new TokenVerificadoJpaController(emf);
            dao.destroy(token);
        }catch(Exception ex)
        {
            
        }
    }
    
    // Verificar si un token es válido (no está en lista negra)
    public static boolean isTokenValido(String token) {
         try {
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            TokenVerificadoJpaController dao = new TokenVerificadoJpaController(emf);
            return dao.findTokenVerificado(token) != null && getIdUsuarioFromToken(token) != null;
        }catch(Exception ex)
        {
            
        }
        return false;
    }
}
