package frontend;

import frontend.clients.RestaurantApiClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class SelectResturantType
{

    @FXML private Button signInButton;
    @FXML private Button signOutButton;
    @FXML private TextField searchField;

    
     @FXML
    public void initialize() {
        if (AppState.isSignedIn()) {
            // Already signed in → show profile icon + sign out
            signInButton.setText("👤");
            signInButton.setStyle("-fx-background-color: white; -fx-background-radius: 50%; -fx-padding: 5;");
            signOutButton.setVisible(true);
        } else {
            // Not signed in → normal "Sign In" button
            signInButton.setText("Sign In");
            signInButton.setStyle("-fx-background-color: white; -fx-text-fill: #c0392b; -fx-font-weight: bold; -fx-background-radius: 5;");
            signOutButton.setVisible(false);
        }
    }

    @FXML
    private void onSignInClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "login-view.fxml", "Sign In");
    }

    private Stage getStage() {
        return (Stage) signInButton.getScene().getWindow();
    }

    @FXML
    private void onSignOutClick() {
        AppState.setSignedIn(false);

        signInButton.setText("Sign In");
        signInButton.setStyle("-fx-background-color: white; -fx-text-fill: #c0392b; -fx-font-weight: bold; -fx-background-radius: 5;");
        signOutButton.setVisible(false);
    }

    // ====== NAVIGATION TO RESTAURANT LIST (HOME) ======

 // ====== NAVIGATION TO RESTAURANT LIST (HOME) ======

    private void goToRestaurantList() throws IOException {
        Stage stage = getStage();
        SceneNavigator.switchScene(stage, "resturantlists.fxml", "Restaurants");
    }

    @FXML
    private void onSearchClick() throws IOException {
        String tag = searchField.getText();

        if (tag != null && !tag.trim().isEmpty()) {
            RestaurantApiClient.setCurrentTagFilter(tag.trim());
        } else {
            // empty search → no filter, show all restaurants
            RestaurantApiClient.setCurrentTagFilter(null);
        }

        goToRestaurantList();
    }

    @FXML
    private void onAllClick() throws IOException {
        RestaurantApiClient.setCurrentTagFilter(null); // show all
        goToRestaurantList();
    }

    @FXML
    private void onBuffetClick() throws IOException {
        RestaurantApiClient.setCurrentTagFilter("Buffet");
        goToRestaurantList();
    }

    @FXML
    private void onCafeClick() throws IOException {
        RestaurantApiClient.setCurrentTagFilter("Cafe");
        goToRestaurantList();
    }

    @FXML
    private void onFineDiningClick() throws IOException {
        RestaurantApiClient.setCurrentTagFilter("Fine Dining");
        goToRestaurantList();
    }

    @FXML
    private void onBistroClick() throws IOException {
        RestaurantApiClient.setCurrentTagFilter("Bistro");
        goToRestaurantList();
    }
}
