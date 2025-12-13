package frontend;

public class CartItem {

    private final String foodName;
    private final String category;
    private final double price;

    public CartItem(String foodName, String category, double price) {
        this.foodName = foodName;
        this.category = category;
        this.price = price;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }
}
