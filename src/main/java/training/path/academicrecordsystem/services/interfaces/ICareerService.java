package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;

import java.util.List;

public interface ICareerService {

    void save(Career career);
    void update(Career career) throws NotFoundResourceException;
    void deleteById(String id) throws NotFoundResourceException;
    Career findById(String id) throws NotFoundResourceException;
    Career findByName(String name) throws NotFoundResourceException;
    List<Career> findAll();
    List<Career> findAll(int limit, int offset);
    void assignClassesToCareer(String careerId, List<CourseClass> classes) throws NotFoundResourceException;
    List<Course> findCoursesByCareer(String careerId) throws NotFoundResourceException;

}
