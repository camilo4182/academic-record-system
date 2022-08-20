package training.path.academicrecordsystem.exceptions.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationExceptionResponse {

    private String fieldName;
    private String message;

}
