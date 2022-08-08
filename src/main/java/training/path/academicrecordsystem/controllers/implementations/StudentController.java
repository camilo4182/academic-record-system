package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.path.academicrecordsystem.controllers.dtos.StudentDTO;
import training.path.academicrecordsystem.controllers.interfaces.IStudentController;
import training.path.academicrecordsystem.controllers.mappers.StudentMapper;
import training.path.academicrecordsystem.exceptions.BadArgumentsException;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.services.interfaces.IStudentService;

import java.util.List;

@RestController
public class StudentController implements IStudentController {

    private final IStudentService studentService;

    @Autowired
    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    @PostMapping("students")
    public ResponseEntity<String> save(@RequestBody StudentDTO studentDTO) {
        try {
            Student student = StudentMapper.createEntity(studentDTO);
            studentService.save(student);
            return new ResponseEntity<>("Student created", HttpStatus.OK);
        } catch (BadArgumentsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PutMapping("students/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody StudentDTO studentDTO) {
        try {
            studentDTO.setId(id);
            Student student = StudentMapper.toEntity(studentDTO);
            studentService.update(id, student);
            return new ResponseEntity<>("Student updated", HttpStatus.OK);
        } catch (BadArgumentsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundResourceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @DeleteMapping("students/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        try {
            studentService.deleteById(id);
            return new ResponseEntity<>("Student was deleted", HttpStatus.OK);
        } catch (NotFoundResourceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("students/{id}")
    public ResponseEntity<StudentDTO> findById(@PathVariable("id") String id) {
        try {
            StudentDTO studentDTO = StudentMapper.toDTO(studentService.findById(id));
            return new ResponseEntity<>(studentDTO, HttpStatus.OK);
        } catch (NotFoundResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("students")
    public ResponseEntity<List<StudentDTO>> findAll() {
        List<Student> studentList = studentService.findAll();
        return new ResponseEntity<>(studentList.stream().map(StudentMapper::toDTO).toList(), HttpStatus.OK);
    }
}
