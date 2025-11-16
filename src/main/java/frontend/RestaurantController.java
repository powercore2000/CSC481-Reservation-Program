package main.java.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class RestaurantController {
    @FXML private Label restaurantName;
    @FXML private Label restaurantAddress;

    @FXML
    protected void initialize() {
        // Example: could be dynamic if you pass data later
        restaurantName.setText("Restaurant 1");
        restaurantAddress.setText("123 Oak St, Carson, CA\nOpen • Closes at 10 PM");
    }

    @FXML
    protected void onMakeReservationClick(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "make-reservation.fxml", "Make Reservation");
    }

    @FXML
    protected void onBackClick(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "home-view.fxml", "Home");
    }

    @FXML
    protected void onHomeClick(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "home-view.fxml", "Home");
    }


}
