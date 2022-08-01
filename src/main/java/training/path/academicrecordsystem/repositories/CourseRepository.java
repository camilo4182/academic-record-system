package training.path.academicrecordsystem.repositories;

import training.path.academicrecordsystem.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {

    Optional<Course> findById(long id);
    Optional<Course> findByName(String name);
    List<Course> findAll();
    int save(Course course);
    int update(long id, Course course);
    int deleteById(long id);
    int deleteAll();
    
}
