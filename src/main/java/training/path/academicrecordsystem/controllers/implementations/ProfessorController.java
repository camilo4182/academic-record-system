package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import training.path.academicrecordsystem.controllers.dtos.*;
import training.path.academicrecordsystem.controllers.interfaces.IProfessorController;
import training.path.academicrecordsystem.controllers.mappers.CourseClassMapper;
import training.path.academicrecordsystem.controllers.mappers.ProfessorMapper;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.UniqueColumnViolationException;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.services.interfaces.IProfessorService;

import java.util.List;
import java.util.Objects;

@RestController
@Validated
public class ProfessorController implements IProfessorController {

    private final IProfessorService professorService;

    @Autowired
    public ProfessorController(IProfessorService professorService) {
        this.professorService = professorService;
    }

    @Override
    @PostMapping("professors")
    public ResponseEntity<String> save(@RequestBody RequestBodyProfessorDTO professorDTO) throws UniqueColumnViolationException {
        Professor professor = ProfessorMapper.createEntity(professorDTO);
        professorService.save(professor);
        return new ResponseEntity<>("Professor registered", HttpStatus.OK);
    }

    @Override
    @PutMapping("professors/{id}")
    public ResponseEntity<String> updateByAdmin(@PathVariable("id") String id, @RequestBody UpdateProfessorByAdminDTO professorDTO)
            throws ResourceNotFoundException, UniqueColumnViolationException {
        professorDTO.setId(id);
        Professor professor = ProfessorMapper.toEntity(professorDTO);
        professorService.update(id, professor);
        return new ResponseEntity<>("Professor information updated", HttpStatus.OK);
    }

    @Override
    @PutMapping("professors/profile/{id}")
    public ResponseEntity<String> updateProfile(@PathVariable("id") String id, @RequestBody UpdateUserByUserDTO professorDTO)
            throws ResourceNotFoundException, UniqueColumnViolationException {
        professorDTO.setId(id);
        Professor professor = ProfessorMapper.toEntity(professorDTO);
        professorService.updateBasicInfo(id, professor);
        return new ResponseEntity<>("Profile information updated", HttpStatus.OK);
    }

    @Override
    @DeleteMapping("professors/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) throws ResourceNotFoundException {
        professorService.deleteById(id);
        return new ResponseEntity<>("Professor deleted", HttpStatus.OK);
    }

    @Override
    @GetMapping("professors/{id}")
    public ResponseEntity<ResponseBodyProfessorDTO> findById(@PathVariable("id") String id) throws ResourceNotFoundException {
        ResponseBodyProfessorDTO professorDTO = ProfessorMapper.toDTO(professorService.findById(id));
        return new ResponseEntity<>(professorDTO, HttpStatus.OK);
    }

    @Override
    @GetMapping("professors/profile/{id}")
    public ResponseEntity<ResponseBodyProfessorDTO> viewProfile(@PathVariable("id") String id) throws ResourceNotFoundException {
        ResponseBodyProfessorDTO professorDTO = ProfessorMapper.toDTO(professorService.findById(id));
        return new ResponseEntity<>(professorDTO, HttpStatus.OK);
    }

    @Override
    @GetMapping("professors")
    public ResponseEntity<List<ResponseBodyProfessorDTO>> findAll(@RequestParam(name = "limit", required = false) Integer limit,
                                                                 @RequestParam(name = "offset", required = false) Integer offset) {
        List<Professor> professorList;
        if (Objects.isNull(limit) && Objects.isNull(offset))
            professorList = professorService.findAll();
        else
            professorList = professorService.findAll(limit, offset);
        return new ResponseEntity<>(professorList.stream().map(ProfessorMapper::toDTO).toList(), HttpStatus.OK);
    }

    @Override
    @GetMapping("professors/classes/{professorId}")
    public ResponseEntity<List<ResponseBodyCourseClassDTO>> findClassesByProfessor(@PathVariable("professorId") String professorId) throws ResourceNotFoundException {
        List<CourseClass> classes = professorService.findClassesByProfessor(professorId);
        return new ResponseEntity<>(classes.stream().map(CourseClassMapper::toDTO).toList(), HttpStatus.OK);
    }

}
