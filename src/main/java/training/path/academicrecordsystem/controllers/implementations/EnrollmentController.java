package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyEnrollmentDTO;
import training.path.academicrecordsystem.controllers.interfaces.IEnrollmentController;
import training.path.academicrecordsystem.controllers.mappers.EnrollmentMapper;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.services.interfaces.IEnrollmentService;

import java.util.List;
import java.util.Objects;

@RestController
public class EnrollmentController implements IEnrollmentController {

    private final IEnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(IEnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @Override
    @GetMapping("enrollments")
    public ResponseEntity<List<ResponseBodyEnrollmentDTO>> findAll(Integer limit, Integer offset) {
        List<Enrollment> enrollments;
        if (Objects.isNull(limit) && Objects.isNull(offset)) {
            enrollments = enrollmentService.findAll();
        }
        else {
            enrollments = enrollmentService.findAll(limit, offset);
        }
        return new ResponseEntity<>(enrollments.stream().map(EnrollmentMapper::toDTO).toList(), HttpStatus.OK);
    }

}
