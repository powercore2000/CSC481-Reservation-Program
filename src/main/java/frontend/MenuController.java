package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController
{

    @FXML
    private Label menuTitleLabel;

    @FXML
    public void initialize()
    {
        // Set title like "Andies Menu", "Jays sushi palace Menu", etc.
        String name = AppState.getSelectedRestaurantName();
        if (name == null || name.isBlank())
        {
            name = "Restaurant";
        }
        menuTitleLabel.setText(name + "'s Menu");
    }

    @FXML
    private void onBackClick() {
        Stage stage = (Stage) menuTitleLabel.getScene().getWindow();
        try {
            SceneNavigator.switchScene(
                    stage,
                    "/frontend/restaurant-view.fxml",
                    AppState.getSelectedRestaurantName()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
