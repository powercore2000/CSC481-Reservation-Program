package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ReservationInfoController
{

    @FXML private Label infoLabel;

    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label restaurantLabel;
    @FXML private Label locationLabel;
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;
    @FXML private Label partyLabel;

    // Called to set the reservation details
    public void setReservationDetails(String name, String email, String restaurant,
                                      String location, String date, String time, String party) {

        infoLabel.setText("Reservation Details");

        nameLabel.setText("Name: " + name);
        emailLabel.setText("Email: " + email);
        restaurantLabel.setText("Restaurant: " + restaurant);
        locationLabel.setText("Location: " + location);
        dateLabel.setText("Date: " + date);
        timeLabel.setText("Time: " + time);
        partyLabel.setText("Party size: " + party);
    }

    @FXML
    private void onBackClick() throws IOException
    {
        Stage stage = (Stage) infoLabel.getScene().getWindow();
        SceneNavigator.switchScene(stage, "/frontend/my-reservations.fxml", "My Reservations");
    }

    @FXML
    private void onBackToHomeClick() throws IOException
    {
        Stage stage = (Stage) infoLabel.getScene().getWindow();
        SceneNavigator.switchScene(stage, "/frontend/home-view.fxml", "Home");
    }
}
