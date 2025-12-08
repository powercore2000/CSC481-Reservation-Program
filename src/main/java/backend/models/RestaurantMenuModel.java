package backend.models;

public class RestaurantMenuModel {

    private Long restaurantMenuId;  // PK
    private Long restaurantId;      // FK → Restaurant

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
        return "RestaurantMenuModel{" +
                "restaurantMenuId=" + restaurantMenuId +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
