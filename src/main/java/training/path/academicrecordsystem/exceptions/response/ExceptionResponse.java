package training.path.academicrecordsystem.exceptions.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    private HttpStatus httpStatus;
    private String message;
    private List<ValidationExceptionResponse> errors;

}
