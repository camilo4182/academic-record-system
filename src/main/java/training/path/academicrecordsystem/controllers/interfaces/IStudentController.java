package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.StudentDTO;

import java.util.List;

public interface IStudentController {

    ResponseEntity<String> save(StudentDTO studentDTO);
    ResponseEntity<String> update(String id, StudentDTO studentDTO);
    ResponseEntity<String> deleteById(String id);
    ResponseEntity<StudentDTO> findById(String id);
    ResponseEntity<List<StudentDTO>> findAll(Integer limit, Integer offset);

}
