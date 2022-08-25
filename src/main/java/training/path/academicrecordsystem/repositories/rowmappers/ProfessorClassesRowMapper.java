package training.path.academicrecordsystem.repositories.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Professor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ProfessorClassesRowMapper implements RowMapper<Professor> {

    @Override
    public Professor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Professor professor = new Professor();
        professor.setId(rs.getObject("prof_id", UUID.class).toString());

        Course course = new Course();
        course.setId(rs.getObject("course_id", UUID.class).toString());
        course.setName(rs.getString("course"));
        course.setCredits(rs.getByte("credits"));

        CourseClass courseClass = new CourseClass();
        courseClass.setId(rs.getObject("class_id", UUID.class).toString());
        courseClass.setCapacity(rs.getInt("capacity"));
        courseClass.setEnrolledStudents(rs.getInt("enrolled_students"));
        courseClass.setAvailable(rs.getBoolean("available"));
        courseClass.setCourse(course);

        professor.addClass(courseClass);

        return professor;
    }

}
