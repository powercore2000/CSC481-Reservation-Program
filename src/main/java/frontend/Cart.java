package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import database.dto.FoodDTO;

public class Cart {

    @FXML private ListView<String> cartListView;
    @FXML private Label totalLabel;

    @FXML
    public void initialize() {
        refreshCart();
    }

    private Stage getStage() {
        return (Stage) cartListView.getScene().getWindow();
    }

    private void refreshCart() {
        cartListView.getItems().clear();

        double total = 0.0;
        List<FoodDTO> foodItems = backend.controllers.ReservationController.getAllFoodFromCurrentReservation();
        
        for (FoodDTO item : foodItems) {
        	System.out.println("Found:"+item.toString());
            String line = item.toString();
            cartListView.getItems().add(line);
            total += (double)item.getPriceCents()/100;
        }

        totalLabel.setText(String.format("Total: $%.2f", total));
    }

    @FXML
    private void onBackToMenuClick() throws IOException {
        SceneNavigator.switchScene(getStage(), "menu-view.fxml", "Menu");
    }

    @FXML
    private void onClearCartClick() {
        AppState.clearCart();
        refreshCart();
    }

    @FXML
    private void onCheckoutClick() {
        totalLabel.setText(totalLabel.getText() + "   (Checked out ✅)");
    }
}
