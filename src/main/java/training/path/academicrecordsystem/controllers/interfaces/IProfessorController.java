package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.ProfessorDTO;

import java.util.List;

public interface IProfessorController {

    ResponseEntity<String> save(ProfessorDTO professorDTO);
    ResponseEntity<String> update(String id, ProfessorDTO professorDTO);
    ResponseEntity<String> deleteById(String id);
    ResponseEntity<ProfessorDTO> findById(String id);
    ResponseEntity<List<ProfessorDTO>> findAll();

}
