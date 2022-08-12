package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.RequestBodyEnrollmentDTO;
import training.path.academicrecordsystem.exceptions.BadResourceDataException;
import training.path.academicrecordsystem.exceptions.NullRequestBodyException;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;

import java.util.Objects;
import java.util.UUID;

public class EnrollmentMapper {

    public static RequestBodyEnrollmentDTO toDTO(Enrollment enrollment) {
        RequestBodyEnrollmentDTO enrollmentDTO = new RequestBodyEnrollmentDTO();
        enrollmentDTO.setSemester(enrollment.getSemester());

        return enrollmentDTO;
    }

    public static Enrollment toEntity(RequestBodyEnrollmentDTO requestBodyEnrollmentDTO) throws BadResourceDataException, NullRequestBodyException {
        validateDTO(requestBodyEnrollmentDTO);

        Enrollment enrollment = new Enrollment();
        enrollment.setId(enrollment.getId());
        enrollment.setSemester(requestBodyEnrollmentDTO.getSemester());

        Student student = new Student();
        student.setId(requestBodyEnrollmentDTO.getStudentId());
        enrollment.setStudent(student);

        CourseClass courseClass = new CourseClass();
        courseClass.setId(requestBodyEnrollmentDTO.getCourseClassId());
        enrollment.setCourseClass(courseClass);

        return enrollment;
    }

    public static Enrollment createEntity(RequestBodyEnrollmentDTO requestBodyEnrollmentDTO) throws BadResourceDataException, NullRequestBodyException {
        validateDTO(requestBodyEnrollmentDTO);

        Enrollment enrollment = new Enrollment();
        enrollment.setId(UUID.randomUUID().toString());
        enrollment.setSemester(requestBodyEnrollmentDTO.getSemester());

        Student student = new Student();
        student.setId(requestBodyEnrollmentDTO.getStudentId());
        enrollment.setStudent(student);

        CourseClass courseClass = new CourseClass();
        courseClass.setId(requestBodyEnrollmentDTO.getCourseClassId());
        enrollment.setCourseClass(courseClass);

        return enrollment;
    }

    private static void validateDTO(RequestBodyEnrollmentDTO requestBodyEnrollmentDTO) throws BadResourceDataException {
        if (Objects.isNull(requestBodyEnrollmentDTO.getStudentId())) throw new BadResourceDataException("This enrollment has to be assigned to a student");
        if (Objects.isNull(requestBodyEnrollmentDTO.getCourseClassId())) throw new BadResourceDataException("You must enroll to a class");
        if (requestBodyEnrollmentDTO.getSemester() < 1 || requestBodyEnrollmentDTO.getSemester() > 12) throw new BadResourceDataException("The semester must be between 1 and 12");
    }

}
