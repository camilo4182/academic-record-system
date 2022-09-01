package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.AdminDTO;
import training.path.academicrecordsystem.controllers.dtos.AuthenticationRequestDTO;
import training.path.academicrecordsystem.controllers.dtos.AuthenticationResponse;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;

import javax.validation.Valid;

public interface IMainController {

    ResponseEntity<AuthenticationResponse> login(@Valid AuthenticationRequestDTO request) throws Exception;

    ResponseEntity<String> registerAdmin(AdminDTO adminDTO) throws ResourceNotFoundException;

}
