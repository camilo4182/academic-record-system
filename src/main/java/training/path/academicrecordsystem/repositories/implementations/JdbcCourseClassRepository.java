package training.path.academicrecordsystem.repositories.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.repositories.implementations.rowmappers.CourseClassFindAllRowMapper;
import training.path.academicrecordsystem.repositories.interfaces.CourseClassRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcCourseClassRepository implements CourseClassRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcCourseClassRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(CourseClass courseClass) {
        String query = "INSERT INTO classes (id, available, course_id, professor_id) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(query, UUID.fromString(courseClass.getId()), courseClass.isAvailable(),
                UUID.fromString(courseClass.getCourse().getId()), UUID.fromString(courseClass.getProfessor().getId()));
    }

    @Override
    public int update(String id, CourseClass courseClass) {
        return 0;
    }

    @Override
    public int deleteById(String id) {
        return 0;
    }

    @Override
    public Optional<CourseClass> findById(String id) {
        String query = "SELECT cl.id AS class_id, co.id AS course_id, co.name AS course_name, credits, available, p.id AS prof_id, u.name AS prof_name, u.email AS prof_email, salary\n" +
                "FROM classes cl INNER JOIN courses co ON co.id = cl.course_id\n" +
                "INNER JOIN professors p ON p.id = cl.professor_id\n" +
                "INNER JOIN users u ON p.id = u.id\n" +
                "WHERE cl.id = ?";
        try {
            CourseClass courseClass = jdbcTemplate.queryForObject(query, new CourseClassFindAllRowMapper(), UUID.fromString(id));
            return Optional.ofNullable(courseClass);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<CourseClass> findAll() {
        String query = "SELECT cl.id AS class_id, co.id AS course_id, co.name AS course_name, credits, available, p.id AS prof_id, u.name AS prof_name, u.email AS prof_email, salary\n" +
                "FROM classes cl INNER JOIN courses co ON co.id = cl.course_id\n" +
                "INNER JOIN professors p ON p.id = cl.professor_id\n" +
                "INNER JOIN users u ON p.id = u.id";
        return jdbcTemplate.query(query, new CourseClassFindAllRowMapper());
    }

    @Override
    public boolean exists(String id) {
        String query = "SELECT * FROM classes";
        return Objects.nonNull(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(CourseClass.class)));
    }
}
