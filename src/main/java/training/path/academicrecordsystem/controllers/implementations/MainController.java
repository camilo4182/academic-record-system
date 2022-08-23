package training.path.academicrecordsystem.controllers.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import training.path.academicrecordsystem.controllers.dtos.AuthenticationRequest;
import training.path.academicrecordsystem.controllers.dtos.AuthenticationResponse;
import training.path.academicrecordsystem.controllers.interfaces.IMainController;
import training.path.academicrecordsystem.security.AcademicUserPwdAuthenticationProvider;

@RestController
@RequiredArgsConstructor
public class MainController implements IMainController {

    private final AcademicUserPwdAuthenticationProvider authenticationProvider;
    //private final JwtService jwtService;

    @Override
    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) throws Exception {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            Authentication responseToken = authenticationProvider.authenticate(authenticationToken);
            return new ResponseEntity<>(new AuthenticationResponse(responseToken.toString()), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid username or password", e);
        }
        /*UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.createToken(userDetails);
        return new ResponseEntity<>(new AuthenticationResponse(token), HttpStatus.OK);
        return null;*/
    }

}
