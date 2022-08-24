package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.RequestBodyProfessorDTO;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyProfessorDTO;
import training.path.academicrecordsystem.controllers.dtos.UpdateProfessorByAdminDTO;
import training.path.academicrecordsystem.controllers.dtos.UpdateUserByUserDTO;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.UniqueColumnViolationException;

import javax.validation.Valid;
import java.util.List;

public interface IProfessorController {

    ResponseEntity<String> save(@Valid RequestBodyProfessorDTO professorDTO) throws UniqueColumnViolationException;

    ResponseEntity<String> updateByAdmin(String id, @Valid UpdateProfessorByAdminDTO professorDTO) throws ResourceNotFoundException, UniqueColumnViolationException;

    ResponseEntity<String> updateProfile(String id, @Valid UpdateUserByUserDTO professorDTO) throws ResourceNotFoundException, UniqueColumnViolationException;

    ResponseEntity<String> deleteById(String id) throws ResourceNotFoundException;

    ResponseEntity<ResponseBodyProfessorDTO> findById(String id) throws ResourceNotFoundException;

    ResponseEntity<List<ResponseBodyProfessorDTO>> findAll(Integer limit, Integer offset);

}
