package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.EnrollmentDTO;

import java.util.List;

public interface IEnrollmentController {

    ResponseEntity<String> save(EnrollmentDTO enrollmentDTO);
    ResponseEntity<String> update(String id, EnrollmentDTO enrollmentDTO);
    ResponseEntity<String> deleteById(String id);
    ResponseEntity<EnrollmentDTO> findById(String id);
    ResponseEntity<List<EnrollmentDTO>> findAll(Integer limit, Integer offset);

}
