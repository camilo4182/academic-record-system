package training.path.academicrecordsystem.repositories.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.repositories.interfaces.IEnrollmentRepository;
import training.path.academicrecordsystem.repositories.rowmappers.EnrollmentInfoRowMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class EnrollmentRepository implements IEnrollmentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EnrollmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Enrollment enrollment, CourseClass courseClass) {
        String queryEnrollments = "INSERT INTO enrollments (id, semester, student_id) VALUES (?, ?, ?);";
        jdbcTemplate.update(queryEnrollments, UUID.fromString(enrollment.getId()), enrollment.getSemester(),
                UUID.fromString(enrollment.getStudent().getId()));
        String queryEnrollmentClasses = "INSERT INTO enrollment_classes (enrollment_id, class_id) VALUES (?, ?);";
        jdbcTemplate.update(queryEnrollmentClasses, UUID.fromString(enrollment.getId()), UUID.fromString(courseClass.getId()));
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
        String query =
                """
                SELECT e.id AS enroll_id, cl.id AS class_id, co.id AS course_id, co.name AS course_name, semester, s.id AS student_id, u.name AS student_name, u.email AS student_email, available, p.id AS prof_id
                FROM enrollments e INNER JOIN classes cl ON e.class_id = cl.id
                INNER JOIN courses co ON co.id = cl.course_id
                INNER JOIN students s ON s.id = e.student_id
                INNER JOIN users u ON u.id = s.id
                INNER JOIN professors p ON u.id = p.id;
                """;
        return jdbcTemplate.query(query, new EnrollmentInfoRowMapper());
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
