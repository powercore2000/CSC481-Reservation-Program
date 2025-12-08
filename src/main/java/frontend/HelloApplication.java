package frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

<<<<<<< HEAD

public class HelloApplication extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("search-view.fxml"));
        Scene scene = new Scene(loader.load(), 600, 500);
        stage.setTitle("Smart N Dine");
        stage.setScene(scene);
        stage.show();
    }

=======
public class HelloApplication extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("home-search.fxml"));
		Scene scene = new Scene(loader.load(), 600, 500);
		stage.setTitle("Smart N Dine");
		stage.setScene(scene);
		stage.show();
	}
>>>>>>> parent of fc329e7 (Update HelloApplication.java)

}
