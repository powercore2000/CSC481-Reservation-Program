package database.dto;

public class RestaurantScheduleDTO {

    private int weekday;      // 0 = Monday ... 6 = Sunday
    private String openTime;  // "HH:MM:SS"
    private String closeTime; // "HH:MM:SS"

    public RestaurantScheduleDTO(int weekday,
                                 String openTime,
                                 String closeTime) {
        this.weekday = weekday;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public int getWeekday()      { return weekday; }
    public String getOpenTime()  { return openTime; }
    public String getCloseTime() { return closeTime; }

    @Override
    public String toString() {
        return "Day " + weekday + ": " + openTime + " - " + closeTime;
    }
}