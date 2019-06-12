package Users;

public class User {
    private String name;
    private Integer id;
    public User(String name, Integer id){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
