package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;

import java.util.List;
import java.util.Optional;

public interface ICourseRepository {

    Optional<Course> findById(String id);

    List<Course> findAll();

    List<Course> findAll(int limit, int offset);

    int save(Course course);

    int update(String id, Course course);

    int deleteById(String id);

    boolean exists(String id);

    List<CourseClass> getClassesByCourse(String courseId);

}
