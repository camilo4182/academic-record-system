package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class Course {

    private String id;
    private String name;
    private int credits;
    private Set<Career> careers;
    private Map<String, CourseClass> classes;

    public Course() {
        careers = new HashSet<>();
    }

    @Builder
    public Course(String id, String name, int credits, Set<Career> careers, Map<String, CourseClass> classes) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.careers = careers;
        this.classes = classes;
    }

    public void addCareer(Career career) {
        careers.add(career);
    }

    public void removeCareer(Career career) {
        careers.remove(career);
    }

    public void addClass(CourseClass courseClass) {
        classes.put(courseClass.getId(), courseClass);
    }

    public void removeClass(CourseClass courseClass) {
        classes.remove(courseClass.getId());
    }
}
