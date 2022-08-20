package training.path.academicrecordsystem.validations.custom;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.PARAMETER)
@Constraint(validatedBy = {})
@Retention(RUNTIME)
@Min(1)
@Max(12)
@ReportAsSingleViolation
public @interface SemesterValidator {

    String message() default "Semester number must be between 1 and 12";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
