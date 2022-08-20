package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Enrollment;

import java.util.List;
import java.util.Optional;

public interface IEnrollmentRepository {

    void save(Enrollment enrollment);

    void saveClass(Enrollment enrollment, CourseClass courseClass);

    int update(String id, Enrollment enrollment);

    int deleteById(String id);

    Optional<Enrollment> findById(String id);

    List<Enrollment> findAll();

    List<Enrollment> findAll(int limit, int offset);

    boolean exists(String id);

}
