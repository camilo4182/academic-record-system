package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Course {

    private String id;
    private String name;
    private int credits;
    private Set<Career> careers;

    public Course() {
        careers = new HashSet<>();
    }

    @Builder
    public Course(String id, String name, int credits, Set<Career> careers) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.careers = careers;
    }

    public void addCareer(Career career) {
        careers.add(career);
    }

    public void removeCareer(Career career) {
        careers.remove(career);
    }
}
