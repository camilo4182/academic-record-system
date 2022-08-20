package training.path.academicrecordsystem.services;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.repositories.implementations.JdbcStudentRepository;
import training.path.academicrecordsystem.services.implementations.StudentService;

@SpringBootTest
public class StudentServiceTests {

    @Mock
    JdbcStudentRepository studentRepository;

    @InjectMocks
    StudentService studentService;

}
