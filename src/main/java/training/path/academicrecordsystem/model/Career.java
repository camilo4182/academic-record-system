package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;

import javax.validation.constraints.*;

@Data
public class Career {

    @NotBlank(message = "Career id cannot be null")
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String id;

    @NotBlank(message = "You must provide the career name")
    @Size(min = 4, message = "Career name must have at least 4 characters")
    private String name;

    public Career() {
    }

    @Builder
    public Career(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
