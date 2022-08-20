package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.repositories.interfaces.ICourseRepository;
import training.path.academicrecordsystem.services.interfaces.ICourseService;

import java.util.List;

@Service
@Validated
public class CourseService implements ICourseService {

    private final ICourseRepository jdbcCourseRepository;

    @Autowired
    public CourseService(ICourseRepository jdbcCourseRepository) {
        this.jdbcCourseRepository = jdbcCourseRepository;
    }

    @Override
    public void save(Course course) {
        jdbcCourseRepository.save(course);
    }

    @Override
    public void update(Course course) throws ResourceNotFoundException {
        if (!jdbcCourseRepository.exists(course.getId())) throw new ResourceNotFoundException("Course " + course.getId() + " was not found");
        jdbcCourseRepository.update(course.getId(), course);
    }

    @Override
    public void deleteById(String id) throws ResourceNotFoundException {
        if (!jdbcCourseRepository.exists(id)) throw new ResourceNotFoundException("Course " + id + " was not found");
        jdbcCourseRepository.deleteById(id);
    }

    @Override
    public Course findById(String id) throws ResourceNotFoundException {
        return jdbcCourseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course " + id + " was not found"));
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
    public List<CourseClass> findClassesByCourse(String courseId) throws ResourceNotFoundException {
        if (!jdbcCourseRepository.exists(courseId)) throw new ResourceNotFoundException("Course " + courseId + " was not found");
        return jdbcCourseRepository.getClassesByCourse(courseId);
    }

}
