package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HomeSearchController {

    @FXML private TextField searchField;

    @FXML
    private void onSearchClick() {
        String query = searchField.getText().trim();

        // later you can pass this search term to Restaurants page
        System.out.println("Searching for: " + query);
    }

    @FXML
    private void onViewAllClick() throws Exception {
        Stage stage = (Stage) searchField.getScene().getWindow();
        SceneNavigator.switchScene(stage,
                "/frontend/home-view.fxml",
                "Restaurants");
    }
}
