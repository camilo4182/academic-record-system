package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import training.path.academicrecordsystem.controllers.dtos.*;
import training.path.academicrecordsystem.controllers.interfaces.IStudentController;
import training.path.academicrecordsystem.controllers.mappers.EnrollmentMapper;
import training.path.academicrecordsystem.controllers.mappers.StudentMapper;
import training.path.academicrecordsystem.exceptions.NotMatchEnrollmentStudentException;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.UniqueColumnViolationException;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.services.interfaces.IEnrollmentService;
import training.path.academicrecordsystem.services.interfaces.IStudentService;

import java.util.List;
import java.util.Objects;

@RestController
@Validated
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
    public ResponseEntity<String> save(@RequestBody RequestBodyStudentDTO requestBodyStudentDTO) throws ResourceNotFoundException, UniqueColumnViolationException {
        Student student = StudentMapper.createEntity(requestBodyStudentDTO);
        studentService.save(student);
        Enrollment enrollment = EnrollmentMapper.createEntityFromStudent(student);
        enrollmentService.save(enrollment);
        return new ResponseEntity<>("Student registered", HttpStatus.OK);
    }

    @Override
    @PutMapping("students/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody UpdateStudentByAdminDTO updateByAdmin)
            throws ResourceNotFoundException, UniqueColumnViolationException {
        updateByAdmin.setId(id);
        Student student = StudentMapper.toEntity(updateByAdmin);
        studentService.update(id, student);
        return new ResponseEntity<>("Student information updated", HttpStatus.OK);
    }

    @Override
    @PutMapping("students/profile/{id}")
    public ResponseEntity<String> updateBasicInfo(@PathVariable("id") String id, @RequestBody UpdateStudentByStudentDTO updateByStudent)
            throws ResourceNotFoundException, UniqueColumnViolationException {
        updateByStudent.setId(id);
        Student student = StudentMapper.toEntity(updateByStudent);
        studentService.updateBasicInfo(id, student);
        return new ResponseEntity<>("Profile information updated", HttpStatus.OK);
    }

    @Override
    @DeleteMapping("students/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) throws ResourceNotFoundException {
        studentService.deleteById(id);
        return new ResponseEntity<>("Student deleted", HttpStatus.OK);
    }

    @Override
    @GetMapping("students/profile/{id}")
    public ResponseEntity<ResponseBodyStudentDTO> findById(@PathVariable("id") String id) throws ResourceNotFoundException {
        ResponseBodyStudentDTO responseBodyStudentDTO = StudentMapper.toDTO(studentService.findById(id));
        return new ResponseEntity<>(responseBodyStudentDTO, HttpStatus.OK);
    }

    @Override
    @GetMapping("students")
    public ResponseEntity<List<ResponseBodyStudentDTO>> findAll(@RequestParam(name = "limit", required = false) Integer limit,
                                                               @RequestParam(name = "offset", required = false) Integer offset) {
        List<Student> studentList;
        if (Objects.isNull(limit) && Objects.isNull(offset))
            studentList = studentService.findAll();
        else
            studentList = studentService.findAll(limit, offset);
        return new ResponseEntity<>(studentList.stream().map(StudentMapper::toDTO).toList(), HttpStatus.OK);
    }

    @Override
    @GetMapping("students/{studentId}/enrollment")
    public ResponseEntity<List<ResponseBodyEnrollmentDTO>> findEnrollmentInfo(@PathVariable("studentId") String studentId,
                                                                              @RequestParam(name = "semester", required = false) Integer semester)
            throws ResourceNotFoundException {
        if (Objects.isNull(semester)) {
            List<Enrollment> enrollments = studentService.findEnrollmentInfo(studentId);
            return new ResponseEntity<>(enrollments.stream().map(EnrollmentMapper::toDTO).toList(), HttpStatus.OK);
        }
        else {
            List<Enrollment> enrollmentList = studentService.findEnrollmentsBySemester(studentId, semester);
            return new ResponseEntity<>(enrollmentList.stream().map(EnrollmentMapper::toDTO).toList(), HttpStatus.OK);
        }
    }

    @Override
    @PostMapping("students/{studentId}/enrollment/{enrollmentId}")
    public ResponseEntity<String> enroll(@PathVariable("studentId") String studentId,
                                         @PathVariable("enrollmentId") String enrollmentId,
                                         @RequestBody RequestBodyEnrollmentDTO requestBodyEnrollmentDTO)
            throws ResourceNotFoundException, NotMatchEnrollmentStudentException {
        requestBodyEnrollmentDTO.setStudentId(studentId);
        requestBodyEnrollmentDTO.setId(enrollmentId);
        Enrollment enrollment = EnrollmentMapper.toEntity(requestBodyEnrollmentDTO);
        enrollmentService.saveClass(enrollment, enrollment.getCourseClasses());
        return new ResponseEntity<>("Student was enrolled to a class", HttpStatus.OK);
    }

}
