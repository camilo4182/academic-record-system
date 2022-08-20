package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.repositories.interfaces.CareerRepository;
import training.path.academicrecordsystem.repositories.interfaces.StudentRepository;
import training.path.academicrecordsystem.services.interfaces.IStudentService;

import java.util.List;

@Service
@Validated
public class StudentService implements IStudentService {

    private final StudentRepository studentRepository;
    private final CareerRepository careerRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CareerRepository careerRepository) {
        this.studentRepository = studentRepository;
        this.careerRepository = careerRepository;
    }

    @Override
    public void save(Student student) throws ResourceNotFoundException {
        if (!careerRepository.exists(student.getCareer().getId())) throw new ResourceNotFoundException("Career " + student.getCareer().getId() + " was not found");
        studentRepository.save(student);
    }

    @Override
    public void update(String id, Student student) throws ResourceNotFoundException {
        if (!studentRepository.exists(id)) throw new ResourceNotFoundException("Student " + id + " was not found");
        if (!careerRepository.exists(student.getCareer().getId())) throw new ResourceNotFoundException("Career " + student.getCareer().getId() + " was not found");
        studentRepository.update(id, student);
    }

    @Override
    public void deleteById(String id) throws ResourceNotFoundException {
        if (!studentRepository.exists(id)) throw new ResourceNotFoundException("Student " + id + " was not found");
        studentRepository.deleteById(id);
    }

    @Override
    public Student findById(String id) throws ResourceNotFoundException {
        return studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student " + id + " was not found"));
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findAll(int limit, int offset) {
        return studentRepository.findAll(limit, offset);
    }

    @Override
    public Enrollment findEnrollmentInfo(String studentId) throws ResourceNotFoundException {
        if (!studentRepository.exists(studentId)) throw new ResourceNotFoundException("Student with id " + studentId + " could not be found");
        return studentRepository.findEnrollmentInfo(studentId);
    }

    @Override
    public List<Enrollment> findEnrollmentsBySemester(String studentId, int semester) throws ResourceNotFoundException {
        if (!studentRepository.exists(studentId)) throw new ResourceNotFoundException("Student with id " + studentId + " could not be found");
        return studentRepository.findEnrollmentsBySemester(studentId, semester);
    }


}
