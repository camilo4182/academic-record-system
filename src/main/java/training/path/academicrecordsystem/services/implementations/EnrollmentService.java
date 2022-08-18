package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.repositories.interfaces.CourseClassRepository;
import training.path.academicrecordsystem.repositories.interfaces.IEnrollmentRepository;
import training.path.academicrecordsystem.repositories.interfaces.StudentRepository;
import training.path.academicrecordsystem.services.interfaces.IEnrollmentService;

import java.util.List;

@Service
@Validated
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
    public void save(Enrollment enrollment, List<CourseClass> courseClasses) throws ResourceNotFoundException {
        if (!studentRepository.exists(enrollment.getStudent().getId())) throw new ResourceNotFoundException("Student with id " + enrollment.getStudent().getId() + " was not found");
        for (CourseClass courseClass : courseClasses) {
            if (!courseClassRepository.exists(courseClass.getId())) throw new ResourceNotFoundException("Class with id " + courseClass.getId() + " was not found");
            courseClass.setEnrolledStudents(courseClassRepository.findById(courseClass.getId()).orElseThrow().getEnrolledStudents() + 1);
            enrollmentRepository.save(enrollment, courseClass);
        }
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
