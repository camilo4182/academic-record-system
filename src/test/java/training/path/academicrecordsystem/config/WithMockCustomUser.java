package training.path.academicrecordsystem.config;

import org.springframework.security.test.context.support.WithSecurityContext;
import training.path.academicrecordsystem.security.interfaces.IRoles;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String username() default "admin1.test1";

    String password() default "12345678";

    String[] authorities() default {IRoles.ADMIN};

}
