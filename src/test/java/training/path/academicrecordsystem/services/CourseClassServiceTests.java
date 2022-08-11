package training.path.academicrecordsystem.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.repositories.implementations.JdbcCourseClassRepository;
import training.path.academicrecordsystem.services.implementations.CourseClassService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CourseClassServiceTests {

    @Mock
    JdbcCourseClassRepository jdbcCourseClassRepository;

    @InjectMocks
    CourseClassService courseClassService;

    @Test
    void givenValidCourseClassDTO_whenSave_thenItDoesNotThrowException() {
        CourseClass courseClass = CourseClass.builder().id(UUID.randomUUID().toString()).available(true).build();

        when(jdbcCourseClassRepository.save(courseClass)).thenReturn(1);

        assertDoesNotThrow(() -> courseClassService.save(courseClass));
    }

    @Test
    void givenExistingCourseClass_whenUpdate_thenItDoesNotThrowException() {
        CourseClass courseClass = CourseClass.builder().id(UUID.randomUUID().toString()).available(true).build();

        when(jdbcCourseClassRepository.exists(anyString())).thenReturn(true);
        when(jdbcCourseClassRepository.update(anyString(), any())).thenReturn(1);

        assertDoesNotThrow(() -> courseClassService.update(courseClass));
    }

    @Test
    void givenNonExistingCourseClass_whenUpdate_thenItThrowsException() {
        CourseClass career = CourseClass.builder().id(UUID.randomUUID().toString()).available(false).build();

        when(jdbcCourseClassRepository.exists(anyString())).thenReturn(false);
        when(jdbcCourseClassRepository.update(anyString(), any())).thenReturn(1);

        assertThrows(ResourceNotFoundException.class, () -> courseClassService.update(career));
    }

    @Test
    void givenExistingCourseClass_whenDeleteById_thenItDoesNotThrowException() {
        String id = "cd967193-b0e9-423c-8452-8dc52c17a218";

        when(jdbcCourseClassRepository.exists(anyString())).thenReturn(true);
        when(jdbcCourseClassRepository.deleteById(anyString())).thenReturn(1);

        assertDoesNotThrow(() -> courseClassService.deleteById(id));
    }

    @Test
    void givenNonExistingCourseClass_whenDeleteById_thenItThrowsException() {
        String id = "1111";

        when(jdbcCourseClassRepository.exists(anyString())).thenReturn(false);
        when(jdbcCourseClassRepository.deleteById(anyString())).thenReturn(0);

        assertThrows(ResourceNotFoundException.class, () -> courseClassService.deleteById(id));
    }

    @Test
    void givenValidId_whenFindById_thenItReturnsTheCourseClass() throws ResourceNotFoundException {
        CourseClass career = CourseClass.builder().id("1").available(true).build();

        when(jdbcCourseClassRepository.findById(anyString())).thenReturn(Optional.of(career));

        assertDoesNotThrow(() -> courseClassService.findById("1"));
        CourseClass foundCourseClass = courseClassService.findById("1");
        assertTrue(foundCourseClass.isAvailable());
    }

    @Test
    void givenInvalidId_whenFindById_thenItThrowsException() {
        String id = "0000";

        when(jdbcCourseClassRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseClassService.findById(id));
    }

}
