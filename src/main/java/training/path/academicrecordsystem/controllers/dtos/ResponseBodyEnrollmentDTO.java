package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ResponseBodyEnrollmentDTO {

    private String id;
    private String name;
    private String career;
    private List<ResponseBodyCourseClassDTO> classes;

}
