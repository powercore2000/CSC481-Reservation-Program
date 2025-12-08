package database.dto;

public class ReservationFoodDTO {

    private long foodId;
    private String name;
    private String description;
    private int priceCents;
    private String category;
    private int quantity;

    public ReservationFoodDTO(long foodId,
                              String name,
                              String description,
                              int priceCents,
                              String category,
                              int quantity) {
        this.foodId = foodId;
        this.name = name;
        this.description = description;
        this.priceCents = priceCents;
        this.category = category;
        this.quantity = quantity;
    }

    public long getFoodId()       { return foodId; }
    public String getName()       { return name; }
    public String getDescription(){ return description; }
    public int getPriceCents()    { return priceCents; }
    public String getCategory()   { return category; }
    public int getQuantity()      { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return quantity + " x " + name + " ($" + (priceCents / 100.0) + ")";
    }
}