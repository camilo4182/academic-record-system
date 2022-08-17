package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.controllers.dtos.CareerDTO;
import training.path.academicrecordsystem.controllers.dtos.CourseDTO;
import training.path.academicrecordsystem.validations.groups.OnCreate;
import training.path.academicrecordsystem.validations.groups.OnUpdate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public interface ICareerController {

    @Validated(OnCreate.class)
    ResponseEntity<String> save(@Valid CareerDTO careerDTO);
    @Validated(OnUpdate.class)
    ResponseEntity<String> update(@NotNull(message = "You must select a valid career id") String id, @Valid CareerDTO careerDTO);
    ResponseEntity<String> deleteById(@Pattern(message = "Invalid id format", regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$") String id);
    ResponseEntity<CareerDTO> findById(@Pattern(message = "Invalid id format", regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$") String id);
    ResponseEntity<List<CareerDTO>> findAll(Integer limit, Integer offset);

    ResponseEntity<String> assignCourseToCareer(String careerId, CourseDTO courseDTO);
    ResponseEntity<List<CourseDTO>> findCoursesByCareer(String careerId);

}
