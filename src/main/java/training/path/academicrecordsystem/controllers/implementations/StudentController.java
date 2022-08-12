package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.path.academicrecordsystem.controllers.dtos.EnrollmentDTO;
import training.path.academicrecordsystem.controllers.dtos.StudentDTO;
import training.path.academicrecordsystem.controllers.interfaces.IStudentController;
import training.path.academicrecordsystem.controllers.mappers.EnrollmentMapper;
import training.path.academicrecordsystem.controllers.mappers.StudentMapper;
import training.path.academicrecordsystem.exceptions.BadResourceDataException;
import training.path.academicrecordsystem.exceptions.NullRequestBodyException;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.services.interfaces.IEnrollmentService;
import training.path.academicrecordsystem.services.interfaces.IStudentService;

import java.util.List;
import java.util.Objects;

@RestController
public class StudentController implements IStudentController {

    private final IStudentService studentService;
    private final IEnrollmentService enrollmentService;

    @Autowired
    public StudentController(IStudentService studentService, IEnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.enrollmentService = enrollmentService;
    }

    @Override
    @PostMapping("students")
    public ResponseEntity<String> save(@RequestBody StudentDTO studentDTO) {
        try {
            Student student = StudentMapper.createEntity(studentDTO);
            studentService.save(student);
            return new ResponseEntity<>("Student registered", HttpStatus.OK);
        } catch (BadResourceDataException e) {
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
            return new ResponseEntity<>("Student information updated", HttpStatus.OK);
        } catch (BadResourceDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @DeleteMapping("students/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        try {
            studentService.deleteById(id);
            return new ResponseEntity<>("Student deleted", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("students/{id}")
    public ResponseEntity<StudentDTO> findById(@PathVariable("id") String id) {
        try {
            StudentDTO studentDTO = StudentMapper.toDTO(studentService.findById(id));
            return new ResponseEntity<>(studentDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("students")
    public ResponseEntity<List<StudentDTO>> findAll(@RequestParam(name = "limit", required = false) Integer limit,
                                                    @RequestParam(name = "offset", required = false) Integer offset) {
        List<Student> studentList;
        if (Objects.isNull(limit) && Objects.isNull(offset)) {
            studentList = studentService.findAll();
        }
        else {
            studentList = studentService.findAll(limit, offset);
        }
        return new ResponseEntity<>(studentList.stream().map(StudentMapper::toDTO).toList(), HttpStatus.OK);
    }

    @Override
    @PostMapping("students/{studentId}/courses")
    public ResponseEntity<String> enroll(@PathVariable("studentId") String studentId, @RequestBody EnrollmentDTO enrollmentDTO) {
        try {
            Student student = studentService.findById(studentId);
            enrollmentDTO.setStudent(StudentMapper.toDTO(student));
            Enrollment enrollment = EnrollmentMapper.createEntity(enrollmentDTO);
            enrollmentService.save(enrollment);
            return new ResponseEntity<>("Student was enrolled to a class", HttpStatus.OK);
        } catch (BadResourceDataException | NullRequestBodyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
