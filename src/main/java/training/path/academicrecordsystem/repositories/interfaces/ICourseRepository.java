package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;

import java.util.List;
import java.util.Optional;

public interface ICourseRepository {

    int save(Course course);

    int update(String id, Course course);

    int deleteById(String id);

    Optional<Course> findById(String id);

    Optional<Course> findByName(String name);

    List<Course> findAll();

    List<Course> findAll(int limit, int offset);

    boolean exists(String id);

    List<CourseClass> findClassesByCourse(String courseId);

}
