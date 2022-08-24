package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.*;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Student;

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

    public static Student toEntity(UpdateStudentByAdminDTO requestBodyStudentDTO) {
        Student student = new Student();
        student.setId(requestBodyStudentDTO.getId());
        student.setAverageGrade(requestBodyStudentDTO.getAverageGrade());

        Career career = new Career();
        career.setId(requestBodyStudentDTO.getCareerId());
        student.setCareer(career);

        return student;
    }

    public static Student toEntity(UpdateStudentByStudentDTO update) {
        Student student = new Student();
        student.setId(update.getId());
        student.setName(update.getName());
        student.setPassword(update.getPassword());
        student.setEmail(update.getEmail());

        return student;
    }

    public static Student createEntity(RequestBodyStudentDTO requestBodyStudentDTO) {
        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setName(requestBodyStudentDTO.getName());
        student.setPassword(requestBodyStudentDTO.getPassword());
        student.setEmail(requestBodyStudentDTO.getEmail());
        student.setAverageGrade(0.0f);

        Career career = new Career();
        career.setId(requestBodyStudentDTO.getCareerId());
        student.setCareer(career);

        return student;
    }

}
