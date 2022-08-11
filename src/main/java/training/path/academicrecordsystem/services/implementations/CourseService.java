package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.path.academicrecordsystem.exceptions.CouldNotPerformDBOperationException;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.repositories.interfaces.CourseRepository;
import training.path.academicrecordsystem.services.interfaces.ICourseService;

import java.util.List;

@Service
public class CourseService implements ICourseService {

    private final CourseRepository jdbcCourseRepository;

    @Autowired
    public CourseService(CourseRepository jdbcCourseRepository) {
        this.jdbcCourseRepository = jdbcCourseRepository;
    }

    @Override
    public void save(Course course) {
        jdbcCourseRepository.save(course);
    }

    @Override
    public void update(Course course) throws NotFoundResourceException {
        if (!jdbcCourseRepository.exists(course.getId())) throw new NotFoundResourceException("Course " + course.getId() + " was not found");
        jdbcCourseRepository.update(course.getId(), course);
    }

    @Override
    public void deleteById(String id) throws NotFoundResourceException {
        if (!jdbcCourseRepository.exists(id)) throw new NotFoundResourceException("Course " + id + " was not found");
        jdbcCourseRepository.deleteById(id);
    }

    @Override
    public Course findById(String id) throws NotFoundResourceException {
        return jdbcCourseRepository.findById(id).orElseThrow(() -> new NotFoundResourceException("Course " + id + " was not found"));
    }

    @Override
    public Course findByName(String name) throws NotFoundResourceException {
        return jdbcCourseRepository.findByName(name).orElseThrow(() -> new NotFoundResourceException("Course " + name + " was not found"));
    }

    @Override
    public List<Course> findAll() {
        return jdbcCourseRepository.findAll();
    }

    @Override
    public List<Course> findAll(int limit, int offset) {
        return jdbcCourseRepository.findAll(limit, offset);
    }

    @Override
    public List<CourseClass> getClassesByCourse(String courseId) throws NotFoundResourceException, CouldNotPerformDBOperationException {
        if (!jdbcCourseRepository.exists(courseId)) throw new NotFoundResourceException("Course " + courseId + " was not found");
        return jdbcCourseRepository.getClassesByCourse(courseId).orElseThrow(() -> new CouldNotPerformDBOperationException("Internal error"));
    }

}
