package training.path.academicrecordsystem.repositories.interfaces;

import org.springframework.dao.DataAccessException;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {

    Optional<Course> findById(String id);
    Optional<Course> findByName(String name);
    List<Course> findAll();
    List<Course> findAll(int limit, int offset);

    int save(Course course);
    int update(String id, Course course);
    int deleteById(String id);
    boolean exists(String id);
    Optional<List<CourseClass>> getClassesByCourse(String courseId);
}
