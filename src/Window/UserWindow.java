package Window;

import Utils.FlightUtils;
import Utils.UserUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class UserWindow {
     private Stage UserStage;
     private Parent root;
     private FlightUtils flightUtils;

     private Button button_logout;
     private Button button_searchflight;

     private ComboBox<String> start;
     private ComboBox<String> end;

     private DatePicker datePicker;

    public UserWindow(){
        UserStage=new Stage();
        try{
            root = FXMLLoader.load(getClass().getResource("../fxml/user.fxml"));
        } catch (Exception e){
            e.printStackTrace();
        }

        Scene scene=new Scene(root,1024,768);
        UserStage.setTitle("Hello,会员");
        UserStage.setScene(scene);
        UserStage.show();

        /* 连接数据库*/
        flightUtils=new FlightUtils();

        InitFlightControl();
        Flight_Buttonevent();
        InitUserControl();
        User_Buttonevent();
    }

    void InitFlightControl(){
        button_searchflight=(Button)root.lookup("#button_searchflight");

        start=( ComboBox<String>)root.lookup("#startparams");
        end=( ComboBox<String>)root.lookup("#endparams");
        datePicker =(DatePicker) root.lookup("#datapicker");
        List<String> cities= Arrays.asList("北京","上海","成都","深圳","长沙","重庆","广州","哈尔滨","海口","杭州","昆明",
                "南京","三亚","沈阳","武汉","乌鲁木齐","厦门","西安", "郑州");
        start.setItems(FXCollections.observableArrayList(cities));
        end.setItems(FXCollections.observableArrayList(cities));

      dateConvert();


    }


  void InitUserControl(){
       button_logout =(Button)root.lookup("#button_logout");

  }

  void Flight_Buttonevent(){

  }

  void User_Buttonevent(){
        button_searchflight.setOnAction(event -> {
            System.out.println(datePicker.getEditor().getText());
        });
     button_logout.setOnAction(event -> {
         UserStage.hide();
         LoginWindow LW= new LoginWindow();
         Stage s1=new Stage();
         LW.start(s1);

     });
  }

  /*   改变datePicker的格式 */
  void dateConvert(){
      String pattern="yyyy-MM-dd";
      DateTimeFormatter.ofPattern(pattern);

      StringConverter converter = new StringConverter<LocalDate>() {
          DateTimeFormatter dateFormatter =
                  DateTimeFormatter.ofPattern(pattern);
          @Override
          public String toString(LocalDate date) {
              if (date != null) {
                  return dateFormatter.format(date);
              } else {
                  return "";
              }
          }
          @Override
          public LocalDate fromString(String string) {
              if (string != null && !string.isEmpty()) {
                  return LocalDate.parse(string, dateFormatter);
              } else {
                  return null;
              }
          }
      };
      datePicker.setConverter(converter);
  }

}
