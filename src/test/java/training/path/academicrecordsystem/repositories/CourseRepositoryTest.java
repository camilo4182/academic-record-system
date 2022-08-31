package training.path.academicrecordsystem.repositories;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.repositories.implementations.CourseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseRepositoryTest {

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

    private Course setupCourse(String id, String name) {
        return Course.builder()
                .id(id)
                .name(name)
                .build();
    }

    @Test
    @Order(1)
    void givenValidCourseInformation_whenSave_thenTheCourseIsStoredInTheDB() {
        String courseID = UUID.randomUUID().toString();
        Course course = setupCourse(courseID, "Algorithms II");

        assertEquals(1, courseRepository.save(course));
    }

    @Test
    @Order(2)
    void givenRegisteredCourse_whenFindByName_thenTheCourseWithThatNameIsReturned() {
        String courseName = "Algorithms I";

        Optional<Course> courseOptional = courseRepository.findByName(courseName);
        assertTrue(courseOptional.isPresent());

        Course foundCourse = courseOptional.get();
        assertEquals("Algorithms I", foundCourse.getName());
    }

    @Test
    @Order(3)
    void givenUnregisteredCourse_whenFindByName_thenAnEmptyOptionalIsReturned() {
        String courseName = "Art";

        Optional<Course> courseOptional = courseRepository.findByName(courseName);
        assertTrue(courseOptional.isEmpty());
    }

    @Test
    @Order(4)
    void givenRegisteredCourse_whenFindById_thenTheCourseIdentifiedByThatIdIsReturned() {
        Course registeredCourse = courseRepository.findByName("Algebra").orElseThrow();
        String courseID = registeredCourse.getId();

        Optional<Course> courseOptional = courseRepository.findById(courseID);
        assertTrue(courseOptional.isPresent());

        Course foundCourse = courseOptional.get();
        assertEquals("Algebra", foundCourse.getName());
    }

    @Test
    @Order(5)
    void givenUpdatedCourseInformation_whenUpdate_thenTheTableRecordIsUpdatedInDB() {
        Course registeredCourse = courseRepository.findByName("Calculus I").orElseThrow();
        String courseID = registeredCourse.getId();

        Course courseToUpdate = setupCourse(courseID, "Differential Calculus");
        assertEquals(1, courseRepository.update(courseID, courseToUpdate));

        Optional<Course> courseOptional = courseRepository.findById(courseID);
        assertTrue(courseOptional.isPresent());

        Course updatedCourse = courseOptional.get();
        assertEquals("Differential Calculus", updatedCourse.getName());
    }

    @Test
    @Order(6)
    void givenValidCourseId_whenDeleteById_thenTheCourseIsDeletedFromDB() {
        String courseID = UUID.randomUUID().toString();
        Course courseToDelete = setupCourse(courseID, "Introduction to engineering");
        courseRepository.save(courseToDelete);

        assertEquals(1, courseRepository.deleteById(courseID));
        assertTrue(courseRepository.findById(courseID).isEmpty());
    }

    @Test
    @Order(7)
    void givenRegisteredCourses_whenFindAll_thenAListOfCoursesIsReturned() {
        List<Course> courses = courseRepository.findAll();

        assertFalse(courses.isEmpty());
        assertEquals(8, courses.size());
    }

    @Test
    @Order(8)
    void givenRegisteredCourses_whenFindAllWithPagination_thenAListOfCoursesIsReturned() {
        int limit = 4;
        int offset = 1;
        List<Course> courses = courseRepository.findAll(limit, offset);

        assertFalse(courses.isEmpty());
        assertEquals(4, courses.size());
    }

    @Test
    @Order(9)
    void givenRegisteredCourse_whenCallingExistsMethod_thenItReturnsTrue() {
        Optional<Course> courseOptional = courseRepository.findByName("Data Bases");
        assertTrue(courseOptional.isPresent());
        Course course = courseOptional.get();

        assertTrue(courseRepository.exists(course.getId()));
    }

    @Test
    @Order(10)
    void givenUnregisteredCourse_whenCallingExistsMethod_thenItReturnsFalse() {
        String unregisteredCourseID = UUID.randomUUID().toString();

        assertFalse(courseRepository.exists(unregisteredCourseID));
    }

    @Test
    @Order(11)
    void givenACourseWithClasses_whenFindClassesByCourse_thenAListOfClassesIsReturned() {
        Course course = courseRepository.findByName("Algorithms I").orElseThrow();

        List<CourseClass> classes = courseRepository.findClassesByCourse(course.getId());

        assertFalse(classes.isEmpty());
        assertEquals(2, classes.size());
    }
}
