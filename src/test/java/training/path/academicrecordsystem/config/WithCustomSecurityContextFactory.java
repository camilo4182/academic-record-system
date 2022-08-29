package training.path.academicrecordsystem.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import training.path.academicrecordsystem.model.Administrator;
import training.path.academicrecordsystem.model.User;
import training.path.academicrecordsystem.security.userdetails.AcademicUserDetails;

import java.util.List;

public class WithCustomSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        AcademicUserDetails principal = new AcademicUserDetails(
                annotation.username(),
                annotation.password(),
                List.of(new SimpleGrantedAuthority(annotation.authorities()[0]))
        );
        Authentication auth = UsernamePasswordAuthenticationToken.authenticated(principal, "12345678", principal.getAuthorities());
        securityContext.setAuthentication(auth);
        return securityContext;
    }

}
