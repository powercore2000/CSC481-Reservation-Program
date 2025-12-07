package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;


import java.time.LocalDate;
import java.util.UUID;

import database.dto.*;



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
                "/frontend/restaurant-view.fxml",
                "Restaurant Info");
    }

    @FXML
    private void onHomeClick() throws IOException
    {
        Stage stage = (Stage) nameField.getScene().getWindow();
        SceneNavigator.switchScene(stage,
                "/frontend/home-view.fxml",
                "Restaurants");
    }

    @FXML
    private void onCreateReservationClick() throws IOException
    {
    	String name = nameField.getText();
        String email = emailField.getText();
        LocalDate date = datePicker.getValue();
        String time = timeField.getText();
        int partySize = Integer.parseInt(partyField.getText());
        UUID uuid = UUID.randomUUID();
        
        ReservationDTO reservation = new ReservationDTO(name, email, partySize, date, time, "Reserved", uuid.toString());
        backend.controllers.ReservationController.CreateReservation(reservation);

       
        
        
    	
        Stage stage = (Stage) nameField.getScene().getWindow();
        SceneNavigator.switchScene(stage,
                "/frontend/reservation-info.fxml",
                "Reservation Info");
    }
}
