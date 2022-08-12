package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.EnrollmentDTO;
import training.path.academicrecordsystem.controllers.dtos.RequestBodyStudentDTO;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyStudentDTO;

import java.util.List;

public interface IStudentController {

    ResponseEntity<String> save(RequestBodyStudentDTO requestBodyStudentDTO);
    ResponseEntity<String> update(String id, RequestBodyStudentDTO requestBodyStudentDTO);
    ResponseEntity<String> deleteById(String id);
    ResponseEntity<ResponseBodyStudentDTO> findById(String id);
    ResponseEntity<List<ResponseBodyStudentDTO>> findAll(Integer limit, Integer offset);
    ResponseEntity<String> enroll(String studentId, EnrollmentDTO enrollmentDTO);

}
