package training.path.academicrecordsystem.repositories.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.repositories.interfaces.StudentRepository;

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
        String queryUser = "INSERT INTO users (id, name, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(queryUser, UUID.fromString(student.getId()), student.getName(), student.getEmail());
        String queryStudent = "INSERT INTO students (id, grade_average) VALUES (?, ?)";
        return jdbcTemplate.update(queryStudent, UUID.fromString(student.getId()), student.getAverageGrade());
    }

    @Override
    public int update(String id, Student student) {
        String queryUser = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(queryUser, student.getName(), student.getEmail(), UUID.fromString(student.getId()));
        String queryStudent = "UPDATE students SET grade_average = ? WHERE id = ?";
        return jdbcTemplate.update(queryStudent, student.getAverageGrade(), UUID.fromString(student.getId()));
    }

    @Override
    public int deleteById(String id) {
        String query = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(query, UUID.fromString(id));
    }

    @Override
    public Optional<Student> findById(String id) {
        String query = "SELECT * FROM students s INNER JOIN users u ON s.id = u.id WHERE u.id = ?";
        try {
            Student student = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Student.class), UUID.fromString(id));
            return Optional.ofNullable(student);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Student> findAll() {
        String query = "SELECT * FROM students s INNER JOIN users u ON s.id = u.id ORDER BY u.name";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public List<Student> findAll(int limit, int offset) {
        String query = "SELECT * FROM students s INNER JOIN users u ON s.id = u.id ORDER BY u.name LIMIT ? OFFSET ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Student.class), limit, offset);
    }

    @Override
    public boolean exists(String id) {
        String query = "SELECT * FROM students WHERE id = ?";
        try {
            jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Student.class), UUID.fromString(id));
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
