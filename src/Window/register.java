package Window;

import Utils.UserUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class register {
    Parent root;
    Button b_re;
    TextField tf1,tf2,tf3,tf4,tf5,tf6 ;
    CheckBox cb1;
    Text t1;
    RadioButton rb1,rb2;
   // ToggleGroup group = new ToggleGroup();

    public  register() {
        Stage registerStage=new Stage();
        try{
            root = FXMLLoader.load(getClass().getResource("../fxml/sample.fxml"));
        }
        catch(Exception e){e.printStackTrace();}
        Scene scene=new Scene(root,350,500);
        registerStage.setTitle("注册");
        registerStage.setScene(scene);
        registerStage.show();
    }

    public void Init_Bre() {

        b_re=(Button)root.lookup("#b1");
        t1=(Text)root.lookup("#t1");
        tf1=(TextField)root.lookup("#tf1");
        tf2=(TextField)root.lookup("#tf2");
        tf3=(TextField)root.lookup("#tf3");
        tf4=(TextField)root.lookup("#tf4");
        tf5=(TextField)root.lookup("#tf5");
        tf6=(TextField)root.lookup("#tf6");
        cb1=(CheckBox)root.lookup("#cb1");
        rb1=(RadioButton)root.lookup("#rb1");
        rb2=(RadioButton)root.lookup("#rb2");
       /* final ToggleGroup group = new ToggleGroup();
        rb1=(RadioButton)root.lookup("rb1");rb1.setToggleGroup(group);rb1.setSelected(true);
        rb2=(RadioButton)root.lookup("rb2");rb2.setToggleGroup(group);*/

        b_re.setOnAction(event ->
        {
            //System.out.print(tf6.getText()+" "+tf5.getText()+ " "+tf4.getText());
                UserUtils m_re = new UserUtils();
                m_re.getConnection();
                int agree = 0;
                String sex = "男";
                if(cb1.isSelected()){agree=1;//System.out.print("tongyi");
                }
                if(rb2.isSelected()){sex="女";//System.out.print("nv");
                }

                try {
                    switch (m_re.re_user(tf1.getText(), tf2.getText(), tf3.getText(),tf4.getText(),sex,tf5.getText(),tf6.getText(),agree)) {
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
                            //System.out.print("3");
                            break;
                    }
                } catch (Exception e) { }
        });
    }
}
