package frontend;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.io.IOException;


public class MenuController {


    @FXML private Label menuTitleLabel;
    @FXML private Button cartButton;


    @FXML
    public void initialize() {
        // Set the menu title using the selected restaurant name
        String name = AppState.getSelectedRestaurantName();
        if (name == null || name.isBlank()) {
            menuTitleLabel.setText("Menu");
        } else {
            menuTitleLabel.setText(name + "'s Menu");
        }


        // Show the Cart button only if there are items in the cart
        cartButton.setVisible(AppState.hasCartItems());
    }


    // Back to restaurant page
    @FXML
    private void onBackClick(ActionEvent event) throws IOException {
        Stage stage = getStageFrom(event);
        SceneNavigator.switchScene(stage, "restaurant-view.fxml", "Restaurant");
    }


    // Open Cart page
    @FXML
    private void onCartClick(ActionEvent event) throws IOException {
        Stage stage = getStageFrom(event);
        SceneNavigator.switchScene(stage, "cart-view.fxml", "Cart");
    }


    // View more for item 1–4
    @FXML
    private void onViewMore1(ActionEvent event) throws IOException {
        AppState.setSelectedMenuItem(1);
        goToDetail(event);
    }


    @FXML
    private void onViewMore2(ActionEvent event) throws IOException {
        AppState.setSelectedMenuItem(2);
        goToDetail(event);
    }


    @FXML
    private void onViewMore3(ActionEvent event) throws IOException {
        AppState.setSelectedMenuItem(3);
        goToDetail(event);
    }


    @FXML
    private void onViewMore4(ActionEvent event) throws IOException {
        AppState.setSelectedMenuItem(4);
        goToDetail(event);
    }


    private void goToDetail(ActionEvent event) throws IOException {
        Stage stage = getStageFrom(event);
        SceneNavigator.switchScene(stage, "menu-item-detail.fxml", "Item Details");
    }


    private Stage getStageFrom(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
}

