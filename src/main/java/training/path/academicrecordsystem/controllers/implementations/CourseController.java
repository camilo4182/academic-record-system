package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyCourseClassDTO;
import training.path.academicrecordsystem.controllers.dtos.CourseDTO;
import training.path.academicrecordsystem.controllers.interfaces.ICourseController;
import training.path.academicrecordsystem.controllers.mappers.CourseClassMapper;
import training.path.academicrecordsystem.controllers.mappers.CourseMapper;
import training.path.academicrecordsystem.exceptions.BadResourceDataException;
import training.path.academicrecordsystem.exceptions.CouldNotPerformOperationException;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.NullRequestBodyException;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.services.interfaces.ICourseService;

import java.util.List;
import java.util.Objects;

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
        } catch (BadResourceDataException | NullRequestBodyException e) {
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
        } catch (BadResourceDataException | NullRequestBodyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @DeleteMapping("courses/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        try {
            courseService.deleteById(id);
            return new ResponseEntity<>("Course deleted", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("courses/{id}")
    public ResponseEntity<CourseDTO> findById(@PathVariable("id") String id) {
        try {
            CourseDTO courseDTO = CourseMapper.toDTO(courseService.findById(id));
            return new ResponseEntity<>(courseDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    @GetMapping("courses")
    public ResponseEntity<List<CourseDTO>> findAll(@RequestParam(name = "limit", required = false) Integer limit,
                                                   @RequestParam(name = "offset", required = false) Integer offset) {
        List<Course> courseList;
        if (Objects.isNull(limit) && Objects.isNull(offset)) {
            courseList = courseService.findAll();
        }
        else {
            courseList = courseService.findAll(limit, offset);
        }
        return new ResponseEntity<>(courseList.stream().map(CourseMapper::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("courses/{courseId}/classes")
    public ResponseEntity<List<ResponseBodyCourseClassDTO>> findClassesByCourse(@PathVariable("courseId") String courseId) {
        try {
            List<CourseClass> classesList = courseService.getClassesByCourse(courseId);
            return new ResponseEntity<>(classesList.stream().map(CourseClassMapper::toDTO).toList(), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CouldNotPerformOperationException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
