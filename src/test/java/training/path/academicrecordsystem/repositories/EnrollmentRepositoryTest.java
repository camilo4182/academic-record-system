package training.path.academicrecordsystem.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import training.path.academicrecordsystem.model.*;
import training.path.academicrecordsystem.repositories.implementations.CourseRepository;
import training.path.academicrecordsystem.repositories.implementations.EnrollmentRepository;
import training.path.academicrecordsystem.repositories.implementations.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class EnrollmentRepositoryTest {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14-alpine")
            .withDatabaseName("academic-record-system")
            .withUsername("postgres")
            .withPassword("password");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        System.out.println("JDBC: " + postgreSQLContainer.getJdbcUrl());
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username",postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void givenRegisteredStudent_whenFindByStudent_thenTheEnrollmentAssociatedToThatStudentIsReturned() {
        Optional<User> userOptional = userRepository.findByUserName("student1.test1");
        assertTrue(userOptional.isPresent());

        Student student = (Student) userOptional.get();

        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByStudent(student.getId());
        assertTrue(enrollmentOptional.isPresent());

        Enrollment foundEnrollment = enrollmentOptional.get();
        assertEquals(student.getId(), foundEnrollment.getStudent().getId());
    }

    @Test
    void givenRegisteredStudentWithEnrollment_whenFindById_thenTheEnrollmentAssociatedToThatStudentIsReturned() {
        Optional<User> userOptional = userRepository.findByUserName("student1.test1");
        assertTrue(userOptional.isPresent());

        Student student = (Student) userOptional.get();

        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByStudent(student.getId());
        assertTrue(enrollmentOptional.isPresent());

        Enrollment foundEnrollment = enrollmentOptional.get();

        Optional<Enrollment> enrollmentOptionalByID = enrollmentRepository.findById(foundEnrollment.getId());
        assertTrue(enrollmentOptionalByID.isPresent());

        Enrollment foundEnrollmentByID = enrollmentOptionalByID.get();
        assertEquals(foundEnrollment.getId(), foundEnrollmentByID.getId());
    }

    @Test
    void givenStudentsWithEnrollmentsToCourses_whenFindAll_thenAListOfEnrollmentsWithCourseClassesIsReturned() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();

        assertEquals(1, enrollments.size());
    }

    @Test
    void givenRegisteredEnrollmentID_whenCallingExistsMethod_thenItReturnsTrue() {
        Optional<User> userOptional = userRepository.findByUserName("student1.test1");
        assertTrue(userOptional.isPresent());

        Student student = (Student) userOptional.get();

        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByStudent(student.getId());
        assertTrue(enrollmentOptional.isPresent());

        Enrollment foundEnrollment = enrollmentOptional.get();

        assertTrue(enrollmentRepository.exists(foundEnrollment.getId()));
    }

    @Test
    void givenUnregisteredEnrollmentID_whenCallingExistsMethod_thenItReturnsFalse() {
        String unregisteredEnrollmentID = UUID.randomUUID().toString();

        assertFalse(enrollmentRepository.exists(unregisteredEnrollmentID));
    }

    @Test
    void givenStudentAlreadyEnrolledToTheSpecifiedClass_whenCallingStudentAlreadyEnrolledMethod_thenItReturnsTrue() {
        Optional<User> userOptional = userRepository.findByUserName("student1.test1");
        assertTrue(userOptional.isPresent());

        Student student = (Student) userOptional.get();

        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findAll().stream().filter(
                enrollment -> Objects.equals(enrollment.getStudent().getId(), student.getId())
        ).findFirst();
        assertTrue(enrollmentOptional.isPresent());
        Enrollment enrollmentOfStudent1 = enrollmentOptional.get();

        CourseClass alreadyEnrolledClass = enrollmentOfStudent1.getCourseClasses().get(0);

        assertTrue(enrollmentRepository.studentAlreadyEnrolled(enrollmentOfStudent1.getId(), alreadyEnrolledClass.getId()));
    }

    @Test
    void givenStudentNotEnrolledToTheSpecifiedClass_whenCallingStudentAlreadyEnrolledMethod_thenItReturnsFalse() {
        Optional<User> userOptional = userRepository.findByUserName("student1.test1");
        assertTrue(userOptional.isPresent());

        Student student = (Student) userOptional.get();

        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findAll().stream().filter(
                enrollment -> Objects.equals(enrollment.getStudent().getId(), student.getId())
        ).findFirst();
        assertTrue(enrollmentOptional.isPresent());
        Enrollment enrollmentOfStudent1 = enrollmentOptional.get();

        assertFalse(enrollmentRepository.studentAlreadyEnrolled(enrollmentOfStudent1.getId(), UUID.randomUUID().toString()));
    }

    @Test
    void givenEnrollmentOfAStudentAndAClass_whenEnrollToClass_theRecordIsStoredInDB() {
        Optional<User> userOptional = userRepository.findByUserName("student1.test1");
        assertTrue(userOptional.isPresent());

        Student student = (Student) userOptional.get();

        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findAll().stream().filter(
                enrollment -> Objects.equals(enrollment.getStudent().getId(), student.getId())
        ).findFirst();
        assertTrue(enrollmentOptional.isPresent());
        Enrollment enrollmentOfStudent1 = enrollmentOptional.get();

        Course registeredCourse = courseRepository.findByName("Cellular Biology").orElseThrow();
        List<CourseClass> classes = courseRepository.findClassesByCourse(registeredCourse.getId());
        assertFalse(classes.isEmpty());

        assertEquals(2, enrollmentRepository.enrollToClass(enrollmentOfStudent1, classes.get(0)));
    }

}
