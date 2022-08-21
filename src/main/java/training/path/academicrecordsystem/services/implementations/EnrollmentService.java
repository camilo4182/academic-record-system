package training.path.academicrecordsystem.services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.exceptions.NotMatchEnrollmentStudentException;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.repositories.interfaces.ICareerRepository;
import training.path.academicrecordsystem.repositories.interfaces.ICourseClassRepository;
import training.path.academicrecordsystem.repositories.interfaces.IEnrollmentRepository;
import training.path.academicrecordsystem.repositories.interfaces.IStudentRepository;
import training.path.academicrecordsystem.services.interfaces.IEnrollmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Validated
@AllArgsConstructor
public class EnrollmentService implements IEnrollmentService {

    private final IEnrollmentRepository enrollmentRepository;
    private final IStudentRepository studentRepository;
    private final ICourseClassRepository courseClassRepository;
    private final ICareerRepository careerRepository;

    @Override
    public void save(Enrollment enrollment) throws ResourceNotFoundException {
        if (!studentRepository.exists(enrollment.getStudent().getId()))
            throw new ResourceNotFoundException("Student with id " + enrollment.getStudent().getId() + " was not found");
        if (!careerRepository.exists(enrollment.getStudent().getCareer().getId()))
            throw new ResourceNotFoundException("Career with id" + enrollment.getStudent().getId() + " was not found");
        enrollmentRepository.save(enrollment);
    }

    @Override
    public void saveClass(Enrollment enrollment, List<CourseClass> courseClasses) throws ResourceNotFoundException, NotMatchEnrollmentStudentException {
        if (!studentRepository.exists(enrollment.getStudent().getId()))
            throw new ResourceNotFoundException("Student with id " + enrollment.getStudent().getId() + " was not found");
        if (!enrollmentRepository.exists(enrollment.getId()))
            throw new ResourceNotFoundException("Enrollment with id " + enrollment.getId() + " was not found");
        Enrollment foundEnrollment = enrollmentRepository.findById(enrollment.getId()).orElseThrow(() -> new ResourceNotFoundException(""));
        if (!Objects.equals(enrollment.getStudent().getId(), foundEnrollment.getStudent().getId()))
            throw new NotMatchEnrollmentStudentException("This enrollment does not belong to the selected student");
        List<CourseClass> foundClasses = new ArrayList<>();
        for (CourseClass courseClass : courseClasses) {
            foundClasses.add(courseClassRepository.findById(courseClass.getId()).orElseThrow(() -> new ResourceNotFoundException("Class with id " + courseClass.getId() + " was not found")));
        }
        for (CourseClass courseClass : foundClasses) {
            courseClass.increaseEnrolledStudents();
            enrollmentRepository.saveClass(enrollment, courseClass);
        }
    }

    private void verifyClasses(List<CourseClass> courseClasses) throws ResourceNotFoundException {
        for (CourseClass courseClass : courseClasses) {
            if (!courseClassRepository.exists(courseClass.getId()))
                throw new ResourceNotFoundException("Class with id " + courseClass.getId() + " was not found");
        }
    }

    @Override
    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

}
