package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.CourseClassDTO;

import java.util.List;

public interface ICourseClassController {

    ResponseEntity<String> save(CourseClassDTO courseClassDTO);
    ResponseEntity<String> update(String id, CourseClassDTO courseClassDTO);
    ResponseEntity<String> deleteById(String id);
    ResponseEntity<CourseClassDTO> findById(String id);
    ResponseEntity<List<CourseClassDTO>> findAll(Integer limit, Integer offset);
    
}
