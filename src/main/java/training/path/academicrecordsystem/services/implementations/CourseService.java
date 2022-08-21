package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.UniqueColumnViolationException;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.repositories.interfaces.ICourseRepository;
import training.path.academicrecordsystem.services.interfaces.ICourseService;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class CourseService implements ICourseService {

    private final ICourseRepository courseRepository;

    @Autowired
    public CourseService(ICourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void save(Course course) throws UniqueColumnViolationException {
        Optional<Course> foundCourse = courseRepository.findByName(course.getName());
        if (foundCourse.isPresent()) throw new UniqueColumnViolationException("There is already a course with the name " + course.getName() + ". Enter another one.");
        courseRepository.save(course);
    }

    @Override
    public void update(Course course) throws ResourceNotFoundException, UniqueColumnViolationException {
        if (!courseRepository.exists(course.getId())) throw new ResourceNotFoundException("Course " + course.getId() + " was not found");
        Optional<Course> foundCourse = courseRepository.findByName(course.getName());
        if (foundCourse.isPresent()) throw new UniqueColumnViolationException("There is already a course with the name " + course.getName() + ". Enter another one.");
        courseRepository.update(course.getId(), course);
    }

    @Override
    public void deleteById(String id) throws ResourceNotFoundException {
        if (!courseRepository.exists(id)) throw new ResourceNotFoundException("Course " + id + " was not found");
        courseRepository.deleteById(id);
    }

    @Override
    public Course findById(String id) throws ResourceNotFoundException {
        return courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course " + id + " was not found"));
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findAll(int limit, int offset) {
        return courseRepository.findAll(limit, offset);
    }

    @Override
    public List<CourseClass> findClassesByCourse(String courseId) throws ResourceNotFoundException {
        if (!courseRepository.exists(courseId)) throw new ResourceNotFoundException("Course " + courseId + " was not found");
        return courseRepository.getClassesByCourse(courseId);
    }

}
