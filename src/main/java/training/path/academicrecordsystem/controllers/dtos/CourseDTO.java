package training.path.academicrecordsystem.controllers.dtos;

import lombok.Builder;
import lombok.Data;

@Data
public class CourseDTO {

    private String id;
    private String name;
    private int credits;

}
