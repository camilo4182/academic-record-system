package training.path.academicrecordsystem.services.interfaces;

import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.exceptions.NotMatchEnrollmentStudentException;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.StudentAlreadyEnrolledException;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.validations.groups.OnEnrollClasses;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public interface IEnrollmentService {

    void save(@Valid Enrollment enrollment) throws ResourceNotFoundException;

    @Validated(OnEnrollClasses.class)
    void saveClass(@Valid Enrollment enrollment, @NotEmpty List<CourseClass> courseClasses)
            throws ResourceNotFoundException, NotMatchEnrollmentStudentException, StudentAlreadyEnrolledException;

    List<Enrollment> findAll();

}
