package training.path.academicrecordsystem.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> findById(long id) {
        String query = "SELECT * FROM users WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(User.class), id));
    }

    @Override
    public Optional<User> findByName(String name) {
        String query = "SELECT * FROM users WHERE name ILIKE ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(User.class), name));
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public int save(User user) {
        String query = "INSERT INTO users (id, name) VALUES (nextval('users_id_sec'), ?)";
        return jdbcTemplate.update(query, user.getName());
    }

    @Override
    public int update(long id, User user) {
        String query = "UPDATE users SET name = ? WHERE id = ?";
        return jdbcTemplate.update(query, user.getName(), id);
    }

    @Override
    public int deleteById(long id) {
        String query = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(query, id);
    }

    @Override
    public int deleteAll() {
        String query = "DELETE FROM users";
        return jdbcTemplate.update(query);
    }

}
