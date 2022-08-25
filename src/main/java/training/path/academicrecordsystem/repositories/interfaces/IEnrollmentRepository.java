package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Enrollment;

import java.util.List;
import java.util.Optional;

public interface IEnrollmentRepository {

    void save(Enrollment enrollment);

    void saveEnrollmentClasses(Enrollment enrollment, CourseClass courseClass);

    Optional<Enrollment> findById(String id);

    List<Enrollment> findAll();

    Optional<Enrollment> findByStudent(String id);

    boolean exists(String id);

    boolean studentAlreadyEnrolled(String enrollmentId, String classId);

}
