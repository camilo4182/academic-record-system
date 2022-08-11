package training.path.academicrecordsystem.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.repositories.implementations.JdbcCourseRepository;
import training.path.academicrecordsystem.services.implementations.CourseService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CourseServiceTests {

    @Mock
    JdbcCourseRepository jdbcCourseRepository;

    @InjectMocks
    CourseService courseService;

    @Test
    void givenValidCourseDTO_whenSave_thenItDoesNotThrowException() {
        Course course = Course.builder().id(UUID.randomUUID().toString()).name("Data Bases").credits(3).build();

        when(jdbcCourseRepository.save(course)).thenReturn(1);

        assertDoesNotThrow(() -> courseService.save(course));
    }

    @Test
    void givenExistingCourse_whenUpdate_thenItDoesNotThrowException() {
        Course course = Course.builder().id(UUID.randomUUID().toString()).name("Anatomy").credits(4).build();

        when(jdbcCourseRepository.exists(anyString())).thenReturn(true);
        when(jdbcCourseRepository.update(anyString(), any())).thenReturn(1);

        assertDoesNotThrow(() -> courseService.update(course));
    }

    @Test
    void givenNonExistingCourse_whenUpdate_thenItThrowsException() {
        Course course = Course.builder().id(UUID.randomUUID().toString()).name("afjakvc").credits(100).build();

        when(jdbcCourseRepository.exists(anyString())).thenReturn(false);
        when(jdbcCourseRepository.update(anyString(), any())).thenReturn(1);

        assertThrows(ResourceNotFoundException.class, () -> courseService.update(course));
    }

    @Test
    void givenExistingCourse_whenDeleteById_thenItDoesNotThrowException() {
        String id = "cd967193-b0e9-423c-8452-8dc52c17a218";

        when(jdbcCourseRepository.exists(anyString())).thenReturn(true);
        when(jdbcCourseRepository.deleteById(anyString())).thenReturn(1);

        assertDoesNotThrow(() -> courseService.deleteById(id));
    }

    @Test
    void givenNonExistingCourse_whenDeleteById_thenItThrowsException() {
        String id = "1111";

        when(jdbcCourseRepository.exists(anyString())).thenReturn(false);
        when(jdbcCourseRepository.deleteById(anyString())).thenReturn(0);

        assertThrows(ResourceNotFoundException.class, () -> courseService.deleteById(id));
    }

    @Test
    void givenValidId_whenFindById_thenItReturnsTheCourse() throws ResourceNotFoundException {
        Course course = Course.builder().id("1").name("Algebra I").credits(4).build();

        when(jdbcCourseRepository.findById(anyString())).thenReturn(Optional.of(course));

        assertDoesNotThrow(() -> courseService.findById("1"));
        Course foundCourse = courseService.findById("1");
        assertEquals("Algebra I", foundCourse.getName());
        assertEquals(4, foundCourse.getCredits());
    }

    @Test
    void givenInvalidId_whenFindById_thenItThrowsException() {
        String id = "0000";

        when(jdbcCourseRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.findById(id));
    }

}
