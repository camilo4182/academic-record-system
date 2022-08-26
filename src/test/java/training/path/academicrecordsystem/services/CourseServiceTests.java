package training.path.academicrecordsystem.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.repositories.implementations.CourseRepository;
import training.path.academicrecordsystem.services.implementations.CourseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CourseServiceTests {

    @Mock
    CourseRepository courseRepository;

    @InjectMocks
    CourseService courseService;

    @Test
    void givenValidCourseDTO_whenSave_thenItDoesNotThrowException() {
        Course course = Course.builder().id(UUID.randomUUID().toString()).name("Data Bases").credits(3).build();

        when(courseRepository.save(course)).thenReturn(1);

        assertDoesNotThrow(() -> courseService.save(course));
    }

    @Test
    void givenExistingCourse_whenUpdate_thenItDoesNotThrowException() {
        Course course = Course.builder().id(UUID.randomUUID().toString()).name("Anatomy").credits(4).build();

        when(courseRepository.exists(anyString())).thenReturn(true);
        when(courseRepository.update(anyString(), any())).thenReturn(1);

        assertDoesNotThrow(() -> courseService.update(course));
    }

    @Test
    void givenNonExistingCourse_whenUpdate_thenItThrowsException() {
        Course course = Course.builder().id(UUID.randomUUID().toString()).name("afjakvc").credits(100).build();

        when(courseRepository.exists(anyString())).thenReturn(false);
        when(courseRepository.update(anyString(), any())).thenReturn(1);

        assertThrows(ResourceNotFoundException.class, () -> courseService.update(course));
    }

    @Test
    void givenExistingCourse_whenDeleteById_thenItDoesNotThrowException() {
        String id = UUID.randomUUID().toString();

        when(courseRepository.exists(anyString())).thenReturn(true);
        when(courseRepository.deleteById(anyString())).thenReturn(1);

        assertDoesNotThrow(() -> courseService.deleteById(id));
    }

    @Test
    void givenNonExistingCourse_whenDeleteById_thenItThrowsException() {
        String id = "1111";

        when(courseRepository.exists(anyString())).thenReturn(false);
        when(courseRepository.deleteById(anyString())).thenReturn(0);

        assertThrows(ResourceNotFoundException.class, () -> courseService.deleteById(id));
    }

    @Test
    void givenValidId_whenFindById_thenItReturnsTheCourse() throws ResourceNotFoundException {
        String courseId = UUID.randomUUID().toString();
        Course course = Course.builder().id(courseId).name("Algebra I").credits(4).build();

        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));

        assertDoesNotThrow(() -> courseService.findById(courseId));

        Course foundCourse = courseService.findById(courseId);

        assertEquals("Algebra I", foundCourse.getName());
        assertEquals(4, foundCourse.getCredits());
    }

    @Test
    void givenInvalidId_whenFindById_thenItThrowsException() {
        String id = "0000";

        when(courseRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.findById(id));
    }

    @Test
    void givenExistingCourses_whenFindAll_thenItReturnsListOfCourses() {
        String courseId1 = UUID.randomUUID().toString();
        String courseId2 = UUID.randomUUID().toString();
        String courseId3 = UUID.randomUUID().toString();
        String courseId4 = UUID.randomUUID().toString();

        Course course1 = Course.builder().id(courseId1).name("Course 1").credits(0).build();
        Course course2 = Course.builder().id(courseId2).name("Course 2").credits(4).build();
        Course course3 = Course.builder().id(courseId3).name("Course 3").credits(3).build();
        Course course4 = Course.builder().id(courseId4).name("Course 4").credits(2).build();

        List<Course> courses = List.of(course1, course2, course3, course4);

        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> responseList = courseService.findAll();

        assertEquals(courseId1, responseList.get(0).getId());
        assertEquals(courseId2, responseList.get(1).getId());
        assertEquals(courseId3, responseList.get(2).getId());
        assertEquals(courseId4, responseList.get(3).getId());

        assertEquals("Course 1", responseList.get(0).getName());
        assertEquals("Course 2", responseList.get(1).getName());
        assertEquals("Course 3", responseList.get(2).getName());
        assertEquals("Course 4", responseList.get(3).getName());
    }

    @Test
    void givenExistingClasses_whenFindClassesByCourse_thenItReturnsListOfClasses() throws ResourceNotFoundException {
        String courseId = UUID.randomUUID().toString();

        String classId1 = UUID.randomUUID().toString();
        String classId2 = UUID.randomUUID().toString();
        String classId3 = UUID.randomUUID().toString();
        String classId4 = UUID.randomUUID().toString();

        CourseClass courseClass1 = CourseClass.builder().id(classId1).build();
        CourseClass courseClass2 = CourseClass.builder().id(classId2).build();
        CourseClass courseClass3 = CourseClass.builder().id(classId3).build();
        CourseClass courseClass4 = CourseClass.builder().id(classId4).build();

        List<CourseClass> classes = List.of(courseClass1, courseClass2, courseClass3, courseClass4);

        when(courseRepository.exists(anyString())).thenReturn(true);
        when(courseRepository.getClassesByCourse(anyString())).thenReturn(classes);

        List<CourseClass> responseList = courseService.findClassesByCourse(courseId);

        assertEquals(classId1, responseList.get(0).getId());
        assertEquals(classId2, responseList.get(1).getId());
        assertEquals(classId3, responseList.get(2).getId());
        assertEquals(classId4, responseList.get(3).getId());
    }

    @Test
    void givenNonExistingCourse_whenFindClassesByCourse_thenItThrowsException() {
        String courseId = UUID.randomUUID().toString();

        List<CourseClass> classes = new ArrayList<>();

        when(courseRepository.exists(anyString())).thenReturn(false);
        when(courseRepository.getClassesByCourse(anyString())).thenReturn(classes);

        assertThrows(ResourceNotFoundException.class, () -> courseService.findClassesByCourse(courseId));
    }

    @Test
    void givenExistingCourses_whenFindAllWithPagination_thenItReturnsTheSublist() {
        String courseId1 = UUID.randomUUID().toString();
        String courseId2 = UUID.randomUUID().toString();
        String courseId3 = UUID.randomUUID().toString();
        String courseId4 = UUID.randomUUID().toString();
        String courseId5 = UUID.randomUUID().toString();
        String courseId6 = UUID.randomUUID().toString();

        Course course1 = Course.builder().id(courseId1).name("Course 1").credits(0).build();
        Course course2 = Course.builder().id(courseId2).name("Course 2").credits(4).build();
        Course course3 = Course.builder().id(courseId3).name("Course 3").credits(3).build();
        Course course4 = Course.builder().id(courseId4).name("Course 4").credits(2).build();
        Course course5 = Course.builder().id(courseId5).name("Course 5").credits(5).build();
        Course course6 = Course.builder().id(courseId6).name("Course 6").credits(2).build();

        List<Course> courses = List.of(course1, course2, course3, course4, course5, course6);

        int limit = 3;
        int offset = 3;

        when(courseRepository.findAll(anyInt(), anyInt())).thenReturn(courses.subList(offset, courses.size()));

        List<Course> responseList = courseService.findAll(limit, offset);

        assertEquals(courseId4, responseList.get(0).getId());
        assertEquals(courseId5, responseList.get(1).getId());
        assertEquals(courseId6, responseList.get(2).getId());

        assertEquals("Course 4", responseList.get(0).getName());
        assertEquals("Course 5", responseList.get(1).getName());
        assertEquals("Course 6", responseList.get(2).getName());
    }

}
