package org.example.resturant;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class RestaurantController {

    @FXML private Label restaurantNameLabel;
    @FXML private Label addressLabel;
    @FXML private Label openCloseLabel;

    @FXML
    private void initialize() {
        int id = AppState.getSelectedRestaurant();
        String name = AppState.getSelectedRestaurantName();

        if (name == null || name.isEmpty()) {
            name = "Restaurant";
        }

        restaurantNameLabel.setText(name);

        switch (id) {
            case 1 -> {
                addressLabel.setText("123 Oak St, Carson, CA");
                openCloseLabel.setText("Open • Closes at 10 PM");
            }
            case 2 -> {
                addressLabel.setText("500 Sushi Ave, Torrance, CA");
                openCloseLabel.setText("Open • Closes at 11 PM");
            }
            case 3 -> {
                addressLabel.setText("77 Pasta Lane, Gardena, CA");
                openCloseLabel.setText("Open • Closes at 9 PM");
            }
            case 4 -> {
                addressLabel.setText("88 Dragon Rd, Carson, CA");
                openCloseLabel.setText("Open • Closes at 12 AM");
            }
            case 5 -> {
                addressLabel.setText("245 Greenview Blvd, Carson, CA");
                openCloseLabel.setText("Open • Closes at 8 PM");
            }
            default -> {
                addressLabel.setText("Address unavailable");
                openCloseLabel.setText("Hours unavailable");
            }
        }
    }

    @FXML
    private void onBackClick() {
        Stage stage = (Stage) restaurantNameLabel.getScene().getWindow();
        try {
            SceneNavigator.switchScene(
                    stage,
                    "/org/example/resturant/home-view.fxml",
                    "Restaurants"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onMakeReservationClick() {
        Stage stage = (Stage) restaurantNameLabel.getScene().getWindow();
        try {
            SceneNavigator.switchScene(
                    stage,
                    "/org/example/resturant/make-reservation.fxml",
                    "Make Reservation"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
