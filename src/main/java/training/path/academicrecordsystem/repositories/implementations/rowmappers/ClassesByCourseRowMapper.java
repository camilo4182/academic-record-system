package training.path.academicrecordsystem.repositories.implementations.rowmappers;

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
        courseClass.setId(rs.getObject("course_id", UUID.class).toString());
        courseClass.setAvailable(rs.getBoolean("available"));

        Professor professor = new Professor();
        professor.setId(rs.getObject("professor_id", UUID.class).toString());
        courseClass.setProfessor(professor);

        Course course = new Course();
        course.setId(rs.getObject("course_id", UUID.class).toString());
        courseClass.setCourse(course);

        return courseClass;
    }

}
