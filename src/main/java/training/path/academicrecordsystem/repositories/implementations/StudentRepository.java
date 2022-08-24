package training.path.academicrecordsystem.repositories.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.Enrollment;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.repositories.interfaces.IStudentRepository;
import training.path.academicrecordsystem.repositories.rowmappers.EnrollmentFullInfoRowMapper;
import training.path.academicrecordsystem.repositories.rowmappers.EnrollmentInfoRowMapper;
import training.path.academicrecordsystem.repositories.rowmappers.StudentInfoRowMapper;

import java.util.*;

@Repository
public class StudentRepository implements IStudentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Student student) {
        String queryUser = "INSERT INTO users (id, first_name, last_name, username, password, email, role_id) VALUES (?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(queryUser, UUID.fromString(student.getId()),
                student.getFirstName(),
                student.getLastName(),
                student.getUserName(),
                student.getPassword(),
                student.getEmail(),
                UUID.fromString(student.getRole().getId()));
        String queryStudent = "INSERT INTO students (id, average_grade) VALUES (?, ?);";
        return jdbcTemplate.update(queryStudent, UUID.fromString(student.getId()), student.getAverageGrade());
    }

    @Override
    public int update(String id, Student student) {
        String queryStudent = "UPDATE students SET average_grade = ? WHERE id = ?;";
        jdbcTemplate.update(queryStudent, student.getAverageGrade(), UUID.fromString(id));
        String queryUser = "UPDATE enrollments SET career_id = ? WHERE student_id = ?;";
        return jdbcTemplate.update(queryUser, UUID.fromString(student.getCareer().getId()), UUID.fromString(id));
    }

    @Override
    public int updateBasicInfo(String id, Student student) {
        String queryUser = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ?, email = ? WHERE id = ?;";
        return jdbcTemplate.update(queryUser, student.getFirstName(),
                student.getLastName(),
                student.getUserName(),
                student.getPassword(),
                student.getEmail(),
                UUID.fromString(id));
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
                SELECT s.id AS student_id, u.first_name AS first_name, u.last_name AS last_name, u.username AS username, u.email AS student_email, average_grade, c.id AS career_id, c.name AS career
                FROM students s INNER JOIN users u ON s.id = u.id
                INNER JOIN enrollments e ON e.student_id = s.id
                INNER JOIN careers c ON e.career_id = c.id
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
    public Optional<Student> findByFirstName(String firstName) {
        String query =
                """
                SELECT *
                FROM students s INNER JOIN users u ON s.id = u.id
                WHERE u.first_name ILIKE ?;
                """;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Student.class), firstName));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Student> findByUserName(String name) {
        String query =
                """
                SELECT *
                FROM students s INNER JOIN users u ON s.id = u.id
                WHERE u.username ILIKE ?;
                """;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Student.class), name));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        String query =
                """
                SELECT *
                FROM students s INNER JOIN users u ON s.id = u.id
                WHERE u.email ILIKE ?;
                """;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Student.class), email));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Student> findAll() {
        String query =
                """
                SELECT s.id AS student_id, u.first_name AS first_name, u.last_name AS last_name, u.username AS username, u.email AS student_email, average_grade, c.id AS career_id, c.name AS career
                FROM students s INNER JOIN users u ON s.id = u.id
                INNER JOIN enrollments e ON s.id = e.student_id
                INNER JOIN careers c ON e.career_id = c.id
                ORDER BY u.first_name;
                """;
        return jdbcTemplate.query(query, new StudentInfoRowMapper());
    }

    @Override
    public List<Student> findAll(int limit, int offset) {
        String query =
                """
                SELECT s.id AS student_id, u.first_name AS first_name, u.last_name AS last_name, u.username AS username, u.email AS student_email, average_grade, c.id AS career_id, c.name AS career
                FROM students s INNER JOIN users u ON s.id = u.id
                INNER JOIN careers c ON s.career_id = c.id
                ORDER BY u.first_name
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
                SELECT e.id AS enrollment_id, u.id AS student_id, u.first_name AS first_name, u.last_name AS last_name, u.username AS username, semester, cl.id AS class_id, capacity,
                    enrolled_students, available, co.id AS course_id, co.name AS course, credits, prof.professor_id, professor_first_name, professor_last_name,
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
                WHERE s.id = ?;
                """;
        try {
            List<Enrollment> enrollmentList = jdbcTemplate.query(query, new EnrollmentFullInfoRowMapper(), UUID.fromString(studentId));

            int maxSemester = enrollmentList.stream().max(Comparator.comparing(Enrollment::getSemester)).orElseThrow().getSemester();
            List<Enrollment> enrollmentsPerSemester = new ArrayList<>();

            for (int i = 1; i <= maxSemester; i++) {
                Enrollment enrollment = new Enrollment();
                enrollment.setId(enrollmentList.get(0).getId());
                enrollment.setStudent(enrollmentList.get(0).getStudent());

                int finalI = i;
                List<Enrollment> filteredEnrollments = enrollmentList.stream().filter(e -> e.getSemester() == finalI).toList();
                enrollment.setSemester(filteredEnrollments.get(0).getSemester());
                for (Enrollment e : filteredEnrollments) {
                    enrollment.addClass(e.getCourseClasses().get(0));
                }
                enrollmentsPerSemester.add(enrollment);
            }
            return enrollmentsPerSemester;
        } catch (NoSuchElementException e) {
            String queryForNoClasses =
                    """
                    SELECT e.id AS enrollment_id, u.id AS student_id, u.first_name AS first_name, u.last_name AS last_name, u.username AS username, e.career_id AS career_id, c.name AS career
                    FROM users u INNER JOIN students s ON u.id = s.id
                    INNER JOIN enrollments e ON e.student_id = s.id
                    INNER JOIN careers c ON c.id = e.career_id
                    WHERE s.id = ?;
                    """;
            return jdbcTemplate.query(queryForNoClasses, new EnrollmentInfoRowMapper(), UUID.fromString(studentId));
        }
    }

    @Override
    public List<Enrollment> findEnrollmentsBySemester(String studentId, int semester) {
        List<Enrollment> allEnrollments = findEnrollmentInfo(studentId);
        return allEnrollments.stream().filter(e -> e.getSemester() == semester).toList();
    }

}
