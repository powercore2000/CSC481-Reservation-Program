package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class MyReservationsController
{

    @FXML private ListView<String> reservationList;

    @FXML
    public void initialize()
    {
        reservationList.getItems().addAll
                (
                "John Smith – Dec 10 – 7PM – Party 4",
                "Mary Johnson – Dec 12 – 6PM – Party 2"
        );
    }

    @FXML
    private void onBackClick() throws IOException
    {
        Stage stage = (Stage) reservationList.getScene().getWindow();
        SceneNavigator.switchScene(stage, "/frontend/home-view.fxml", "Restaurants");
    }

    @FXML
    private void onViewReservationClick() throws IOException
    {
        String selected = reservationList.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        Stage stage = (Stage) reservationList.getScene().getWindow();
        SceneNavigator.switchScene(stage, "/frontend/reservation-info.fxml", "Reservation Info");
    }
}
