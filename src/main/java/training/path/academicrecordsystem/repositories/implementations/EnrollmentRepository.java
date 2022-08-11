package training.path.academicrecordsystem.repositories.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.repositories.interfaces.IEnrollmentRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class EnrollmentRepository implements IEnrollmentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EnrollmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Enrollment enrollment) {
        String query = "INSERT INTO enrollments (id, semester, student_id, career_id, class_id) VALUES (?, ?, ?, ?, ?);";
        return 0;
    }

    @Override
    public int update(String id, Enrollment enrollment) {
        return 0;
    }

    @Override
    public int deleteById(String id) {
        return 0;
    }

    @Override
    public Optional<Enrollment> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Enrollment> findAll() {
        return null;
    }

    @Override
    public List<Enrollment> findAll(int limit, int offset) {
        return null;
    }

    @Override
    public boolean exists(String id) {
        return false;
    }
}
