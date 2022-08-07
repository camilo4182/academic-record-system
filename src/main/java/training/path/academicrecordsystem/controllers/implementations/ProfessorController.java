package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.path.academicrecordsystem.controllers.dtos.ProfessorDTO;
import training.path.academicrecordsystem.controllers.interfaces.IProfessorController;
import training.path.academicrecordsystem.controllers.mappers.ProfessorMapper;
import training.path.academicrecordsystem.exceptions.BadArgumentsException;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.exceptions.NullRequestBodyException;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.services.interfaces.IProfessorService;

import java.util.List;

@RestController
public class ProfessorController implements IProfessorController {

    private final IProfessorService professorService;

    @Autowired
    public ProfessorController(IProfessorService professorService) {
        this.professorService = professorService;
    }

    @Override
    @PostMapping("professors")
    public ResponseEntity<String> save(@RequestBody ProfessorDTO professorDTO) {
        try {
            Professor professor = ProfessorMapper.createEntity(professorDTO);
            professorService.save(professor);
            return new ResponseEntity<>("Professor registered", HttpStatus.OK);
        } catch (NullRequestBodyException | BadArgumentsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PutMapping("professors/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody ProfessorDTO professorDTO) {
        try {
            professorDTO.setId(id);
            Professor professor = ProfessorMapper.toEntity(professorDTO);
            professorService.update(id, professor);
            return new ResponseEntity<>("Professor information updated", HttpStatus.OK);
        } catch (NullRequestBodyException | BadArgumentsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundResourceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @DeleteMapping("professors/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        try {
            professorService.deleteById(id);
            return new ResponseEntity<>("Professor deleted", HttpStatus.OK);
        } catch (NotFoundResourceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("professors/{id}")
    public ResponseEntity<ProfessorDTO> findById(@PathVariable("id") String id) {
        try {
            ProfessorDTO professorDTO = ProfessorMapper.toDTO(professorService.findById(id));
            return new ResponseEntity<>(professorDTO, HttpStatus.OK);
        } catch (NotFoundResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("professors")
    public ResponseEntity<List<ProfessorDTO>> findAll() {
        List<Professor> professorList = professorService.findAll();
        return new ResponseEntity<>(professorList.stream().map(ProfessorMapper::toDTO).toList(), HttpStatus.OK);
    }
}
