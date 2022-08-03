package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.model.Course;

import java.util.List;

public interface ICourseService {

    Course findById(long id);
    Course findByName(String name);
    List<Course> findCoursesByUser(long userId);
    List<Course> findAll();
    void save(long userId, Course course) throws Exception;
    void update(long courseId, Course course) throws Exception;
    void deleteById(long id) throws Exception;
    public void deleteCoursesByUser(long userId) throws Exception;
    void deleteAll();
    
}
