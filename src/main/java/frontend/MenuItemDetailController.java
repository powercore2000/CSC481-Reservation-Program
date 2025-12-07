package frontend;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuItemDetailController
{

    @FXML
    private Label foodNameLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    public void initialize()
    {
        int id = AppState.getSelectedMenuItem();

        switch (id) {
            case 1 -> {
                foodNameLabel.setText("Spicy Ramen");
                categoryLabel.setText("Category: Noodles");
                priceLabel.setText("Price: $12.99");
                descriptionLabel.setText("Description: Rich spicy broth with tender noodles and toppings.");
            }
            case 2 -> {
                foodNameLabel.setText("California Roll");
                categoryLabel.setText("Category: Sushi");
                priceLabel.setText("Price: $9.50");
                descriptionLabel.setText("Description: Crab, avocado, and cucumber rolled in seaweed and rice.");
            }
            case 3 -> {
                foodNameLabel.setText("Cheeseburger");
                categoryLabel.setText("Category: Grill");
                priceLabel.setText("Price: $11.25");
                descriptionLabel.setText("Description: Juicy beef patty with cheese, lettuce, and tomato.");
            }
            case 4 -> {
                foodNameLabel.setText("Vegan Bowl");
                categoryLabel.setText("Category: Vegan");
                priceLabel.setText("Price: $10.75");
                descriptionLabel.setText("Description: Mixed grains, roasted veggies, and house-made sauce.");
            }
            default -> {
                foodNameLabel.setText("Menu Item");
                categoryLabel.setText("Category: N/A");
                priceLabel.setText("Price: $0.00");
                descriptionLabel.setText("Description: No description available.");
            }
        }
    }

    @FXML
    private void onBackToMenuClick(javafx.event.ActionEvent event) throws Exception
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "menu-view.fxml", "Menu");
    }

    @FXML
    private void onAddToCartClick()
    {
        System.out.println("Item added to cart: " + foodNameLabel.getText());
    }
}
