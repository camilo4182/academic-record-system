package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;

@Data
public class Enrollment {

    private String id;
    private int semester;
    private Student student;
    private CourseClass courseClass;
    private Career career;

    public Enrollment() {
    }

    @Builder
    public Enrollment(String id, int semester, Student student, CourseClass courseClass, Career career) {
        this.id = id;
        this.semester = semester;
        this.student = student;
        this.courseClass = courseClass;
        this.career = career;
    }
}
