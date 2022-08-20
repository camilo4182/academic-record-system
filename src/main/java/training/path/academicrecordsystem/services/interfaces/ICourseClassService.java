package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.validations.custom.UUIDValidator;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

public interface ICourseClassService {

    void save(@Valid CourseClass courseClass) throws ResourceNotFoundException;
    void update(@Valid CourseClass courseClass) throws ResourceNotFoundException;
    void deleteById(@UUIDValidator String id) throws ResourceNotFoundException;
    CourseClass findById(@UUIDValidator String id) throws ResourceNotFoundException;
    List<CourseClass> findAll();
    List<CourseClass> findAll(@Min(0) int limit, @Min(0) int offset);


}
