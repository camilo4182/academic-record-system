package training.path.academicrecordsystem.controllers.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import training.path.academicrecordsystem.controllers.dtos.AuthenticationRequest;
import training.path.academicrecordsystem.controllers.dtos.AuthenticationResponse;
import training.path.academicrecordsystem.controllers.interfaces.IMainController;
import training.path.academicrecordsystem.security.AcademicUserDetailsService;

@RestController
@RequiredArgsConstructor
public class MainController implements IMainController {

    //private final AuthenticationManager authenticationManager;
    private final AcademicUserDetailsService myUserDetailsService;
    //private final JwtService jwtService;

    @Override
    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request, AuthenticationResponse response) throws Exception {
        /*try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid username or password", e);
        }
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.createToken(userDetails);
        return new ResponseEntity<>(new AuthenticationResponse(token), HttpStatus.OK);*/
        return null;
    }

}
