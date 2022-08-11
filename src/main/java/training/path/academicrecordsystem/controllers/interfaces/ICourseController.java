package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.CourseClassDTO;
import training.path.academicrecordsystem.controllers.dtos.CourseDTO;

import java.util.List;

public interface ICourseController {

    ResponseEntity<String> save(CourseDTO courseDTO);
    ResponseEntity<String> update(String id, CourseDTO courseDTO);
    ResponseEntity<String> deleteById(String id);
    ResponseEntity<CourseDTO> findById(String id);
    ResponseEntity<List<CourseDTO>> findAll(Integer limit, Integer offset);
    ResponseEntity<List<CourseClassDTO>> getClassesByCourse(String courseId);

}
