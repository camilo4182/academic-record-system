package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.CourseClass;

import java.util.List;
import java.util.Optional;

public interface CourseClassRepository {

    int save(CourseClass courseClass);
    int update(String id, CourseClass courseClass);
    int deleteById(String id);
    Optional<CourseClass> findById(String id);
    List<CourseClass> findAll();
    boolean exists(String id);
    
}
