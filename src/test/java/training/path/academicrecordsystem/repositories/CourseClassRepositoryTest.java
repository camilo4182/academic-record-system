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
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.model.User;
import training.path.academicrecordsystem.repositories.implementations.CourseClassRepository;
import training.path.academicrecordsystem.repositories.implementations.CourseRepository;
import training.path.academicrecordsystem.repositories.implementations.ProfessorRepository;
import training.path.academicrecordsystem.repositories.implementations.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseClassRepositoryTest {

    @Autowired
    private CourseClassRepository classRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private UserRepository userRepository;

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
    void givenCourseClass_whenSave_thenAClassIsStoredInDB() {
        Optional<Course> courseOptional = courseRepository.findByName("Data Bases");
        assertTrue(courseOptional.isPresent());
        Course course = courseOptional.get();

        Optional<User> professorOptional = userRepository.findByUserName("professor2.test2");
        assertTrue(professorOptional.isPresent());
        Professor professor = (Professor) professorOptional.get();

        CourseClass newClass = CourseClass.builder()
                .id(UUID.randomUUID().toString())
                .course(course)
                .professor(professor)
                .capacity(30)
                .enrolledStudents(3)
                .available(true)
                .build();

        assertEquals(1, classRepository.save(newClass));
    }

    @Test
    void givenUpdatedClassInformation_whenUpdate_thenTheRecordIsUpdatedInDB() {
        Optional<Course> courseOptional = courseRepository.findByName("Data Bases");
        assertTrue(courseOptional.isPresent());
        Course course = courseOptional.get();
        List<CourseClass> classes = courseRepository.findClassesByCourse(course.getId());
        CourseClass classToUpdate = classes.get(0);
        classToUpdate.setEnrolledStudents(30);
        classToUpdate.setAvailable(false);

        assertEquals(1, classRepository.update(classToUpdate.getId(), classToUpdate));
    }

    @Test
    void givenClassId_whenDeleteById_thenTheRecordIsDeletedFromDB() {
        Optional<Course> courseOptional = courseRepository.findByName("Cellular Biology");
        assertTrue(courseOptional.isPresent());
        Course course = courseOptional.get();

        Optional<User> professorOptional = userRepository.findByUserName("professor1.test1");
        assertTrue(professorOptional.isPresent());
        Professor professor = (Professor) professorOptional.get();

        CourseClass newClass = CourseClass.builder()
                .id(UUID.randomUUID().toString())
                .course(course)
                .professor(professor)
                .capacity(20)
                .enrolledStudents(5)
                .available(true)
                .build();
        classRepository.save(newClass);

        assertEquals(1, classRepository.deleteById(newClass.getId()));
    }

    @Test
    void givenClassId_whenFindById_thenTheClassIdentifiedByThatIdIsReturned() {
        Optional<Course> courseOptional = courseRepository.findByName("Cellular Biology");
        assertTrue(courseOptional.isPresent());
        Course course = courseOptional.get();
        List<CourseClass> classes = courseRepository.findClassesByCourse(course.getId());
        CourseClass classToFind = classes.get(0);

        Optional<CourseClass> classOptional = classRepository.findById(classToFind.getId());
        assertTrue(classOptional.isPresent());
    }

    @Test
    void givenRegisteredClasses_whenFindAll_thenAListOfCourseClassesIsReturned() {
        List<CourseClass> classes = classRepository.findAll();

        assertEquals(6, classes.size());
    }

    @Test
    void givenRegisteredClasses_whenFindAllWithPagination_thenAListOfCourseClassesIsReturned() {
        int limit = 2;
        int offset = 1;
        List<CourseClass> classes = classRepository.findAll(limit, offset);

        assertEquals(2, classes.size());
    }

    @Test
    void givenRegisteredAvailableAndUnavailableClasses_whenFindAllAvailable_thenAListOfAvailableCourseClassesIsReturned() {
        List<CourseClass> classes = classRepository.findAllAvailable();

        assertEquals(5, classes.size());
        assertTrue(classes.stream().allMatch(CourseClass::isAvailable));
    }

    @Test
    void givenRegisteredAvailableAndUnavailableClasses_whenFindAllUnavailable_thenAListOfUnavailableCourseClassesIsReturned() {
        List<CourseClass> classes = classRepository.findAllUnavailable();

        assertEquals(1, classes.size());
        assertTrue(classes.stream().noneMatch(CourseClass::isAvailable));
    }

    @Test
    void givenRegisteredClassID_whenCallingExistsMethod_thenItReturnsTrue() {
        Optional<Course> courseOptional = courseRepository.findByName("Algorithms I");
        assertTrue(courseOptional.isPresent());
        Course course = courseOptional.get();
        List<CourseClass> classes = courseRepository.findClassesByCourse(course.getId());
        CourseClass classToFind = classes.get(0);

        assertTrue(classRepository.exists(classToFind.getId()));
    }

    @Test
    void givenUnregisteredClassID_whenCallingExistsMethod_thenItReturnsFalse() {
        String unregisteredClassID = UUID.randomUUID().toString();

        assertFalse(classRepository.exists(unregisteredClassID));
    }

    @Test
    void givenAvailableClass_whenCheckingIfItsAvailable_thenItReturnsTrue() {
        Optional<Course> courseOptional = courseRepository.findByName("Algorithms I");
        assertTrue(courseOptional.isPresent());
        Course course = courseOptional.get();
        List<CourseClass> classes = courseRepository.findClassesByCourse(course.getId());
        CourseClass classToFind = classes.get(0);

        assertTrue(classRepository.isAvailable(classToFind.getId()));
    }

}
