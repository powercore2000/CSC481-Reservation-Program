package backend.models;

import java.time.LocalDate;

public class Reservation {
	
	private String name;
    private String email;
    private int partySize;
    private LocalDate date;
    private String time;

    public Reservation(String name, String email, int partySize, LocalDate date, String time) {
        this.name = name;
        this.email = email;
        this.partySize = partySize;
        this.date = date;
        this.time = time;
    }
    
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getPartySize() { return partySize; }
    public LocalDate getDate() { return date; }
    public String getTime() { return time; }

    @Override
    public String toString() {
        return name + " – " + date + " – " + time + " – Party " + partySize;
    }
    
}
