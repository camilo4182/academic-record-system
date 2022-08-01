package training.path.academicrecordsystem.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomUserJoinCourseRowMapper implements RowMapper<User> {

    private final CustomCourseRowMapper customCourseRowMapper;

    public CustomUserJoinCourseRowMapper(CustomCourseRowMapper customCourseRowMapper) {
        this.customCourseRowMapper = customCourseRowMapper;
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setName(rs.getString("user_name"));
        while (rs.next()) {
            Course course = customCourseRowMapper.mapRow(rs, rowNum);
            //course.setCreatedBy(user);

            System.out.println("Entered rs.next() " + rs.getString("course_id"));
            System.out.println(course.toString());

            user.addCourse(course);
        }
        return user;
    }
}
