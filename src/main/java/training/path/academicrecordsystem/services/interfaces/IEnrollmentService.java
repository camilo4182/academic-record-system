package training.path.academicrecordsystem.services.interfaces;

import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.validations.custom.UUIDValidator;
import training.path.academicrecordsystem.validations.groups.OnEnrollClasses;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public interface IEnrollmentService {

    void save(@Valid Enrollment enrollment) throws ResourceNotFoundException;

    @Validated(OnEnrollClasses.class)
    void saveClass(@Valid Enrollment enrollment, @NotEmpty List<CourseClass> courseClasses) throws ResourceNotFoundException;

    void update(@UUIDValidator String id, @Valid Enrollment enrollment);
    void deleteById(@UUIDValidator String id);
    Enrollment finById(@UUIDValidator String id);
    List<Enrollment> findAll();
    List<Enrollment> findAll(@Min(0) int limit, @Min(0) int offset);

}
