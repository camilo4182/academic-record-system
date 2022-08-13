package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ResponseBodyEnrollmentDTO {

    private String id;
    private List<ResponseBodyCourseClassDTO> classes;
    private int semester;

}
