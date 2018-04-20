package Window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginWindow extends Application {

    private Button login;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/Login.fxml"));
        primaryStage.setTitle("机票预定系统");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();



      login=(Button)root.lookup("#Button_1");
      login.setOnAction(event ->
      {
          primaryStage.hide();
           new ManagerWindow();

      });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
