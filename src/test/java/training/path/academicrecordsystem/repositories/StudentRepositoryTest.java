package training.path.academicrecordsystem.repositories;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import training.path.academicrecordsystem.model.*;
import training.path.academicrecordsystem.repositories.implementations.CareerRepository;
import training.path.academicrecordsystem.repositories.implementations.EnrollmentRepository;
import training.path.academicrecordsystem.repositories.implementations.StudentRepository;
import training.path.academicrecordsystem.repositories.implementations.UserRepository;
import training.path.academicrecordsystem.security.interfaces.IRoles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

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
    @Order(1)
    void givenStudentInfoToSave_whenSaveAndFindById_thenANewRecordIsStoredInDBAndReturnsThatStudent() {
        Optional<Career> careerOptional = careerRepository.findByName("Medicine");
        assertTrue(careerOptional.isPresent());
        Career career = careerOptional.get();

        Role role = userRepository.findRoleByName("ROLE_" + IRoles.STUDENT).orElseThrow();

        String newStudentID = UUID.randomUUID().toString();
        Student newStudent = Student.builder()
                .id(newStudentID)
                .firstName("John")
                .lastName("Doe")
                .userName("john.doe")
                .email("john.doe@emial.com")
                .password("12345678")
                .career(career)
                .role(role)
                .averageGrade(0.0f)
                .build();

        Enrollment newStudentEnrollment = Enrollment.builder()
                .id(UUID.randomUUID().toString())
                .student(newStudent)
                .build();

        assertEquals(1, studentRepository.save(newStudent));
        assertEquals(1, enrollmentRepository.save(newStudentEnrollment));

        Optional<Student> studentOptional = studentRepository.findById(newStudentID);
        assertTrue(studentOptional.isPresent());

    }

    @Test
    void givenStudentID_whenFindById_thenTheStudentIdentifiedByThatIdIsReturned() {
        User user = userRepository.findByUserName("student1.test1").orElseThrow();
        Optional<Student> studentOptional = studentRepository.findById(user.getId());
        assertTrue(studentOptional.isPresent());
    }

    @Test
    void givenUpdatedStudentInformation_whenUpdate_thenTheStudentRecordInDBIsUpdated() {
        User user = userRepository.findByUserName("student1.test1").orElseThrow();

        Optional<Student> studentOptional = studentRepository.findById(user.getId());
        assertTrue(studentOptional.isPresent());
        Student studentToUpdate = studentOptional.get();
        studentToUpdate.setAverageGrade(5.0f);

        assertEquals(1, studentRepository.update(studentToUpdate.getId(), studentToUpdate));

        Optional<Student> updatedStudentOptional = studentRepository.findById(user.getId());
        assertTrue(updatedStudentOptional.isPresent());
        Student updatedStudent = studentOptional.get();

        assertEquals(5.0f, updatedStudent.getAverageGrade());
    }

    @Test
    void givenRegisteredStudents_whenFindAll_thenAListOfStudentsIsReturned() {
        List<Student> students = studentRepository.findAll();

        assertEquals(4, students.size());
    }

    @Test
    void givenRegisteredStudents_whenFindAllWithPagination_thenAListOfStudentsIsReturned() {
        int limit = 2;
        int offset = 1;
        List<Student> students = studentRepository.findAll(limit, offset);

        assertEquals(2, students.size());
    }

    @Test
    void givenStudentEnrolledToClasses_whenFindEnrollmentInfo_thenTheEnrollmentInfoWithClassesIsReturned() {
        User user = userRepository.findByUserName("student1.test1").orElseThrow();

        List<Enrollment> enrollments = studentRepository.findEnrollmentInfo(user.getId());

        assertEquals(1, enrollments.size());
        assertTrue(enrollments.stream().anyMatch(enrollment -> enrollment.getCourseClasses().size() == 1));
        assertTrue(enrollments.stream().anyMatch(enrollment -> enrollment.getSemester() == 1));
    }

    @Test
    void givenStudentWithoutClasses_whenFindEnrollmentInfo_thenTheEnrollmentInfoWithoutClassesIsReturned() {
        User user = userRepository.findByUserName("student2.test2").orElseThrow();

        List<Enrollment> enrollments = studentRepository.findEnrollmentInfo(user.getId());

        assertEquals(1, enrollments.size());
        assertTrue(enrollments.stream().anyMatch(enrollment -> enrollment.getCourseClasses().size() == 0));
        assertTrue(enrollments.stream().anyMatch(enrollment -> enrollment.getSemester() == 0));
    }

    @Test
    void givenStudentEnrolledToClassesInASemester_whenFindEnrollmentsBySemester_thenTheEnrollmentInfoWithClassesPerSemesterIsReturned() {
        User user = userRepository.findByUserName("student1.test1").orElseThrow();
        int semester = 1;

        Optional<Enrollment> enrollmentOptional = studentRepository.findEnrollmentsBySemester(user.getId(), semester);
        assertTrue(enrollmentOptional.isPresent());

        Enrollment enrollment = enrollmentOptional.get();
        assertEquals(1, enrollment.getSemester());
        assertEquals(1, enrollment.getCourseClasses().size());
    }

}
