package training.path.academicrecordsystem.model;

public class Course {

    private long id;
    private String name;
    private User createdBy;

    public Course() {
    }

    public Course(String name, User createdBy) {
        this.name = name;
        this.createdBy = createdBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Course {" + "id=" + id + ", name='" + name + '\'' + ", createdBy=" + createdBy + '}';
    }

}
