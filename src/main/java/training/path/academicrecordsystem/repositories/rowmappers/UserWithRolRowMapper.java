package training.path.academicrecordsystem.repositories.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import training.path.academicrecordsystem.factory.UserFactory;
import training.path.academicrecordsystem.model.Role;
import training.path.academicrecordsystem.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserWithRolRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        role.setRoleName(rs.getString("role"));

        User user = UserFactory.create(role.getRoleName());
        user.setId(rs.getObject("user_id", UUID.class).toString());
        user.setUserName(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setRole(role);
        return user;
    }

}
