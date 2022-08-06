package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.Course;

import java.util.List;

public interface ICourseService {

    void save(Course course);
    void update(Course course) throws NotFoundResourceException;
    void deleteById(String id) throws NotFoundResourceException;
    Course findById(String id) throws NotFoundResourceException;
    Course findByName(String name) throws NotFoundResourceException;
    List<Course> findAll();

}
