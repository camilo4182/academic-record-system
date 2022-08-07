package training.path.academicrecordsystem.repositories.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.repositories.interfaces.CourseRepository;
import training.path.academicrecordsystem.rowmappers.CustomCourseRowMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcCourseRepository implements CourseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcCourseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Course> findById(String id) {
        String query = "SELECT * FROM courses WHERE id = ?";
        try {
            Course course = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Course.class), UUID.fromString(id));
            return Optional.ofNullable(course);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Course> findByName(String name) {
        String query = "SELECT * FROM courses WHERE name ILIKE ?";
        try {
            Course course = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Course.class), name);
            return Optional.ofNullable(course);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    /*
    public List<Course> findCoursesByUser(String id) {
        String query = "SELECT * FROM users INNER JOIN courses ON users.user_id = courses.created_by WHERE users.user_id = ?";
        return jdbcTemplate.query(query, new CustomCourseRowMapper(), user_id);
    }
    */

    @Override
    public List<Course> findAll() {
        String query = "SELECT * FROM courses";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Course.class));
    }

    @Override
    public int save(Course course) {
        String query = "INSERT INTO courses (id, name, credits) VALUES (?, ?, ?)";
        return jdbcTemplate.update(query, UUID.fromString(course.getId()), course.getName(), course.getCredits());
    }

    @Override
    public int update(String id, Course course) {
        String query = "UPDATE courses SET name = ?, credits = ? WHERE id = ?";
        return jdbcTemplate.update(query, course.getName(), course.getCredits(), UUID.fromString(id));
    }

    /*
    public int deleteByUser(String id) {
        String query = "DELETE FROM courses WHERE created_by = ?";
        return jdbcTemplate.update(query, userId);
    }
    */

    @Override
    public int deleteById(String id) {
        String query = "DELETE FROM courses WHERE id = ?";
        return jdbcTemplate.update(query, UUID.fromString(id));
    }

    @Override
    public boolean exists(String id) {
        String query = "SELECT * FROM courses WHERE id = ?";
        try {
            jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Course.class), UUID.fromString(id));
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    /*
    static class CustomCourseRowMapper implements RowMapper<Course> {

        @Override
        public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
            Course course = new Course();
            course.setId(rs.getInt("id"));
            course.setName(rs.getString("name"));
            course.setCreatedBy(rs.getObject("created_by", User.class));
            return course;
        }
    }
    */
}
