package training.path.academicrecordsystem.repositories.implementations;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training.path.academicrecordsystem.model.Administrator;
import training.path.academicrecordsystem.repositories.interfaces.IAdminRepository;

import java.util.UUID;

@Repository
public class AdminRepository implements IAdminRepository {

    private final JdbcTemplate jdbcTemplate;

    public AdminRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Administrator administrator) {
        String query = "INSERT INTO users (id, first_name, last_name, username, password, email, role_id) VALUES (?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, UUID.fromString(administrator.getId()),
                administrator.getFirstName(),
                administrator.getLastName(),
                administrator.getUserName(),
                administrator.getPassword(),
                administrator.getEmail(),
                UUID.fromString(administrator.getRole().getId()));
        String queryAdmin = "INSERT INTO administrators (id) VALUES (?);";
        return jdbcTemplate.update(queryAdmin, UUID.fromString(administrator.getId()));
    }

}
