package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.RequestBodyCourseClassDTO;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyCourseClassDTO;
import training.path.academicrecordsystem.controllers.dtos.CourseDTO;
import training.path.academicrecordsystem.controllers.dtos.ProfessorDTO;
import training.path.academicrecordsystem.exceptions.BadResourceDataException;
import training.path.academicrecordsystem.exceptions.NullRequestBodyException;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Professor;

import java.util.Objects;
import java.util.UUID;

public class CourseClassMapper {

    public static ResponseBodyCourseClassDTO toDTO(CourseClass courseClass) {
        ResponseBodyCourseClassDTO responseBodyCourseClassDTO = new ResponseBodyCourseClassDTO();
        responseBodyCourseClassDTO.setId(courseClass.getId());
        responseBodyCourseClassDTO.setAvailable(courseClass.isAvailable());
        responseBodyCourseClassDTO.setCapacity(courseClass.getCapacity());

        CourseDTO courseDTO = CourseMapper.toDTO(courseClass.getCourse());
        responseBodyCourseClassDTO.setCourse(courseDTO);

        ProfessorDTO professorDTO = ProfessorMapper.toDTO(courseClass.getProfessor());
        responseBodyCourseClassDTO.setProfessor(professorDTO);

        return responseBodyCourseClassDTO;
    }

    public static CourseClass toEntity(RequestBodyCourseClassDTO courseClassDTO) {
        //validateDTO(courseClassDTO);
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
        //validateDTO(courseClassDTO);
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

    private static void validateDTO(RequestBodyCourseClassDTO courseClassDTO) throws BadResourceDataException {
        if (Objects.isNull(courseClassDTO.getCourseId())) throw new BadResourceDataException("You must assign this class to a course");
        if (Objects.isNull(courseClassDTO.getProfessorId())) throw new BadResourceDataException("You must assign a professor to this class");
        if (courseClassDTO.getCourseId().isBlank()) throw new BadResourceDataException("Course id cannot be empty");
        if (courseClassDTO.getProfessorId().isBlank()) throw new BadResourceDataException("Professor id cannot be empty");
        if (courseClassDTO.getCapacity() <= 0) throw new BadResourceDataException("Capacity must be greater than 0");
    }

}
