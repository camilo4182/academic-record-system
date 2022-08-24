package training.path.academicrecordsystem.controllers.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import training.path.academicrecordsystem.controllers.dtos.AuthenticationRequest;
import training.path.academicrecordsystem.controllers.dtos.AuthenticationResponse;
import training.path.academicrecordsystem.controllers.interfaces.IMainController;
import training.path.academicrecordsystem.security.AcademicUserDetailsService;
import training.path.academicrecordsystem.security.AcademicUserPwdAuthenticationProvider;
import training.path.academicrecordsystem.security.JWTTokenGeneratorService;

@Profile("dev")
@RestController
@RequiredArgsConstructor
public class MainController implements IMainController {

    private final AcademicUserPwdAuthenticationProvider authenticationProvider;
    private final JWTTokenGeneratorService jwtService;
    private final AcademicUserDetailsService service;

    @Override
    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication responseToken = authenticationProvider.authenticate(authenticationToken);
        UserDetails userDetails = service.loadUserByUsername(responseToken.getName());
        String token = jwtService.generateJWT(userDetails);
        return new ResponseEntity<>(new AuthenticationResponse(token), HttpStatus.OK);
    }

}
