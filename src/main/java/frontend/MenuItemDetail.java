package frontend;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.control.Button;
import database.dto.FoodDTO;




import java.io.IOException;


public class MenuItemDetail {


    @FXML private Label foodNameLabel;
    @FXML private Label categoryLabel;
    @FXML private Label priceLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label addedLabel;
    @FXML private Button addToCartButton;



    @FXML
    public void initialize() {
        FoodDTO f = backend.controllers.RestaurantController.getSelectedFood();

        if (f == null) {
            foodNameLabel.setText("Menu Item");
            categoryLabel.setText("Category: N/A");
            priceLabel.setText("Price: $0.00");
            descriptionLabel.setText("No description available.");
            return;
        }

        foodNameLabel.setText(f.getName());

        String cat = (f.getCategory() == null || f.getCategory().isBlank()) ? "N/A" : f.getCategory();
        categoryLabel.setText("Category: " + cat);

        double price = f.getPriceCents() / 100.0;
        priceLabel.setText(String.format("Price: $%.2f", price));

        String desc = (f.getDescription() == null || f.getDescription().isBlank())
                ? "No description available."
                : f.getDescription();
        descriptionLabel.setText(desc);
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

        String oldText = addToCartButton.getText();
        addToCartButton.setText("Added!");
        addToCartButton.setDisable(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(e -> {
            addedLabel.setVisible(false);
            addToCartButton.setText(oldText);
            addToCartButton.setDisable(false);
        });
        pause.play();
        System.out.println("Added to cart: " + name);
    }


    private Stage getStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
}

