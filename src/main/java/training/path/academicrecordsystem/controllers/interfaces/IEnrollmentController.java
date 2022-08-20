package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyEnrollmentDTO;

import java.util.List;

public interface IEnrollmentController {

    ResponseEntity<List<ResponseBodyEnrollmentDTO>> findAll(Integer limit, Integer offset);

}
