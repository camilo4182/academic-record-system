package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import training.path.academicrecordsystem.controllers.dtos.RequestBodyCourseClassDTO;
import training.path.academicrecordsystem.controllers.dtos.RequestBodyEnrollmentDTO;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyCourseClassDTO;
import training.path.academicrecordsystem.controllers.interfaces.ICourseClassController;
import training.path.academicrecordsystem.controllers.mappers.CourseClassMapper;
import training.path.academicrecordsystem.controllers.mappers.EnrollmentMapper;
import training.path.academicrecordsystem.exceptions.*;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.services.interfaces.ICourseClassService;
import training.path.academicrecordsystem.services.interfaces.IEnrollmentService;

import java.util.List;
import java.util.Objects;

@RestController
@Validated
public class CourseClassController implements ICourseClassController {

    private final ICourseClassService courseClassService;
    private final IEnrollmentService enrollmentService;

    @Autowired
    public CourseClassController(ICourseClassService courseClassService, IEnrollmentService enrollmentService) {
        this.courseClassService = courseClassService;
        this.enrollmentService = enrollmentService;
    }

    @Override
    @PostMapping("classes")
    public ResponseEntity<String> save(@RequestBody RequestBodyCourseClassDTO courseClassDTO) throws ResourceNotFoundException, UniqueColumnViolationException {
        CourseClass courseClass = CourseClassMapper.createEntity(courseClassDTO);
        courseClassService.save(courseClass);
        return new ResponseEntity<>("Class registered", HttpStatus.OK);
    }

    @Override
    @PutMapping("classes/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody RequestBodyCourseClassDTO courseClassDTO)
            throws ResourceNotFoundException {
        courseClassDTO.setId(id);
        CourseClass courseClass = CourseClassMapper.toEntity(courseClassDTO);
        courseClassService.update(courseClass);
        return new ResponseEntity<>("Class updated", HttpStatus.OK);
    }

    @Override
    @DeleteMapping("classes/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) throws ResourceNotFoundException {
        courseClassService.deleteById(id);
        return new ResponseEntity<>("Class deleted", HttpStatus.OK);
    }

    @Override
    @GetMapping("classes/{id}")
    public ResponseEntity<ResponseBodyCourseClassDTO> findById(@PathVariable("id") String id) throws ResourceNotFoundException {
        ResponseBodyCourseClassDTO responseBodyCourseClassDTO = CourseClassMapper.toDTO(courseClassService.findById(id));
        return new ResponseEntity<>(responseBodyCourseClassDTO, HttpStatus.OK);
    }

    @Override
    @GetMapping("classes")
    public ResponseEntity<List<ResponseBodyCourseClassDTO>> findAll(@RequestParam(name = "available", required = false) String available,
                                                                    @RequestParam(name = "limit", required = false) Integer limit,
                                                                    @RequestParam(name = "offset", required = false) Integer offset) {
        List<CourseClass> courseClasses;
        boolean pagination = !Objects.isNull(limit) && !Objects.isNull(offset);

        if (Objects.isNull(available)) {
            if (pagination)
                courseClasses = courseClassService.findAll(limit, offset);
            else
                courseClasses = courseClassService.findAll();
        }
        else {
            if (Boolean.parseBoolean(available)) {
                if (pagination)
                    courseClasses = courseClassService.findAllAvailable(limit, offset);
                else
                    courseClasses = courseClassService.findAllAvailable();
            }
            else {
                if (pagination)
                    courseClasses = courseClassService.findAllUnavailable(limit, offset);
                else
                    courseClasses = courseClassService.findAllUnavailable();
            }
        }
        return new ResponseEntity<>(courseClasses.stream().map(CourseClassMapper::toDTO).toList(), HttpStatus.OK);
    }

    @Override
    @PostMapping("classes/enroll/{classId}")
    public ResponseEntity<String> enroll(@PathVariable("classId") String classId, @RequestBody RequestBodyEnrollmentDTO enrollmentDTO)
            throws ResourceNotFoundException, NotMatchEnrollmentStudentException, StudentAlreadyEnrolledException, ClassNotAvailableException {
        enrollmentDTO.setClassId(classId);
        Enrollment enrollment = EnrollmentMapper.toEntity(enrollmentDTO);
        enrollmentService.enrollToClasses(enrollment, enrollment.getCourseClasses());
        return new ResponseEntity<>("Student was enrolled to a class", HttpStatus.OK);
    }

}
