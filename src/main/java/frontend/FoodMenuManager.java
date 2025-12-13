package frontend;


import javafx.fxml.FXML;
import javafx.geometry.Pos;


import java.util.List;


import database.dto.FoodDTO;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;



public class FoodMenuManager {


    @FXML
    private HBox itemsRow;   // the HBox with fx:id="itemsRow"


    @FXML
    private Label menuTitleLabel;


    @FXML
    private Button cartButton;  // <-- cart button from FXML (fx:id="cartButton")


    @FXML
    public void initialize() {


        List<FoodDTO> foodList = backend.controllers.RestaurantController.allRestaurantFood();
        menuTitleLabel.setText(backend.controllers.RestaurantController.getCurrentRestaurant().getName());


        // Debug print
        for (FoodDTO f : foodList) {
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


        // === CART VISIBILITY LOGIC ===
        // Only show cart button if there are items in the cart
        if (cartButton != null) {
            cartButton.setVisible(AppState.hasCartItems());
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
        setFoodImage(imageButton, food);

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
            } catch (Exception ex) {
                ex.printStackTrace();
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


    // === CART CLICK HANDLER ===
    @FXML
    private void onCartClick(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "cart-view.fxml", "Cart");
    }


    // Called from the dynamically created "View more" buttons
    @FXML
    private void onViewMore(FoodDTO f, ActionEvent event) throws Exception {
        AppState.setSelectedFoodItem(f);
        backend.controllers.RestaurantController.setSelectedFoodID(f);
        goToDetail(event);
    }


    // Called from FXML if you still have onAction="#onViewMore"
    @FXML
    private void onViewMore(ActionEvent event) throws Exception
    {
        AppState.setSelectedMenuItemID(1);
        goToDetail(event);
    }


    private void goToDetail(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "menu-item-detail.fxml", "Item Details");
    }
    
    private void setFoodImage(Button imageButton, FoodDTO food) {
        String path = getImagePathForFood(food); // decide which png to use
        setButtonGraphic(imageButton, path);
    }

    private void setButtonGraphic(Button btn, String resourcePath) {
        InputStream is = getClass().getResourceAsStream(resourcePath);
        if (is == null) {
            System.out.println("Missing image: " + resourcePath);
            btn.setText("No image");
            return;
        }

        ImageView iv = new ImageView(new Image(is));
        iv.setFitWidth(100);
        iv.setFitHeight(100);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);

        btn.setText(null);      // removes "Image"
        btn.setGraphic(iv);     // shows image
    }

    private String getImagePathForFood(FoodDTO food) {
        if (food == null || food.getName() == null) return "/images/all.png";

        // normalize the name to match your filenames
        String key = food.getName().trim().replaceAll("\\s+", "");

        // examples based on your filenames
        switch (key) {
            case "AvocadoToast": return "/images/AvocadoToast.png";
            case "BerryPancakes": return "/images/BerryPancakes.png";
            case "ChocolateLavaCake": return "/images/ChocolateLavaCake.png";
            case "ClassicBurger": return "/images/ClassicBurger.png";
            case "GrilledChicken": return "/images/GrilledChicken.png";
            case "SpicyNachos": return "/images/SpicyNachos.png";
            case "StreetTacos": return "/images/StreetTacos.png";
            default: return "/images/all.png"; // fallback image
        }
    }

}

