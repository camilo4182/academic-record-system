package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.CouldNotPerformOperationException;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;

import java.util.List;

public interface ICourseService {

    void save(Course course);
    void update(Course course) throws ResourceNotFoundException;
    void deleteById(String id) throws ResourceNotFoundException;
    Course findById(String id) throws ResourceNotFoundException;
    Course findByName(String name) throws ResourceNotFoundException;
    List<Course> findAll();
    List<Course> findAll(int limit, int offset);

    List<CourseClass> getClassesByCourse(String courseId) throws ResourceNotFoundException, CouldNotPerformOperationException;
}
