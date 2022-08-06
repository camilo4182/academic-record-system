package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.CourseDTO;
import training.path.academicrecordsystem.exceptions.BadArgumentsException;
import training.path.academicrecordsystem.exceptions.NullRequestBodyException;
import training.path.academicrecordsystem.model.Course;

import java.util.Objects;
import java.util.UUID;

public class CourseMapper {
    
    public static CourseDTO toDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setCredits(course.getCredits());
        return courseDTO;
    }
    
    public static Course toEntity(CourseDTO courseDTO) throws NullRequestBodyException, BadArgumentsException {
        validateCourseDTO(courseDTO);
        Course course = new Course();
        course.setId(courseDTO.getId());
        course.setName(courseDTO.getName());
        course.setCredits(courseDTO.getCredits());
        return course;
    }
    
    public static Course createEntity(CourseDTO courseDTO) throws NullRequestBodyException, BadArgumentsException {
        validateCourseDTO(courseDTO);
        Course course = new Course();
        course.setId(UUID.randomUUID().toString());
        course.setName(courseDTO.getName());
        course.setCredits(courseDTO.getCredits());
        return course;
    }

    private static void validateCourseDTO(CourseDTO courseDTO) throws NullRequestBodyException, BadArgumentsException {
        if (!Objects.nonNull(courseDTO)) throw new NullRequestBodyException("CourseDTO object is null");
        if (!Objects.nonNull(courseDTO.getName())) throw new BadArgumentsException("Name cannot be null");
        if (courseDTO.getName().isBlank()) throw new BadArgumentsException("Name cannot be empty");
    }
    
}
