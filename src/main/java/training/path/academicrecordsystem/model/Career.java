package training.path.academicrecordsystem.model;

public class Career {

    private long id;
    private String name;

    public Career() {
    }

    public Career(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Career {" + "id=" + id + ", name='" + name + '\'' + '}';
    }

}
