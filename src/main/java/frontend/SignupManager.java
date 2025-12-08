package frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

import java.io.IOException;

import database.dto.UserDTO;

public class SignupManager
{

    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorLabel;

    private Stage getStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
    
    public void initalize() {
    	phoneField.setTextFormatter(new TextFormatter<>(change -> {
    	    return change.getControlNewText().matches("[0-9()\\-\\s]*") ? change : null;
    	}));
    }

    @FXML
    private void onSignupClick(ActionEvent event) throws IOException {
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String pw = passwordField.getText();
        String pw2 = confirmPasswordField.getText();

        // Very simple validation (no real database here)
        if (fullName.isEmpty() || email.isEmpty() || pw.isEmpty() || pw2.isEmpty()) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        if (!pw.equals(pw2)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        UserDTO signUpUser = new UserDTO(fullNameField,email,password);
        Boolean signInState = backend.controllers.UserController.loginUser(loginUser);
        
        if(!signInState) {
        	errorLabel.setText("No user found with that username and password.");
        	return;
        }
        // this helps pretend login success
        AppState.setSignedIn(true);
        
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
