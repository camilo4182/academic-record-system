package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Course;

import java.util.List;
import java.util.Optional;

public interface ICareerRepository {

    int save(Career career);

    int update(String id, Career career);

    int deleteById(String id);

    Optional<Career> findById(String id);

    Optional<Career> findByName(String name);

    List<Career> findAll();

    List<Career> findAll(int limit, int offset);

    boolean exists(String id);

    boolean isCourseAssignedToCareer(String courseId, String careerId);

    void assignCourseToCareer(String courseId, String careerId);

    List<Course> findCoursesByCareer(String careerName);
    
}
