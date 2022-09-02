package training.path.academicrecordsystem.repositories.rowmappers;

import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.factory.UserFactory;
import training.path.academicrecordsystem.model.Role;
import training.path.academicrecordsystem.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class UserWithRolRowMapper implements RowMapper<User> {

    @SneakyThrows
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        role.setRoleName(rs.getString("role"));

        Optional<User> userOptional = UserFactory.create(role.getRoleName());
        if (userOptional.isEmpty()) throw new ResourceNotFoundException("Role was not found");
        User user = userOptional.get();
        user.setId(rs.getObject("user_id", UUID.class).toString());
        user.setUserName(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setRole(role);
        return user;
    }

}
