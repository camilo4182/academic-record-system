package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.CourseClassDTO;
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

    public static CourseClassDTO toDTO(CourseClass courseClass) {
        CourseClassDTO courseClassDTO = new CourseClassDTO();
        courseClassDTO.setId(courseClass.getId());
        courseClassDTO.setAvailable(courseClass.isAvailable());

        CourseDTO courseDTO = CourseMapper.toDTO(courseClass.getCourse());
        courseClassDTO.setCourse(courseDTO);

        ProfessorDTO professorDTO = ProfessorMapper.toDTO(courseClass.getProfessor());
        courseClassDTO.setProfessor(professorDTO);

        return courseClassDTO;
    }

    public static CourseClass toEntity(CourseClassDTO courseClassDTO) throws BadResourceDataException, NullRequestBodyException {
        validateDTO(courseClassDTO);
        CourseClass courseClass = new CourseClass();
        courseClass.setId(courseClassDTO.getId());
        courseClass.setAvailable(courseClassDTO.isAvailable());

        Course course = new Course();
        course.setId(courseClassDTO.getCourse().getId());
        courseClass.setCourse(course);

        Professor professor = new Professor();
        professor.setId(courseClassDTO.getProfessor().getId());
        courseClass.setProfessor(professor);

        return courseClass;
    }

    public static CourseClass createEntity(CourseClassDTO courseClassDTO) throws BadResourceDataException, NullRequestBodyException {
        validateDTO(courseClassDTO);
        CourseClass courseClass = new CourseClass();
        courseClass.setId(UUID.randomUUID().toString());
        courseClass.setAvailable(courseClassDTO.isAvailable());

        Course course = new Course();
        course.setId(courseClassDTO.getCourse().getId());
        courseClass.setCourse(course);

        Professor professor = new Professor();
        professor.setId(courseClassDTO.getProfessor().getId());
        courseClass.setProfessor(professor);

        return courseClass;
    }

    private static void validateDTO(CourseClassDTO courseClassDTO) throws BadResourceDataException {
        if (Objects.isNull(courseClassDTO.getCourse())) throw new BadResourceDataException("You must assign this class to a course");
        if (Objects.isNull(courseClassDTO.getProfessor())) throw new BadResourceDataException("You must assign a professor to this class");
        if (courseClassDTO.getCourse().getId().isBlank()) throw new BadResourceDataException("Course id cannot be empty");
        if (courseClassDTO.getProfessor().getId().isBlank()) throw new BadResourceDataException("Professor id cannot be empty");
    }

}
