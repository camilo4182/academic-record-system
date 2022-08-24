package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.UniqueColumnViolationException;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.repositories.interfaces.ICareerRepository;
import training.path.academicrecordsystem.repositories.interfaces.IStudentRepository;
import training.path.academicrecordsystem.services.interfaces.IStudentService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Validated
public class StudentService implements IStudentService {

    private final IStudentRepository studentRepository;
    private final ICareerRepository careerRepository;

    @Autowired
    public StudentService(IStudentRepository studentRepository, ICareerRepository careerRepository) {
        this.studentRepository = studentRepository;
        this.careerRepository = careerRepository;
    }

    @Override
    public void save(Student student) throws ResourceNotFoundException, UniqueColumnViolationException {
        if (!careerRepository.exists(student.getCareer().getId())) throw new ResourceNotFoundException("Career " + student.getCareer().getId() + " was not found");
        verifyUniqueness(student);
        studentRepository.save(student);
    }

    @Override
    public void update(String id, Student student) throws ResourceNotFoundException, UniqueColumnViolationException {
        if (!studentRepository.exists(id)) throw new ResourceNotFoundException("Student " + id + " was not found");
        if (!careerRepository.exists(student.getCareer().getId())) throw new ResourceNotFoundException("Career " + student.getCareer().getId() + " was not found");
        verifyUniqueness(student);
        studentRepository.update(id, student);
    }

    @Override
    public void updateBasicInfo(String id, Student student) throws ResourceNotFoundException, UniqueColumnViolationException {
        if (!studentRepository.exists(id)) throw new ResourceNotFoundException("Student " + id + " was not found");
        verifyUniqueness(student);
        studentRepository.updateBasicInfo(id, student);
    }

    private void verifyUniqueness(Student student) throws UniqueColumnViolationException {
        Optional<Student> foundStudentWithName = studentRepository.findByName(student.getName());
        if (foundStudentWithName.isPresent() && !Objects.equals(foundStudentWithName.orElseThrow().getId(), student.getId()))
            throw new UniqueColumnViolationException("There is already a student with the name " + student.getName() + ". Enter another one.");

        Optional<Student> foundStudentWithEmail = studentRepository.findByEmail(student.getEmail());
        if (foundStudentWithEmail.isPresent() && !Objects.equals(foundStudentWithEmail.orElseThrow().getId(), student.getId()))
            throw new UniqueColumnViolationException("There is already a student with the email " + student.getEmail() + ". Enter another one.");
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
    public List<Enrollment> findEnrollmentInfo(String studentId) throws ResourceNotFoundException {
        if (!studentRepository.exists(studentId)) throw new ResourceNotFoundException("Student with id " + studentId + " could not be found");
        return studentRepository.findEnrollmentInfo(studentId);
    }

    @Override
    public List<Enrollment> findEnrollmentsBySemester(String studentId, int semester) throws ResourceNotFoundException {
        if (!studentRepository.exists(studentId)) throw new ResourceNotFoundException("Student with id " + studentId + " could not be found");
        return studentRepository.findEnrollmentsBySemester(studentId, semester);
    }


}
