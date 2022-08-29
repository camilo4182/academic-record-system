package training.path.academicrecordsystem.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import training.path.academicrecordsystem.model.Role;
import training.path.academicrecordsystem.model.User;
import training.path.academicrecordsystem.repositories.implementations.UserRepository;
import training.path.academicrecordsystem.security.interfaces.IRoles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class UserRepositoryTest {

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
    void givenRegisteredUser_whenFindByUserName_thenTheUserWithThatUsernameIsReturned() {
        String registeredUsername = "student3.test3";

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
    void givenRegisteredRole_whenFindRoleByName_thenTheRoleIsReturned() {
        Optional<Role> roleOptional = userRepository.findRoleByName("ROLE_" + IRoles.PROFESSOR);
        assertTrue(roleOptional.isPresent());
    }

    @Test
    void givenUnregisteredRole_whenFindRoleByName_thenAnEmptyOptionalIsReturned() {
        Optional<Role> roleOptional = userRepository.findRoleByName("ROLE_ASSISTANT");
        assertTrue(roleOptional.isEmpty());
    }

}
