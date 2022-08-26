package training.path.academicrecordsystem.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.UniqueColumnViolationException;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Role;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.repositories.implementations.CareerRepository;
import training.path.academicrecordsystem.repositories.implementations.StudentRepository;
import training.path.academicrecordsystem.repositories.implementations.UserRepository;
import training.path.academicrecordsystem.security.interfaces.IRoles;
import training.path.academicrecordsystem.services.implementations.StudentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StudentServiceTests {

    @Mock
    StudentRepository studentRepository;

    @Mock
    CareerRepository careerRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    StudentService studentService;

    @Test
    void givenValidStudentData_whenSave_thenItDoesNotThrowException() {
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Software Engineering").build();
        Role role = Role.builder().id(UUID.randomUUID().toString()).roleName(IRoles.STUDENT).build();
        Student student = Student.builder().id(UUID.randomUUID().toString())
                .firstName("Andres")
                .lastName("Quintero")
                .userName("andres.quintero")
                .email("andres@email.com")
                .career(career)
                .role(role)
                .build();

        when(userRepository.findRoleByName(anyString())).thenReturn(Optional.of(role));
        when(careerRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.save(any())).thenReturn(1);

        assertDoesNotThrow(() -> studentService.save(student));
    }

    @Test
    void givenNonExistingRole_whenSave_thenItThrowsException() {
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Software Engineering").build();
        Role role = Role.builder().id(UUID.randomUUID().toString()).roleName("Seller").build();
        Student student = Student.builder().id(UUID.randomUUID().toString())
                .firstName("Andres")
                .lastName("Quintero")
                .userName("andres.quintero")
                .email("andres@email.com")
                .career(career)
                .role(role)
                .build();

        when(userRepository.findRoleByName(anyString())).thenReturn(Optional.empty());
        when(careerRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.save(any())).thenReturn(0);

        assertThrows(ResourceNotFoundException.class, () -> studentService.save(student));
    }

    @Test
    void givenTwoStudentsWithSameData_whenSave_thenItThrowsException() {
        Role role = Role.builder().id(UUID.randomUUID().toString()).roleName(IRoles.STUDENT).build();
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Software Engineering").build();
        Student student1 = Student.builder().id(UUID.randomUUID().toString())
                .firstName("Juan")
                .lastName("Rodriguez")
                .userName("juan.rodriguez")
                .email("andres@email.com")
                .career(career)
                .role(role)
                .build();

        Student student2 = Student.builder().id(UUID.randomUUID().toString())
                .firstName("Juan")
                .lastName("Rodriguez")
                .userName("juan.rodriguez")
                .email("andres@email.com")
                .career(career)
                .role(role)
                .build();

        when(careerRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.save(any())).thenReturn(0);
        when(studentRepository.findByUserName(anyString())).thenReturn(Optional.of(student2));
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.of(student2));

        assertThrows(UniqueColumnViolationException.class, () -> studentService.save(student1));
    }

    @Test
    void givenTwoStudentsWithSameName_whenSave_thenItThrowsException() {
        Role role = Role.builder().id(UUID.randomUUID().toString()).roleName(IRoles.STUDENT).build();
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Software Engineering").build();
        Student student1 = Student.builder().id(UUID.randomUUID().toString())
                .firstName("Juan")
                .lastName("Rodriguez")
                .userName("juan.rodriguez")
                .email("other@email.com")
                .career(career)
                .role(role)
                .build();

        Student student2 = Student.builder().id(UUID.randomUUID().toString())
                .firstName("Juan")
                .lastName("Quintero")
                .userName("juan.quintero")
                .email("another@email.com")
                .career(career)
                .role(role)
                .build();

        when(careerRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.save(any())).thenReturn(0);
        when(studentRepository.findByUserName(anyString())).thenReturn(Optional.of(student2));

        assertThrows(UniqueColumnViolationException.class, () -> studentService.save(student1));
    }

    @Test
    void givenTwoStudentsWithSameEmail_whenSave_thenItThrowsException() {
        Role role = Role.builder().id(UUID.randomUUID().toString()).roleName(IRoles.STUDENT).build();
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Software Engineering").build();
        Student student1 = Student.builder().id(UUID.randomUUID().toString())
                .firstName("Juan")
                .lastName("Rodriguez")
                .userName("juan.rodriguez")
                .email("same@email.com")
                .career(career)
                .role(role)
                .build();

        Student student2 = Student.builder().id(UUID.randomUUID().toString())
                .firstName("Andres")
                .lastName("Quintero")
                .userName("andres.quintero")
                .email("same@email.com")
                .career(career)
                .role(role)
                .build();

        when(careerRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.save(any())).thenReturn(0);
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.of(student2));

        assertThrows(UniqueColumnViolationException.class, () -> studentService.save(student1));
    }

    @Test
    void givenNonExistingCareer_whenSave_thenItThrowsException() {
        Role role = Role.builder().id(UUID.randomUUID().toString()).roleName(IRoles.STUDENT).build();
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Software Engineering").build();
        Student student = Student.builder().id(UUID.randomUUID().toString())
                .firstName("Andres")
                .lastName("Quintero")
                .userName("andres.quintero")
                .email("same@email.com")
                .career(career)
                .role(role)
                .build();

        when(careerRepository.exists(anyString())).thenReturn(false);
        when(studentRepository.save(any())).thenReturn(1);

        assertThrows(ResourceNotFoundException.class, () -> studentService.save(student));
    }

    @Test
    void givenExistingStudent_whenUpdate_thenItDoesNotThrowException() {
        String id = UUID.randomUUID().toString();
        Role role = Role.builder().id(UUID.randomUUID().toString()).roleName(IRoles.STUDENT).build();
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Software Engineering").build();
        Student student = Student.builder().id(id)
                .firstName("Maria")
                .lastName("Rodriguez")
                .userName("maria.rodriguez")
                .email("maria.rodriguez@email.com")
                .career(career)
                .role(role)
                .build();

        when(careerRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.update(anyString(), any())).thenReturn(1);

        assertDoesNotThrow(() -> studentService.update(id, student));
    }

    @Test
    void givenNonExistingStudent_whenUpdate_thenItThrowsException() {
        String id = UUID.randomUUID().toString();
        Role role = Role.builder().id(UUID.randomUUID().toString()).roleName(IRoles.STUDENT).build();
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("Software Engineering").build();
        Student student = Student.builder().id(id)
                .firstName("Null")
                .lastName("Null")
                .userName("null.null")
                .email("null@email.com")
                .career(career)
                .role(role)
                .build();

        when(careerRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.exists(anyString())).thenReturn(false);
        when(studentRepository.update(anyString(), any())).thenReturn(0);

        assertThrows(ResourceNotFoundException.class, () -> studentService.update(id, student));
    }

    @Test
    void givenNonExistingCareer_whenUpdate_thenItThrowsException() {
        String id = UUID.randomUUID().toString();
        Career career = Career.builder().id(UUID.randomUUID().toString()).name("").build();
        Student student = Student.builder().id(id).firstName("Juan").email("juan@email.com").career(career).build();

        when(careerRepository.exists(anyString())).thenReturn(false);
        when(studentRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.update(anyString(), any())).thenReturn(0);

        assertThrows(ResourceNotFoundException.class, () -> studentService.update(id, student));
    }

    @Test
    void givenExistingStudent_whenUpdateBasicInfo_thenItDoesNotThrowException() {
        String id = UUID.randomUUID().toString();
        Student student = Student.builder().id(id).firstName("New Name").email("new_email@email.com").build();

        when(studentRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.update(anyString(), any())).thenReturn(1);
        when(studentRepository.findByUserName(anyString())).thenReturn(Optional.of(student));
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.of(student));

        assertDoesNotThrow(() -> studentService.updateBasicInfo(id, student));
    }

    @Test
    void givenTwoStudentsWithSameNameAndEmail_whenUpdateBasicInfo_thenItThrowsException() {
        String id = UUID.randomUUID().toString();
        Student studentToUpdate = Student.builder().id(id).firstName("Juan").email("same@email.com").build();
        Student existingStudent = Student.builder().id(UUID.randomUUID().toString()).firstName("Juan").email("same@email.com").build();

        when(studentRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.findByUserName(anyString())).thenReturn(Optional.of(existingStudent));
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.of(existingStudent));
        when(studentRepository.update(anyString(), any())).thenReturn(0);

        assertThrows(UniqueColumnViolationException.class, () -> studentService.updateBasicInfo(id, studentToUpdate));
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
        Student student = Student.builder().id(id).firstName("Juan").email("juan@email.co").build();

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

        Student student1 = Student.builder().id(studentId1).firstName("Student 1").build();
        Student student2 = Student.builder().id(studentId2).firstName("Student 2").build();
        Student student3 = Student.builder().id(studentId3).firstName("Student 3").build();

        List<Student> students = List.of(student1, student2, student3);

        when(studentRepository.findAll()).thenReturn(students);

        List<Student> responseList = studentService.findAll();

        assertEquals(studentId1, responseList.get(0).getId());
        assertEquals(studentId2, responseList.get(1).getId());
        assertEquals(studentId3, responseList.get(2).getId());

        assertEquals("Student 1", responseList.get(0).getFirstName());
        assertEquals("Student 2", responseList.get(1).getFirstName());
        assertEquals("Student 3", responseList.get(2).getFirstName());

    }

    @Test
    void givenStudentWithEnrollment_whenFindEnrollmentInfo_thenItReturnsListOfEnrollments() throws ResourceNotFoundException {
        String careerId = UUID.randomUUID().toString();
        Career career = Career.builder().id(careerId).name("Medicine").build();

        String studentId = UUID.randomUUID().toString();
        Student student = Student.builder().id(studentId).firstName("Juan").email("juan@email.ocm").career(career).build();

        String enrollmentId = UUID.randomUUID().toString();
        Enrollment enrollment = Enrollment.builder().id(enrollmentId).student(student).semester(1).build();

        when(studentRepository.exists(anyString())).thenReturn(true);
        when(studentRepository.findEnrollmentInfo(anyString())).thenReturn(List.of(enrollment));

        assertDoesNotThrow(() -> studentService.findEnrollmentInfo(studentId));

        List<Enrollment> enrollments = studentService.findEnrollmentInfo(studentId);
        Enrollment studentEnrollment = enrollments.get(0);
        assertEquals(enrollmentId, studentEnrollment.getId());
        assertEquals("Juan", studentEnrollment.getStudent().getFirstName());

    }

    @Test
    void givenExistingStudents_whenSaveWithPagination_thenItReturnsTheSublist() {
        String studentId1 = UUID.randomUUID().toString();
        String studentId2 = UUID.randomUUID().toString();
        String studentId3 = UUID.randomUUID().toString();
        String studentId4 = UUID.randomUUID().toString();
        String studentId5 = UUID.randomUUID().toString();
        String studentId6 = UUID.randomUUID().toString();

        Student student1 = Student.builder().id(studentId1).firstName("Student 1").build();
        Student student2 = Student.builder().id(studentId2).firstName("Student 2").build();
        Student student3 = Student.builder().id(studentId3).firstName("Student 3").build();
        Student student4 = Student.builder().id(studentId4).firstName("Student 4").build();
        Student student5 = Student.builder().id(studentId5).firstName("Student 5").build();
        Student student6 = Student.builder().id(studentId6).firstName("Student 6").build();

        List<Student> students = List.of(student1, student2, student3, student4, student5, student6);

        int limit = 2;
        int offset = 2;

        when(studentRepository.findAll(anyInt(), anyInt())).thenReturn(students.subList(offset, offset*2));

        List<Student> responseList = studentService.findAll(limit, offset);

        assertEquals(studentId3, responseList.get(0).getId());
        assertEquals(studentId4, responseList.get(1).getId());

        assertEquals("Student 3", responseList.get(0).getFirstName());
        assertEquals("Student 4", responseList.get(1).getFirstName());
    }

    @Test
    void givenValidStudentIdAndSemester_whenFindEnrollmentsBySemester_thenItReturnsListOfEnrollments() {
        // TODO: implement the Enrollments and Student


        String studentId = UUID.randomUUID().toString();
        int semester = 4;

        when(studentRepository.exists(anyString())).thenReturn(true);
    }
    
}
