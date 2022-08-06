package training.path.academicrecordsystem.controllers.dtos;

import lombok.Builder;
import lombok.Data;

@Data
public class CourseDTO {

    private String id;
    private String name;
    private int credits;

    public CourseDTO() {
    }

    @Builder
    public CourseDTO(String id, String name, int credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
    }
}
