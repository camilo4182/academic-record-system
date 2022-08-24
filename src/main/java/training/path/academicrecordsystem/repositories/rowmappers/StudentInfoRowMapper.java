package training.path.academicrecordsystem.repositories.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class StudentInfoRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(rs.getObject("student_id", UUID.class).toString());
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setUserName(rs.getString("username"));
        student.setEmail(rs.getString("student_email"));
        student.setAverageGrade(rs.getFloat("average_grade"));

        Career career = new Career();
        career.setId(rs.getObject("career_id", UUID.class).toString());
        career.setName(rs.getString("career"));
        student.setCareer(career);

        return student;
    }

}
