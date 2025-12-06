package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import backend.models.Reservation;
import java.time.LocalDate;


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
        
        Reservation reservation = new Reservation(name, email, partySize, date, time);
        System.out.println("Created reservation: " + reservation);
    	
        Stage stage = (Stage) nameField.getScene().getWindow();
        SceneNavigator.switchScene(stage,
                "/frontend/reservation-info.fxml",
                "Reservation Info");
    }
}
