package training.path.academicrecordsystem.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.rowmappers.CustomCourseRowMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCourseRepository implements CourseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcCourseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Course> findById(long id) {
        String query = "SELECT * FROM courses WHERE course_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new CustomCourseRowMapper(), id));
    }

    @Override
    public Optional<Course> findByName(String name) {
        String query = "SELECT * FROM courses WHERE course_name ILIKE ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Course.class), name));
    }

    public List<Course> findCoursesByUser(long user_id) {
        String query = "SELECT * FROM users INNER JOIN courses ON users.user_id = courses.created_by WHERE users.user_id = ?";
        return jdbcTemplate.query(query, new CustomCourseRowMapper(), user_id);
    }

    @Override
    public List<Course> findAll() {
        String query = "SELECT * FROM courses";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Course.class));
    }

    @Override
    public int save(Course course) {
        String query = "INSERT INTO courses (course_name, created_by) VALUES (?, ?)";
        return jdbcTemplate.update(query, course.getName(), course.getCreatedBy().getId());
    }

    @Override
    public int update(long id, Course course) {
        String query = "UPDATE courses SET course_name = ? WHERE course_id = ?";
        return jdbcTemplate.update(query, course.getName(), id);
    }

    public int deleteByUser(long userId) {
        String query = "DELETE FROM courses WHERE created_by = ?";
        return jdbcTemplate.update(query, userId);
    }

    @Override
    public int deleteById(long id) {
        String query = "DELETE FROM courses WHERE course_id = ?";
        return jdbcTemplate.update(query, id);
    }

    @Override
    public int deleteAll() {
        String query = "DELETE FROM courses";
        return jdbcTemplate.update(query);
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
