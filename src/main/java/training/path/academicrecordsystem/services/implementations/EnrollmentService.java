package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.repositories.interfaces.IEnrollmentRepository;
import training.path.academicrecordsystem.services.interfaces.IEnrollmentService;

import java.util.List;

@Service
public class EnrollmentService implements IEnrollmentService {

    private final IEnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentService(IEnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public void save(Enrollment enrollment) {
        enrollmentRepository.save(enrollment);
    }

    @Override
    public void update(String id, Enrollment enrollment) {

    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public Enrollment finById(String id) {
        return null;
    }

    @Override
    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    @Override
    public List<Enrollment> findAll(int limit, int offset) {
        return null;
    }
}
