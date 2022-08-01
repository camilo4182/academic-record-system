package training.path.academicrecordsystem.services;

import training.path.academicrecordsystem.model.Course;

import java.util.List;

public interface ICourseService {

    Course findById(long id);
    Course findByName(String name);
    List<Course> findCoursesByUser(long userId);
    List<Course> findAll();
    void save(Course course) throws Exception;
    void update(long id, Course course) throws Exception;
    void deleteById(long id) throws Exception;
    void deleteAll();
    
}
