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

    @Validated(OnCreate.class)
    ResponseEntity<String> save(@Valid CareerDTO careerDTO);
    @Validated(OnUpdate.class)
    ResponseEntity<String> update(@UUIDValidator(groups = OnUpdate.class) String id, @Valid CareerDTO careerDTO);
    ResponseEntity<String> deleteById(@UUIDValidator String id);
    ResponseEntity<CareerDTO> findById(@UUIDValidator String id);
    ResponseEntity<List<CareerDTO>> findAll(@Min(0) Integer limit, @Min(0) Integer offset);
    @Validated(OnAssignToCareer.class)
    ResponseEntity<String> assignCourseToCareer(@UUIDValidator(groups = OnAssignToCareer.class) String careerId, @Valid CourseDTO courseDTO);
    ResponseEntity<List<CourseDTO>> findCoursesByCareer(@UUIDValidator String careerId);

}
