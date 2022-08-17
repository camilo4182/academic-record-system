package training.path.academicrecordsystem.exceptions.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {

    private String fieldName;
    private String message;

}
