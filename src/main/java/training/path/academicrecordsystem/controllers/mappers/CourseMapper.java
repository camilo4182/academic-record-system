package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.CourseDTO;
import training.path.academicrecordsystem.exceptions.BadResourceDataException;
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
    
    public static Course toEntity(CourseDTO courseDTO) throws NullRequestBodyException, BadResourceDataException {
        //validateCourseDTO(courseDTO);
        Course course = new Course();
        course.setId(courseDTO.getId());
        course.setName(courseDTO.getName());
        course.setCredits(courseDTO.getCredits());
        return course;
    }
    
    public static Course createEntity(CourseDTO courseDTO) throws NullRequestBodyException, BadResourceDataException {
        //validateCourseDTO(courseDTO);
        Course course = new Course();
        course.setId(UUID.randomUUID().toString());
        course.setName(courseDTO.getName());
        course.setCredits(courseDTO.getCredits());
        return course;
    }

    private static void validateCourseDTO(CourseDTO courseDTO) throws NullRequestBodyException, BadResourceDataException {
        if (Objects.isNull(courseDTO)) throw new NullRequestBodyException("You must provide course information");
        if (Objects.isNull(courseDTO.getName())) throw new BadResourceDataException("Course name cannot be null");
        if (courseDTO.getName().isEmpty()) throw new BadResourceDataException("You must provide course name");
        if (courseDTO.getName().isBlank()) throw new BadResourceDataException("Course name cannot be just blank spaces");
        if (courseDTO.getCredits() < 0 || courseDTO.getCredits() > 10) throw new BadResourceDataException("Course credits must be between 0 and 10");
    }
    
}
