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
    protected void onCreateReservationClick() throws IOException
    {
        Stage stage = (Stage) nameField.getScene().getWindow();
        SceneNavigator.switchScene(stage, "reservation-info.fxml", "Reservation Info");
    }
}
