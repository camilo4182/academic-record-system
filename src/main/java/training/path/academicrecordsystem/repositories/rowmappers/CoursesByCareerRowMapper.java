package training.path.academicrecordsystem.repositories.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import training.path.academicrecordsystem.model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CoursesByCareerRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course();
        course.setId(rs.getObject("course_id", UUID.class).toString());
        course.setName(rs.getString("course_name"));
        course.setCredits(rs.getInt("credits"));
        return course;
    }

}
