package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.path.academicrecordsystem.controllers.interfaces.ICourseController;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.services.interfaces.ICourseService;

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
    public ResponseEntity<Course> findCourseByIdAndUser(@PathVariable("user_id") long userId, @PathVariable("course_id") long courseId) {
        try {
            List<Course> courses = courseService.findCoursesByUser(userId);
            Course course = courses.stream().filter(c -> c.getId() == courseId).findFirst().orElse(new Course("not found", null));
            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("users/{user_id}/courses")
    public ResponseEntity<List<Course>> findAllCoursesByUser(@PathVariable("user_id") long userId) {
        try {
            List<Course> courses = courseService.findCoursesByUser(userId);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @PostMapping("users/{user_id}/courses")
    public ResponseEntity<String> saveCourseForUser(@PathVariable("user_id") long userId, @RequestBody Course course) {
        try {
            courseService.save(userId, course);
            return new ResponseEntity<>("Course created successfully.", HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PutMapping("users/{user_id}/courses/{course_id}")
    public ResponseEntity<String> update(@PathVariable("user_id") long userId, @PathVariable("course_id") long courseId, @RequestBody Course course) {
        try {
            courseService.update(courseId, course);
            return new ResponseEntity<>("Course updated successfully.", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @DeleteMapping("users/{user_id}/courses/{course_id}")
    public ResponseEntity<String> deleteById(@PathVariable("user_id") long userId, @PathVariable("course_id") long courseId) {
        try {
            courseService.deleteById(courseId);
            return new ResponseEntity<>("Course deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @DeleteMapping("users/{user_id}/courses")
    public ResponseEntity<String> deleteAllCoursesByUser(@PathVariable("user_id") long userId) {
        try {
            courseService.deleteCoursesByUser(userId);
            return new ResponseEntity<>("All courses for user " + userId + " were deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
