package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.CouldNotPerformDBOperationException;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;

import java.util.List;

public interface ICourseService {

    void save(Course course);
    void update(Course course) throws NotFoundResourceException;
    void deleteById(String id) throws NotFoundResourceException;
    Course findById(String id) throws NotFoundResourceException;
    Course findByName(String name) throws NotFoundResourceException;
    List<Course> findAll();
    List<Course> findAll(int limit, int offset);

    List<CourseClass> getClassesByCourse(String courseId) throws NotFoundResourceException, CouldNotPerformDBOperationException;
}
