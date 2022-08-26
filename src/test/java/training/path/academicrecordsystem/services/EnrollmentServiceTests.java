package training.path.academicrecordsystem.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.exceptions.ClassNotAvailableException;
import training.path.academicrecordsystem.exceptions.NotMatchEnrollmentStudentException;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.repositories.implementations.CareerRepository;
import training.path.academicrecordsystem.repositories.implementations.CourseClassRepository;
import training.path.academicrecordsystem.repositories.implementations.EnrollmentRepository;
import training.path.academicrecordsystem.repositories.implementations.StudentRepository;
import training.path.academicrecordsystem.services.implementations.EnrollmentService;

import javax.management.relation.RoleInfoNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EnrollmentServiceTests {

    @Mock
    EnrollmentRepository enrollmentRepository;

    @Mock
    StudentRepository studentRepository;

    @Mock
    CourseClassRepository classRepository;

    @Mock
    CareerRepository careerRepository;

    @InjectMocks
    EnrollmentService enrollmentService;

    @Test
    void givenValidEnrollment_whenSave_thenItStoresTheEnrollmentCorrectly() {
        String careerID = UUID.randomUUID().toString();
        String studentID = UUID.randomUUID().toString();
        String enrollmentID = UUID.randomUUID().toString();

        Career career = Career.builder()
                .id(careerID)
                .name("Software Engineering")
                .build();

        Student student = Student.builder()
                .id(studentID)
                .userName("student.test")
                .career(career)
                .build();

        Enrollment enrollment = Enrollment.builder()
                .id(enrollmentID)
                .student(student)
                .semester(1)
                .build();

        when(studentRepository.exists(studentID)).thenReturn(true);
        when(careerRepository.exists(careerID)).thenReturn(true);

        assertDoesNotThrow(() -> enrollmentService.save(enrollment));
    }

    @Test
    void givenEnrollmentWithUnregisteredStudent_whenSave_thenItThrowsException() {
        String careerID = UUID.randomUUID().toString();
        String unregisteredStudentID = UUID.randomUUID().toString();
        String enrollmentID = UUID.randomUUID().toString();

        Career career = Career.builder()
                .id(careerID)
                .name("Software Engineering")
                .build();

        Student student = Student.builder()
                .id(unregisteredStudentID)
                .userName("student.test")
                .career(career)
                .build();

        Enrollment enrollment = Enrollment.builder()
                .id(enrollmentID)
                .student(student)
                .semester(1)
                .build();

        when(studentRepository.exists(unregisteredStudentID)).thenReturn(false);
        when(careerRepository.exists(careerID)).thenReturn(true);

        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.save(enrollment));
    }

    @Test
    void givenEnrollmentWithUnregisteredCareer_whenSave_thenItThrowsException() {
        String unregisteredCareerID = UUID.randomUUID().toString();
        String studentID = UUID.randomUUID().toString();
        String enrollmentID = UUID.randomUUID().toString();

        Career career = Career.builder()
                .id(unregisteredCareerID)
                .name("Software Engineering")
                .build();

        Student student = Student.builder()
                .id(studentID)
                .userName("student.test")
                .career(career)
                .build();

        Enrollment enrollment = Enrollment.builder()
                .id(enrollmentID)
                .student(student)
                .semester(1)
                .build();

        when(studentRepository.exists(studentID)).thenReturn(true);
        when(careerRepository.exists(unregisteredCareerID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.save(enrollment));
    }

    @Test
    void givenEnrollmentAndRegisteredCourseAndRegisteredStudent_whenEnrollToClasses_thenItRegisterTheStudentToTheGivenClasses() {
        String studentID = UUID.randomUUID().toString();
        String classID = UUID.randomUUID().toString();
        String registeredEnrollmentID = UUID.randomUUID().toString();

        Student student = Student.builder()
                .id(studentID)
                .userName("student.test")
                .email("student.test@email.com")
                .build();

        CourseClass classToEnroll = CourseClass.builder()
                .id(classID)
                .enrolledStudents(5)
                .capacity(15)
                .available(true)
                .build();

        Enrollment newEnrollment = Enrollment.builder()
                .student(student)
                .semester(1)
                .build();

        Enrollment registeredEnrollment = Enrollment.builder()
                .id(registeredEnrollmentID)
                .student(student)
                .build();

        when(studentRepository.exists(studentID)).thenReturn(true);
        when(enrollmentRepository.findByStudent(studentID)).thenReturn(Optional.of(registeredEnrollment));
        when(classRepository.exists(classID)).thenReturn(true);
        when(classRepository.isAvailable(classID)).thenReturn(true);

        assertDoesNotThrow(() -> enrollmentService.enrollToClasses(newEnrollment, List.of(classToEnroll)));
    }

    @Test
    void givenEnrollmentAndRegisteredCourseAndUnregisteredStudent_whenEnrollToClasses_thenItThrowsException() {
        String studentID = UUID.randomUUID().toString();
        String classID = UUID.randomUUID().toString();
        String registeredEnrollmentID = UUID.randomUUID().toString();

        Student student = Student.builder()
                .id(studentID)
                .userName("student.test")
                .email("student.test@email.com")
                .build();

        CourseClass classToEnroll = CourseClass.builder()
                .id(classID)
                .enrolledStudents(5)
                .capacity(15)
                .available(true)
                .build();

        Enrollment newEnrollment = Enrollment.builder()
                .student(student)
                .semester(1)
                .build();

        Enrollment registeredEnrollment = Enrollment.builder()
                .id(registeredEnrollmentID)
                .student(student)
                .build();

        when(studentRepository.exists(studentID)).thenReturn(false);
        when(enrollmentRepository.findByStudent(studentID)).thenReturn(Optional.of(registeredEnrollment));
        when(classRepository.exists(classID)).thenReturn(true);
        when(classRepository.isAvailable(classID)).thenReturn(true);

        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.enrollToClasses(newEnrollment, List.of(classToEnroll)));
    }

    @Test
    void givenEnrollmentAndRegisteredCourseAndRegisteredStudentWithoutEnrollment_whenEnrollToClasses_thenItThrowsException() {
        String studentID = UUID.randomUUID().toString();
        String classID = UUID.randomUUID().toString();
        String registeredEnrollmentID = UUID.randomUUID().toString();

        Student student = Student.builder()
                .id(studentID)
                .userName("student.test")
                .email("student.test@email.com")
                .build();

        CourseClass classToEnroll = CourseClass.builder()
                .id(classID)
                .enrolledStudents(5)
                .capacity(15)
                .available(true)
                .build();

        Enrollment newEnrollment = Enrollment.builder()
                .student(student)
                .semester(1)
                .build();

        when(studentRepository.exists(studentID)).thenReturn(true);
        when(enrollmentRepository.findByStudent(studentID)).thenReturn(Optional.empty());
        when(classRepository.exists(classID)).thenReturn(true);
        when(classRepository.isAvailable(classID)).thenReturn(true);

        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.enrollToClasses(newEnrollment, List.of(classToEnroll)));
    }

    @Test
    void givenEnrollmentAndRegisteredCourseAndTwoDifferentRegisteredStudents_whenEnrollToClasses_thenItThrowsException() {
        String studentID1 = UUID.randomUUID().toString();
        String studentID2 = UUID.randomUUID().toString();
        String classID = UUID.randomUUID().toString();
        String registeredEnrollmentID = UUID.randomUUID().toString();

        Student student1 = Student.builder()
                .id(studentID1)
                .userName("student1.test1")
                .email("student1.test1@email.com")
                .build();

        Student student2 = Student.builder()
                .id(studentID2)
                .userName("student2.test2")
                .email("student2.test2@email.com")
                .build();

        CourseClass classToEnroll = CourseClass.builder()
                .id(classID)
                .enrolledStudents(5)
                .capacity(15)
                .available(true)
                .build();

        Enrollment newEnrollment = Enrollment.builder()
                .student(student1)
                .semester(1)
                .build();

        Enrollment registeredEnrollment = Enrollment.builder()
                .id(registeredEnrollmentID)
                .student(student2)
                .build();

        when(studentRepository.exists(studentID1)).thenReturn(true);
        when(enrollmentRepository.findByStudent(studentID1)).thenReturn(Optional.of(registeredEnrollment));
        when(classRepository.exists(classID)).thenReturn(true);
        when(classRepository.isAvailable(classID)).thenReturn(true);

        assertThrows(NotMatchEnrollmentStudentException.class, () -> enrollmentService.enrollToClasses(newEnrollment, List.of(classToEnroll)));
    }

    @Test
    void givenEnrollmentAndUnregisteredCourseAndRegisteredStudent_whenEnrollToClasses_thenItThrowsException() {
        String studentID = UUID.randomUUID().toString();
        String classID = UUID.randomUUID().toString();
        String registeredEnrollmentID = UUID.randomUUID().toString();

        Student student = Student.builder()
                .id(studentID)
                .userName("student.test")
                .email("student.test@email.com")
                .build();

        CourseClass classToEnroll = CourseClass.builder()
                .id(classID)
                .enrolledStudents(5)
                .capacity(15)
                .available(true)
                .build();

        Enrollment newEnrollment = Enrollment.builder()
                .student(student)
                .semester(1)
                .build();

        Enrollment registeredEnrollment = Enrollment.builder()
                .id(registeredEnrollmentID)
                .student(student)
                .build();

        when(studentRepository.exists(studentID)).thenReturn(true);
        when(enrollmentRepository.findByStudent(studentID)).thenReturn(Optional.of(registeredEnrollment));
        when(classRepository.exists(classID)).thenReturn(false);
        when(classRepository.isAvailable(classID)).thenReturn(true);

        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.enrollToClasses(newEnrollment, List.of(classToEnroll)));
    }

    @Test
    void givenEnrollmentAndRegisteredUnavailableCourseAndRegisteredStudent_whenEnrollToClasses_thenItThrowsException() {
        String studentID = UUID.randomUUID().toString();
        String classID = UUID.randomUUID().toString();
        String registeredEnrollmentID = UUID.randomUUID().toString();

        Student student = Student.builder()
                .id(studentID)
                .userName("student.test")
                .email("student.test@email.com")
                .build();

        CourseClass classToEnroll = CourseClass.builder()
                .id(classID)
                .enrolledStudents(5)
                .capacity(15)
                .available(false)
                .build();

        Enrollment newEnrollment = Enrollment.builder()
                .student(student)
                .courseClasses(Set.of(classToEnroll))
                .semester(1)
                .build();

        Enrollment registeredEnrollment = Enrollment.builder()
                .id(registeredEnrollmentID)
                .student(student)
                .build();

        when(studentRepository.exists(studentID)).thenReturn(true);
        when(enrollmentRepository.findByStudent(studentID)).thenReturn(Optional.of(registeredEnrollment));
        when(classRepository.exists(classID)).thenReturn(true);
        when(classRepository.isAvailable(classID)).thenReturn(false);

        assertThrows(ClassNotAvailableException.class, () -> enrollmentService.enrollToClasses(newEnrollment, List.of(classToEnroll)));
    }

    @Test
    void givenEnrollments_whenFindAll_thenItReturnsListOfEnrollments() {
        String enrollmentID1 = UUID.randomUUID().toString();
        String enrollmentID2 = UUID.randomUUID().toString();
        String enrollmentID3 = UUID.randomUUID().toString();
        String enrollmentID4 = UUID.randomUUID().toString();

        Enrollment enrollment1 = Enrollment.builder()
                .id(enrollmentID1)
                .build();

        Enrollment enrollment2 = Enrollment.builder()
                .id(enrollmentID2)
                .build();

        Enrollment enrollment3 = Enrollment.builder()
                .id(enrollmentID3)
                .build();

        Enrollment enrollment4 = Enrollment.builder()
                .id(enrollmentID4)
                .build();

        List<Enrollment> enrollments = List.of(enrollment1, enrollment2, enrollment3, enrollment4);

        when(enrollmentRepository.findAll()).thenReturn(enrollments);

        List<Enrollment> responseList = enrollmentService.findAll();
        assertEquals(4, responseList.size());
        assertEquals(enrollmentID1, responseList.get(0).getId());
        assertEquals(enrollmentID2, responseList.get(1).getId());
        assertEquals(enrollmentID3, responseList.get(2).getId());
        assertEquals(enrollmentID4, responseList.get(3).getId());
    }

    @Test
    void givenStudentAndEnrollmentAssociated_whenFindByStudent_thenItReturnsTheEnrollment() throws ResourceNotFoundException {
        String studentID = UUID.randomUUID().toString();
        String enrollmentID = UUID.randomUUID().toString();

        Student student = Student.builder()
                .id(studentID)
                .build();

        Enrollment enrollment = Enrollment.builder()
                .id(enrollmentID)
                .student(student)
                .build();

        when(studentRepository.exists(studentID)).thenReturn(true);
        when(enrollmentRepository.findByStudent(studentID)).thenReturn(Optional.of(enrollment));

        assertDoesNotThrow(() -> enrollmentService.findByStudent(studentID));
        Enrollment responseEnrollment = enrollmentService.findByStudent(studentID);
        assertEquals(enrollmentID, responseEnrollment.getId());
    }

    @Test
    void givenUnregisteredStudent_whenFindByStudent_thenItReturnsTheEnrollment() {
        String unregisteredStudentID = UUID.randomUUID().toString();

        when(studentRepository.exists(unregisteredStudentID)).thenReturn(false);
        when(enrollmentRepository.findByStudent(unregisteredStudentID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.findByStudent(unregisteredStudentID));
    }

    @Test
    void givenRegisteredStudentWithoutEnrollment_whenFindByStudent_thenItReturnsTheEnrollment() throws ResourceNotFoundException {
        String studentID = UUID.randomUUID().toString();

        when(studentRepository.exists(studentID)).thenReturn(true);
        when(enrollmentRepository.findByStudent(studentID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.findByStudent(studentID));
    }

}
