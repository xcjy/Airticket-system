package Window;

import Utils.UserUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javafx.stage.Stage;


public class LoginWindow extends Application
{

    @Override
    public void start(Stage primaryStage){


        GridPane grid=new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap((10));
        grid.setPadding(new Insets(25,25,25,25));

        Text scenetitle =new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma",FontWeight.NORMAL,20));
        grid.add(scenetitle,0,0,2,1);

        Label userName = new Label("账号:");
        grid.add(userName,0,1);
        Label pw=new Label("密码:");
        grid.add(pw,0,2);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn=new Button("登陆");
        HBox hbBtn =new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn,1,4);

        Button bt1=new Button("注册");
        grid.add(bt1,0,4);
        final Text actiontarget=new Text();
        grid.add(actiontarget,1,6);

        RadioButton ra1 = new RadioButton("管理员");
        grid.add(ra1,0,3);
        RadioButton ra2 = new RadioButton("用户");
        grid.add(ra2,1,3);
        ToggleGroup gp = new ToggleGroup();
        ra1.setToggleGroup(gp);
        ra2.setToggleGroup(gp);
        ra1.setSelected(true);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                if(ra1.isSelected()){
                    if((userTextField.getText().equals("admin"))&&(pwBox.getText().equals("admin"))) {
                        //System.out.print("guanliyuan");
                        primaryStage.hide();
                        new ManagerWindow();
                    }
                    if((!userTextField.getText().equals("admin"))||(!pwBox.getText().equals("admin"))) {
                        Alert yzcw = new Alert(Alert.AlertType.ERROR,"管理员验证失败");
                        yzcw.showAndWait();
                    }
                }
                else {
                    UserUtils dl = new UserUtils();
                   dl.getConnection();
                    try {
                        switch (dl.isexst_user(userTextField.getText(), pwBox.getText())) {
                            case 0:
                                Alert cw1 = new Alert(Alert.AlertType.ERROR,"该账号没有注册");
                                cw1.showAndWait();
                                break;
                            case 1:
                                Alert cw2 = new Alert(Alert.AlertType.ERROR,"密码错误");
                                cw2.showAndWait();
                                break;
                            case 2:
                                //登陆成功
                                System.out.println("登陆成功");
                                break;
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });

        bt1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RegistWindow re = new RegistWindow();
                re.Init_Bre();

            }
        });

        Scene scene=new Scene(grid,300,275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
