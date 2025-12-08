package frontend;

import javafx.fxml.FXML;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

import database.dto.FoodDTO;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FoodMenuManager {


	@FXML
	private HBox itemsRow;   // the HBox with fx:id="itemsRow"
 
	@FXML
	private Label menuTitleLabel;
	
	
	public void initialize() {
		
		List<FoodDTO> foodList = backend.controllers.RestaurantController.allRestaurantFood();
		menuTitleLabel.setText(backend.controllers.RestaurantController.getCurrentRestaurant().getName());
		
		for(FoodDTO f : foodList) {
			System.out.println(f);
		}
		 if (itemsRow != null) {
	            itemsRow.getChildren().clear();
	        }
		 
		 if (foodList.isEmpty() || itemsRow == null) {
	            return;
	        }

	        for (FoodDTO food : foodList) {
	            VBox itemBox = createFoodItemBox(food);
	            itemsRow.getChildren().add(itemBox);
	        }
	}
	

    private VBox createFoodItemBox(FoodDTO food) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);

        // Image button
        Button imageButton = new Button("Image");
        imageButton.setPrefWidth(105);
        imageButton.setPrefHeight(110);
        imageButton.setStyle(
                "-fx-pref-width: 100; " +
                "-fx-pref-height: 100; " +
                "-fx-background-color: #d0d7e2; " +
                "-fx-border-color: #bbbbbb;"
        );

        // Name label
        Label nameLabel = new Label(food != null ? food.getName() : "Placeholder Food Name");
        
        // "View more" button
        Button viewMoreButton = new Button("View more");
        viewMoreButton.setStyle(
                "-fx-background-color: #c0392b; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 5;"
        );
        viewMoreButton.setOnAction(e -> {
			try {
				
				
				onViewMore(food, e);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        box.getChildren().addAll(imageButton, nameLabel, viewMoreButton);
        return box;
    }
    
    @FXML
    private void onBackClick(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "restaurant-view.fxml", "Restaurant");
    }

    @FXML
    private void onViewMore(FoodDTO f, ActionEvent event) throws Exception {
        AppState.setSelectedMenuItem((int)f.getId());
        backend.controllers.RestaurantController.setSelectedFoodID(f);
        goToDetail(event);
    }
    @FXML
    private void onViewMore(ActionEvent event) throws Exception {
        AppState.setSelectedMenuItem(1);
        goToDetail(event);
    }

    private void goToDetail(ActionEvent event) throws Exception
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "menu-item-detail.fxml", "Item Details");
    }

}
