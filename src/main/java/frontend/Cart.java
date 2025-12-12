package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

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
        for (CartItem item : AppState.getCartItems()) {
            String line = item.getFoodName() + " (" + item.getCategory() + ") - $" + item.getPrice();
            cartListView.getItems().add(line);
            total += item.getPrice();
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
