package backend.models;

public class User {

    private String name;//full_name
    private Long userId;
    private String email;
    private String phoneNumber; //phone
    private String passwordHash; //password_hash
    
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public void setUserId(Long userId) {this.userId = userId;}
    public Long getUserId() {return userId;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public String getPasswordHash() {return passwordHash;}
    public void setPasswordHash(String passwordHash) {this.passwordHash = passwordHash;}
}
