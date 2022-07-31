package training.path.academicrecordsystem.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findById(long id) {
        String query = "SELECT * FROM users WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new CustomRowMapper(), id));
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
        String query = "INSERT INTO users (name) VALUES (?)";
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

    static class CustomRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            return user;
        }
    }

}
