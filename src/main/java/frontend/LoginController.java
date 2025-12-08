package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

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
    private void onCancelClick() {
        System.out.println("Cancel clicked");
    }
}
