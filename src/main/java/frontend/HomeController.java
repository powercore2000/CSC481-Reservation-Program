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
    public void initialize() {
        if (AppState.isSignedIn()) {
            signInButton.setText("👤");
            signInButton.setStyle("-fx-background-color: white; -fx-background-radius: 50%; -fx-padding: 5;");
            viewReservationButton.setVisible(true);
            signOutButton.setVisible(true);
        } else {
            viewReservationButton.setVisible(false);
            signOutButton.setVisible(false);
        }
    }

    @FXML
    protected void onSignInClick() {
        if (!AppState.isSignedIn()) {
            AppState.setSignedIn(true);
            signInButton.setText("👤");
            signInButton.setStyle("-fx-background-color: white; -fx-background-radius: 50%; -fx-padding: 5;");
            viewReservationButton.setVisible(true);
            signOutButton.setVisible(true);
        }
    }

    @FXML
    protected void onSignOutClick() {
        AppState.setSignedIn(false);
        signInButton.setText("Sign In");
        signInButton.setStyle("-fx-background-color: white; -fx-text-fill: #c0392b; -fx-font-weight: bold;");
        viewReservationButton.setVisible(false);
        signOutButton.setVisible(false);
    }

    private Stage getStage() {
        return (Stage) signInButton.getScene().getWindow();
    }

    // ======= 5 RESTAURANTS =======

    @FXML
    protected void onSelectRestaurant1() {
        AppState.setSelectedRestaurant(1);
        AppState.setSelectedRestaurantName("Andies");
        goToRestaurantPage();
    }

    @FXML
    protected void onSelectRestaurant2() {
        AppState.setSelectedRestaurant(2);
        AppState.setSelectedRestaurantName("Jay's Sushi Palace");
        goToRestaurantPage();
    }

    @FXML
    protected void onSelectRestaurant3() {
        AppState.setSelectedRestaurant(3);
        AppState.setSelectedRestaurantName("Mama Rosa's Italian Kitchen");
        goToRestaurantPage();
    }

    @FXML
    protected void onSelectRestaurant4() {
        AppState.setSelectedRestaurant(4);
        AppState.setSelectedRestaurantName("Golden Dragon BBQ");
        goToRestaurantPage();
    }

    @FXML
    protected void onSelectRestaurant5() {
        AppState.setSelectedRestaurant(5);
        AppState.setSelectedRestaurantName("The Garden Vegan Bistro");
        goToRestaurantPage();
    }

    private void goToRestaurantPage() {
        try {
            SceneNavigator.switchScene(
                    getStage(),
                    "/org/example/resturant/restaurant-view.fxml",
                    AppState.getSelectedRestaurantName()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ======= VIEW RESERVATIONS =======

    @FXML
    protected void onViewReservationClick() {
        try {
            SceneNavigator.switchScene(
                    getStage(),
                    "/org/example/resturant/my-reservations.fxml",
                    "My Reservations"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
