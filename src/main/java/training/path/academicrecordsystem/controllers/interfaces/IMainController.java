package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import training.path.academicrecordsystem.controllers.dtos.AuthenticationRequest;
import training.path.academicrecordsystem.controllers.dtos.AuthenticationResponse;

public interface IMainController {

    ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request) throws Exception;

}
