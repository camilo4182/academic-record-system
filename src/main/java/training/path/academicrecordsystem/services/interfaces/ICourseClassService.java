package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.CourseClass;

import java.util.List;

public interface ICourseClassService {

    void save(CourseClass courseClass);
    void update(CourseClass courseClass) throws ResourceNotFoundException;
    void deleteById(String id) throws ResourceNotFoundException;
    CourseClass findById(String id) throws ResourceNotFoundException;
    List<CourseClass> findAll();
    List<CourseClass> findAll(int limit, int offset);


}
