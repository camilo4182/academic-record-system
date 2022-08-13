package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.path.academicrecordsystem.controllers.dtos.RequestBodyEnrollmentDTO;
import training.path.academicrecordsystem.controllers.dtos.RequestBodyStudentDTO;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyEnrollmentDTO;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyStudentDTO;
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
    public ResponseEntity<String> save(@RequestBody RequestBodyStudentDTO requestBodyStudentDTO) {
        try {
            Student student = StudentMapper.createEntity(requestBodyStudentDTO);
            studentService.save(student);
            return new ResponseEntity<>("Student registered", HttpStatus.OK);
        } catch (BadResourceDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PutMapping("students/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody RequestBodyStudentDTO requestBodyStudentDTO) {
        try {
            requestBodyStudentDTO.setId(id);
            Student student = StudentMapper.toEntity(requestBodyStudentDTO);
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
    public ResponseEntity<ResponseBodyStudentDTO> findById(@PathVariable("id") String id) {
        try {
            ResponseBodyStudentDTO responseBodyStudentDTO = StudentMapper.toDTO(studentService.findById(id));
            return new ResponseEntity<>(responseBodyStudentDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("students")
    public ResponseEntity<List<ResponseBodyStudentDTO>> findAll(@RequestParam(name = "limit", required = false) Integer limit,
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
    public ResponseEntity<String> enroll(@PathVariable("studentId") String studentId, @RequestBody RequestBodyEnrollmentDTO requestBodyEnrollmentDTO) {
        try {
            requestBodyEnrollmentDTO.setStudentId(studentId);
            Enrollment enrollment = EnrollmentMapper.createEntity(requestBodyEnrollmentDTO);
            enrollmentService.save(enrollment, enrollment.getCourseClasses());
            return new ResponseEntity<>("Student was enrolled to a class", HttpStatus.OK);
        } catch (BadResourceDataException | NullRequestBodyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping("students/{studentId}/enrollments")
    public ResponseEntity<List<ResponseBodyEnrollmentDTO>> findEnrollmentsBySemester(@PathVariable("studentId") String studentId,
                                                                                     @RequestParam(name = "semester") Integer semester) {
        try {
            List<Enrollment> enrollmentList = studentService.findEnrollmentsBySemester(studentId, semester);
            return new ResponseEntity<>(enrollmentList.stream().map(EnrollmentMapper::toDTO).toList(), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
