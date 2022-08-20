package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import training.path.academicrecordsystem.controllers.dtos.CareerDTO;
import training.path.academicrecordsystem.controllers.dtos.CourseDTO;
import training.path.academicrecordsystem.controllers.dtos.CourseOnlyIdDTO;
import training.path.academicrecordsystem.controllers.interfaces.ICareerController;
import training.path.academicrecordsystem.controllers.mappers.CareerMapper;
import training.path.academicrecordsystem.controllers.mappers.CourseMapper;
import training.path.academicrecordsystem.exceptions.BadResourceDataException;
import training.path.academicrecordsystem.exceptions.CouldNotPerformOperationException;
import training.path.academicrecordsystem.exceptions.NullRequestBodyException;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.services.interfaces.ICareerService;
import training.path.academicrecordsystem.services.interfaces.ICourseService;
import training.path.academicrecordsystem.validations.groups.OnCreate;
import training.path.academicrecordsystem.validations.groups.OnUpdate;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@RestController
@Validated
public class CareerController implements ICareerController {

    private final ICareerService careerService;
    private final ICourseService courseService;

    @Autowired
    public CareerController(ICareerService careerService, ICourseService courseService) {
        this.careerService = careerService;
        this.courseService = courseService;
    }

    @Override
    @PostMapping("careers")
    public ResponseEntity<String> save(@RequestBody CareerDTO careerDTO) {
        try {
            Career career = CareerMapper.createEntity(careerDTO);
            careerService.save(career);
            return new ResponseEntity<>("Career was registered", HttpStatus.OK);
        } catch (BadResourceDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NullRequestBodyException e) {
            return new ResponseEntity<>(e.getMessage() + " register the career", HttpStatus.NO_CONTENT);
        }
    }

    @Override
    @PutMapping("careers/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody CareerDTO careerDTO) {
        try {
            careerDTO.setId(id);
            Career career = CareerMapper.toEntity(careerDTO);
            careerService.update(career);
            return new ResponseEntity<>("Career was updated", HttpStatus.OK);
        } catch (BadResourceDataException | NullRequestBodyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @DeleteMapping("careers/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        try {
            careerService.deleteById(id);
            return new ResponseEntity<>("Career was deleted", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("careers/{id}")
    public ResponseEntity<CareerDTO> findById(@PathVariable("id") String id) {
        try {
            Career career = careerService.findById(id);
            CareerDTO careerDTO = CareerMapper.toDTO(career);
            return new ResponseEntity<>(careerDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("careers")
    public ResponseEntity<List<CareerDTO>> findAll(@RequestParam(name = "limit", required = false) Integer limit,
                                                   @RequestParam(name = "offset", required = false) Integer offset) {
        List<Career> careerList;
        if (Objects.isNull(limit) && Objects.isNull(offset)) {
            careerList = careerService.findAll();
        }
        else {
            careerList = careerService.findAll(limit, offset);
        }
        return new ResponseEntity<>(careerList.stream().map(CareerMapper::toDTO).toList(), HttpStatus.OK);
    }

    @PutMapping("careers/{careerId}/courses")
    public ResponseEntity<String> assignCourseToCareer(@PathVariable("careerId") String careerId, @RequestBody CourseOnlyIdDTO courseId) {
        try {
            careerService.assignCourseToCareer(courseId.getCourseId(), careerId);
            return new ResponseEntity<>("Course was assigned to career", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("careers/{careerId}/courses")
    public ResponseEntity<List<CourseDTO>> findCoursesByCareer(@PathVariable("careerId") String careerId) {
        try {
            List<Course> courseList = careerService.findCoursesByCareer(careerId);
            return new ResponseEntity<>(courseList.stream().map(CourseMapper::toDTO).toList(), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
