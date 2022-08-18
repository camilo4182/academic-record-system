package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.CouldNotPerformOperationException;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.validations.custom.UUIDValidator;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

public interface ICourseService {

    void save(@Valid Course course);
    void update(@Valid Course course) throws ResourceNotFoundException;
    void deleteById(@UUIDValidator String id) throws ResourceNotFoundException;
    Course findById(@UUIDValidator String id) throws ResourceNotFoundException;
    Course findByName(String name) throws ResourceNotFoundException;
    List<Course> findAll();
    List<Course> findAll(@Min(0) int limit, @Min(0) int offset);
    List<CourseClass> getClassesByCourse(@UUIDValidator String courseId) throws ResourceNotFoundException, CouldNotPerformOperationException;
}
