package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.RequestBodyCourseClassDTO;
import training.path.academicrecordsystem.controllers.dtos.RequestBodyEnrollmentDTO;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyCourseClassDTO;
import training.path.academicrecordsystem.exceptions.*;
import training.path.academicrecordsystem.validations.custom.UUIDValidator;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

public interface ICourseClassController {

    ResponseEntity<String> save(@Valid RequestBodyCourseClassDTO courseClassDTO) throws ResourceNotFoundException, UniqueColumnViolationException;

    ResponseEntity<String> update(String id, @Valid RequestBodyCourseClassDTO courseClassDTO) throws ResourceNotFoundException;

    ResponseEntity<String> deleteById(String id) throws ResourceNotFoundException;

    ResponseEntity<ResponseBodyCourseClassDTO> findById(String id) throws ResourceNotFoundException;

    ResponseEntity<List<ResponseBodyCourseClassDTO>> findAll(@Pattern(regexp = "^true$|^false$",
                                                                message = "Invalid request param value: only accepted 'true' or 'false'") String available,
                                                             Integer limit, Integer offset);

    ResponseEntity<String> enroll(@UUIDValidator String classId, @Valid RequestBodyEnrollmentDTO enrollmentDTO)
            throws ResourceNotFoundException, NotMatchEnrollmentStudentException, StudentAlreadyEnrolledException, ClassNotAvailableException;

}
