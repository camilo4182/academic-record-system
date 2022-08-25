package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.RequestBodyEnrollmentDTO;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyEnrollmentDTO;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;

import java.util.UUID;

public class EnrollmentMapper {

    public static ResponseBodyEnrollmentDTO toDTO(Enrollment enrollment) {
        ResponseBodyEnrollmentDTO enrollmentDTO = new ResponseBodyEnrollmentDTO();
        enrollmentDTO.setId(enrollment.getId());
        enrollmentDTO.setName(enrollment.getStudent().getFirstName());
        enrollmentDTO.setCareer(enrollment.getStudent().getCareer().getName());
        enrollmentDTO.setClasses(enrollment.getCourseClasses().stream().map(CourseClassMapper::toDTO).toList());
        enrollmentDTO.setSemester(enrollment.getSemester());

        return enrollmentDTO;
    }

    public static Enrollment toEntity(RequestBodyEnrollmentDTO requestBodyEnrollmentDTO) {
        Enrollment enrollment = new Enrollment();
        enrollment.setSemester(requestBodyEnrollmentDTO.getSemester());

        Student student = new Student();
        student.setId(requestBodyEnrollmentDTO.getStudentId());
        enrollment.setStudent(student);

        CourseClass courseClass = new CourseClass();
        courseClass.setId(requestBodyEnrollmentDTO.getClassId());
        enrollment.addClass(courseClass);

        return enrollment;
    }

    // Not used
    public static Enrollment createEntity(RequestBodyEnrollmentDTO requestBodyEnrollmentDTO) {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(UUID.randomUUID().toString());
        enrollment.setSemester(requestBodyEnrollmentDTO.getSemester());

        Student student = new Student();
        student.setId(requestBodyEnrollmentDTO.getStudentId());
        enrollment.setStudent(student);

        CourseClass courseClass = new CourseClass();
        courseClass.setId(requestBodyEnrollmentDTO.getClassId());
        enrollment.addClass(courseClass);

        return enrollment;
    }

    public static Enrollment createEntityFromStudent(Student student) {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(UUID.randomUUID().toString());
        enrollment.setStudent(student);

        return enrollment;
    }

}
