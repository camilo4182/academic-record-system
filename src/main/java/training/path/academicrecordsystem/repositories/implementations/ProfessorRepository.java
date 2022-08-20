package training.path.academicrecordsystem.repositories.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.repositories.interfaces.IProfessorRepository;

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
        String queryUser = "INSERT INTO users (id, name, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(queryUser, UUID.fromString(professor.getId()), professor.getName(), professor.getEmail());
        String queryProfessor = "INSERT INTO professors (id, salary) VALUES (?, ?)";
        return jdbcTemplate.update(queryProfessor, UUID.fromString(professor.getId()), professor.getSalary());
    }

    @Override
    public int update(String id, Professor professor) {
        String queryUser = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(queryUser, professor.getName(), professor.getEmail(), UUID.fromString(professor.getId()));
        String queryProfessor = "UPDATE professors SET salary = ? WHERE id = ?";
        return jdbcTemplate.update(queryProfessor, professor.getSalary(), UUID.fromString(professor.getId()));
    }

    @Override
    public int deleteById(String id) {
        String queryUser = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(queryUser, UUID.fromString(id));
    }

    @Override
    public Optional<Professor> findById(String id) {
        String query = "SELECT * FROM professors p INNER JOIN users u ON p.id = u.id WHERE p.id = ?";
        try {
            Professor professor = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Professor.class), UUID.fromString(id));
            return Optional.ofNullable(professor);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Professor> findAll() {
        String query = "SELECT * FROM professors p INNER JOIN users u ON p.id = u.id ORDER BY name";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Professor.class));
    }

    @Override
    public List<Professor> findAll(int limit, int offset) {
        String query = "SELECT * FROM professors p INNER JOIN users u ON p.id = u.id ORDER BY name LIMIT ? OFFSET ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Professor.class), limit, offset);
    }

    @Override
    public boolean exists(String id) {
        String query = "SELECT * FROM professors WHERE id = ?";
        try {
            jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Professor.class), UUID.fromString(id));
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
