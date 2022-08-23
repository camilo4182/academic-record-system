package training.path.academicrecordsystem.repositories.implementations;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.User;
import training.path.academicrecordsystem.repositories.interfaces.IUserRepository;
import training.path.academicrecordsystem.repositories.rowmappers.UserWithRolRowMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository implements IUserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> findByName(String name) {
        String query = """
                SELECT u.id AS user_id, u.name AS name, u.password AS password, u.email AS email, r.name AS role
                FROM users u INNER JOIN roles r ON u.role_id = r.id
                WHERE u.name ILIKE ?;
                """;
        try {
            User user = jdbcTemplate.queryForObject(query, new UserWithRolRowMapper(), name);
            return Optional.ofNullable(user);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

}
