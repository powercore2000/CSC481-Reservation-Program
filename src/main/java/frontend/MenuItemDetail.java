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

import database.dto.FoodDTO;


public class MenuItemDetail {


    @FXML private Label foodNameLabel;
    @FXML private Label categoryLabel;
    @FXML private Label priceLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label addedLabel;
    @FXML private Button addToCartButton;


    FoodDTO food;
    @FXML
    public void initialize() {

        food = AppState.getSelectedFoodItem();

        foodNameLabel.setText(food.getName());
        categoryLabel.setText(food.getCategory());
        priceLabel.setText("Price: $" + food.getPriceCents());
        descriptionLabel.setText(food.getDescription());

    }



    @FXML
    private void onBackToMenuClick(ActionEvent event) throws IOException {
        Stage stage = getStage(event);
        SceneNavigator.switchScene(stage, "menu-view.fxml", "Menu");
    }


    @FXML
    private void onAddToCartClick() {
        // Simple parse of price text: "Price: $12.99"
        String name = food.getName();
        String category = food.getCategory();
        int price = food.getPriceCents();


        double newPrice = 0.0;
        try {
        	newPrice = (double)price / 100;
        } catch (NumberFormatException ignored) {
        }


        AppState.addToCart(food);
        

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
