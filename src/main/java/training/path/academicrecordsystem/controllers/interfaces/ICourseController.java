package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyCourseClassDTO;
import training.path.academicrecordsystem.controllers.dtos.CourseDTO;

import javax.validation.Valid;
import java.util.List;

public interface ICourseController {

    ResponseEntity<String> save(@Valid CourseDTO courseDTO);
    ResponseEntity<String> update(String id, @Valid CourseDTO courseDTO);
    ResponseEntity<String> deleteById(String id);
    ResponseEntity<CourseDTO> findById(String id);
    ResponseEntity<List<CourseDTO>> findAll(Integer limit, Integer offset);
    ResponseEntity<List<ResponseBodyCourseClassDTO>> findClassesByCourse(String courseId);

}
