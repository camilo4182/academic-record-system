package training.path.academicrecordsystem.repositories.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.repositories.interfaces.IEnrollmentRepository;
import training.path.academicrecordsystem.repositories.rowmappers.EnrollmentFullInfoRowMapper;
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
    public void save(Enrollment enrollment) {
        String queryEnrollments = "INSERT INTO enrollments (id, student_id, career_id) VALUES (?, ?, ?);";
        jdbcTemplate.update(queryEnrollments,
                UUID.fromString(enrollment.getId()),
                UUID.fromString(enrollment.getStudent().getId()),
                UUID.fromString(enrollment.getStudent().getCareer().getId()));
    }

    @Override
    public void saveClass(Enrollment enrollment, CourseClass courseClass) {
        String queryEnrollmentClasses = "INSERT INTO enrollment_classes (enrollment_id, class_id, semester) VALUES (?, ?, ?);";
        jdbcTemplate.update(queryEnrollmentClasses, UUID.fromString(enrollment.getId()),
                UUID.fromString(courseClass.getId()),
                enrollment.getSemester());
        String queryUpdateClass = "UPDATE classes SET enrolled_students = ?, available = ? WHERE id = ?;";
        jdbcTemplate.update(queryUpdateClass, courseClass.getEnrolledStudents(), courseClass.isAvailable(), UUID.fromString(courseClass.getId()));
    }

    @Override
    public Optional<Enrollment> findById(String id) {
        String query = """
                SELECT e.id AS enrollment_id, u.id AS student_id, u.name AS student, e.career_id AS career_id, c.name AS career
                FROM users u INNER JOIN students s ON u.id = s.id
                INNER JOIN enrollments e ON e.student_id = s.id
                INNER JOIN careers c ON c.id = e.career_id
                WHERE e.id = ?;
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new EnrollmentInfoRowMapper(), UUID.fromString(id)));
    }

    @Override
    public List<Enrollment> findAll() {
        String query =
                """
                SELECT e.id AS enrollment_id, u.id AS student_id, u.first_name AS first_name, u.last_name AS last_name,
                u.username AS username, semester, cl.id AS class_id, capacity, enrolled_students, available,
                co.id AS course_id, co.name AS course, credits, prof.professor_id, professor_first_name, professor_last_name,
                e.career_id AS career_id, c.name AS career
                FROM users u INNER JOIN students s ON u.id = s.id
                INNER JOIN enrollments e ON e.student_id = s.id
                INNER JOIN careers c ON c.id = e.career_id
                INNER JOIN enrollment_classes ec ON e.id = ec.enrollment_id
                INNER JOIN classes cl ON cl.id = ec.class_id
                INNER JOIN courses co ON co.id = cl.course_id
                INNER JOIN (
                            SELECT p.id AS professor_id, u.first_name AS professor_first_name, u.last_name AS professor_last_name
                            FROM professors p INNER JOIN users u ON u.id = p.id
                           ) AS prof ON cl.professor_id = prof.professor_id
                """;
        return jdbcTemplate.query(query, new EnrollmentFullInfoRowMapper());
    }

    @Override
    public boolean exists(String id) {
        String query = "SELECT * FROM enrollments WHERE id = ?;";
        try {
            jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Enrollment.class), UUID.fromString(id));
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
