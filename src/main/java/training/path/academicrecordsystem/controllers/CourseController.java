package training.path.academicrecordsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.User;
import training.path.academicrecordsystem.services.ICourseService;
import training.path.academicrecordsystem.services.IUserService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class CourseController implements ICourseController {

    private final ICourseService courseService;

    @Autowired
    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    @GetMapping("users/{user_id}/courses/{course_id}")
    public ResponseEntity<Course> findById(@PathVariable("user_id") long userId, @PathVariable("course_id") long courseId) {
        try {
            List<Course> courses = courseService.findCoursesByUser(userId);

            Course course = courses.stream().filter(c -> c.getId() == courseId).findFirst().orElse(new Course("not found", null));
            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Course>> findAll(String name) {
        return null;
    }

    @Override
    public ResponseEntity<String> save(Course course) {
        return null;
    }

    @Override
    public ResponseEntity<String> update(long id, Course course) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteById(long id) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteAll() {
        return null;
    }
}
