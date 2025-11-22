package frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/frontend/home-view.fxml"));
        Scene scene = new Scene(loader.load(), 600, 500);
        stage.setTitle("Smart N Dine");
        stage.setScene(scene);
        stage.show();
    }


}
