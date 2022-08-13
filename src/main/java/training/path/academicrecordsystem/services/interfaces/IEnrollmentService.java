package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Enrollment;

import java.util.List;

public interface IEnrollmentService {

    void save(Enrollment enrollment, List<CourseClass> courseClasses) throws ResourceNotFoundException;
    void update(String id, Enrollment enrollment);
    void deleteById(String id);
    Enrollment finById(String id);
    List<Enrollment> findAll();
    List<Enrollment> findAll(int limit, int offset);

}
