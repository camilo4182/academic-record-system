package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.CourseDTO;
import training.path.academicrecordsystem.model.Course;

import java.util.UUID;

public class CourseMapper {
    
    public static CourseDTO toDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setCredits(course.getCredits());
        return courseDTO;
    }
    
    public static Course toEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setId(courseDTO.getId());
        course.setName(courseDTO.getName());
        course.setCredits(courseDTO.getCredits());
        return course;
    }
    
    public static Course createEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setId(UUID.randomUUID().toString());
        course.setName(courseDTO.getName());
        course.setCredits(courseDTO.getCredits());
        return course;
    }
    
}
