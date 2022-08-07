package training.path.academicrecordsystem.repositories.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.repositories.interfaces.ProfessorRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcProfessorRepository implements ProfessorRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcProfessorRepository(JdbcTemplate jdbcTemplate) {
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
        return Optional.empty();
    }

    @Override
    public Optional<Professor> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Professor> findAll() {
        return null;
    }

    @Override
    public boolean exists(String id) {
        String query = "SELECT * FROM professors WHERE id = ?";
        return Objects.nonNull(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Professor.class), UUID.fromString(id)));
    }
}
