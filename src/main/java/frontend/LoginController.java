package frontend;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;


public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void onLoginClick() {
        System.out.println("Login clicked with email: " + emailField.getText());
    }

    @FXML
    private void onCancelClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "/frontend/home-view.fxml", "Smart N Dine");
    }

}
