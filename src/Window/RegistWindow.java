package Window;

import Utils.UserUtils;
import com.jfoenix.controls.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegistWindow {
    Parent root;
    JFXButton b_re;
    JFXTextField tf1,tf4,tf6;
    JFXPasswordField tf2,tf3;
    JFXCheckBox cb1;
    JFXRadioButton rb1,rb2;
    Stage registerStage=new Stage();

    public RegistWindow() {

        try{
            root = FXMLLoader.load(getClass().getResource("../fxml/sign_up1.fxml"));
        }
        catch(Exception e){e.printStackTrace();}
        Scene scene=new Scene(root,320,580);
        registerStage.setTitle("注册");
        registerStage.setScene(scene);
        registerStage.initStyle(StageStyle.UTILITY);
        registerStage.show();
    }

    public void Init_Bre() {

        b_re=(JFXButton) root.lookup("#b1");
        tf1=(JFXTextField) root.lookup("#tf1");
        tf2=(JFXPasswordField) root.lookup("#tf2");
        tf3=(JFXPasswordField) root.lookup("#tf3");
        tf4=(JFXTextField)root.lookup("#tf4");
        tf6=(JFXTextField)root.lookup("#tf6");
        cb1=(JFXCheckBox) root.lookup("#cb1");
        rb1=(JFXRadioButton) root.lookup("#rb1");
        rb2=(JFXRadioButton) root.lookup("#rb2");
       ToggleGroup group = new ToggleGroup();
        rb1.setToggleGroup(group);rb1.setSelected(true);
        rb2.setToggleGroup(group);

        b_re.setOnAction(event ->
        {
            //System.out.print(tf6.getText()+" "+tf5.getText()+ " "+tf4.getText());
                UserUtils m_re = new UserUtils();
              //  m_re.getConnection();
                int agree = 0;
                String sex = "男";
                if(cb1.isSelected()){agree=1;//System.out.print("tongyi");
                }
                if(rb2.isSelected()){sex="女";//System.out.print("nv");
                }

                try {
                    switch (m_re.re_user(tf1.getText(), tf2.getText(), tf3.getText(),tf4.getText(),sex,tf6.getText(),agree)) {
                        case 0:
                            //请认真阅读声明条款
                            //System.out.print("0");
                            Alert rcw0 = new Alert(Alert.AlertType.WARNING,"请认真阅读声明条款");
                            rcw0.showAndWait();
                            break;
                        case 1:
                            //用户名被使用
                           // System.out.print("1");
                            Alert rcw1 = new Alert(Alert.AlertType.WARNING,"用户名被使用");
                            rcw1.showAndWait();
                            break;
                        case 2:
                            //密码不一致
                            //System.out.print("2");
                            Alert rcw2 = new Alert(Alert.AlertType.WARNING,"2次输入密码不一致");
                            rcw2.showAndWait();
                            break;
                        case 3:
                            //注册成功
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle(" ");
                            alert.setHeaderText(null);
                            alert.setContentText("注册成功");

                            alert.showAndWait();
                            registerStage.hide();
                            System.out.println(alert.getResult());
                            break;
                    }
                } catch (Exception e) { }
        });
    }
}
