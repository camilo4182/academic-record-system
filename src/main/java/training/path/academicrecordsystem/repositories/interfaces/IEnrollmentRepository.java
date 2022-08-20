package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Enrollment;

import java.util.List;
import java.util.Optional;

public interface IEnrollmentRepository {

    void save(Enrollment enrollment);

    void saveClass(Enrollment enrollment, CourseClass courseClass);

    Optional<Enrollment> findById(String id);

    List<Enrollment> findAll();

    boolean exists(String id);

}
