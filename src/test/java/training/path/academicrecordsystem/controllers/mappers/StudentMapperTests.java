package training.path.academicrecordsystem.controllers.mappers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.controllers.dtos.RequestBodyStudentDTO;
import training.path.academicrecordsystem.model.Student;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentMapperTests {

    @Test
    void givenRequestBodyStudentDTO_whenCreateEntity_thenItReturnsCreatedEntity() {
        String careerId = UUID.randomUUID().toString();

        RequestBodyStudentDTO dto = RequestBodyStudentDTO.builder()
                .firstName("Juan")
                .lastName("Rodriguez")
                .email("juan.rodriguez@email.com")
                .password("12345678")
                .careerId(careerId)
                .build();

        Student student = StudentMapper.createEntity(dto);

        assertEquals("Juan", student.getFirstName());
        assertEquals("Rodriguez", student.getLastName());
        assertEquals("juan.rodriguez", student.getUserName());
        assertEquals("juan.rodriguez@email.com", student.getEmail());
        assertEquals("12345678", student.getPassword());
        assertEquals(0.0f, student.getAverageGrade());
        assertEquals(careerId, student.getCareer().getId());
    }

}
