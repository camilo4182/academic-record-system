package training.path.academicrecordsystem.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.repositories.implementations.CareerRepository;
import training.path.academicrecordsystem.repositories.implementations.CourseRepository;
import training.path.academicrecordsystem.services.implementations.CareerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CareerServiceTests {

    @Mock
    CareerRepository careerRepository;

    @Mock
    CourseRepository courseRepository;

    @InjectMocks
    CareerService careerService;

    @Test
    void givenValidCareer_whenSave_thenItDoesNotThrowException() {
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("System Engineering").build();

        when(careerRepository.save(any())).thenReturn(1);

        assertDoesNotThrow(() -> careerService.save(career));
    }

    @Test
    void givenExistingCareer_whenUpdate_thenItDoesNotThrowException() {
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Medicine").build();

        when(careerRepository.exists(anyString())).thenReturn(true);
        when(careerRepository.update(anyString(), any())).thenReturn(1);

        assertDoesNotThrow(() -> careerService.update(career));
    }

    @Test
    void givenNonExistingCareer_whenUpdate_thenItThrowsException() {
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("afjakvc").build();

        when(careerRepository.exists(anyString())).thenReturn(false);
        when(careerRepository.update(anyString(), any())).thenReturn(1);

        assertThrows(ResourceNotFoundException.class, () -> careerService.update(career));
    }

    @Test
    void givenExistingCareer_whenDeleteById_thenItDoesNotThrowException() {
        String id = "cd967193-b0e9-423c-8452-8dc52c17a218";

        when(careerRepository.exists(anyString())).thenReturn(true);
        when(careerRepository.deleteById(anyString())).thenReturn(1);

        assertDoesNotThrow(() -> careerService.deleteById(id));
    }

    @Test
    void givenNonExistingCareer_whenDeleteById_thenItThrowsException() {
        String id = "1111";

        when(careerRepository.exists(anyString())).thenReturn(false);
        when(careerRepository.deleteById(anyString())).thenReturn(0);

        assertThrows(ResourceNotFoundException.class, () -> careerService.deleteById(id));
    }

    @Test
    void givenValidId_whenFindById_thenItReturnsTheCareer() throws ResourceNotFoundException {
        Career career = Career.builder().id("1").name("Physics").build();

        when(careerRepository.findById(anyString())).thenReturn(Optional.of(career));

        assertDoesNotThrow(() -> careerService.findById("1"));
        Career foundCareer = careerService.findById("1");
        assertEquals("Physics", foundCareer.getName());
    }

    @Test
    void givenInvalidId_whenFindById_thenItThrowsException() {
        String id = "0000";

        when(careerRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> careerService.findById(id));
    }

    @Test
    void givenExistingStudents_whenFindAll_thenItReturnsListOfStudents() {
        String careerId1 = UUID.randomUUID().toString();
        String careerId2 = UUID.randomUUID().toString();
        String careerId3 = UUID.randomUUID().toString();
        String careerId4 = UUID.randomUUID().toString();

        Career career1 = Career.builder().id(careerId1).name("Career 1").build();
        Career career2 = Career.builder().id(careerId2).name("Career 2").build();
        Career career3 = Career.builder().id(careerId3).name("Career 3").build();
        Career career4 = Career.builder().id(careerId4).name("Career 4").build();

        List<Career> careers = List.of(career1, career2, career3, career4);

        when(careerRepository.findAll()).thenReturn(careers);

        List<Career> responseList = careerService.findAll();

        assertEquals(careerId1, responseList.get(0).getId());
        assertEquals(careerId2, responseList.get(1).getId());
        assertEquals(careerId3, responseList.get(2).getId());
        assertEquals(careerId4, responseList.get(3).getId());

        assertEquals("Career 1", responseList.get(0).getName());
        assertEquals("Career 2", responseList.get(1).getName());
        assertEquals("Career 3", responseList.get(2).getName());
        assertEquals("Career 4", responseList.get(3).getName());

    }

    @Test
    void givenValidCourseAndCareer_whenAssignCourseToCareer_thenItDoesNotThrowException() {
        String careerId = UUID.randomUUID().toString();
        String courseId = UUID.randomUUID().toString();

        doNothing().when(careerRepository).assignCourseToCareer(anyString(), anyString());
        when(courseRepository.exists(anyString())).thenReturn(true);
        when(careerRepository.exists(anyString())).thenReturn(true);

        assertDoesNotThrow(() -> careerService.assignCourseToCareer(courseId, careerId));
    }

    @Test
    void givenInvalidCourseOrCareer_whenAssignCourseToCareer_thenItThrowsException() {
        String careerId = "123-456";
        String courseId = UUID.randomUUID().toString();

        doNothing().when(careerRepository).assignCourseToCareer(anyString(), anyString());
        when(courseRepository.exists(anyString())).thenReturn(true);
        when(careerRepository.exists(anyString())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> careerService.assignCourseToCareer(courseId, careerId));
    }

    @Test
    void givenExistingCourses_whenFindCoursesById_thenItReturnsListOfCourses() throws ResourceNotFoundException {
        String careerId = UUID.randomUUID().toString();

        String courseId1 = UUID.randomUUID().toString();
        String courseId2 = UUID.randomUUID().toString();
        String courseId3 = UUID.randomUUID().toString();
        String courseId4 = UUID.randomUUID().toString();

        Course course1 = Course.builder().id(courseId1).name("Course 1").credits(4).build();
        Course course2 = Course.builder().id(courseId2).name("Course 2").credits(3).build();
        Course course3 = Course.builder().id(courseId3).name("Course 3").credits(5).build();
        Course course4 = Course.builder().id(courseId4).name("Course 4").credits(2).build();

        List<Course> courses = List.of(course1, course2, course3, course4);

        when(careerRepository.exists(anyString())).thenReturn(true);
        when(careerRepository.findCoursesByCareer(anyString())).thenReturn(courses);

        List<Course> responseList = careerService.findCoursesByCareer(careerId);

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
    void givenNoCourses_whenFindCoursesById_thenItReturnsEmptyList() throws ResourceNotFoundException {
        String careerId = UUID.randomUUID().toString();

        List<Course> courses = new ArrayList<>();

        when(careerRepository.exists(anyString())).thenReturn(true);
        when(careerRepository.findCoursesByCareer(anyString())).thenReturn(courses);

        List<Course> responseList = careerService.findCoursesByCareer(careerId);

        assertTrue(responseList.isEmpty());
    }

    @Test
    void givenNonExistingCareer_whenFindCoursesById_thenItThroesException() {
        String careerId = "0000-1234";

        when(careerRepository.exists(anyString())).thenReturn(false);
        when(careerRepository.findCoursesByCareer(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> careerService.findCoursesByCareer(careerId));
    }

    @Test
    void givenExistingCareers_whenFindAllWithPagination_thenItReturnsTheSublist() {
        String careerId1 = UUID.randomUUID().toString();
        String careerId2 = UUID.randomUUID().toString();
        String careerId3 = UUID.randomUUID().toString();
        String careerId4 = UUID.randomUUID().toString();

        Career career1 = Career.builder().id(careerId1).name("Career 1").build();
        Career career2 = Career.builder().id(careerId2).name("Career 2").build();
        Career career3 = Career.builder().id(careerId3).name("Career 3").build();
        Career career4 = Career.builder().id(careerId4).name("Career 4").build();

        List<Career> careers = List.of(career1, career2, career3, career4);

        int limit = 2;
        int offset = 2;

        when(careerRepository.findAll(anyInt(), anyInt())).thenReturn(careers.subList(offset, careers.size()));

        List<Career> responseList = careerService.findAll(limit, offset);

        assertEquals(careerId3, responseList.get(0).getId());
        assertEquals(careerId4, responseList.get(1).getId());

        assertEquals("Career 3", responseList.get(0).getName());
        assertEquals("Career 4", responseList.get(1).getName());
    }

}
