package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.CourseClass;

import java.util.List;

public interface ICourseClassService {

    void save(CourseClass courseClass);
    void update(CourseClass courseClass) throws NotFoundResourceException;
    void deleteById(String id) throws NotFoundResourceException;
    CourseClass findById(String id) throws NotFoundResourceException;
    List<CourseClass> findAll();
    List<CourseClass> findAll(int limit, int offset);


}
