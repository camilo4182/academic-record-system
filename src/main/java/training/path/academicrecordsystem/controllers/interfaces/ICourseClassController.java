package training.path.academicrecordsystem.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.controllers.dtos.RequestBodyCourseClassDTO;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyCourseClassDTO;

import javax.validation.Valid;
import java.util.List;

public interface ICourseClassController {

    ResponseEntity<String> save(@Valid RequestBodyCourseClassDTO courseClassDTO);
    ResponseEntity<String> update(String id, @Valid RequestBodyCourseClassDTO courseClassDTO);
    ResponseEntity<String> deleteById(String id);
    ResponseEntity<ResponseBodyCourseClassDTO> findById(String id);
    ResponseEntity<List<ResponseBodyCourseClassDTO>> findAll(Integer limit, Integer offset);
    
}
