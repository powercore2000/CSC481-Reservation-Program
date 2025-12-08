package frontend;

import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;

import database.dto.FoodDTO;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class FoodMenuManager {

	
	public void initialize() {
		
		List<FoodDTO> food = backend.controllers.RestaurantController.allRestaurantFood();
		
		for(FoodDTO f : food) {
			System.out.println(f);
		}
	}
    @FXML
    private void onBackClick(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "restaurant-view.fxml", "Restaurant");
    }

    @FXML
    private void onViewMore1(ActionEvent event) throws Exception {
        AppState.setSelectedMenuItem(1);
        goToDetail(event);
    }

    @FXML
    private void onViewMore2(ActionEvent event) throws Exception
    {
        AppState.setSelectedMenuItem(2);
        goToDetail(event);
    }

    @FXML
    private void onViewMore3(ActionEvent event) throws Exception
    {
        AppState.setSelectedMenuItem(3);
        goToDetail(event);
    }

    @FXML
    private void onViewMore4(ActionEvent event) throws Exception
    {
        AppState.setSelectedMenuItem(4);
        goToDetail(event);
    }

    private void goToDetail(ActionEvent event) throws Exception
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "menu-item-detail.fxml", "Item Details");
    }

}
