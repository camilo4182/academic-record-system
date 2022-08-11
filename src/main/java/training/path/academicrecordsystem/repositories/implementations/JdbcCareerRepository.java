package training.path.academicrecordsystem.repositories.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.repositories.implementations.rowmappers.CoursesByCareerRowMapper;
import training.path.academicrecordsystem.repositories.interfaces.CareerRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcCareerRepository implements CareerRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcCareerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Career> findById(String id) {
        String query = "SELECT * FROM careers WHERE id = ?";
        try {
            Career career = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Career.class), UUID.fromString(id));
            return Optional.ofNullable(career);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Career> findByName(String name) {
        String query = "SELECT * FROM careers WHERE name = ?";
        try {
            Career career = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Career.class), name);
            return Optional.ofNullable(career);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Career> findAll() {
        String query = "SELECT * FROM careers ORDER BY name";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Career.class));
    }

    @Override
    public List<Career> findAll(int limit, int offset) {
        String query = "SELECT * FROM careers ORDER BY name LIMIT ? OFFSET ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Career.class), limit, offset);
    }

    @Override
    public int save(Career career) {
        String query = "INSERT INTO careers (id, name) VALUES (?, ?)";
        return jdbcTemplate.update(query, UUID.fromString(career.getId()), career.getName());
    }

    @Override
    public int update(String id, Career career) {
        String query = "UPDATE careers SET name = ? WHERE id = ?";
        return jdbcTemplate.update(query, career.getName(), UUID.fromString(id));
    }

    @Override
    public int deleteById(String id) {
        String query = "DELETE FROM careers WHERE id = ?";
        return jdbcTemplate.update(query, UUID.fromString(id));
    }

    @Override
    public boolean exists(String id) {
        String query = "SELECT * FROM careers WHERE id = ?";
        try {
            jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Career.class), UUID.fromString(id));
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public int insertIntoCareerClasses(String careerId, String classId) {
        String query = "INSERT INTO career_classes (career_id, class_id) VALUES (?, ?)";
        return jdbcTemplate.update(query, UUID.fromString(careerId), UUID.fromString(classId));
    }

    @Override
    public List<Course> getCoursesByCareer(String careerId) {
        String query = """
                SELECT ca.name AS career_name, co.id AS course_id, co.name AS course_name, credits
                FROM careers ca INNER JOIN career_classes cc ON ca.id = cc.career_id\s
                INNER JOIN classes cl ON cc.class_id = cl.id\s
                INNER JOIN courses co ON co.id = cl.course_id
                WHERE ca.id = ?
                GROUP BY career_name, co.id, co.name, credits
                ORDER BY career_name;""";
        return jdbcTemplate.query(query, new CoursesByCareerRowMapper(), UUID.fromString(careerId));
    }

}
