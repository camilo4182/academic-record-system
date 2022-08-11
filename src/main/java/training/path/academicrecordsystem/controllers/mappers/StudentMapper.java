package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.StudentDTO;
import training.path.academicrecordsystem.exceptions.BadResourceDataException;
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

    public static Student toEntity(StudentDTO studentDTO) throws BadResourceDataException {
        validateStudentDTO(studentDTO);
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setAverageGrade(studentDTO.getAverageGrade());
        return student;
    }

    public static Student createEntity(StudentDTO studentDTO) throws BadResourceDataException {
        validateStudentDTO(studentDTO);
        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setAverageGrade(studentDTO.getAverageGrade());
        return student;
    }

    private static void validateStudentDTO(StudentDTO studentDTO) throws BadResourceDataException {
        if (Objects.isNull(studentDTO.getName())) throw new BadResourceDataException("Student name cannot be null");
        if (studentDTO.getName().isEmpty()) throw new BadResourceDataException("You must provide a student name");
        if (studentDTO.getName().isBlank()) throw new BadResourceDataException("Student name cannot be just blank spaces");
        if (Objects.isNull(studentDTO.getEmail())) throw new BadResourceDataException("Student email cannot be null");
        if (studentDTO.getEmail().isEmpty()) throw new BadResourceDataException("You must provide student email");
        if (studentDTO.getEmail().isBlank()) throw new BadResourceDataException("Student email cannot be just blank spaces");
        if (studentDTO.getAverageGrade() < 0.0 || studentDTO.getAverageGrade() > 5.0) throw new BadResourceDataException("Student average grades must be between 0.0 and 5.0");
    }

}
