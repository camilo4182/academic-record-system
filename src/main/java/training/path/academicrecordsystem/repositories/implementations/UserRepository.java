package training.path.academicrecordsystem.repositories.implementations;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.Role;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.model.User;
import training.path.academicrecordsystem.repositories.interfaces.IUserRepository;
import training.path.academicrecordsystem.repositories.rowmappers.UserWithRolRowMapper;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class UserRepository implements IUserRepository {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public int update(String id, User user) {
        String queryUser = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ?, email = ? WHERE id = ?;";
        return jdbcTemplate.update(queryUser, user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getPassword(),
                user.getEmail(),
                UUID.fromString(id));
    }

    @Override
    public int deleteById(String id) {
        String query = "DELETE FROM users WHERE id = ?;";
        return jdbcTemplate.update(query, UUID.fromString(id));
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        String query = """
                SELECT u.id AS user_id, u.username AS username, u.password AS password, u.email AS email, r.role AS role
                FROM users u INNER JOIN roles r ON u.role_id = r.id
                WHERE u.username ILIKE ?;
                """;
        try {
            User user = jdbcTemplate.queryForObject(query, new UserWithRolRowMapper(), userName);
            return Optional.ofNullable(user);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String query =
                """
                SELECT u.id AS user_id, u.username AS username, u.password AS password, u.email AS email, r.role AS role
                FROM users u INNER JOIN roles r ON u.role_id = r.id
                WHERE u.email ILIKE ?;
                """;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, new UserWithRolRowMapper(), email));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        String query = "SELECT * FROM roles WHERE role ILIKE ?;";
        try {
            Role role = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Role.class), name);
            return Optional.ofNullable(role);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean exists(String id) {
        String query = """
                SELECT u.id AS user_id, u.username AS username, u.password AS password, u.email AS email, r.role AS role
                FROM users u INNER JOIN roles r ON u.role_id = r.id
                WHERE u.id = ?;
                """;
        try {
            jdbcTemplate.queryForObject(query, new UserWithRolRowMapper(), UUID.fromString(id));
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

}
