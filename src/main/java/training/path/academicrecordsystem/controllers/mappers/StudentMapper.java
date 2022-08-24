package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.*;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Student;

import java.util.UUID;

public class StudentMapper {

    public static ResponseBodyStudentDTO toDTO(Student student) {
        ResponseBodyStudentDTO responseBodyStudentDTO = new ResponseBodyStudentDTO();
        responseBodyStudentDTO.setId(student.getId());
        responseBodyStudentDTO.setFirstName(student.getFirstName());
        responseBodyStudentDTO.setLastName(student.getLastName());
        responseBodyStudentDTO.setUserName(student.getUserName());
        responseBodyStudentDTO.setEmail(student.getEmail());
        responseBodyStudentDTO.setAverageGrade(student.getAverageGrade());

        CareerDTO careerDTO = CareerMapper.toDTO(student.getCareer());
        responseBodyStudentDTO.setCareer(careerDTO);

        return responseBodyStudentDTO;
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

    public static Student toEntity(UpdateUserByUserDTO update) {
        Student student = new Student();
        student.setId(update.getId());
        student.setFirstName(update.getFirstName());
        student.setLastName(update.getLastName());
        student.setUserName(update.getFirstName().toLowerCase() + "." + update.getLastName().toLowerCase());
        student.setPassword(update.getPassword());
        student.setEmail(update.getEmail());

        return student;
    }

    public static Student createEntity(RequestBodyStudentDTO requestBodyStudentDTO) {
        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setFirstName(requestBodyStudentDTO.getFirstName());
        student.setLastName(requestBodyStudentDTO.getLastName());
        student.setUserName(requestBodyStudentDTO.getFirstName().toLowerCase() + "." + requestBodyStudentDTO.getLastName().toLowerCase());
        student.setPassword(requestBodyStudentDTO.getPassword());
        student.setEmail(requestBodyStudentDTO.getEmail());
        student.setAverageGrade(0.0f);

        Career career = new Career();
        career.setId(requestBodyStudentDTO.getCareerId());
        student.setCareer(career);

        return student;
    }

}
