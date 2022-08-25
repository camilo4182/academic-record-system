package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.*;
import training.path.academicrecordsystem.exceptions.*;
import training.path.academicrecordsystem.validations.custom.SemesterValidator;
import training.path.academicrecordsystem.validations.custom.UUIDValidator;

import javax.validation.Valid;
import java.util.List;

public interface IStudentController {

    ResponseEntity<String> save(@Valid RequestBodyStudentDTO requestBodyStudentDTO) throws ResourceNotFoundException, UniqueColumnViolationException;

    ResponseEntity<String> updateByAdmin(String id, @Valid UpdateStudentByAdminDTO updateByAdmin) throws ResourceNotFoundException, UniqueColumnViolationException;

    ResponseEntity<String> updateProfile(String id, @Valid UpdateUserByUserDTO updateByStudent) throws ResourceNotFoundException, UniqueColumnViolationException;

    ResponseEntity<String> deleteById(String id) throws ResourceNotFoundException;

    ResponseEntity<ResponseBodyStudentDTO> findById(String id) throws ResourceNotFoundException;

    ResponseEntity<ResponseBodyStudentDTO> viewProfile(String id) throws ResourceNotFoundException;

    ResponseEntity<List<ResponseBodyStudentDTO>> findAll(Integer limit, Integer offset);

    ResponseEntity<List<ResponseBodyEnrollmentDTO>> findEnrollmentInfo(@UUIDValidator String studentId,
                                                                       @SemesterValidator Integer semester) throws ResourceNotFoundException;

}
