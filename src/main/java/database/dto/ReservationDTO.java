package database.dto;

import java.time.LocalDate;
import java.util.UUID;

public class ReservationDTO {
	
	
	private String name;
    private String email;
    
    private String restaurantName;
    private String restaurantLocation;
    
    private int partySize;
    private LocalDate date;
    private String time;
    private String status;
    private String confirmationCode;

    public ReservationDTO(String name, String email, int partySize, LocalDate date, String time, String status, String confirmationCode) {
        this.name = name;
        this.email = email;
        this.partySize = partySize;
        this.date = date;
        this.time = time;
        this.confirmationCode = confirmationCode;
        setStatus(status);
    }
    
    public ReservationDTO(String name, String email, int partySize, LocalDate date, String time, String status) {
        this.name = name;
        this.email = email;
        this.partySize = partySize;
        this.date = date;
        this.time = time;
        confirmationCode = UUID.randomUUID().toString();
        setStatus(status);
    }
    
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getPartySize() { return partySize; }
    public LocalDate getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() {return status;}
    public String getConfirmationCode() {return confirmationCode;}
    
    public String getRestaurantName() {return restaurantName;}
    public String getRestaurantLocation() {return restaurantLocation;}
    
    public void setStatus(String status) {this.status = status;}
    public void setEmail(String email) {this.email = email;}
    public void setRestaurantName(String restaurantName) {this.restaurantName = restaurantName;}
    public void setRestaurantLocation(String restaurantLocation) {this.restaurantLocation = restaurantLocation;}

    @Override
    public String toString() {
        return name + " – " + date + " – " + time + " – Party " + partySize;
    }
    
    
}
