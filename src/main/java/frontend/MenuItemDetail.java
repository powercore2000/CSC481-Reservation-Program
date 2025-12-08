package frontend;

import database.dto.FoodDTO;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuItemDetail
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
    private Button addToCartButton;

    @FXML
    public void initialize()
    {
        int id = AppState.getSelectedMenuItem();

        FoodDTO food = backend.controllers.RestaurantController.getSelectedFood();
        
        foodNameLabel.setText(food.getName());
        categoryLabel.setText("Category: "+ food.getCategory());
        priceLabel.setText("Price: $"+food.getPriceCents());
        descriptionLabel.setText("Description: "+food.getDescription());
        
        addToCartButton.setDisable(!AppState.getBuyMode());
        	
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
