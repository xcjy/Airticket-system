package Window;

import Utils.MysqlUtils;
import Utils.UserUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class findpassW {
    Parent root2;
    Button fid5;
    TextField fid1;
    TextField fid4;
    PasswordField fid2 ;
    PasswordField fid3 ;
    Text fid6;

    public findpassW()
    {
        Stage finfpwStage2=new Stage();
        try{
            root2 = FXMLLoader.load(getClass().getResource("../fxml/fpw.fxml"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Scene scene=new Scene(root2,350,500);
        finfpwStage2.setTitle("注册");
        finfpwStage2.setScene(scene);
        finfpwStage2.show();
        Init_findpw();

    }

    public void Init_findpw()
    {
        fid1 = (TextField)root2.lookup("#fid1");
        fid4 = (TextField)root2.lookup("#fid4");
        fid2 = (PasswordField)root2.lookup("#fid2");
        fid3 = (PasswordField)root2.lookup("#fid3");
        fid5 = (Button) root2.lookup("#fid5");
        fid6 = (Text) root2.lookup("#fid6");

        fid5.setOnAction(event ->
        {

            UserUtils m_fp = new UserUtils();

            try{
                switch (m_fp.changepw(fid1.getText(),fid2.getText(),fid3.getText(),fid4.getText())){
                    case 0:
                       // System.out.print("0");
                        fid6.setText("修改成功");
                        //修改成功
                        break;
                    case 1:
                        Alert fp1 = new Alert(Alert.AlertType.ERROR,"2次密码不匹配");
                        fp1.showAndWait();
                        break;
                    case 2:
                        Alert fp2 = new Alert(Alert.AlertType.ERROR,"身份证号码不匹配");
                        fp2.showAndWait();
                        break;
                    case 3:
                        Alert fp3 = new Alert(Alert.AlertType.ERROR,"该账号没有注册");
                        fp3.showAndWait();
                        System.out.print("3");
                        break;
                }
            }
            catch (Exception e){e.printStackTrace();}
        });

    }
}
