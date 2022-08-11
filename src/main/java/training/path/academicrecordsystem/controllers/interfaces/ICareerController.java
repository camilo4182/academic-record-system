package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.CareerDTO;
import training.path.academicrecordsystem.controllers.dtos.CourseDTO;

import java.util.List;

public interface ICareerController {

    ResponseEntity<String> save(CareerDTO careerDTO);
    ResponseEntity<String> update(String id, CareerDTO careerDTO);
    ResponseEntity<String> deleteById(String id);
    ResponseEntity<CareerDTO> findById(String id);
    ResponseEntity<List<CareerDTO>> findAll(Integer limit, Integer offset);

    ResponseEntity<String> assignCourseToCareer(String careerId, CourseDTO courseDTO);
    ResponseEntity<List<CourseDTO>> findCoursesByCareer(String careerId);

}
