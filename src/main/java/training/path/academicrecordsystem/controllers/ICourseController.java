package training.path.academicrecordsystem.controllers;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.model.Course;

import java.util.List;

public interface ICourseController {

    ResponseEntity<Course> findById(long userId, long courseId);
    //ResponseEntity<Course> findByName(String name);
    ResponseEntity<List<Course>> findAll(String name);
    ResponseEntity<String> save(Course course);
    ResponseEntity<String> update(long id, Course course);
    ResponseEntity<String> deleteById(long id);
    ResponseEntity<String> deleteAll();
    
}
