package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.AuthenticationRequestDTO;
import training.path.academicrecordsystem.controllers.dtos.AuthenticationResponse;

import javax.validation.Valid;

public interface IMainController {

    ResponseEntity<AuthenticationResponse> login(@Valid AuthenticationRequestDTO request) throws Exception;

}
