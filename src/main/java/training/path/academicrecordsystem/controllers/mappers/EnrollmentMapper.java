package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.RequestBodyEnrollmentDTO;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyEnrollmentDTO;
import training.path.academicrecordsystem.exceptions.BadResourceDataException;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class EnrollmentMapper {

    public static ResponseBodyEnrollmentDTO toDTO(Enrollment enrollment) {
        ResponseBodyEnrollmentDTO enrollmentDTO = new ResponseBodyEnrollmentDTO();
        enrollmentDTO.setId(enrollment.getId());
        enrollmentDTO.setName(enrollment.getStudent().getName());
        enrollmentDTO.setCareer(enrollment.getStudent().getCareer().getName());
        enrollmentDTO.setClasses(enrollment.getCourseClasses().stream().map(CourseClassMapper::toDTO).toList());
        enrollmentDTO.setSemester(enrollment.getSemester());

        return enrollmentDTO;
    }

    public static Enrollment toEntity(RequestBodyEnrollmentDTO requestBodyEnrollmentDTO) {
        //validateDTO(requestBodyEnrollmentDTO);

        Enrollment enrollment = new Enrollment();
        enrollment.setId(requestBodyEnrollmentDTO.getId());
        enrollment.setSemester(requestBodyEnrollmentDTO.getSemester());

        Student student = new Student();
        student.setId(requestBodyEnrollmentDTO.getStudentId());
        enrollment.setStudent(student);

        for (String classId : requestBodyEnrollmentDTO.getClassIds()) {
            CourseClass courseClass = new CourseClass();
            courseClass.setId(classId);
            enrollment.addClass(courseClass);
        }

        return enrollment;
    }

    public static Enrollment createEntity(RequestBodyEnrollmentDTO requestBodyEnrollmentDTO) {
        //validateDTO(requestBodyEnrollmentDTO);

        Enrollment enrollment = new Enrollment();
        enrollment.setId(UUID.randomUUID().toString());
        enrollment.setSemester(requestBodyEnrollmentDTO.getSemester());

        Student student = new Student();
        student.setId(requestBodyEnrollmentDTO.getStudentId());
        enrollment.setStudent(student);

        for (String classId : requestBodyEnrollmentDTO.getClassIds()) {
            CourseClass courseClass = new CourseClass();
            courseClass.setId(classId);
            enrollment.addClass(courseClass);
        }

        return enrollment;
    }

    public static Enrollment createEntityFromStudent(Student student) {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(UUID.randomUUID().toString());
        enrollment.setStudent(student);

        return enrollment;
    }

    private static void validateDTO(RequestBodyEnrollmentDTO requestBodyEnrollmentDTO) throws BadResourceDataException {
        if (Objects.isNull(requestBodyEnrollmentDTO.getStudentId())) throw new BadResourceDataException("This enrollment has to be assigned to a student");
        if (Objects.isNull(requestBodyEnrollmentDTO.getClassIds())) throw new BadResourceDataException("You must enroll to a class");
        if (requestBodyEnrollmentDTO.getSemester() < 1 || requestBodyEnrollmentDTO.getSemester() > 12) throw new BadResourceDataException("The semester must be between 1 and 12");
    }

}
