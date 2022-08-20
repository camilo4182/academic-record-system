package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.CourseClass;

import java.util.List;
import java.util.Optional;

public interface ICourseClassRepository {

    int save(CourseClass courseClass);

    int update(String id, CourseClass courseClass);

    int deleteById(String id);

    Optional<CourseClass> findById(String id);

    List<CourseClass> findAll();

    List<CourseClass> findAll(int limit, int offset);

    boolean exists(String id);
    
}
