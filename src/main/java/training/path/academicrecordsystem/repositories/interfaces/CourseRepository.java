package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {

    Optional<Course> findById(String id);
    Optional<Course> findByName(String name);
    List<Course> findAll();
    int save(Course course);
    int update(String id, Course course);
    int deleteById(String id);
    boolean exists(String id);
}
