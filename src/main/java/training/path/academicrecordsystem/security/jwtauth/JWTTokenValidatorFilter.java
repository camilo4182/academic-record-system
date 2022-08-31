package training.path.academicrecordsystem.security.jwtauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import training.path.academicrecordsystem.exceptions.response.ExceptionResponse;
import training.path.academicrecordsystem.security.interfaces.SecurityConstants;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    private static final Logger log = Logger.getLogger(JWTTokenValidatorFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, JwtException {
        String authorizationHeader = request.getHeader(SecurityConstants.JWT_HEADER);
        if (!Objects.isNull(authorizationHeader)) {
            try {
                String jwt = authorizationHeader.substring(7);
                SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String username = String.valueOf(claims.get("username"));
                String authorities = (String) claims.get("authorities");
                Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(auth);
                filterChain.doFilter(request, response);
            } catch (SignatureException e) {
                handleJwtExceptions(request, response, HttpStatus.INTERNAL_SERVER_ERROR,  "Signature failure");
                log.info("Handled SignatureException - Signature failed");
            } catch (MalformedJwtException e) {
                handleJwtExceptions(request, response, HttpStatus.BAD_REQUEST,  "Malformed JWT token");
                log.info("Handled MalformedJwtException - Malformed JWT token");
            } catch (ExpiredJwtException e) {
                handleJwtExceptions(request, response, HttpStatus.BAD_REQUEST,  "Token has expired");
                log.info("Handled ExpiredJwtException - Token has expired");
            }
        }
        else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Objects.equals(request.getServletPath(), "/login");
    }

    private void handleJwtExceptions(HttpServletRequest request, HttpServletResponse response, HttpStatus httpStatus, String message)
            throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus.value());
        ObjectMapper mapper = new ObjectMapper();
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED, message, List.of());
        mapper.writeValue(response.getOutputStream(), exceptionResponse);
    }

}
