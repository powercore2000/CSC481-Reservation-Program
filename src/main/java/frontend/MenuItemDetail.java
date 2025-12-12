package frontend;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import java.io.IOException;


public class MenuItemDetail {


    @FXML private Label foodNameLabel;
    @FXML private Label categoryLabel;
    @FXML private Label priceLabel;
    @FXML private Label descriptionLabel;


    @FXML
    public void initialize() {
        int id = AppState.getSelectedMenuItem();


        switch (id) {
            case 1 -> {
                foodNameLabel.setText("Spicy Ramen");
                categoryLabel.setText("Category: Noodles");
                priceLabel.setText("Price: $12.99");
                descriptionLabel.setText("Rich spicy broth with tender noodles and toppings.");
            }
            case 2 -> {
                foodNameLabel.setText("California Roll");
                categoryLabel.setText("Category: Sushi");
                priceLabel.setText("Price: $9.50");
                descriptionLabel.setText("Crab, avocado, and cucumber rolled in seaweed and rice.");
            }
            case 3 -> {
                foodNameLabel.setText("Cheeseburger");
                categoryLabel.setText("Category: Grill");
                priceLabel.setText("Price: $11.25");
                descriptionLabel.setText("Juicy beef patty with cheese, lettuce, and tomato.");
            }
            case 4 -> {
                foodNameLabel.setText("Vegan Bowl");
                categoryLabel.setText("Category: Vegan");
                priceLabel.setText("Price: $10.75");
                descriptionLabel.setText("Mixed grains, roasted veggies, and house-made sauce.");
            }
            default -> {
                foodNameLabel.setText("Menu Item");
                categoryLabel.setText("Category: N/A");
                priceLabel.setText("Price: $0.00");
                descriptionLabel.setText("No description available.");
            }
        }
    }


    @FXML
    private void onBackToMenuClick(ActionEvent event) throws IOException {
        Stage stage = getStage(event);
        SceneNavigator.switchScene(stage, "menu-view.fxml", "Menu");
    }


    @FXML
    private void onAddToCartClick() {
        // Simple parse of price text: "Price: $12.99"
        String name = foodNameLabel.getText();
        String category = categoryLabel.getText().replace("Category: ", "").trim();
        String priceText = priceLabel.getText().replace("Price:", "").replace("$", "").trim();


        double price = 0.0;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException ignored) {
        }


        CartItem item = new CartItem(name, category, price);
        AppState.addToCart(item);


        System.out.println("Added to cart: " + name);
    }


    private Stage getStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
}

