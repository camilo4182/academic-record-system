package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import training.path.academicrecordsystem.controllers.dtos.CourseClassDTO;
import training.path.academicrecordsystem.controllers.interfaces.ICourseClassController;
import training.path.academicrecordsystem.controllers.mappers.CourseClassMapper;
import training.path.academicrecordsystem.exceptions.BadArgumentsException;
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
    public ResponseEntity<String> update(String id, CourseClassDTO courseClassDTO) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<CourseClassDTO> findById(String id) {
        return null;
    }

    @Override
    @GetMapping("classes")
    public ResponseEntity<List<CourseClassDTO>> findAll() {
        List<CourseClass> courseClasses = courseClassService.findAll();
        return new ResponseEntity<>(courseClasses.stream().map(CourseClassMapper::toDTo).toList(), HttpStatus.OK);
    }
}
