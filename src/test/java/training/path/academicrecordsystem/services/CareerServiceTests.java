package training.path.academicrecordsystem.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.controllers.dtos.CareerDTO;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.repositories.implementations.JdbcCareerRepository;
import training.path.academicrecordsystem.services.implementations.CareerService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CareerServiceTests {

    @Mock
    JdbcCareerRepository jdbcCareerRepository;

    @InjectMocks
    CareerService careerService;

    @Test
    void givenValidCareerDTO_whenSave_thenItDoesNotThrowException() {
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("System Engineering").build();

        when(jdbcCareerRepository.save(career)).thenReturn(1);

        assertDoesNotThrow(() -> careerService.save(career));
    }

    @Test
    void givenExistingCareer_whenUpdate_thenItDoesNotThrowException() {
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Medicine").build();

        when(jdbcCareerRepository.exists(anyString())).thenReturn(true);
        when(jdbcCareerRepository.update(anyString(), any())).thenReturn(1);

        assertDoesNotThrow(() -> careerService.update(career));
    }

    @Test
    void givenNonExistingCareer_whenUpdate_thenItThrowsException() {
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("afjakvc").build();

        when(jdbcCareerRepository.exists(anyString())).thenReturn(false);
        when(jdbcCareerRepository.update(anyString(), any())).thenReturn(1);

        assertThrows(NotFoundResourceException.class, () -> careerService.update(career));
    }

    @Test
    void givenExistingCareer_whenDeleteById_thenItDoesNotThrowException() {
        String id = "cd967193-b0e9-423c-8452-8dc52c17a218";

        when(jdbcCareerRepository.exists(anyString())).thenReturn(true);
        when(jdbcCareerRepository.deleteById(anyString())).thenReturn(1);

        assertDoesNotThrow(() -> careerService.deleteById(id));
    }

    @Test
    void givenNonExistingCareer_whenDeleteById_thenItThrowsException() {
        String id = "1111";

        when(jdbcCareerRepository.exists(anyString())).thenReturn(false);
        when(jdbcCareerRepository.deleteById(anyString())).thenReturn(0);

        assertThrows(NotFoundResourceException.class, () -> careerService.deleteById(id));
    }

}
