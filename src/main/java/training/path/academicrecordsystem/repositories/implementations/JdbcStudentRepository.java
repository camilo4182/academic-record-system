package training.path.academicrecordsystem.repositories.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.repositories.interfaces.StudentRepository;
import training.path.academicrecordsystem.repositories.rowmappers.EnrollmentFullInfoRowMapper;
import training.path.academicrecordsystem.repositories.rowmappers.EnrollmentInfoRowMapper;
import training.path.academicrecordsystem.repositories.rowmappers.StudentInfoRowMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcStudentRepository implements StudentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcStudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Student student) {
        String queryUser = "INSERT INTO users (id, name, email) VALUES (?, ?, ?);";
        jdbcTemplate.update(queryUser, UUID.fromString(student.getId()), student.getName(), student.getEmail());
        String queryStudent = "INSERT INTO students (id, average_grade) VALUES (?, ?);";
        return jdbcTemplate.update(queryStudent, UUID.fromString(student.getId()), student.getAverageGrade());
    }

    @Override
    public int update(String id, Student student) {
        String queryUser = "UPDATE users SET name = ?, email = ? WHERE id = ?;";
        jdbcTemplate.update(queryUser, student.getName(), student.getEmail(), UUID.fromString(id));
        String queryStudent = "UPDATE students SET average_grade = ?, career_id = ? WHERE id = ?;";
        return jdbcTemplate.update(queryStudent, student.getAverageGrade(), UUID.fromString(student.getCareer().getId()), UUID.fromString(id));
    }

    @Override
    public int deleteById(String id) {
        String query = "DELETE FROM users WHERE id = ?;";
        return jdbcTemplate.update(query, UUID.fromString(id));
    }

    @Override
    public Optional<Student> findById(String id) {
        String query =
                """
                SELECT s.id AS student_id, u.name AS student_name, u.email AS student_email, average_grade, c.id AS career_id, c.name AS career
                FROM students s INNER JOIN users u ON s.id = u.id
                INNER JOIN careers c ON s.career_id = c.id
                WHERE u.id = ?;
                """;
        try {
            Student student = jdbcTemplate.queryForObject(query, new StudentInfoRowMapper(), UUID.fromString(id));
            return Optional.ofNullable(student);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Student> findAll() {
        String query =
                """
                SELECT s.id AS student_id, u.name AS student_name, u.email AS student_email, average_grade, c.id AS career_id, c.name AS career
                FROM students s INNER JOIN users u ON s.id = u.id
                INNER JOIN enrollments e ON s.id = e.student_id
                INNER JOIN careers c ON e.career_id = c.id
                ORDER BY u.name;
                """;
        return jdbcTemplate.query(query, new StudentInfoRowMapper());
    }

    @Override
    public List<Student> findAll(int limit, int offset) {
        String query =
                """
                SELECT s.id AS student_id, u.name AS student_name, u.email AS student_email, average_grade, c.id AS career_id, c.name AS career
                FROM students s INNER JOIN users u ON s.id = u.id
                INNER JOIN careers c ON s.career_id = c.id
                ORDER BY u.name
                LIMIT ? OFFSET ?;
                """;
        return jdbcTemplate.query(query, new StudentInfoRowMapper(), limit, offset);
    }

    @Override
    public boolean exists(String id) {
        String query = "SELECT * FROM students WHERE id = ?;";
        try {
            jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Student.class), UUID.fromString(id));
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public List<Enrollment> findEnrollmentInfo(String studentId) {
        String query =
                """
                SELECT e.id AS enrollment_id, u.id AS student_id, u.name AS student, e.career_id AS career_id, c.name AS career
                FROM users u INNER JOIN students s ON u.id = s.id
                INNER JOIN enrollments e ON e.student_id = s.id
                INNER JOIN careers c ON c.id = e.career_id
                WHERE s.id = ?;
                """;
        return jdbcTemplate.query(query, new EnrollmentInfoRowMapper(), UUID.fromString(studentId));
    }

    @Override
    public List<Enrollment> findEnrollmentsBySemester(String studentId, int semester) {
        String query =
                """
                SELECT e.id AS enrollment_id, u.id AS student_id, u.name AS student, semester, cl.id AS class_id, capacity, enrolled_students, available, co.id AS course_id, co.name AS course, credits, prof.professor_id, professor_name
                FROM users u INNER JOIN students s ON u.id = s.id
                INNER JOIN enrollments e ON e.student_id = s.id
                INNER JOIN enrollment_classes ec ON e.id = ec.enrollment_id
                INNER JOIN classes cl ON cl.id = ec.class_id
                INNER JOIN courses co ON co.id = cl.course_id
                INNER JOIN (
                            SELECT p.id AS professor_id, u.name AS professor_name
                            FROM professors p INNER JOIN users u ON u.id = p.id
                           ) AS prof ON cl.professor_id = prof.professor_id
                WHERE s.id = ? AND semester = ?;
                """;
        return jdbcTemplate.query(query, new EnrollmentFullInfoRowMapper(), UUID.fromString(studentId), semester);
    }

}
