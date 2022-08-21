package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Course;

import java.util.List;
import java.util.Optional;

public interface ICareerRepository {

    Optional<Career> findById(String id);

    Optional<Career> findByName(String name);

    List<Career> findAll();

    List<Career> findAll(int limit, int offset);

    int save(Career career);

    int update(String id, Career career);

    int deleteById(String id);

    boolean exists(String id);

    void assignCourseToCareer(String courseId, String careerId);

    List<Course> findCoursesByCareer(String careerName);
    
}
