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
    private void onBackClick() throws IOException
    {
        // Go back to that restaurant's info page
        Stage stage = (Stage) menuTitleLabel.getScene().getWindow();
        SceneNavigator.switchScene(stage, "restaurant-view.fxml",
                AppState.getSelectedRestaurantName());
    }
}
