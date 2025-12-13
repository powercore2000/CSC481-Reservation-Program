package database.dto;

import java.time.LocalDate;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationDTO {

    // --- NEW FIELDS (Required for Logic) ---
    private Long id;              // PK from DB
    private Long userId;          // FK to User
    private Long restaurantId;    // FK to Restaurant

    // Key = Food ID, Value = Quantity (e.g., {1=2, 5=1} -> 2 Burgers, 1 Soda)
    private List<FoodDTO> foodSelections = new ArrayList<FoodDTO>();

    // --- EXISTING FIELDS ---
    private String name;
    private String email;
    private String restaurantName;
    private String restaurantLocation;
    private int partySize;
    private LocalDate date;
    private String time;
    private String status;
    private String confirmationCode;

    // --- CONSTRUCTORS ---
    public ReservationDTO() {} // Default constructor (helpful for JSON parsing)

    public ReservationDTO(String name, String email, int partySize, LocalDate date, String time, String status, String confirmationCode) {
        this.name = name;
        this.email = email;
        this.partySize = partySize;
        this.date = date;
        this.time = time;
        this.confirmationCode = confirmationCode;
        setStatus(status);
    }

    // Constructor for creating new reservations
    public ReservationDTO(String name, String email, int partySize, LocalDate date, String time, String status) {
        this.name = name;
        this.email = email;
        this.partySize = partySize;
        this.date = date;
        this.time = time;
        this.confirmationCode = UUID.randomUUID().toString();
        setStatus(status);
    }

    
    @JsonCreator
    public ReservationDTO(
    	 @JsonProperty("name") String name, 
    	 @JsonProperty("email") String email, 
    	 @JsonProperty("partySize") int partySize, 
    	 @JsonProperty("date") LocalDate date, 
    	 @JsonProperty("time") String time,
    	 @JsonProperty("status") String status,
    	 @JsonProperty("confirmationCode") String confirmationCode,
    	 @JsonProperty("id") Long id)
    
    	{
    	
    	this.name = name;
        this.email = email;
        this.partySize = partySize;
        this.date = date;
        this.time = time;
        this.confirmationCode = confirmationCode;
        this.id = id;
        setStatus(status);
    }

    // --- GETTERS & SETTERS (Add these for the new fields) ---

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }

    //public Map<Long, FoodDTO> getFoodSelections() { return foodSelections; }
    //public void setFoodSelections(Map<Long, FoodDTO> foodSelections) { this.foodSelections = foodSelections; }
    
    public List<FoodDTO> getFoodSelections() { return foodSelections; }
    public void setFoodSelections(List<FoodDTO> foodSelections) { this.foodSelections = foodSelections; }


    // --- EXISTING GETTERS & SETTERS ---
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getPartySize() { return partySize; }
    public LocalDate getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() { return status; }
    public String getConfirmationCode() { return confirmationCode; }

    public String getRestaurantName() { return restaurantName; }
    public String getRestaurantLocation() { return restaurantLocation; }

    public void setStatus(String status) { this.status = status; }
    public void setEmail(String email) { this.email = email; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }
    public void setRestaurantLocation(String restaurantLocation) { this.restaurantLocation = restaurantLocation; }

    @Override
    public String toString() {
        return name + " – " + date + " – " + time + " – Party " + partySize +
                " (Food Items: " + (foodSelections == null ? 0 : foodSelections.size()) + ")";
    }
}