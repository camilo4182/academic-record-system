package training.path.academicrecordsystem.repositories.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.repositories.interfaces.IProfessorRepository;
import training.path.academicrecordsystem.repositories.rowmappers.ProfessorClassesRowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProfessorRepository implements IProfessorRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProfessorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Professor professor) {
        String queryUser = "INSERT INTO users (id, first_name, last_name, username, password, email, role_id) VALUES (?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(queryUser, UUID.fromString(professor.getId()),
                professor.getFirstName(),
                professor.getLastName(),
                professor.getUserName(),
                professor.getPassword(),
                professor.getEmail(),
                UUID.fromString(professor.getRole().getId()));
        String queryProfessor = "INSERT INTO professors (id, salary) VALUES (?, ?)";
        return jdbcTemplate.update(queryProfessor, UUID.fromString(professor.getId()), professor.getSalary());
    }

    @Override
    public int update(String id, Professor professor) {
        String queryUser = "UPDATE professors SET salary = ? WHERE id = ?;";
        return jdbcTemplate.update(queryUser, professor.getSalary(), UUID.fromString(professor.getId()));
    }

    @Override
    public int updateBasicInfo(String id, Professor professor) {
        String queryUser = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ?, email = ? WHERE id = ?;";
        return jdbcTemplate.update(queryUser, professor.getFirstName(),
                professor.getLastName(),
                professor.getUserName(),
                professor.getPassword(),
                professor.getEmail(),
                UUID.fromString(id));
    }

    @Override
    public int deleteById(String id) {
        String queryUser = "DELETE FROM users WHERE id = ?;";
        return jdbcTemplate.update(queryUser, UUID.fromString(id));
    }

    @Override
    public Optional<Professor> findById(String id) {
        String query = "SELECT * FROM professors p INNER JOIN users u ON p.id = u.id WHERE p.id = ?;";
        try {
            Professor professor = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Professor.class), UUID.fromString(id));
            return Optional.ofNullable(professor);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Professor> findByUserName(String userName) {
        String query =
                """
                SELECT *
                FROM professors p INNER JOIN users u ON p.id = u.id
                WHERE u.username ILIKE ?;
                """;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Professor.class), userName));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Professor> findByEmail(String email) {
        String query =
                """
                SELECT *
                FROM professors p INNER JOIN users u ON p.id = u.id
                WHERE u.email ILIKE ?;
                """;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Professor.class), email));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Professor> findAll() {
        String query = "SELECT * FROM professors p INNER JOIN users u ON p.id = u.id ORDER BY first_name;";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Professor.class));
    }

    @Override
    public List<Professor> findAll(int limit, int offset) {
        String query = "SELECT * FROM professors p INNER JOIN users u ON p.id = u.id ORDER BY first_name LIMIT ? OFFSET ?;";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Professor.class), limit, offset);
    }

    @Override
    public List<CourseClass> findClasses(String id) {
        String query =
                """
                SELECT u.id AS prof_id, cl.id AS class_id, capacity, enrolled_students, available,
                co.id AS course_id, co.name AS course, credits
                FROM professors p INNER JOIN users u ON u.id = p.id
                INNER JOIN classes cl ON cl.professor_id = p.id
                INNER JOIN courses co ON cl.course_id = co.id
                WHERE u.id = ?;
                """;
        List<Professor> professorList = jdbcTemplate.query(query, new ProfessorClassesRowMapper(), UUID.fromString(id));
        List<CourseClass> classes = new ArrayList<>();

        for (Professor value : professorList) {
            classes.add(value.getClassesAsList().get(0));
        }

        return classes;
    }

    @Override
    public boolean exists(String id) {
        String query = "SELECT * FROM professors WHERE id = ?;";
        try {
            jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Professor.class), UUID.fromString(id));
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
