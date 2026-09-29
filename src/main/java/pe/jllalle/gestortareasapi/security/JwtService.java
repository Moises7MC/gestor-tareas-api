package pe.jllalle.gestortareasapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.jllalle.gestortareasapi.entity.Usuario;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    //Esta parte convierte el texto en una clave criptográfica que Java/JJWT puede utilizar para firmar el token.
    private SecretKey obtenerClave() {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8) //Convierte el STRING en bytes.
        );
    }

    public String generarToken(Usuario usuario) {
        return Jwts.builder()
                .subject(usuario.getEmail()) //El sub normalmente identifica a quién pertenece el token | El usuario se identifica mediante su email.
                .claim("rol", usuario.getRol().name()) //Dato Personalizado "ADMIN".
                .issuedAt(new Date())//Indica cuándo fue creado el token. iat significa Issued At(Fecha de emisión), es decir: Emitido en...
                .expiration(new Date(System.currentTimeMillis() + expiration))//cuándo va a caducar el token, Fecha de expiración.
                .signWith(obtenerClave()) //Firmando el JWT utilizando la clave secreta, "¿Este token realmente fue generado por mi aplicación y no fue alterado?"
                .compact();//Finalmente convierte toda esa información en el JWT que normalmente ves como una cadena larga: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtb2NoZUBnbWFpbC5jb20iLCJyb2wiOiJBRE1JTiJ9.xxxxxxxxx
    }

    public String extraerEmail(String token) {
        return extraerClaims(token).getSubject();
    }

    public boolean esTokenValido(String token, String email) {
        return extraerEmail(token).equals(email) && !estaExpirado(token);
    }

    private boolean estaExpirado(String token) {
        return extraerClaims(token).getExpiration().before(new Date());
    }

    private Claims extraerClaims(String token) {
        return Jwts.parser()
                .verifyWith(obtenerClave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

