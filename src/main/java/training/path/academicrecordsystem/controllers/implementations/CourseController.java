package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.path.academicrecordsystem.controllers.dtos.CourseDTO;
import training.path.academicrecordsystem.controllers.interfaces.ICourseController;
import training.path.academicrecordsystem.controllers.mappers.CourseMapper;
import training.path.academicrecordsystem.exceptions.BadArgumentsException;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.exceptions.NullRequestBodyException;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.services.interfaces.ICourseService;

import java.util.List;

@RestController
public class CourseController implements ICourseController {

    private final ICourseService courseService;

    @Autowired
    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    @PostMapping("courses")
    public ResponseEntity<String> save(@RequestBody CourseDTO courseDTO) {
        try {
            Course course = CourseMapper.createEntity(courseDTO);
            courseService.save(course);
            return new ResponseEntity<>("Course created", HttpStatus.OK);
        } catch (BadArgumentsException | NullRequestBodyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PutMapping("courses/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody CourseDTO courseDTO) {
        try {
            courseDTO.setId(id);
            Course course = CourseMapper.toEntity(courseDTO);
            courseService.update(course);
            return new ResponseEntity<>("Course updated", HttpStatus.OK);
        } catch (BadArgumentsException | NullRequestBodyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundResourceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    @Override
    @DeleteMapping("courses/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        try {
            courseService.deleteById(id);
            return new ResponseEntity<>("Course deleted", HttpStatus.OK);
        } catch (NotFoundResourceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    @Override
    @GetMapping("courses/{id}")
    public ResponseEntity<CourseDTO> findById(@PathVariable("id") String id) {
        try {
            CourseDTO courseDTO = CourseMapper.toDTO(courseService.findById(id));
            return new ResponseEntity<>(courseDTO, HttpStatus.OK);
        } catch (NotFoundResourceException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    @GetMapping("courses")
    public ResponseEntity<List<CourseDTO>> findAll() {
        List<Course> courseList = courseService.findAll();
        return new ResponseEntity<>(courseList.stream().map(CourseMapper::toDTO).toList(), HttpStatus.OK);
    }
}
