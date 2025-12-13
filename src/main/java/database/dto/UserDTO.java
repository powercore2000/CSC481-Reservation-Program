package database.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {

    private String name;//full_name
    private Long userId;
    private String email;
    private String phoneNumber; //phone
    private String passwordString; //password_hash
    
    
    /**
     * Creates a UserDTO client model 
     *
     * @param first and last name of user
     * @param email of user
     * @param phoneNumber of user
     * @param plainText string password of user
     *\
     
     /**
     * For creating a UserDTO on the client used to login 
     *
     * @param email of user
     * @param plainText string password of user
     * @return UserDTO new DTO model instance
     */
     
    public UserDTO(String name, String email, String phoneNumber, String passwordString) {
    	
    	this.name = name;
    	this.email = email;
    	this.phoneNumber = phoneNumber;
    	this.passwordString = passwordString;
    }
    
    @JsonCreator
    public UserDTO(
    	 @JsonProperty("userId") long id, 
    	 @JsonProperty("name") String name, 
    	 @JsonProperty("email") String email, 
    	 @JsonProperty("phoneNumber") String phoneNumber, 
    	 @JsonProperty("passwordString") String passwordString) {
    	
    	this.userId = id;
    	this.name = name;
    	this.email = email;
    	this.phoneNumber = phoneNumber;
    	this.passwordString = passwordString;
    }
    
    public UserDTO(String email, String passwordString) {
    	
    	this.email = email;
    	this.passwordString = passwordString;
    }
    
    public UserDTO() {}
    
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public void setUserId(Long userId) {this.userId = userId;}
    public Long getUserId() {return userId;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public String getPasswordString() {return passwordString;}
    public void setPasswordString(String passwordHash) {this.passwordString = passwordHash;}
}
