package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchController {

    @FXML private Button signInButton;
    @FXML private Button signOutButton;
    @FXML private TextField searchField;

    @FXML
    public void initialize()
    {

        if (AppState.isSignedIn())
        {

            signInButton.setText("👤");
            signInButton.setStyle("-fx-background-color: white; -fx-background-radius: 50%; -fx-padding: 5;");
            signOutButton.setVisible(true);

        } else {

            signInButton.setText("Sign In");
            signInButton.setStyle("-fx-background-color: white; -fx-text-fill: #c0392b; -fx-font-weight: bold; -fx-background-radius: 5;");
            signOutButton.setVisible(false);
        }
    }

    @FXML
    private void onSignInClick() {
        if (!AppState.isSignedIn()) {
            AppState.setSignedIn(true);

            signInButton.setText("👤");
            signInButton.setStyle("-fx-background-color: white; -fx-background-radius: 50%; -fx-padding: 5;");
            signOutButton.setVisible(true);
        }
    }

    @FXML
    private void onSignOutClick() {
        AppState.setSignedIn(false);

        signInButton.setText("Sign In");
        signInButton.setStyle("-fx-background-color: white; -fx-text-fill: #c0392b; -fx-font-weight: bold; -fx-background-radius: 5;");
        signOutButton.setVisible(false);
    }

    private void goToRestaurantList() throws IOException {
        Stage stage = (Stage) signInButton.getScene().getWindow();
        SceneNavigator.switchScene(stage, "home-view.fxml", "Restaurants");
    }

    @FXML
    private void onSearchClick() throws IOException
    {
        goToRestaurantList();
    }

    @FXML
    private void onBuffetClick() throws IOException
    {
        goToRestaurantList();
    }

    @FXML
    private void onCafeClick() throws IOException
    {
        goToRestaurantList();
    }

    @FXML
    private void onFineDiningClick() throws IOException
    {
        goToRestaurantList();
    }

    @FXML
    private void onBistroClick() throws IOException
    {
        goToRestaurantList();
    }
}
