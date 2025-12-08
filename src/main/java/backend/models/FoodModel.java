package backend.models;

public class FoodModel {

    private Long foodId;        // food_id (PK, BIGINT)
    private String name;        // name (NN, VARCHAR(120))
    private String description; // description (TEXT)
    private Integer priceCents; // price_cents (NN, INT)
    private String category;    // category (VARCHAR(64))

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriceCents() {
        return priceCents;
    }

    public void setPriceCents(Integer priceCents) {
        this.priceCents = priceCents;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "FoodModel{" +
                "foodId=" + foodId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priceCents=" + priceCents +
                ", category='" + category + '\'' +
                '}';
    }
}
