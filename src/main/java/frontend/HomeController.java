package org.example.resturant;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeController {

    @FXML private Button signInButton;
    @FXML private Button signOutButton;
    @FXML private Button viewReservationButton;

    @FXML
    public void initialize()
    {
        if (AppState.isSignedIn())
        {
            signInButton.setText("👤");
            signInButton.setStyle("-fx-background-color: white; -fx-background-radius: 50%; -fx-padding: 5;");
            viewReservationButton.setVisible(true);
            signOutButton.setVisible(true);
        }
    }

    @FXML
    protected void onSignInClick()
    {
        if (!AppState.isSignedIn())
        {
            AppState.setSignedIn(true);

            // Change Sign In to profile icon
            signInButton.setText("👤");
            signInButton.setStyle("-fx-background-color: white; -fx-background-radius: 50%; -fx-padding: 5;");

            // Show Sign Out + View Reservation
            viewReservationButton.setVisible(true);
            signOutButton.setVisible(true);
        }
    }

    @FXML
    protected void onSignOutClick() {
        AppState.setSignedIn(false);

        // Reset UI back to logged-out mode
        signInButton.setText("Sign In");
        signInButton.setStyle("-fx-background-color: white; -fx-text-fill: #c0392b; -fx-font-weight: bold;");

        viewReservationButton.setVisible(false);
        signOutButton.setVisible(false);
    }

    @FXML
    protected void onSelectRestaurant1(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) signInButton.getScene().getWindow();
        SceneNavigator.switchScene(stage, "restaurant-view.fxml", "Restaurant 1");
    }

    @FXML
    protected void onSelectRestaurant2(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) signInButton.getScene().getWindow();
        SceneNavigator.switchScene(stage, "restaurant-view.fxml", "Restaurant 2");
    }

    @FXML
    protected void onViewReservationClick() throws IOException {
        Stage stage = (Stage) viewReservationButton.getScene().getWindow();
        SceneNavigator.switchScene(stage, "my-reservations.fxml", "My Reservations");
    }
}
