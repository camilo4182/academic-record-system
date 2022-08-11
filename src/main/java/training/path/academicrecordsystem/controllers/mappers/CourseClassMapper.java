package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.CourseClassDTO;
import training.path.academicrecordsystem.exceptions.BadResourceDataException;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Professor;

import java.util.Objects;
import java.util.UUID;

public class CourseClassMapper {

    public static CourseClassDTO toDTo(CourseClass courseClass) {
        CourseClassDTO courseClassDTO = new CourseClassDTO();
        courseClassDTO.setId(courseClass.getId());
        courseClassDTO.setAvailable(courseClass.isAvailable());
        courseClassDTO.setProfessorId(courseClass.getProfessor().getId());
        courseClassDTO.setProfessorName(courseClass.getProfessor().getName());
        courseClassDTO.setCourseId(courseClass.getCourse().getId());
        courseClassDTO.setCourseName(courseClass.getCourse().getName());
        return courseClassDTO;
    }

    public static CourseClass toEntity(CourseClassDTO courseClassDTO) throws BadResourceDataException {
        validateDTO(courseClassDTO);
        CourseClass courseClass = new CourseClass();
        courseClass.setId(courseClassDTO.getId());
        courseClass.setAvailable(courseClassDTO.isAvailable());

        Professor professor = new Professor();
        professor.setId(courseClassDTO.getProfessorId());
        courseClass.setProfessor(professor);

        Course course = new Course();
        course.setId(courseClassDTO.getCourseId());
        courseClass.setCourse(course);

        return courseClass;
    }

    public static CourseClass createEntity(CourseClassDTO courseClassDTO) throws BadResourceDataException {
        validateDTO(courseClassDTO);
        CourseClass courseClass = new CourseClass();
        courseClass.setId(UUID.randomUUID().toString());
        courseClass.setAvailable(courseClassDTO.isAvailable());

        Professor professor = new Professor();
        professor.setId(courseClassDTO.getProfessorId());
        courseClass.setProfessor(professor);

        Course course = new Course();
        course.setId(courseClassDTO.getCourseId());
        courseClass.setCourse(course);

        return courseClass;
    }

    private static void validateDTO(CourseClassDTO courseClassDTO) throws BadResourceDataException {
        if (Objects.isNull(courseClassDTO.getProfessorId()) || courseClassDTO.getProfessorId().isBlank())
            throw new BadResourceDataException("Class must be assigned to a professor");
        if (Objects.isNull(courseClassDTO.getCourseId()) || courseClassDTO.getCourseId().isBlank())
            throw new BadResourceDataException("Class must have a course associated");
    }

}
