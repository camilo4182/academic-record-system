package training.path.academicrecordsystem.services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.exceptions.ClassNotAvailableException;
import training.path.academicrecordsystem.exceptions.NotMatchEnrollmentStudentException;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.StudentAlreadyEnrolledException;
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
import java.util.Optional;

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
    public void enrollToClasses(Enrollment enrollment, List<CourseClass> courseClasses)
            throws ResourceNotFoundException, NotMatchEnrollmentStudentException, StudentAlreadyEnrolledException, ClassNotAvailableException {

        if (!studentRepository.exists(enrollment.getStudent().getId()))
            throw new ResourceNotFoundException("Student with id " + enrollment.getStudent().getId() + " was not found");

        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByStudent(enrollment.getStudent().getId());

        if (enrollmentOptional.isEmpty())
            throw new ResourceNotFoundException("Enrollment with id " + enrollment.getId() + " was not found");

        Enrollment foundEnrollment = enrollmentOptional.orElseThrow();

        if (!Objects.equals(enrollment.getStudent().getId(), foundEnrollment.getStudent().getId()))
            throw new NotMatchEnrollmentStudentException("This enrollment does not belong to the selected student");

        enrollment.setId(foundEnrollment.getId());

        List<CourseClass> classesToEnroll = new ArrayList<>();
        for (CourseClass courseClass : courseClasses) {
            classesToEnroll.add(courseClassRepository.findById(courseClass.getId()).orElseThrow(() ->
                    new ResourceNotFoundException("Class with id " + courseClass.getId() + " was not found")));

            /*if (enrollmentRepository.studentAlreadyEnrolled(enrollment.getId(), courseClass.getId()))
                throw new StudentAlreadyEnrolledException("This student is already enrolled in the class " + courseClass.getId());*/

            if (!courseClassRepository.isAvailable(courseClass.getId()))
                throw new ClassNotAvailableException("The class " + courseClass.getId() + " does not have available spaces anymore");
        }
        for (CourseClass courseClass : classesToEnroll) {
            courseClass.increaseEnrolledStudents();
            enrollmentRepository.saveEnrollmentClasses(enrollment, courseClass);
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

    @Override
    public Enrollment findByStudent(String id) throws ResourceNotFoundException {
        return enrollmentRepository.findByStudent(id).orElseThrow(() -> new ResourceNotFoundException("The student " + id + " does not have a enrollment"));
    }

}
