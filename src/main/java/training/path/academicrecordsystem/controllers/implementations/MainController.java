package training.path.academicrecordsystem.controllers.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import training.path.academicrecordsystem.controllers.dtos.AdminDTO;
import training.path.academicrecordsystem.controllers.dtos.AuthenticationRequestDTO;
import training.path.academicrecordsystem.controllers.dtos.AuthenticationResponse;
import training.path.academicrecordsystem.controllers.interfaces.IMainController;
import training.path.academicrecordsystem.controllers.mappers.AdminMapper;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Administrator;
import training.path.academicrecordsystem.security.userdetails.AcademicUserDetailsService;
import training.path.academicrecordsystem.security.authproviders.AcademicUserPwdAuthenticationProvider;
import training.path.academicrecordsystem.security.jwtauth.JWTTokenGeneratorService;
import training.path.academicrecordsystem.services.interfaces.IAdminService;

@Profile("dev")
@RestController
@RequiredArgsConstructor
public class MainController implements IMainController {

    private final AcademicUserPwdAuthenticationProvider authenticationProvider;
    private final JWTTokenGeneratorService jwtService;
    private final AcademicUserDetailsService service;
    private final IAdminService adminService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequestDTO request) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication responseToken = authenticationProvider.authenticate(authenticationToken);
        UserDetails userDetails = service.loadUserByUsername(responseToken.getName());
        String token = jwtService.generateJWT(userDetails);
        return new ResponseEntity<>(new AuthenticationResponse(token), HttpStatus.OK);
    }

    @Override
    @PostMapping("admin")
    public ResponseEntity<String> registerAdmin(@RequestBody AdminDTO adminDTO) throws ResourceNotFoundException {
        adminDTO.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        Administrator administrator = AdminMapper.createEntity(adminDTO);
        adminService.save(administrator);
        return new ResponseEntity<>("Admin created", HttpStatus.OK);
    }


}
