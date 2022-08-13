package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;

import java.util.List;

public interface IStudentService {

    void save(Student student);
    void update(String id, Student student) throws ResourceNotFoundException;
    void deleteById(String id) throws ResourceNotFoundException;
    Student findById(String id) throws ResourceNotFoundException;
    List<Student> findAll();
    List<Student> findAll(int limit, int offset);
    List<Enrollment> findEnrollmentsBySemester(String studentId, int semester) throws ResourceNotFoundException;

}
