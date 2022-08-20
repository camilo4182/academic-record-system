package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.RequestBodyEnrollmentDTO;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyEnrollmentDTO;

import javax.validation.Valid;
import java.util.List;

public interface IEnrollmentController {

    ResponseEntity<String> save(@Valid RequestBodyEnrollmentDTO requestBodyEnrollmentDTO);
    ResponseEntity<String> update(String id, @Valid RequestBodyEnrollmentDTO requestBodyEnrollmentDTO);
    ResponseEntity<String> deleteById(String id);
    ResponseEntity<ResponseBodyEnrollmentDTO> findById(String id);
    ResponseEntity<List<ResponseBodyEnrollmentDTO>> findAll(Integer limit, Integer offset);

}
