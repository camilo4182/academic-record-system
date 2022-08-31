package training.path.academicrecordsystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import training.path.academicrecordsystem.security.exceptionshandlers.CustomAccessDeniedHandler;
import training.path.academicrecordsystem.security.interfaces.IRoles;
import training.path.academicrecordsystem.security.interfaces.SecurityConstants;
import training.path.academicrecordsystem.security.jwtauth.JWTTokenValidatorFilter;

import java.util.Collections;
import java.util.List;

@Profile("dev")
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Autowired
    @Qualifier("restAuthenticationEntryPoint")
    AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .cors().and().csrf().disable()
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .mvcMatchers(HttpMethod.POST,"/login").permitAll()
                        .mvcMatchers(HttpMethod.POST, "/careers").hasRole(IRoles.ADMIN)
                        .mvcMatchers(HttpMethod.PUT, "/careers/**").hasRole(IRoles.ADMIN)
                        .mvcMatchers(HttpMethod.DELETE, "/careers/**").hasRole(IRoles.ADMIN)
                        .mvcMatchers(HttpMethod.GET, "/courses/classes/*").authenticated()
                        .mvcMatchers(HttpMethod.POST, "/courses").hasAnyRole(IRoles.ADMIN, IRoles.PROFESSOR)
                        .mvcMatchers(HttpMethod.PUT, "/courses/**").hasRole(IRoles.ADMIN)
                        .mvcMatchers(HttpMethod.DELETE, "/courses/**").hasRole(IRoles.ADMIN)
                        .mvcMatchers(HttpMethod.POST, "/classes/enroll/*").hasRole(IRoles.STUDENT)
                        .mvcMatchers(HttpMethod.GET, "/classes").authenticated()
                        .mvcMatchers(HttpMethod.POST, "/classes").hasRole(IRoles.ADMIN)
                        .mvcMatchers(HttpMethod.PUT, "/classes/*").hasRole(IRoles.ADMIN)
                        .mvcMatchers(HttpMethod.GET, "/students").authenticated()
                        .mvcMatchers(HttpMethod.POST, "/students").hasRole(IRoles.ADMIN)
                        .mvcMatchers(HttpMethod.PUT, "/students/*").hasRole(IRoles.ADMIN)
                        .mvcMatchers(HttpMethod.GET, "/students/profile/**").authenticated()
                        .mvcMatchers(HttpMethod.PUT, "/students/profile/**").hasRole(IRoles.STUDENT)
                        .mvcMatchers(HttpMethod.GET, "/students/enrollment/*").hasRole(IRoles.STUDENT)
                        .mvcMatchers(HttpMethod.DELETE, "/students/*").hasRole(IRoles.ADMIN)
                        .mvcMatchers(HttpMethod.GET, "/professors").authenticated()
                        .mvcMatchers(HttpMethod.POST, "/professors").hasRole(IRoles.ADMIN)
                        .mvcMatchers(HttpMethod.PUT, "/professors/*").hasRole(IRoles.ADMIN)
                        .mvcMatchers(HttpMethod.GET, "/professors/profile/**").authenticated()
                        .mvcMatchers(HttpMethod.PUT, "/professors/profile/**").hasRole(IRoles.PROFESSOR)
                        .mvcMatchers(HttpMethod.GET, "/professors/classes/*").hasRole(IRoles.PROFESSOR)
                        .mvcMatchers(HttpMethod.DELETE, "/professors/*").hasRole(IRoles.ADMIN)
                        .mvcMatchers(HttpMethod.GET, "/enrollments/").hasRole(IRoles.ADMIN)
                        .anyRequest().permitAll()
        ).httpBasic(Customizer.withDefaults())
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);
        return http.build();
    }

    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setExposedHeaders(List.of(SecurityConstants.JWT_HEADER));
        config.setMaxAge(3600L);
        return config;
    }

}
