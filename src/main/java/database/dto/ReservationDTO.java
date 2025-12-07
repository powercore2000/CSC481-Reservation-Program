package database.dto;

import java.time.LocalDate;

public class ReservationDTO {
	
	
	private String name;
    private String email;
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
        setStatus(status);
    }
    
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getPartySize() { return partySize; }
    public LocalDate getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() {return status;}
    public String getConfirmationCode() {return confirmationCode;}
    
    public void setStatus(String status) {this.status = status;}

    @Override
    public String toString() {
        return name + " – " + date + " – " + time + " – Party " + partySize;
    }
    
    
}
