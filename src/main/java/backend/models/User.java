package backend.models;

public class User {

    private String name; //full_name
    private String email;
    private String phoneNumber; //phone
    private String passwordHash; //password_hash
    
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}
