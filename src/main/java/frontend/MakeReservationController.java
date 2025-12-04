package org.example.resturant;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class MakeReservationController
{

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private TextField partyField;

    @FXML
    private void onBackClick() throws IOException
    {
        Stage stage = (Stage) nameField.getScene().getWindow();
        SceneNavigator.switchScene(stage,
                "/org/example/resturant/restaurant-view.fxml",
                "Restaurant Info");
    }

    @FXML
    private void onHomeClick() throws IOException
    {
        Stage stage = (Stage) nameField.getScene().getWindow();
        SceneNavigator.switchScene(stage,
                "/org/example/resturant/home-view.fxml",
                "Restaurants");
    }

    @FXML
    private void onCreateReservationClick() throws IOException
    {
        Stage stage = (Stage) nameField.getScene().getWindow();
        SceneNavigator.switchScene(stage,
                "/org/example/resturant/reservation-info.fxml",
                "Reservation Info");
    }
}
