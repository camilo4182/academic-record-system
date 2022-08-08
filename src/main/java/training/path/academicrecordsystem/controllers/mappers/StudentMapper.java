package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.StudentDTO;
import training.path.academicrecordsystem.exceptions.BadArgumentsException;
import training.path.academicrecordsystem.model.Student;

import java.util.Objects;
import java.util.UUID;

public class StudentMapper {

    public static StudentDTO toDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setAverageGrade(student.getAverageGrade());
        return studentDTO;
    }

    public static Student toEntity(StudentDTO studentDTO) throws BadArgumentsException {
        validateStudentDTO(studentDTO);
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setAverageGrade(studentDTO.getAverageGrade());
        return student;
    }

    public static Student createEntity(StudentDTO studentDTO) throws BadArgumentsException {
        validateStudentDTO(studentDTO);
        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setAverageGrade(studentDTO.getAverageGrade());
        return student;
    }

    private static void validateStudentDTO(StudentDTO studentDTO) throws BadArgumentsException {
        if (Objects.isNull(studentDTO.getName())) throw new BadArgumentsException("Name cannot be null");
        if (studentDTO.getName().isEmpty()) throw new BadArgumentsException("Name cannot be empty");
        if (studentDTO.getName().isBlank()) throw new BadArgumentsException("Name cannot be blank");
        if (Objects.isNull(studentDTO.getEmail())) throw new BadArgumentsException("Email cannot be null");
        if (studentDTO.getEmail().isEmpty()) throw new BadArgumentsException("Email cannot be empty");
        if (studentDTO.getEmail().isBlank()) throw new BadArgumentsException("Email cannot be blank");
    }

}
