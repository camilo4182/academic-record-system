package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import training.path.academicrecordsystem.controllers.dtos.CareerDTO;
import training.path.academicrecordsystem.controllers.dtos.CourseDTO;
import training.path.academicrecordsystem.controllers.dtos.CourseOnlyIdDTO;
import training.path.academicrecordsystem.controllers.interfaces.ICareerController;
import training.path.academicrecordsystem.controllers.mappers.CareerMapper;
import training.path.academicrecordsystem.controllers.mappers.CourseMapper;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.UniqueColumnViolationException;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.services.interfaces.ICareerService;

import java.util.List;
import java.util.Objects;

@RestController
@Validated
public class CareerController implements ICareerController {

    private final ICareerService careerService;

    @Autowired
    public CareerController(ICareerService careerService) {
        this.careerService = careerService;
    }

    @Override
    @PostMapping("careers")
    public ResponseEntity<String> save(@RequestBody CareerDTO careerDTO) throws UniqueColumnViolationException {
        Career career = CareerMapper.createEntity(careerDTO);
        careerService.save(career);
        return new ResponseEntity<>("Career was registered", HttpStatus.OK);
    }

    @Override
    @PutMapping("careers/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody CareerDTO careerDTO)
            throws ResourceNotFoundException, UniqueColumnViolationException {
        careerDTO.setId(id);
        Career career = CareerMapper.toEntity(careerDTO);
        careerService.update(career);
        return new ResponseEntity<>("Career was updated", HttpStatus.OK);
    }

    @Override
    @DeleteMapping("careers/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) throws ResourceNotFoundException {
        careerService.deleteById(id);
        return new ResponseEntity<>("Career was deleted", HttpStatus.OK);
    }

    @Override
    @GetMapping("careers/{id}")
    public ResponseEntity<CareerDTO> findById(@PathVariable("id") String id) throws ResourceNotFoundException {
        Career career = careerService.findById(id);
        CareerDTO careerDTO = CareerMapper.toDTO(career);
        return new ResponseEntity<>(careerDTO, HttpStatus.OK);
    }

    @Override
    @GetMapping("careers")
    public ResponseEntity<List<CareerDTO>> findAll(@RequestParam(name = "limit", required = false) Integer limit,
                                                   @RequestParam(name = "offset", required = false) Integer offset) {
        List<Career> careerList;
        if (Objects.isNull(limit) && Objects.isNull(offset)) careerList = careerService.findAll();
        else careerList = careerService.findAll(limit, offset);
        return new ResponseEntity<>(careerList.stream().map(CareerMapper::toDTO).toList(), HttpStatus.OK);
    }

    @PutMapping("careers/{careerId}/courses")
    public ResponseEntity<String> assignCourseToCareer(@PathVariable("careerId") String careerId,
                                                       @RequestBody CourseOnlyIdDTO courseId) throws ResourceNotFoundException {
        careerService.assignCourseToCareer(courseId.getCourseId(), careerId);
        return new ResponseEntity<>("Course was assigned to career", HttpStatus.OK);
    }

    @Override
    @GetMapping("careers/{careerId}/courses")
    public ResponseEntity<List<CourseDTO>> findCoursesByCareer(@PathVariable("careerId") String careerId)
            throws ResourceNotFoundException {
        List<Course> courseList = careerService.findCoursesByCareer(careerId);
        return new ResponseEntity<>(courseList.stream().map(CourseMapper::toDTO).toList(), HttpStatus.OK);
    }

}
