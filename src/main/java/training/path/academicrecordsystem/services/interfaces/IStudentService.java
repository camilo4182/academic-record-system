package training.path.academicrecordsystem.services.interfaces;

import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.UniqueColumnViolationException;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.validations.custom.UUIDValidator;
import training.path.academicrecordsystem.validations.groups.OnCreate;
import training.path.academicrecordsystem.validations.groups.OnUpdate;
import training.path.academicrecordsystem.validations.groups.OnUpdateByUser;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

public interface IStudentService {

    @Validated(OnCreate.class)
    void save(@Valid Student student) throws ResourceNotFoundException, UniqueColumnViolationException;

    @Validated(OnUpdate.class)
    void update(@UUIDValidator String id, @Valid Student student) throws ResourceNotFoundException, UniqueColumnViolationException;

    @Validated(OnUpdateByUser.class)
    void updateBasicInfo(@UUIDValidator String id, @Valid Student student) throws ResourceNotFoundException, UniqueColumnViolationException;

    void deleteById(@UUIDValidator String id) throws ResourceNotFoundException;

    Student findById(@UUIDValidator String id) throws ResourceNotFoundException;

    List<Student> findAll();

    List<Student> findAll(@Min(0) int limit, @Min(0) int offset);

    List<Enrollment> findEnrollmentInfo(@UUIDValidator String studentId) throws ResourceNotFoundException;

    List<Enrollment> findEnrollmentsBySemester(@UUIDValidator String studentId, @Min(1) @Max(12) int semester) throws ResourceNotFoundException;

}
