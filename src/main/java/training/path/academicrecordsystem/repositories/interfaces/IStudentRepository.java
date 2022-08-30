package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentRepository {

    int save(Student student);

    int update(String id, Student student);

    Optional<Student> findById(String id);

    List<Student> findAll();

    List<Student> findAll(int limit, int offset);

    List<Enrollment> findEnrollmentInfo(String studentId);

    Optional<Enrollment> findEnrollmentsBySemester(String studentId, int semester);

}
