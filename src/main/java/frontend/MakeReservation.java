package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

import com.sun.javafx.scene.control.IntegerField;

import database.dto.ReservationDTO;


public class MakeReservation
{

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private IntegerField partyField;

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
                "/frontend/resturantlists.fxml",
                "Restaurants");
    }

    @FXML
    private void onCreateReservationClick() throws IOException
    {
    	ReservationDTO reservation = new ReservationDTO(
    			"DO NOT FILL",
    			"DO NOT FILL", // dont replace these placeholder values, they will be set in the backend
    			partyField.getValue(), 
    			datePicker.getValue(), 
    			timeField.getText(), 
    			"PENDING"
    			);
    	Boolean makeReservation = backend.controllers.ReservationController.createReservation(reservation).getBody();
    	System.out.println("Resrvation made: " + makeReservation);
    	if(!makeReservation) {
    		System.out.println("Error in making a reservation!");
    		return;
    	}

    		System.out.println("Succeeded in making a reservation!");
    	
        Stage stage = (Stage) nameField.getScene().getWindow();
        SceneNavigator.switchScene(stage,
                "/frontend/reservation-info.fxml",
                "Reservation Info");
    }
}
