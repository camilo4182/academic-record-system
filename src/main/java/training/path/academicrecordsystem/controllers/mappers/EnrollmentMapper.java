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
        enrollmentDTO.setName(enrollment.getStudent().getName());
        enrollmentDTO.setCareer(enrollment.getStudent().getCareer().getName());
        enrollmentDTO.setClasses(enrollment.getCourseClasses().stream().map(CourseClassMapper::toDTO).toList());
        enrollmentDTO.setSemester(enrollment.getSemester());

        return enrollmentDTO;
    }

    public static Enrollment toEntity(RequestBodyEnrollmentDTO requestBodyEnrollmentDTO) {
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

}
