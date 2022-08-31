package training.path.academicrecordsystem.security.jwtauth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import training.path.academicrecordsystem.security.interfaces.SecurityConstants;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;

@Service
public class JWTTokenGeneratorService {

    private static final int EXPIRATION_TIME = 60000;
    private final Logger log = Logger.getLogger(JWTTokenGeneratorService.class.getName());

    public String generateJWT(UserDetails authentication) {
        if (!Objects.isNull(authentication)) {
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

            String jwt = Jwts.builder().setIssuer("University Record System").setSubject("JWT Token")
                    .claim("username", authentication.getUsername())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + EXPIRATION_TIME))
                    .signWith(key)
                    .compact();

            log.info("User %s signed in at %s".formatted(authentication.getUsername(), new Date()));

            return jwt;
        }
        return "";
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

    /*

    private static final String AUTHORITIES = "authorities";
    private final String SECRET_KEY;
    private final Key key;

    public JwtService(String SECRET_KEY) {
        this.SECRET_KEY = Base64.getEncoder().encodeToString("key".getBytes());
        this.key = new SecretKeySpec(this.SECRET_KEY.getBytes(), "AES");
    }

    public String createToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES, authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean hasTokenExpired(String token) {
        return Jwts.parserBuilder()
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (userDetails.getUsername().equals(username) && !hasTokenExpired(token));

    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String token) {
        Claims claims = Jwts.parserBuilder().build().parseClaimsJws(token).getBody();
        return (Collection<? extends GrantedAuthority>) claims.get(AUTHORITIES);
    }
    */
}
