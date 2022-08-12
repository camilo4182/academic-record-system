package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.CourseClassDTO;
import training.path.academicrecordsystem.controllers.dtos.EnrollmentDTO;
import training.path.academicrecordsystem.controllers.dtos.StudentDTO;
import training.path.academicrecordsystem.exceptions.BadResourceDataException;
import training.path.academicrecordsystem.exceptions.NullRequestBodyException;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;

import java.util.Objects;
import java.util.UUID;

public class EnrollmentMapper {

    public static EnrollmentDTO toDTO(Enrollment enrollment) {
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
        enrollmentDTO.setSemester(enrollment.getSemester());

        StudentDTO studentDTO = StudentMapper.toDTO(enrollment.getStudent());
        enrollmentDTO.setStudent(studentDTO);

        CourseClassDTO courseClassDTO = CourseClassMapper.toDTO(enrollment.getCourseClass());
        enrollmentDTO.setCourseClass(courseClassDTO);

        return enrollmentDTO;
    }

    public static Enrollment toEntity(EnrollmentDTO enrollmentDTO) throws BadResourceDataException, NullRequestBodyException {
        validateDTO(enrollmentDTO);

        Enrollment enrollment = new Enrollment();
        enrollment.setId(enrollment.getId());
        enrollment.setSemester(enrollmentDTO.getSemester());

        Student student = new Student();
        student.setId(enrollmentDTO.getStudent().getId());
        enrollment.setStudent(student);

        CourseClass courseClass = new CourseClass();
        courseClass.setId(enrollmentDTO.getCourseClass().getId());
        enrollment.setCourseClass(courseClass);

        Career career = new Career();
        career.setId(enrollmentDTO.getCareer().getId());
        enrollment.setCareer(career);

        return enrollment;
    }

    public static Enrollment createEntity(EnrollmentDTO enrollmentDTO) throws BadResourceDataException, NullRequestBodyException {
        validateDTO(enrollmentDTO);

        Enrollment enrollment = new Enrollment();
        enrollment.setId(UUID.randomUUID().toString());
        enrollment.setSemester(enrollmentDTO.getSemester());

        Student student = new Student();
        student.setId(enrollmentDTO.getStudent().getId());
        enrollment.setStudent(student);

        CourseClass courseClass = new CourseClass();
        courseClass.setId(enrollmentDTO.getCourseClass().getId());
        enrollment.setCourseClass(courseClass);

        Career career = new Career();
        career.setId(enrollmentDTO.getCareer().getId());
        enrollment.setCareer(career);

        return enrollment;
    }

    private static void validateDTO(EnrollmentDTO enrollmentDTO) throws BadResourceDataException {
        if (Objects.isNull(enrollmentDTO.getStudent())) throw new BadResourceDataException("This enrollment has to be assigned to a student");
        if (Objects.isNull(enrollmentDTO.getCourseClass())) throw new BadResourceDataException("You must enroll to a class");
        if (enrollmentDTO.getSemester() < 1 || enrollmentDTO.getSemester() > 12) throw new BadResourceDataException("The semester must be between 1 and 12");
    }

}
