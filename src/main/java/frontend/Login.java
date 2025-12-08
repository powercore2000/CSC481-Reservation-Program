package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class Login
{

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void onLoginClick(ActionEvent event) throws IOException
    {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // super simple check – you can replace with whatever you need
        if (username == null || username.isBlank() ||
                password == null || password.isBlank()) {
            errorLabel.setText("Please enter both username and password.");
            return;
        }

        // this helps pretend login success
        AppState.setSignedIn(true);
        //   to remember username, add a field in AppState and set it here

        // go back to home (or search) after login
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "SelectResturantType.fxml", "Smart N Dine");
    }

    @FXML
    private void onCancelClick(ActionEvent event) throws IOException
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "SelectResturantType.fxml", "Smart N Dine");
    }
    @FXML
    private void onGoToSignupClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "signup-view.fxml", "Sign Up");
    }


}
