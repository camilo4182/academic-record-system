package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.validations.custom.UUIDValidator;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

public interface ICareerService {

    void save(@Valid Career career);

    void update(@Valid Career career) throws ResourceNotFoundException;

    void deleteById(@UUIDValidator String id) throws ResourceNotFoundException;

    Career findById(@UUIDValidator String id) throws ResourceNotFoundException;

    List<Career> findAll();

    List<Career> findAll(@Min(0) int limit, @Min(0) int offset);

    void assignCourseToCareer(@UUIDValidator String courseId, @UUIDValidator String careerId) throws ResourceNotFoundException;

    List<Course> findCoursesByCareer(@UUIDValidator String careerId) throws ResourceNotFoundException;

}
