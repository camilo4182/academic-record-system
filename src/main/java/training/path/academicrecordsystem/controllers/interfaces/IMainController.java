package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.AuthenticationRequest;
import training.path.academicrecordsystem.controllers.dtos.AuthenticationResponse;

public interface IMainController {

    ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request, AuthenticationResponse response) throws Exception;

}
