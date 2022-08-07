package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.path.academicrecordsystem.controllers.dtos.CourseClassDTO;
import training.path.academicrecordsystem.controllers.interfaces.ICourseClassController;
import training.path.academicrecordsystem.controllers.mappers.CourseClassMapper;
import training.path.academicrecordsystem.exceptions.BadArgumentsException;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.services.interfaces.ICourseClassService;

import java.util.List;

@RestController
public class CourseClassController implements ICourseClassController {

    private final ICourseClassService courseClassService;

    @Autowired
    public CourseClassController(ICourseClassService courseClassService) {
        this.courseClassService = courseClassService;
    }

    @Override
    @PostMapping("classes")
    public ResponseEntity<String> save(@RequestBody CourseClassDTO courseClassDTO) {
        try {
            CourseClass courseClass = CourseClassMapper.createEntity(courseClassDTO);
            courseClassService.save(courseClass);
            return new ResponseEntity<>("Class registered", HttpStatus.OK);
        } catch (BadArgumentsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PutMapping("classes/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody CourseClassDTO courseClassDTO) {
        try {
            CourseClass courseClass = CourseClassMapper.toEntity(courseClassDTO);
            courseClassService.update(courseClass);
            return new ResponseEntity<>("Class updates", HttpStatus.OK);
        } catch (BadArgumentsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundResourceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @DeleteMapping("classes/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        try {
            courseClassService.deleteById(id);
            return new ResponseEntity<>("Class deleted", HttpStatus.OK);
        } catch (NotFoundResourceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("classes/{id}")
    public ResponseEntity<CourseClassDTO> findById(@PathVariable("id") String id) {
        try {
            CourseClassDTO courseClassDTO = CourseClassMapper.toDTo(courseClassService.findById(id));
            return new ResponseEntity<>(courseClassDTO, HttpStatus.OK);
        } catch (NotFoundResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("classes")
    public ResponseEntity<List<CourseClassDTO>> findAll() {
        List<CourseClass> courseClasses = courseClassService.findAll();
        return new ResponseEntity<>(courseClasses.stream().map(CourseClassMapper::toDTo).toList(), HttpStatus.OK);
    }

}