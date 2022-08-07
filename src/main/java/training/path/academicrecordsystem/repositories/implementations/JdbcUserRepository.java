package training.path.academicrecordsystem.repositories.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.User;
import training.path.academicrecordsystem.repositories.interfaces.UserRepository;

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
        String query = "SELECT * FROM users WHERE user_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(User.class), id));
    }

    @Override
    public Optional<User> findByName(String name) {
        String query = "SELECT * FROM users WHERE user_name ILIKE ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(User.class), name));
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public int save(User user) {
        String query = "INSERT INTO users (user_name) VALUES (?)";
        return jdbcTemplate.update(query, user.getName());
    }

    @Override
    public int update(long id, User user) {
        String query = "UPDATE users SET user_name = ? WHERE user_id = ?";
        return jdbcTemplate.update(query, user.getName(), id);
    }

    @Override
    public int deleteById(long id) {
        String query = "DELETE FROM users WHERE user_id = ?";
        return jdbcTemplate.update(query, id);
    }

    @Override
    public int deleteAll() {
        String query = "DELETE FROM users";
        return jdbcTemplate.update(query);
    }

    /*
    static class CustomUserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            return user;
        }
    }
    */

}
