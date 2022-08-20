package training.path.academicrecordsystem.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.repositories.implementations.JdbcCourseClassRepository;
import training.path.academicrecordsystem.repositories.implementations.JdbcCourseRepository;
import training.path.academicrecordsystem.repositories.implementations.JdbcProfessorRepository;
import training.path.academicrecordsystem.services.implementations.CourseClassService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CourseClassServiceTests {

    @Mock
    JdbcCourseClassRepository courseClassRepository;

    @Mock
    JdbcProfessorRepository professorRepository;

    @Mock
    JdbcCourseRepository courseRepository;

    @InjectMocks
    CourseClassService courseClassService;

    @Test
    void givenValidCourseClass_whenSave_thenItDoesNotThrowException() {
        String classId = UUID.randomUUID().toString();
        String courseId = UUID.randomUUID().toString();
        String professorId = UUID.randomUUID().toString();

        Course course = Course.builder().id(courseId).name("Course 1").credits(0).build();
        Professor professor = Professor.builder().id(professorId).name("Professor 1").salary(30000).build();

        CourseClass courseClass = CourseClass.builder()
                .id(UUID.randomUUID().toString())
                .capacity(30)
                .course(course)
                .professor(professor)
                .available(true)
                .build();

        when(courseRepository.exists(anyString())).thenReturn(true);
        when(professorRepository.exists(anyString())).thenReturn(true);
        when(courseClassRepository.save(any())).thenReturn(1);

        assertDoesNotThrow(() -> courseClassService.save(courseClass));
    }

    @Test
    void givenExistingCourseClass_whenUpdate_thenItDoesNotThrowException() {
        String classId = UUID.randomUUID().toString();
        String courseId = UUID.randomUUID().toString();
        String professorId = UUID.randomUUID().toString();

        Course course = Course.builder().id(courseId).name("Course 1").credits(0).build();
        Professor professor = Professor.builder().id(professorId).name("Professor 1").salary(30000).build();

        CourseClass courseClass = CourseClass.builder()
                .id(classId)
                .capacity(30)
                .course(course)
                .professor(professor)
                .available(true)
                .build();

        when(courseClassRepository.exists(anyString())).thenReturn(true);
        when(courseRepository.exists(anyString())).thenReturn(true);
        when(professorRepository.exists(anyString())).thenReturn(true);
        when(courseClassRepository.update(anyString(), any())).thenReturn(1);

        assertDoesNotThrow(() -> courseClassService.update(courseClass));
    }

    @Test
    void givenNonExistingCourseClass_whenUpdate_thenItThrowsException() {
        String classId = "000-1234";
        CourseClass career = CourseClass.builder().id(classId).available(false).build();

        when(courseClassRepository.exists(anyString())).thenReturn(false);
        when(courseClassRepository.update(anyString(), any())).thenReturn(0);

        assertThrows(ResourceNotFoundException.class, () -> courseClassService.update(career));
    }

    @Test
    void givenExistingCourseClass_whenDeleteById_thenItDoesNotThrowException() {
        String id = UUID.randomUUID().toString();

        when(courseClassRepository.exists(anyString())).thenReturn(true);
        when(courseClassRepository.deleteById(anyString())).thenReturn(1);

        assertDoesNotThrow(() -> courseClassService.deleteById(id));
    }

    @Test
    void givenNonExistingCourseClass_whenDeleteById_thenItThrowsException() {
        String id = "1111";

        when(courseClassRepository.exists(anyString())).thenReturn(false);
        when(courseClassRepository.deleteById(anyString())).thenReturn(0);

        assertThrows(ResourceNotFoundException.class, () -> courseClassService.deleteById(id));
    }

    @Test
    void givenValidId_whenFindById_thenItReturnsTheCourseClass() throws ResourceNotFoundException {
        String classId = UUID.randomUUID().toString();
        CourseClass career = CourseClass.builder().id(classId).available(true).build();

        when(courseClassRepository.findById(anyString())).thenReturn(Optional.of(career));

        assertDoesNotThrow(() -> courseClassService.findById(classId));

        CourseClass foundCourseClass = courseClassService.findById(classId);

        assertEquals(classId, foundCourseClass.getId());
        assertTrue(foundCourseClass.isAvailable());
    }

    @Test
    void givenInvalidId_whenFindById_thenItThrowsException() {
        String id = "0000";

        when(courseClassRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseClassService.findById(id));
    }

    @Test
    void givenExistingClasses_whenFindAll_thenItReturnsListOfCourseClass() {
        String classId1 = UUID.randomUUID().toString();
        String classId2 = UUID.randomUUID().toString();
        String classId3 = UUID.randomUUID().toString();
        String classId4 = UUID.randomUUID().toString();

        CourseClass courseClass1 = CourseClass.builder().id(classId1).capacity(20).available(true).build();
        CourseClass courseClass2 = CourseClass.builder().id(classId2).capacity(35).available(false).build();
        CourseClass courseClass3 = CourseClass.builder().id(classId3).capacity(30).available(true).build();
        CourseClass courseClass4 = CourseClass.builder().id(classId4).capacity(22).available(false).build();

        List<CourseClass> classes = List.of(courseClass1, courseClass2, courseClass3, courseClass4);

        when(courseClassRepository.findAll()).thenReturn(classes);

        List<CourseClass> responseList = courseClassService.findAll();

        assertEquals(classId1, responseList.get(0).getId());
        assertEquals(classId2, responseList.get(1).getId());
        assertEquals(classId3, responseList.get(2).getId());
        assertEquals(classId4, responseList.get(3).getId());

    }

}
