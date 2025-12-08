package frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController
{

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorLabel;

    private Stage getStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    @FXML
    private void onSignupClick(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String pw = passwordField.getText();
        String pw2 = confirmPasswordField.getText();

        // Very simple validation (no real database here)
        if (username.isEmpty() || email.isEmpty() || pw.isEmpty() || pw2.isEmpty()) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        if (!pw.equals(pw2)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        // “Create” account – in this project we just mark the user as signed in
        AppState.setSignedIn(true);
        // optional: remember username if you want to show it later
        // AppState.setCurrentUserName(username);

        Stage stage = getStage(event);
        SceneNavigator.switchScene(stage, "SelectResturantType.fxml", "Restaurants");
    }

    @FXML
    private void onBackToLoginClick(ActionEvent event) throws IOException {
        Stage stage = getStage(event);
        SceneNavigator.switchScene(stage, "login-view.fxml", "Sign In");
    }
}
