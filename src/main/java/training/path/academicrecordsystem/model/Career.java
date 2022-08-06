package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;

@Data
public class Career {

    private String id;
    private String name;

    public Career() {
    }

    @Builder
    public Career(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
