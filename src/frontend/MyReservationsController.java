package org.example.resturant;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class MyReservationsController {
    @FXML
    protected void onViewReservationClick(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "reservation-info.fxml", "Reservation Info");
    }
}
