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
    			nameField.getText(), 
    			emailField.getText(), 
    			partyField.getValue(), 
    			datePicker.getValue(), 
    			timeField.getText(), 
    			"PENDING"
    			);
    	Boolean makeReservation = backend.controllers.ReservationController.CreateReservation(reservation);
    	
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
