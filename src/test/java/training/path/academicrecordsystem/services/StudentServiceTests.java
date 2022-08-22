package training.path.academicrecordsystem.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.UniqueColumnViolationException;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.repositories.implementations.CareerRepository;
import training.path.academicrecordsystem.repositories.implementations.StudentRepository;
import training.path.academicrecordsystem.services.implementations.StudentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StudentServiceTests {

    @Mock
    StudentRepository studentRepository;

    @Mock
    CareerRepository careerRepository;

    @InjectMocks
    StudentService studentService;

    @Test
    void givenValidStudentData_whenSave_thenItDoesNotThrowException() {
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Software Engineering").build();
        Student student = Student.builder().id(UUID.randomUUID().toString()).name("Andres").email("andres@email.com").career(career).build();

        when(careerRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.save(any())).thenReturn(1);

        assertDoesNotThrow(() -> studentService.save(student));
    }

    @Test
    void givenTwoStudentsWithSameData_whenSave_thenItThrowsException() {
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Software Engineering").build();
        Student student1 = Student.builder().id(UUID.randomUUID().toString()).name("Andres").email("andres@email.com").career(career).build();
        Student student2 = Student.builder().id(UUID.randomUUID().toString()).name("Andres").email("andres@email.com").career(career).build();

        when(careerRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.save(any())).thenReturn(0);
        when(studentRepository.findByName(anyString())).thenReturn(Optional.of(student2));
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.of(student2));

        assertThrows(UniqueColumnViolationException.class, () -> studentService.save(student1));
    }

    @Test
    void givenTwoStudentsWithSameName_whenSave_thenItThrowsException() {
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Software Engineering").build();
        Student student1 = Student.builder().id(UUID.randomUUID().toString()).name("Andres").email("other@email.com").career(career).build();
        Student student2 = Student.builder().id(UUID.randomUUID().toString()).name("Andres").email("another@email.com").career(career).build();

        when(careerRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.save(any())).thenReturn(0);
        when(studentRepository.findByName(anyString())).thenReturn(Optional.of(student2));

        assertThrows(UniqueColumnViolationException.class, () -> studentService.save(student1));
    }

    @Test
    void givenTwoStudentsWithSameEmail_whenSave_thenItThrowsException() {
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Software Engineering").build();
        Student student1 = Student.builder().id(UUID.randomUUID().toString()).name("Juan").email("same@email.com").career(career).build();
        Student student2 = Student.builder().id(UUID.randomUUID().toString()).name("Andres").email("same@email.com").career(career).build();

        when(careerRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.save(any())).thenReturn(0);
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.of(student2));

        assertThrows(UniqueColumnViolationException.class, () -> studentService.save(student1));
    }

    @Test
    void givenNonExistingCareer_whenSave_thenItThrowsException() {
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Software Engineering").build();
        Student student = Student.builder().id(UUID.randomUUID().toString()).name("Andres").email("andres@email.com").career(career).build();

        when(careerRepository.exists(anyString())).thenReturn(false);
        when(studentRepository.save(any())).thenReturn(1);

        assertThrows(ResourceNotFoundException.class, () -> studentService.save(student));
    }

    @Test
    void givenExistingStudent_whenUpdate_thenItDoesNotThrowException() {
        String id = UUID.randomUUID().toString();
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Software Engineering").build();
        Student student = Student.builder().id(id).name("Maria").email("maria@email.com").career(career).build();

        when(careerRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.update(anyString(), any())).thenReturn(1);

        assertDoesNotThrow(() -> studentService.update(id, student));
    }

    @Test
    void givenNonExistingStudent_whenUpdate_thenItThrowsException() {
        String id = UUID.randomUUID().toString();
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Software Engineering").build();
        Student student = Student.builder().id(id).name("Null").email("null@email.com").build();

        when(careerRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.exists(anyString())).thenReturn(false);
        when(studentRepository.update(anyString(), any())).thenReturn(0);

        assertThrows(ResourceNotFoundException.class, () -> studentService.update(id, student));
    }

    @Test
    void givenNonExistingCareer_whenUpdate_thenItThrowsException() {
        String id = UUID.randomUUID().toString();
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("").build();
        Student student = Student.builder().id(id).name("Juan").email("juan@email.com").career(career).build();

        when(careerRepository.exists(anyString())).thenReturn(false);
        when(studentRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.update(anyString(), any())).thenReturn(0);

        assertThrows(ResourceNotFoundException.class, () -> studentService.update(id, student));
    }

    @Test
    void givenValidId_whenDeleteById_thenItDoesNotThrowException() {
        String id = UUID.randomUUID().toString();

        when(studentRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.deleteById(anyString())).thenReturn(1);

        assertDoesNotThrow(() -> studentService.deleteById(id));
    }

    @Test
    void givenNonExistingId_whenDeleteById_thenThrowException() {
        String id = UUID.randomUUID().toString();

        when(studentRepository.exists(anyString())).thenReturn(false);
        when(studentRepository.deleteById(anyString())).thenReturn(0);

        assertThrows(ResourceNotFoundException.class, () -> studentService.deleteById(id));
    }

    @Test
    void givenValidId_whenFindById_thenReturnStudent() {
        String id = UUID.randomUUID().toString();
        Student student = Student.builder().id(id).name("Juan").email("juan@email.co").build();

        when(studentRepository.findById(anyString())).thenReturn(Optional.of(student));

        assertDoesNotThrow(() -> studentService.findById(id));
    }

    @Test
    void givenInvalidId_whenFindById_thenThrowException() {
        String id = "000-123";

        when(studentRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentService.findById(id));
    }

    @Test
    void givenExistingStudents_whenFindAll_thenItReturnsListOfStudents() {
        String studentId1 = UUID.randomUUID().toString();
        String studentId2 = UUID.randomUUID().toString();
        String studentId3 = UUID.randomUUID().toString();

        Student student1 = Student.builder().id(studentId1).name("Student 1").build();
        Student student2 = Student.builder().id(studentId2).name("Student 2").build();
        Student student3 = Student.builder().id(studentId3).name("Student 3").build();

        List<Student> students = List.of(student1, student2, student3);

        when(studentRepository.findAll()).thenReturn(students);

        List<Student> responseList = studentService.findAll();

        assertEquals(studentId1, responseList.get(0).getId());
        assertEquals(studentId2, responseList.get(1).getId());
        assertEquals(studentId3, responseList.get(2).getId());

        assertEquals("Student 1", responseList.get(0).getName());
        assertEquals("Student 2", responseList.get(1).getName());
        assertEquals("Student 3", responseList.get(2).getName());

    }

    @Test
    void givenStudentWithEnrollment_whenFindEnrollmentInfo_thenItReturnsListOfEnrollments() throws ResourceNotFoundException {
        String careerId = UUID.randomUUID().toString();
        Career career = Career.builder().id(careerId).name("Medicine").build();

        String studentId = UUID.randomUUID().toString();
        Student student = Student.builder().id(studentId).name("Juan").email("juan@email.ocm").career(career).build();

        String enrollmentId = UUID.randomUUID().toString();
        Enrollment enrollment = Enrollment.builder().id(enrollmentId).student(student).semester(1).build();

        when(studentRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.findEnrollmentInfo(anyString())).thenReturn(List.of(enrollment));

        assertDoesNotThrow(() -> studentService.findEnrollmentInfo(studentId));

        List<Enrollment> enrollments = studentService.findEnrollmentInfo(studentId);
        Enrollment studentEnrollment = enrollments.get(0);
        assertEquals(enrollmentId, studentEnrollment.getId());
        assertEquals("Juan", studentEnrollment.getStudent().getName());

    }
    
}
