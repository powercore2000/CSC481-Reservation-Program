package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import database.dto.ReservationDTO;

public class ReservationInfo {

    @FXML private Label infoLabel;
    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label restaurantLabel;
    @FXML private Label locationLabel;
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;
    @FXML private Label partyLabel;
    @FXML private Label foodlabel;

    ReservationDTO initalRes;
    
    @FXML
    public void initialize() {
    	
    	initalRes = backend.controllers.ReservationController.getCachedReservation();
        
    	setReservationInfo(
    			initalRes.getName(),
    			initalRes.getEmail(),
    			initalRes.getRestaurantName(),
    			initalRes.getRestaurantLocation(),
    			initalRes.getDate().toString(),
    			initalRes.getTime().toString(),
    			initalRes.getPartySize(),
    			""
    			);
      }
    
    private Stage getStage() {
        return (Stage) infoLabel.getScene().getWindow();
    }

    @FXML
    private void onBackClick() throws IOException {
        // back to "My Reservations"
        SceneNavigator.switchScene(getStage(), "my-reservations.fxml", "My Reservations");
    }

    @FXML
    private void onBackToHomeClick() throws IOException {
        // go all the way back to search
        SceneNavigator.switchScene(getStage(), "SelectResturantType.fxml", "Search Restaurants");
    }

    @FXML
    private void onViewMenuClick() throws IOException {
        // go to menu view for the restaurant
    	AppState.setBuyMode(true);
    	backend.controllers.RestaurantController.setCurrentRestaurantByReservation(initalRes);
        SceneNavigator.switchScene(getStage(), "menu-view.fxml", "Menu");
    }

    // optional: if you want to fill labels programmatically
    public void setReservationInfo(
            String name,
            String email,
            String restaurant,
            String location,
            String date,
            String time,
            int partySize,
            String preorder
    ) {
        nameLabel.setText("Name: " + name);
        emailLabel.setText("Email: " + email);
        restaurantLabel.setText("Restaurant: " + restaurant);
        locationLabel.setText("Location: " + location);
        dateLabel.setText("Date: " + date);
        timeLabel.setText("Time: " + time);
        partyLabel.setText("Party size: " + partySize);
        foodlabel.setText("Pre order: " + preorder);
    }
}
