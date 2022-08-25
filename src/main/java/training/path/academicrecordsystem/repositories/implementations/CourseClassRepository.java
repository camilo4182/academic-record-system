package training.path.academicrecordsystem.repositories.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.repositories.rowmappers.CourseClassInfoRowMapper;
import training.path.academicrecordsystem.repositories.interfaces.ICourseClassRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CourseClassRepository implements ICourseClassRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseClassRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(CourseClass courseClass) {
        String query =
                """
                INSERT INTO classes (id, capacity, enrolled_students, available, course_id, professor_id)
                VALUES (?, ?, ?, ?, ?, ?);
                """;
        return jdbcTemplate.update(query,
                UUID.fromString(courseClass.getId()),
                courseClass.getCapacity(),
                courseClass.getEnrolledStudents(),
                courseClass.isAvailable(),
                UUID.fromString(courseClass.getCourse().getId()),
                UUID.fromString(courseClass.getProfessor().getId()));
    }

    @Override
    public int update(String id, CourseClass courseClass) {
        String query =
                """
                UPDATE classes SET
                capacity = ?, enrolled_students = ?, available = ?, course_id = ?, professor_id = ?
                WHERE id = ?;
                """;
        return jdbcTemplate.update(query,
                courseClass.getCapacity(),
                courseClass.getEnrolledStudents(),
                courseClass.isAvailable(),
                UUID.fromString(courseClass.getCourse().getId()),
                UUID.fromString(courseClass.getProfessor().getId()),
                UUID.fromString(id));
    }

    @Override
    public int deleteById(String id) {
        String query = "DELETE FROM classes WHERE id = ?";
        return jdbcTemplate.update(query, UUID.fromString(id));
    }

    @Override
    public Optional<CourseClass> findById(String id) {
        String query =
                """
                SELECT cl.id AS class_id, capacity, enrolled_students, available, co.id AS course_id, co.name AS course_name,
                credits, p.id AS prof_id, u.first_name AS prof_first_name, u.last_name AS prof_last_name,
                u.username AS prof_username, u.email AS prof_email, salary
                FROM classes cl INNER JOIN courses co ON co.id = cl.course_id
                INNER JOIN professors p ON p.id = cl.professor_id
                INNER JOIN users u ON p.id = u.id
                WHERE cl.id = ?;
                """;
        try {
            CourseClass courseClass = jdbcTemplate.queryForObject(query, new CourseClassInfoRowMapper(), UUID.fromString(id));
            return Optional.ofNullable(courseClass);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<CourseClass> findAll() {
        String query =
                """
                SELECT cl.id AS class_id, capacity, enrolled_students, available, co.id AS course_id,
                co.name AS course_name, credits, p.id AS prof_id, u.first_name AS prof_first_name, u.last_name AS prof_last_name,
                u.username AS prof_username, u.email AS prof_email, salary
                FROM classes cl INNER JOIN courses co ON co.id = cl.course_id
                INNER JOIN professors p ON p.id = cl.professor_id
                INNER JOIN users u ON p.id = u.id;
                """;
        return jdbcTemplate.query(query, new CourseClassInfoRowMapper());
    }

    @Override
    public List<CourseClass> findAll(int limit, int offset) {
        String query =
                """
                SELECT cl.id AS class_id, capacity, enrolled_students, available, co.id AS course_id,
                co.name AS course_name, credits, p.id AS prof_id, u.first_name AS prof_first_name, u.last_name AS prof_last_name,
                u.username AS prof_username, u.email AS prof_email, salary
                FROM classes cl INNER JOIN courses co ON co.id = cl.course_id
                INNER JOIN professors p ON p.id = cl.professor_id
                INNER JOIN users u ON p.id = u.id
                LIMIT ? OFFSET ?;
                """;
        return jdbcTemplate.query(query, new CourseClassInfoRowMapper(), limit, offset);
    }

    @Override
    public List<CourseClass> findAllAvailable() {
        List<CourseClass> allClasses = findAll();
        return allClasses.stream().filter(CourseClass::isAvailable).toList();
    }

    @Override
    public List<CourseClass> findAllAvailable(int limit, int offset) {
        List<CourseClass> allClasses = findAll(limit, offset);
        return allClasses.stream().filter(CourseClass::isAvailable).toList();
    }

    @Override
    public List<CourseClass> findAllUnavailable() {
        List<CourseClass> allClasses = findAll();
        return allClasses.stream().filter(courseClass -> !courseClass.isAvailable()).toList();
    }

    @Override
    public List<CourseClass> findAllUnavailable(int limit, int offset) {
        List<CourseClass> allClasses = findAll(limit, offset);
        return allClasses.stream().filter(courseClass -> !courseClass.isAvailable()).toList();
    }

    @Override
    public boolean exists(String id) {
        String query = "SELECT * FROM classes WHERE id = ?";
        try {
            jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(CourseClass.class), UUID.fromString(id));
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean isAvailable(String id) {
        String query = "SELECT available FROM classes WHERE id = ?";
        CourseClass courseClass = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(CourseClass.class), UUID.fromString(id));
        assert courseClass != null;
        return courseClass.isAvailable();
    }

}
