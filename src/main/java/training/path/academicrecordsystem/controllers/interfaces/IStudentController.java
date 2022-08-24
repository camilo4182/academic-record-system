package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.*;
import training.path.academicrecordsystem.exceptions.NotMatchEnrollmentStudentException;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.UniqueColumnViolationException;
import training.path.academicrecordsystem.validations.custom.SemesterValidator;
import training.path.academicrecordsystem.validations.custom.UUIDValidator;

import javax.validation.Valid;
import java.util.List;

public interface IStudentController {

    ResponseEntity<String> save(@Valid RequestBodyStudentDTO requestBodyStudentDTO) throws ResourceNotFoundException, UniqueColumnViolationException;

    ResponseEntity<String> update(String id, @Valid UpdateStudentByAdminDTO updateByAdmin) throws ResourceNotFoundException, UniqueColumnViolationException;

    ResponseEntity<String> updateBasicInfo(String id, @Valid UpdateStudentByStudentDTO updateByStudent) throws ResourceNotFoundException, UniqueColumnViolationException;

    ResponseEntity<String> deleteById(String id) throws ResourceNotFoundException;

    ResponseEntity<ResponseBodyStudentDTO> findById(String id) throws ResourceNotFoundException;

    ResponseEntity<List<ResponseBodyStudentDTO>> findAll(Integer limit, Integer offset);

    ResponseEntity<List<ResponseBodyEnrollmentDTO>> findEnrollmentInfo(@UUIDValidator String studentId,
                                                                       @SemesterValidator Integer semester) throws ResourceNotFoundException;

    ResponseEntity<String> enroll(@UUIDValidator String studentId,
                                  @UUIDValidator String enrollmentId,
                                  @Valid RequestBodyEnrollmentDTO requestBodyEnrollmentDTO) throws ResourceNotFoundException, NotMatchEnrollmentStudentException;

}
