package org.example.resturant;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class RestaurantController
{

    @FXML private Label restaurantNameLabel;
    @FXML private Label addressLabel;
    @FXML private Label openCloseLabel;

    private String restaurantName;

    public void setRestaurant(String name)
    {
        restaurantName = name;
        restaurantNameLabel.setText(name);

        if (name.equals("Restaurant 1"))
        {
            addressLabel.setText("123 Oak St, Carson, CA");
            openCloseLabel.setText("Open • Closes at 10 PM");
        }
        else if (name.equals("Restaurant 2"))
        {
            addressLabel.setText("500 Main St, Carson, CA");
            openCloseLabel.setText("Open • Closes at 11 PM");
        }
    }

    @FXML
    private void onBackClick() throws IOException
    {
        Stage stage = (Stage) restaurantNameLabel.getScene().getWindow();
        SceneNavigator.switchScene(stage,
                "/org/example/resturant/home-view.fxml",
                "Restaurants");
    }

    @FXML
    private void onMakeReservationClick() throws IOException
    {
        Stage stage = (Stage) restaurantNameLabel.getScene().getWindow();
        SceneNavigator.switchScene(stage,
                "/org/example/resturant/make-reservation.fxml",
                "Make Reservation");
    }
}
