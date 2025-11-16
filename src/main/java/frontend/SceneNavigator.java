package main.java.frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneNavigator {
    public static void switchScene(Stage stage, String fxml, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource("/frontend/"+fxml));
        Scene scene = new Scene(loader.load(), 600, 500);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
