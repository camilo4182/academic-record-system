package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyCourseClassDTO;
import training.path.academicrecordsystem.controllers.dtos.CourseDTO;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;

import javax.validation.Valid;
import java.util.List;

public interface ICourseController {

    ResponseEntity<String> save(@Valid CourseDTO courseDTO);

    ResponseEntity<String> update(String id, @Valid CourseDTO courseDTO) throws ResourceNotFoundException;

    ResponseEntity<String> deleteById(String id) throws ResourceNotFoundException;

    ResponseEntity<CourseDTO> findById(String id) throws ResourceNotFoundException;

    ResponseEntity<List<CourseDTO>> findAll(Integer limit, Integer offset);

    ResponseEntity<List<ResponseBodyCourseClassDTO>> findClassesByCourse(String courseId) throws ResourceNotFoundException;

}
