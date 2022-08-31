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
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.model.Role;
import training.path.academicrecordsystem.model.User;
import training.path.academicrecordsystem.repositories.implementations.ProfessorRepository;
import training.path.academicrecordsystem.repositories.implementations.UserRepository;
import training.path.academicrecordsystem.security.interfaces.IRoles;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfessorRepositoryTest {

    @Autowired
    private UserRepository userRepository;

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
    @Order(1)
    void givenNewProfessorInformation_whenSave_thenANewRecordIsStoredInDB() {
        Optional<Role> roleOptional = userRepository.findRoleByName("ROLE_" + IRoles.PROFESSOR);
        assertTrue(roleOptional.isPresent());

        Role role = roleOptional.get();
        String newProfessorID = UUID.randomUUID().toString();

        Professor newProfessor = Professor.builder()
                .id(newProfessorID)
                .firstName("John")
                .lastName("Smith")
                .userName("john.smith")
                .email("john.smith@email.com")
                .password("password")
                .role(role)
                .salary(80000f)
                .build();

        assertEquals(1, professorRepository.save(newProfessor));

        assertTrue(professorRepository.findById(newProfessorID).isPresent());
    }

    @Test
    void givenRegisteredProfessorID_whenFindById_thenTheProfessorIdentifiedByThatIdIsReturned() {
        Optional<User> professorOptional = userRepository.findByUserName("professor1.test1");
        assertTrue(professorOptional.isPresent());
        Professor registeredProfessor = (Professor) professorOptional.get();

        Optional<Professor> foundProfessorOptional = professorRepository.findById(registeredProfessor.getId());
        assertTrue(foundProfessorOptional.isPresent());

        Professor foundProfessor = foundProfessorOptional.get();
        assertEquals("professor1.test1", foundProfessor.getUserName());
    }

    @Test
    void givenUnregisteredProfessorID_whenFindById_thenAnEmptyOptionalIsReturned() {
        String unregisteredProfessorID = UUID.randomUUID().toString();

        Optional<Professor> foundProfessorOptional = professorRepository.findById(unregisteredProfessorID);
        assertTrue(foundProfessorOptional.isEmpty());
    }

    @Test
    void givenRegisteredProfessor_whenUpdate_thenTheProfessorRecordIsUpdated() {
        Optional<User> professorOptional = userRepository.findByUserName("professor1.test1");
        assertTrue(professorOptional.isPresent());
        Professor professorToUpdate = (Professor) professorOptional.get();

        professorToUpdate.setSalary(0f);

        assertEquals(1, professorRepository.update(professorToUpdate.getId(), professorToUpdate));

        Optional<Professor> professorUpdatedOptional = professorRepository.findById(professorToUpdate.getId());
        assertTrue(professorUpdatedOptional.isPresent());

        Professor professorUpdated = professorUpdatedOptional.get();
        assertEquals(0f, professorUpdated.getSalary());
    }

    @Test
    void givenRegisteredProfessors_whenFindAll_thenAListOfProfessorsIsReturned() {
        List<Professor> professors = professorRepository.findAll();

        assertEquals(4, professors.size());
    }

    @Test
    void givenRegisteredProfessors_whenFindAllWithPagination_thenAListOfProfessorsIsReturned() {
        int limit = 2;
        int offset = 1;
        List<Professor> professors = professorRepository.findAll(limit, offset);

        assertEquals(2, professors.size());
    }

    @Test
    void givenProfessorWithClasses_whenFindClasses_thenAListOfCourseClassesIsReturned() {
        Optional<User> professorOptional = userRepository.findByUserName("professor1.test1");
        assertTrue(professorOptional.isPresent());
        Professor professor = (Professor) professorOptional.get();

        List<CourseClass> classes = professorRepository.findClasses(professor.getId());
        assertEquals(2, classes.size());
        assertTrue(classes.stream().allMatch(courseClass -> Objects.equals(courseClass.getProfessor().getId(), professor.getId())));
    }

}
