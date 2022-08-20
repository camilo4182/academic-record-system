package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.ProfessorDTO;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;

import javax.validation.Valid;
import java.util.List;

public interface IProfessorController {

    ResponseEntity<String> save(@Valid ProfessorDTO professorDTO);

    ResponseEntity<String> update(String id, @Valid ProfessorDTO professorDTO) throws ResourceNotFoundException;

    ResponseEntity<String> deleteById(String id) throws ResourceNotFoundException;

    ResponseEntity<ProfessorDTO> findById(String id) throws ResourceNotFoundException;

    ResponseEntity<List<ProfessorDTO>> findAll(Integer limit, Integer offset);

}
