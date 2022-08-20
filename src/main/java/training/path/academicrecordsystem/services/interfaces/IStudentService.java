package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.validations.custom.UUIDValidator;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

public interface IStudentService {

    void save(@Valid Student student) throws ResourceNotFoundException;

    void update(@UUIDValidator String id, @Valid Student student) throws ResourceNotFoundException;

    void deleteById(@UUIDValidator String id) throws ResourceNotFoundException;

    Student findById(@UUIDValidator String id) throws ResourceNotFoundException;

    List<Student> findAll();

    List<Student> findAll(@Min(0) int limit, @Min(0) int offset);

    List<Enrollment> findEnrollmentInfo(@UUIDValidator String studentId) throws ResourceNotFoundException;

    List<Enrollment> findEnrollmentsBySemester(@UUIDValidator String studentId, @Min(1) @Max(12) int semester) throws ResourceNotFoundException;

}
