package database.dto;

public class RestaurantTagDTO {

    private Long id;
    private Long restaurantId;
    private String tagName;

    // Full constructor
    public RestaurantTagDTO(Long id, Long restaurantId, String tagName) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.tagName = tagName;
    }

    // Constructor for creating new tags (no id yet)
    public RestaurantTagDTO(Long restaurantId, String tagName) {
        this(null, restaurantId, tagName);
    }

    public Long getId() {
        return id;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public String getTagName() {
        return tagName;
    }

    @Override
    public String toString() {
        return "RestaurantTagDTO{" +
                "id=" + id +
                ", restaurantId=" + restaurantId +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
