package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;

import java.util.List;

public interface ICareerService {

    void save(Career career);
    void update(Career career) throws ResourceNotFoundException;
    void deleteById(String id) throws ResourceNotFoundException;
    Career findById(String id) throws ResourceNotFoundException;
    Career findByName(String name) throws ResourceNotFoundException;
    List<Career> findAll();
    List<Career> findAll(int limit, int offset);
    void assignCourseToCareer(String courseId, String careerId) throws ResourceNotFoundException;
    List<Course> findCoursesByCareer(String careerId) throws ResourceNotFoundException;

}
