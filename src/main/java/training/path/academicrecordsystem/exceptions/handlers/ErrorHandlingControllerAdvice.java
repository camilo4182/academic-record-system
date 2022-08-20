package training.path.academicrecordsystem.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import training.path.academicrecordsystem.exceptions.response.ExceptionResponse;
import training.path.academicrecordsystem.exceptions.response.ValidationExceptionResponse;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {



    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<ExceptionResponse> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setMessage("Malformed JSON body");
        List<ValidationExceptionResponse> errors = new ArrayList<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            errors.add(new ValidationExceptionResponse(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        response.setErrors(errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<ExceptionResponse> onConstraintValidationException(ConstraintViolationException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setMessage("Errors in URL");
        List<ValidationExceptionResponse> errors = new ArrayList<>();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            errors.add(new ValidationExceptionResponse(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        response.setErrors(errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
