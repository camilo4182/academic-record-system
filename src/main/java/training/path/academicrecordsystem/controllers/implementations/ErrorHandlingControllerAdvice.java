package training.path.academicrecordsystem.controllers.implementations;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import training.path.academicrecordsystem.exceptions.handlers.ExceptionResponse;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<ExceptionResponse> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ExceptionResponse> exceptionResponses = new ArrayList<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            exceptionResponses.add(new ExceptionResponse(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return exceptionResponses;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<ExceptionResponse> onConstraintValidationException(ConstraintViolationException e) {
        List<ExceptionResponse> exceptionResponses = new ArrayList<>();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            exceptionResponses.add(new ExceptionResponse(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return exceptionResponses;
    }

}
