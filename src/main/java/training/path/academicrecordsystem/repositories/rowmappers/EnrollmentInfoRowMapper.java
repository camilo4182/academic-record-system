package training.path.academicrecordsystem.repositories.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import training.path.academicrecordsystem.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class EnrollmentInfoRowMapper implements RowMapper<Enrollment> {

    @Override
    public Enrollment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(rs.getObject("enrollment_id", UUID.class).toString());
        enrollment.setSemester(rs.getInt("semester"));

        Student student = new Student();
        student.setId(rs.getObject("student_id", UUID.class).toString());
        student.setName(rs.getString("student"));
        enrollment.setStudent(student);

        Course course = new Course();
        course.setId(rs.getObject("course_id", UUID.class).toString());
        course.setName(rs.getString("course"));
        course.setCredits(rs.getInt("credits"));

        Professor professor = new Professor();
        professor.setId(rs.getObject("professor_id", UUID.class).toString());
        professor.setName(rs.getString("professor_name"));

        CourseClass courseClass = new CourseClass();
        courseClass.setId(rs.getObject("class_id", UUID.class).toString());
        courseClass.setCourse(course);
        courseClass.setProfessor(professor);
        courseClass.setCapacity(rs.getInt("capacity"));
        courseClass.setEnrolledStudents(rs.getInt("enrolled_students"));
        courseClass.setAvailable(rs.getBoolean("available"));

        enrollment.addClass(courseClass);

        return enrollment;
    }

}
