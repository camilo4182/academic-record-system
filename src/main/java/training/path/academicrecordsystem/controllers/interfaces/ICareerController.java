package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.controllers.dtos.CareerDTO;
import training.path.academicrecordsystem.controllers.dtos.CourseDTO;
import training.path.academicrecordsystem.validations.custom.UUIDValidator;
import training.path.academicrecordsystem.validations.groups.OnAssignToCareer;
import training.path.academicrecordsystem.validations.groups.OnCreate;
import training.path.academicrecordsystem.validations.groups.OnUpdate;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public interface ICareerController {

    ResponseEntity<String> save(@Valid CareerDTO careerDTO);
    ResponseEntity<String> update(String id, @Valid CareerDTO careerDTO);
    ResponseEntity<String> deleteById(String id);
    ResponseEntity<CareerDTO> findById(String id);
    ResponseEntity<List<CareerDTO>> findAll(Integer limit, Integer offset);
    @Validated(OnAssignToCareer.class)
    ResponseEntity<String> assignCourseToCareer(String careerId, @Valid CourseDTO courseDTO);
    ResponseEntity<List<CourseDTO>> findCoursesByCareer(String careerId);

}
