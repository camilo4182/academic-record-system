package training.path.academicrecordsystem.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomCourseRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course();
        course.setId(rs.getLong("course_id"));
        course.setName(rs.getString("course_name"));
        return course;
    }
}
