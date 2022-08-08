package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.Student;

import java.util.List;

public interface IStudentService {

    void save(Student student);
    void update(String id, Student student) throws NotFoundResourceException;
    void deleteById(String id) throws NotFoundResourceException;
    Student findById(String id) throws NotFoundResourceException;
    List<Student> findAll();

}
