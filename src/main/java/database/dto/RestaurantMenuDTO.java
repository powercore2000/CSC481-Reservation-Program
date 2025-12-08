package database.dto;

public class RestaurantMenuDTO {

    private Long restaurantMenuId;  // PK
    private Long restaurantId;      // FK → Restaurant

    public RestaurantMenuDTO() {
        // Required for Jackson
    }

    public Long getRestaurantMenuId() {
        return restaurantMenuId;
    }

    public void setRestaurantMenuId(Long restaurantMenuId) {
        this.restaurantMenuId = restaurantMenuId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "RestaurantMenuDTO{" +
                "restaurantMenuId=" + restaurantMenuId +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
