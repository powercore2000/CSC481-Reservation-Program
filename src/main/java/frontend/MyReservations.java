package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import backend.controllers.ReservationController;
import database.dto.ReservationDTO;

public class MyReservations
{

    @FXML private ListView<String> reservationList;
    ArrayList<ReservationDTO> allReservations;
    @FXML
    public void initialize()
    {
    	System.out.println("Printing all reservations");
    	allReservations = ReservationController.currentUserReservations();
    	
    	for(ReservationDTO res : allReservations) {
    		
    		reservationList.getItems().add(res.toString());
    	}
    	
    	/*
        reservationList.getItems().addAll
                (
                "John Smith – Dec 10 – 7PM – Party 4",
                "Mary Johnson – Dec 12 – 6PM – Party 2"
        );*/
    }

    @FXML
    private void onBackClick() throws IOException
    {
        Stage stage = (Stage) reservationList.getScene().getWindow();
        SceneNavigator.switchScene(stage, "/frontend/resturantlists.fxml", "Restaurants");
    }

    @FXML
    private void onViewReservationClick() throws IOException
    {
        String selected = reservationList.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        for(ReservationDTO res : allReservations) {
    		
    		if(selected.equals(res.toString()))
    			backend.controllers.ReservationController.setCachedReservation(res);
    	}
    	
        
        Stage stage = (Stage) reservationList.getScene().getWindow();
        SceneNavigator.switchScene(stage, "/frontend/reservation-info.fxml", "Reservation Info");
    }
}
