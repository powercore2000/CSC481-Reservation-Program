package database.dto;

public class RestaurantTagDTO {

    private long id;
    private long restaurantId;
    private String tagName;

    public RestaurantTagDTO(long id,
                            long restaurantId,
                            String tagName) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.tagName = tagName;
    }

    public long getId()            { return id; }
    public long getRestaurantId()  { return restaurantId; }
    public String getTagName()     { return tagName; }

    public void setTagName(String tagName) { this.tagName = tagName; }

    @Override
    public String toString() {
        return tagName;
    }
}