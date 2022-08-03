package training.path.academicrecordsystem.repositories.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.repositories.interfaces.CareerRepository;

import java.util.List;
import java.util.Optional;

public class JdbcCareerRepository implements CareerRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcCareerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Career> findById(long id) {
        String query = "SELECT * FROM careers WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Career.class), id));
    }

    @Override
    public Optional<Career> findByName(String name) {
        String query = "SELECT * FROM careers WHERE name = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Career.class), name));
    }

    @Override
    public List<Career> findAll() {
        String query = "SELECT * FROM careers";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Career.class));
    }

    @Override
    public int save(Career career) {
        String query = "INSERT INTO careers (name) VALUES (?)";
        return jdbcTemplate.update(query, career.getName());
    }

    @Override
    public int update(long id, Career career) {
        String query = "UPDATE careers SET name = ? WHERE id = ?";
        return jdbcTemplate.update(query, career.getName(), id);
    }

    @Override
    public int deleteById(long id) {
        String query = "DELETE FROM careers WHERE id = ?";
        return jdbcTemplate.update(query, id);
    }

}
