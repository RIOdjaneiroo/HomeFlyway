package RIO.example.entities;
public class Client {
    private long id;
    private String name;

    public Client(String name) {
        validateName(name);
        this.name = name;
    }

    public Client(long id, String name) {
        validateName(name);
        this.id = id;
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
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name == null || name.length() < 2 || name.length() > 100) {
            throw new IllegalArgumentException("Name must be between 2 and 100 characters");
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
