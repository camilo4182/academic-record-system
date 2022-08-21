package training.path.academicrecordsystem.repositories.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Professor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ClassesByCourseRowMapper implements RowMapper<CourseClass> {

    @Override
    public CourseClass mapRow(ResultSet rs, int rowNum) throws SQLException {
        CourseClass courseClass = new CourseClass();
        courseClass.setId(rs.getObject("class_id", UUID.class).toString());
        courseClass.setAvailable(rs.getBoolean("available"));
        courseClass.setEnrolledStudents(rs.getInt("enrolled_students"));
        courseClass.setCapacity(rs.getInt("capacity"));

        Professor professor = new Professor();
        professor.setId(rs.getObject("prof_id", UUID.class).toString());
        professor.setName(rs.getString("prof_name"));
        professor.setEmail(rs.getString("prof_email"));
        courseClass.setProfessor(professor);

        Course course = new Course();
        course.setId(rs.getObject("course_id", UUID.class).toString());
        course.setName(rs.getString("course_name"));
        course.setCredits(rs.getInt("credits"));
        courseClass.setCourse(course);

        return courseClass;
    }

}
