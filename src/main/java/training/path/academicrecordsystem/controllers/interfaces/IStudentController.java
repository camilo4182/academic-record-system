package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.RequestBodyEnrollmentDTO;
import training.path.academicrecordsystem.controllers.dtos.RequestBodyStudentDTO;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyEnrollmentDTO;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyStudentDTO;
import training.path.academicrecordsystem.validations.custom.UUIDValidator;

import javax.validation.Valid;
import java.util.List;

public interface IStudentController {

    ResponseEntity<String> save(@Valid RequestBodyStudentDTO requestBodyStudentDTO);
    ResponseEntity<String> update(String id, @Valid RequestBodyStudentDTO requestBodyStudentDTO);
    ResponseEntity<String> deleteById(String id);
    ResponseEntity<ResponseBodyStudentDTO> findById(String id);
    ResponseEntity<List<ResponseBodyStudentDTO>> findAll(Integer limit, Integer offset);
    ResponseEntity<String> enroll(@UUIDValidator String studentId, @Valid RequestBodyEnrollmentDTO requestBodyEnrollmentDTO);
    ResponseEntity<List<ResponseBodyEnrollmentDTO>> findEnrollmentsBySemester(@UUIDValidator String studentId, Integer semester);

}
