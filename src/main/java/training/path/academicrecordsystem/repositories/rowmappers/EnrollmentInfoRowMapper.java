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

        Career career = new Career();
        career.setId(rs.getObject("career_id", UUID.class).toString());
        career.setName(rs.getString("career"));

        Student student = new Student();
        student.setId(rs.getObject("student_id", UUID.class).toString());
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setUserName(rs.getString("username"));
        student.setCareer(career);
        enrollment.setStudent(student);

        return enrollment;
    }

}
