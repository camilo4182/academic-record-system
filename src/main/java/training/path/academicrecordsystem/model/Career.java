package training.path.academicrecordsystem.model;

public class Career {

    private String id;
    private String name;

    public Career() {
    }

    public Career(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Career {" + "id=" + id + ", name='" + name + '\'' + '}';
    }

}
