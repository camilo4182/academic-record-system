package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentRepository {

    int save(Student student);

    int update(String id, Student student);

    int deleteById(String id);

    Optional<Student> findById(String id);

    Optional<Student> findByName(String name);

    Optional<Student> findByEmail(String email);

    List<Student> findAll();

    List<Student> findAll(int limit, int offset);

    boolean exists(String id);

    List<Enrollment> findEnrollmentInfo(String studentId);

    List<Enrollment> findEnrollmentsBySemester(String studentId, int semester);

}
