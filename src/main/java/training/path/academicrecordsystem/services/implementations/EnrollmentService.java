package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.repositories.interfaces.CourseClassRepository;
import training.path.academicrecordsystem.repositories.interfaces.IEnrollmentRepository;
import training.path.academicrecordsystem.repositories.interfaces.StudentRepository;
import training.path.academicrecordsystem.services.interfaces.IEnrollmentService;

import java.util.List;

@Service
public class EnrollmentService implements IEnrollmentService {

    private final IEnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseClassRepository courseClassRepository;

    @Autowired
    public EnrollmentService(IEnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseClassRepository courseClassRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseClassRepository = courseClassRepository;
    }

    @Override
    public void save(Enrollment enrollment) throws ResourceNotFoundException {
        if (!studentRepository.exists(enrollment.getStudent().getId())) throw new ResourceNotFoundException("Student with id " + enrollment.getStudent().getId() + " was not found");
        if (!courseClassRepository.exists(enrollment.getCourseClass().getId())) throw new ResourceNotFoundException("Class with id " + enrollment.getCourseClass().getId() + " was not found");
        enrollmentRepository.save(enrollment);
    }

    @Override
    public void update(String id, Enrollment enrollment) {

    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public Enrollment finById(String id) {
        return null;
    }

    @Override
    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    @Override
    public List<Enrollment> findAll(int limit, int offset) {
        return null;
    }
}
