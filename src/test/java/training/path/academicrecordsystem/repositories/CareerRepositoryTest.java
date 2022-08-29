package training.path.academicrecordsystem.repositories;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import training.path.academicrecordsystem.config.WithMockCustomUser;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.repositories.implementations.CareerRepository;
import training.path.academicrecordsystem.repositories.implementations.CourseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CareerRepositoryTest {

    @Autowired
    private CareerRepository careerRepository;

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

    private Career setupCareer(String id, String name) {
        return Career.builder()
                .id(id)
                .name(name)
                .build();
    }

    @Test
    void givenValidCareerInformation_whenSave_thenTheCareerIsStoredInTheDB() {
        String careerID = UUID.randomUUID().toString();
        Career career = setupCareer(careerID, "Music");

        assertEquals(1, careerRepository.save(career));
    }

    @Test
    void givenRegisteredCareer_whenFindByName_thenTheCareerWithThatNameIsReturned() {
        String careerName = "Medicine";

        Optional<Career> careerOptional = careerRepository.findByName(careerName);
        assertTrue(careerOptional.isPresent());

        Career foundCareer = careerOptional.get();
        assertEquals("Medicine", foundCareer.getName());
    }

    @Test
    void givenUnregisteredCareer_whenFindByName_thenAnEmptyOptionalIsReturned() {
        String careerName = "Art";

        Optional<Career> careerOptional = careerRepository.findByName(careerName);
        assertTrue(careerOptional.isEmpty());
    }

    @Test
    void givenSavedCareer_whenFindById_thenTheCareerIdentifiedByThatIdIsReturned() {
        Career registeredCareer = careerRepository.findByName("Software Engineering").orElseThrow();
        String careerID = registeredCareer.getId();

        Optional<Career> careerOptional = careerRepository.findById(careerID);
        assertTrue(careerOptional.isPresent());

        Career foundCareer = careerOptional.get();
        assertEquals("Software Engineering", foundCareer.getName());
    }

    @Test
    void givenUpdatedCareerInformation_whenUpdate_thenTheTableRecordIsUpdatedInDB() {
        Career registeredCareer = careerRepository.findByName("Chemistry").orElseThrow();
        String careerID = registeredCareer.getId();

        Career careerToUpdate = setupCareer(careerID, "Physics");
        assertEquals(1, careerRepository.update(careerID, careerToUpdate));

        Optional<Career> careerOptional = careerRepository.findById(careerID);
        assertTrue(careerOptional.isPresent());

        Career updatedCareer = careerOptional.get();
        assertEquals("Physics", updatedCareer.getName());
    }

    @Test
    void givenValidCareerId_whenDeleteById_thenTheCareerIsDeletedFromDB() {
        String careerID = UUID.randomUUID().toString();
        Career careerToDelete = setupCareer(careerID, "Philosophy");
        careerRepository.save(careerToDelete);

        assertEquals(1, careerRepository.deleteById(careerID));
        assertTrue(careerRepository.findById(careerID).isEmpty());
    }

    @Test
    void givenRegisteredCareers_whenFindAll_thenAListOfCareersIsReturned() {
        List<Career> careers = careerRepository.findAll();

        assertFalse(careers.isEmpty());
        assertEquals(4, careers.size());
    }

    @Test
    void givenRegisteredCareers_whenFindAllWithPagination_thenAListOfCareersIsReturned() {
        int limit = 1;
        int offset = 1;
        List<Career> careers = careerRepository.findAll(limit, offset);

        assertFalse(careers.isEmpty());
        assertEquals(1, careers.size());
    }

    @Test
    void givenRegisteredCareer_whenCallingExistsMethod_thenItReturnsTrue() {
        Optional<Career> careerOptional = careerRepository.findByName("Medicine");
        assertTrue(careerOptional.isPresent());
        Career career = careerOptional.get();

        assertTrue(careerRepository.exists(career.getId()));
    }

    @Test
    void givenUnregisteredCareer_whenCallingExistsMethod_thenItReturnsFalse() {
        String unregisteredCareerID = UUID.randomUUID().toString();

        assertFalse(careerRepository.exists(unregisteredCareerID));
    }

    @Test
    void givenCareerWithCourses_whenFindCoursesByCareer_thenAListWithCoursesIsReturned() {
        Optional<Career> careerOptional = careerRepository.findByName("Software Engineering");
        assertTrue(careerOptional.isPresent());
        Career registeredCareer = careerOptional.get();

        List<Course> courses = careerRepository.findCoursesByCareer(registeredCareer.getId());
        assertFalse(courses.isEmpty());
        assertEquals(4, courses.size());
    }

    @Test
    void givenCourseAndCareer_whenAssignCourseToCareer_thenARecordIsSavedInTheCareerCoursesTable() {
        Optional<Career> careerOptional = careerRepository.findByName("Medicine");
        assertTrue(careerOptional.isPresent());
        String careerID = careerOptional.get().getId();

        Optional<Course> courseOptional = courseRepository.findByName("Algorithms I");
        assertTrue(courseOptional.isPresent());
        String courseID = courseOptional.get().getId();

        careerRepository.assignCourseToCareer(courseID, careerID);

        List<Course> courses = careerRepository.findCoursesByCareer(careerID);
        assertEquals(3, courses.size());
        assertTrue(courses.stream().anyMatch(course -> "Algorithms I".equals(course.getName())));
    }

}
