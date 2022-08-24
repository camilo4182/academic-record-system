package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.*;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Professor;

import java.util.UUID;

public class CourseClassMapper {

    public static ResponseBodyCourseClassDTO toDTO(CourseClass courseClass) {
        ResponseBodyCourseClassDTO responseBodyCourseClassDTO = new ResponseBodyCourseClassDTO();
        responseBodyCourseClassDTO.setId(courseClass.getId());
        responseBodyCourseClassDTO.setAvailable(courseClass.isAvailable());
        responseBodyCourseClassDTO.setEnrolledStudents(courseClass.getEnrolledStudents());
        responseBodyCourseClassDTO.setCapacity(courseClass.getCapacity());

        CourseDTO courseDTO = CourseMapper.toDTO(courseClass.getCourse());
        responseBodyCourseClassDTO.setCourse(courseDTO);

        ResponseBodyProfessorDTO professorDTO = ProfessorMapper.toDTO(courseClass.getProfessor());
        responseBodyCourseClassDTO.setProfessor(professorDTO);

        return responseBodyCourseClassDTO;
    }

    public static CourseClass toEntity(RequestBodyCourseClassDTO courseClassDTO) {
        CourseClass courseClass = new CourseClass();
        courseClass.setId(courseClassDTO.getId());
        courseClass.setAvailable(courseClassDTO.isAvailable());
        courseClass.setCapacity(courseClassDTO.getCapacity());

        Course course = new Course();
        course.setId(courseClassDTO.getCourseId());
        courseClass.setCourse(course);

        Professor professor = new Professor();
        professor.setId(courseClassDTO.getProfessorId());
        courseClass.setProfessor(professor);

        return courseClass;
    }

    public static CourseClass createEntity(RequestBodyCourseClassDTO courseClassDTO) {
        CourseClass courseClass = new CourseClass();
        courseClass.setId(UUID.randomUUID().toString());
        courseClass.setAvailable(courseClassDTO.isAvailable());
        courseClass.setCapacity(courseClassDTO.getCapacity());

        Course course = new Course();
        course.setId(courseClassDTO.getCourseId());
        courseClass.setCourse(course);

        Professor professor = new Professor();
        professor.setId(courseClassDTO.getProfessorId());
        courseClass.setProfessor(professor);

        return courseClass;
    }

}
