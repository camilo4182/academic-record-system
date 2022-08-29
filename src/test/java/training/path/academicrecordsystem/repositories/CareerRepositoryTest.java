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
import training.path.academicrecordsystem.repositories.implementations.CareerRepository;

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
    @Order(1)
    void givenValidCareerInformation_whenSave_thenTheCareerIsStoredInTheDB() {
        String careerID = UUID.randomUUID().toString();
        Career career = setupCareer(careerID, "Medicine");

        assertEquals(1, careerRepository.save(career));
    }

    @Test
    @Order(2)
    void givenRegisteredCareer_whenFindByName_thenTheCareerWithThatNameIsReturned() {
        String careerName = "Medicine";

        Optional<Career> careerOptional = careerRepository.findByName(careerName);
        assertTrue(careerOptional.isPresent());

        Career foundCareer = careerOptional.get();
        assertEquals("Medicine", foundCareer.getName());
    }

    @Test
    @Order(3)
    void givenUnregisteredCareer_whenFindByName_thenAnEmptyOptionalIsReturned() {
        String careerName = "Art";

        Optional<Career> careerOptional = careerRepository.findByName(careerName);
        assertTrue(careerOptional.isEmpty());
    }

    @Test
    @Order(4)
    void givenSavedCareer_whenFindById_thenTheCareerIdentifiedByThatIdIsReturned() {
        String careerID = UUID.randomUUID().toString();
        Career careerToSave = setupCareer(careerID, "Software Engineering");
        careerRepository.save(careerToSave);

        Optional<Career> careerOptional = careerRepository.findById(careerID);
        assertTrue(careerOptional.isPresent());

        Career foundCareer = careerOptional.get();
        assertEquals("Software Engineering", foundCareer.getName());
    }

    @Test
    @Order(5)
    void givenUpdatedCareerInformation_whenUpdate_thenTheTableRecordIsUpdatedInDB() {
        String careerID = UUID.randomUUID().toString();
        Career careerToSave = setupCareer(careerID, "International Finance");
        careerRepository.save(careerToSave);

        Career careerToUpdate = setupCareer(careerID, "Economics");
        assertEquals(1, careerRepository.update(careerID, careerToUpdate));

        Optional<Career> careerOptional = careerRepository.findById(careerID);
        assertTrue(careerOptional.isPresent());

        Career updatedCareer = careerOptional.get();
        assertEquals("Economics", updatedCareer.getName());
    }

    @Test
    @Order(6)
    void givenValidCareerId_whenDeleteById_thenTheCareerIsDeletedFromDB() {
        String careerID = UUID.randomUUID().toString();
        Career careerToDelete = setupCareer(careerID, "Philosophy");
        careerRepository.save(careerToDelete);

        assertEquals(1, careerRepository.deleteById(careerID));
        assertTrue(careerRepository.findById(careerID).isEmpty());
    }

    @Test
    @Order(7)
    void givenRegisteredCareers_whenFindAll_thenAListOfCareersIsReturned() {
        List<Career> careers = careerRepository.findAll();

        assertFalse(careers.isEmpty());
        assertEquals(3, careers.size());
    }

    @Test
    @Order(8)
    void givenRegisteredCareers_whenFindAllWithPagination_thenAListOfCareersIsReturned() {
        int limit = 1;
        int offset = 1;
        List<Career> careers = careerRepository.findAll(limit, offset);

        assertFalse(careers.isEmpty());
        assertEquals(1, careers.size());
    }

    @Test
    @Order(9)
    void givenRegisteredCareer_whenCallingExistsMethod_thenItReturnsTrue() {
        Optional<Career> careerOptional = careerRepository.findByName("Medicine");
        assertTrue(careerOptional.isPresent());
        Career career = careerOptional.get();

        assertTrue(careerRepository.exists(career.getId()));
    }

    @Test
    @Order(10)
    void givenUnregisteredCareer_whenCallingExistsMethod_thenItReturnsFalse() {
        String unregisteredCareerID = UUID.randomUUID().toString();

        assertFalse(careerRepository.exists(unregisteredCareerID));
    }

}
