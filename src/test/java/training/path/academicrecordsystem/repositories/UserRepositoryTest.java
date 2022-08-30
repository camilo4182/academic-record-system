package training.path.academicrecordsystem.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.model.Role;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.model.User;
import training.path.academicrecordsystem.repositories.implementations.ProfessorRepository;
import training.path.academicrecordsystem.repositories.implementations.StudentRepository;
import training.path.academicrecordsystem.repositories.implementations.UserRepository;
import training.path.academicrecordsystem.security.interfaces.IRoles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

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
    void givenUpdatedProfessorData_whenUpdate_thenItUpdatesTheUsersTableRecordInDB() {
        Optional<User> professorOptional = userRepository.findByUserName("professor1.test1");
        assertTrue(professorOptional.isPresent());
        User professorToUpdate = professorOptional.get();
        professorToUpdate.setFirstName("John");
        professorToUpdate.setLastName("Doe");
        professorToUpdate.setUserName("john.doe");

        assertEquals(1, userRepository.update(professorToUpdate.getId(), professorToUpdate));

        Optional<Professor> updatedProfessorOptional = professorRepository.findById(professorToUpdate.getId());
        assertTrue(updatedProfessorOptional.isPresent());
        User updatedProfessor = updatedProfessorOptional.get();
        assertEquals("John", updatedProfessor.getFirstName());
        assertEquals("Doe", updatedProfessor.getLastName());
        assertEquals("john.doe", updatedProfessor.getUserName());
    }

    @Test
    void givenUpdatedStudentData_whenUpdate_thenItUpdatesTheUsersTableRecordInDB() {
        Optional<User> studentOptional = userRepository.findByUserName("student1.test1");
        assertTrue(studentOptional.isPresent());
        User studentToUpdate = studentOptional.get();
        studentToUpdate.setFirstName("Jane");
        studentToUpdate.setLastName("Doe");
        studentToUpdate.setUserName("jane.doe");

        assertEquals(1, userRepository.update(studentToUpdate.getId(), studentToUpdate));

        Optional<Student> updatedStudentOptional = studentRepository.findById(studentToUpdate.getId());
        assertTrue(updatedStudentOptional.isPresent());
        User updatedStudent = studentOptional.get();
        assertEquals("Jane", updatedStudent.getFirstName());
        assertEquals("Doe", updatedStudent.getLastName());
        assertEquals("jane.doe", updatedStudent.getUserName());
    }

    @Test
    void givenUserId_whenDeleteById_thenTheUserRecordIsDeletedFromDB() {
        Optional<User> studentOptional = userRepository.findByUserName("student3.test3");
        assertTrue(studentOptional.isPresent());
        User studentToDelete = studentOptional.get();

        assertEquals(1, userRepository.deleteById(studentToDelete.getId()));
    }

    @Test
    void givenRegisteredUser_whenFindByUserName_thenTheUserWithThatUsernameIsReturned() {
        String registeredUsername = "student2.test2";

        Optional<User> userOptional = userRepository.findByUserName(registeredUsername);
        assertTrue(userOptional.isPresent());
    }

    @Test
    void givenUnregisteredUser_whenFindByUserName_thenAnEmptyOptionalIsReturned() {
        String unregisteredUsername = "unregistered.user";

        Optional<User> userOptional = userRepository.findByUserName(unregisteredUsername);
        assertTrue(userOptional.isEmpty());
    }

    @Test
    void givenRegisteredEmail_whenFindByEmail_thenTheUserAssociatedWithThatEmailIsReturned() {
        String registeredEmail = "student1@email.com";

        Optional<User> userOptional = userRepository.findByEmail(registeredEmail);
        assertTrue(userOptional.isPresent());
    }

    @Test
    void givenUnregisteredEmail_whenFindByEmail_thenAnEmptyOptionalIsReturned() {
        String registeredEmail = "unregistered@email.com";

        Optional<User> userOptional = userRepository.findByEmail(registeredEmail);
        assertTrue(userOptional.isEmpty());
    }

    @Test
    void givenRegisteredRole_whenFindRoleByName_thenTheRoleIsReturned() {
        Optional<Role> roleOptional = userRepository.findRoleByName("ROLE_" + IRoles.PROFESSOR);
        assertTrue(roleOptional.isPresent());
    }

    @Test
    void givenUnregisteredRole_whenFindRoleByName_thenAnEmptyOptionalIsReturned() {
        Optional<Role> roleOptional = userRepository.findRoleByName("ROLE_ASSISTANT");
        assertTrue(roleOptional.isEmpty());
    }

    @Test
    void givenRegisteredUser_whenCallingExistsMethod_thenItReturnsTrue() {
        String registeredUsername = "student1.test1";
        Optional<User> userOptional = userRepository.findByUserName(registeredUsername);
        assertTrue(userOptional.isPresent());
        Student registeredStudent = (Student) userOptional.get();

        assertTrue(userRepository.exists(registeredStudent.getId()));
    }

    @Test
    void givenUnregisteredUser_whenCallingExistsMethod_thenItReturnsFalse() {
        String unregisteredUserID = UUID.randomUUID().toString();
        assertFalse(userRepository.exists(unregisteredUserID));
    }

}
