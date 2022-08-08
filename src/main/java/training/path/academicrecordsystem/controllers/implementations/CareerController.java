package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.path.academicrecordsystem.controllers.dtos.CareerDTO;
import training.path.academicrecordsystem.controllers.dtos.CourseDTO;
import training.path.academicrecordsystem.controllers.interfaces.ICareerController;
import training.path.academicrecordsystem.controllers.mappers.CareerMapper;
import training.path.academicrecordsystem.exceptions.BadArgumentsException;
import training.path.academicrecordsystem.exceptions.CouldNotPerformDBOperationException;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.services.interfaces.ICareerService;
import training.path.academicrecordsystem.services.interfaces.ICourseService;

import java.util.List;

@RestController
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
        } catch (BadArgumentsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
        } catch (BadArgumentsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundResourceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @DeleteMapping("careers/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        try {
            careerService.deleteById(id);
            return new ResponseEntity<>("Career was deleted", HttpStatus.OK);
        } catch (NotFoundResourceException e) {
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
        } catch (NotFoundResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("careers")
    public ResponseEntity<List<CareerDTO>> findAll() {
        List<Career> careerList = careerService.findAll();
        return new ResponseEntity<>(careerList.stream().map(CareerMapper::toDTO).toList(), HttpStatus.OK);
    }

    @PutMapping("careers/{careerId}/courses")
    public ResponseEntity<String> assignCourseToCareer(@PathVariable("careerId") String careerId, @RequestBody CourseDTO courseDTO) {
        try {
            List<CourseClass> classesByCourse = courseService.getClassesByCourse(courseDTO.getId());
            careerService.assignClassesToCareer(careerId, classesByCourse);
            return new ResponseEntity<>("Course was assigned to the career", HttpStatus.OK);
        } catch (NotFoundResourceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CouldNotPerformDBOperationException e) {
            return new ResponseEntity<>("Internal error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
