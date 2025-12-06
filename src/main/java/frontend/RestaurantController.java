package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class RestaurantController {
    @FXML private Label restaurantNameLabel;
    @FXML private Label addressLabel;
    @FXML private Label openCloseLabel;

    @FXML
    protected void initialize() {
    	restaurantNameLabel.setText("Restaurant 1");
        addressLabel.setText("123 Oak St, Carson, CA");
        openCloseLabel.setText("Open • Closes at 10 PM");
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
