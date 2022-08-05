package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.model.Course;

import java.util.List;

public interface ICourseController {

    ResponseEntity<Course> findCourseByIdAndUser(long userId, long courseId);
    //ResponseEntity<Course> findByName(String name);
    ResponseEntity<List<Course>> findAllCoursesByUser(long userId);
    ResponseEntity<String> saveCourseForUser(long userId, Course course);
    ResponseEntity<String> update(long userId, long courseId, Course course);
    ResponseEntity<String> deleteById(long userId, long courseId);
    ResponseEntity<String> deleteAllCoursesByUser(long userId);
    
}
