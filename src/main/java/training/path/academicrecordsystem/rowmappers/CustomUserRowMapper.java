package training.path.academicrecordsystem.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import training.path.academicrecordsystem.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomUserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        /*
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setName(rs.getString("user_name"));
        return user;
        */
        return null;
    }

}
