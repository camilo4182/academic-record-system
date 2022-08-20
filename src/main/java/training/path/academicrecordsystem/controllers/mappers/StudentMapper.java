package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.CareerDTO;
import training.path.academicrecordsystem.controllers.dtos.RequestBodyStudentDTO;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyStudentDTO;
import training.path.academicrecordsystem.exceptions.BadResourceDataException;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Student;

import java.util.Objects;
import java.util.UUID;

public class StudentMapper {

    public static ResponseBodyStudentDTO toDTO(Student student) {
        ResponseBodyStudentDTO requestBodyStudentDTO = new ResponseBodyStudentDTO();
        requestBodyStudentDTO.setId(student.getId());
        requestBodyStudentDTO.setName(student.getName());
        requestBodyStudentDTO.setEmail(student.getEmail());
        requestBodyStudentDTO.setAverageGrade(student.getAverageGrade());

        CareerDTO careerDTO = CareerMapper.toDTO(student.getCareer());
        requestBodyStudentDTO.setCareer(careerDTO);

        return requestBodyStudentDTO;
    }

    public static Student toEntity(RequestBodyStudentDTO requestBodyStudentDTO) {
        //validateStudentDTO(requestBodyStudentDTO);
        Student student = new Student();
        student.setId(requestBodyStudentDTO.getId());
        student.setName(requestBodyStudentDTO.getName());
        student.setEmail(requestBodyStudentDTO.getEmail());
        student.setAverageGrade(requestBodyStudentDTO.getAverageGrade());

        Career career = new Career();
        career.setId(requestBodyStudentDTO.getCareerId());
        student.setCareer(career);

        return student;
    }

    public static Student createEntity(RequestBodyStudentDTO requestBodyStudentDTO) {
        //validateStudentDTO(requestBodyStudentDTO);
        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setName(requestBodyStudentDTO.getName());
        student.setEmail(requestBodyStudentDTO.getEmail());
        student.setAverageGrade(requestBodyStudentDTO.getAverageGrade());

        Career career = new Career();
        career.setId(requestBodyStudentDTO.getCareerId());
        student.setCareer(career);

        return student;
    }

    private static void validateStudentDTO(RequestBodyStudentDTO requestBodyStudentDTO) throws BadResourceDataException {
        if (Objects.isNull(requestBodyStudentDTO.getName())) throw new BadResourceDataException("Student name cannot be null");
        if (requestBodyStudentDTO.getName().isEmpty()) throw new BadResourceDataException("You must provide a student name");
        if (requestBodyStudentDTO.getName().isBlank()) throw new BadResourceDataException("Student name cannot be just blank spaces");
        if (Objects.isNull(requestBodyStudentDTO.getEmail())) throw new BadResourceDataException("Student email cannot be null");
        if (requestBodyStudentDTO.getEmail().isEmpty()) throw new BadResourceDataException("You must provide student email");
        if (requestBodyStudentDTO.getEmail().isBlank()) throw new BadResourceDataException("Student email cannot be just blank spaces");
        if (requestBodyStudentDTO.getAverageGrade() < 0.0 || requestBodyStudentDTO.getAverageGrade() > 5.0) throw new BadResourceDataException("Student average grades must be between 0.0 and 5.0");
        if (Objects.isNull(requestBodyStudentDTO.getCareerId())) throw new BadResourceDataException("You must assign this student to a career");
        if (requestBodyStudentDTO.getCareerId().isBlank()) throw new BadResourceDataException("You must assign this student to a career");
    }

}
