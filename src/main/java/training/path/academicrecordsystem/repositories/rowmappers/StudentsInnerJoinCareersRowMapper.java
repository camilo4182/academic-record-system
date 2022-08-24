package training.path.academicrecordsystem.repositories.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class StudentsInnerJoinCareersRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(rs.getObject("student_id", UUID.class).toString());
        student.setFirstName(rs.getString("student_name"));
        student.setEmail(rs.getString("student_email"));

        Career career = new Career();
        career.setId(rs.getObject("career_id", UUID.class).toString());
        career.setName(rs.getString("career_name"));
        student.setCareer(career);

        return student;
    }

}
