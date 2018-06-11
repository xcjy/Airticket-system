package Window;

import Entity.Flight;
import Entity.Passenger;
import Utils.FlightUtils;
import Utils.PassengerUtils;
import Utils.UserUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UserWindow {
    private  String useraccount;

     private Stage UserStage;
     private Parent root;
     private FlightUtils flightUtils;
     private PassengerUtils passengerUtils;

     private Button button_logout;
     private Button button_searchflight;

     private ComboBox<String> start;
     private ComboBox<String> end;

     private DatePicker datePicker;
     private TableView FlightTable;
     private TableView PsgTable;
     private TextField PsgName;
    private TextField Psgid;

     private ObservableList<Flight> FlightObList;
     private ObservableList<Passenger> PsgObList;


     private MenuItem bookflight=new MenuItem("订票");
     private MenuItem addpsg=new MenuItem("添加乘机人");
    private MenuItem deleltepsg=new MenuItem("删除乘机人");


    public UserWindow(String user){
        useraccount=user;

        UserStage=new Stage();
        try{
            root = FXMLLoader.load(getClass().getResource("../fxml/user.fxml"));
        } catch (Exception e){
            e.printStackTrace();
        }

        Scene scene=new Scene(root,1024,768);
        UserStage.setTitle("Hello,"+user);
        UserStage.setScene(scene);
        UserStage.show();

        /* 连接数据库*/
        flightUtils=new FlightUtils();
        passengerUtils=new PassengerUtils();

        InitFlightControl();
        Flight_Buttonevent();
        InitUserControl();
        User_Buttonevent();
        InitPassengerControl();

    }

    void InitFlightControl(){
        button_searchflight=(Button)root.lookup("#button_searchflight");

        start=( ComboBox<String>)root.lookup("#startparams");
        end=( ComboBox<String>)root.lookup("#endparams");
        datePicker =(DatePicker) root.lookup("#datapicker");
        FlightTable=(TableView) root.lookup("#flight_table");
        FlightObList=FXCollections.observableArrayList();

        List<String> cities= Arrays.asList("北京","上海","成都","深圳","长沙","重庆","广州","哈尔滨","海口","杭州","昆明",
                "南京","三亚","沈阳","武汉","乌鲁木齐","厦门","西安", "郑州");
        start.setItems(FXCollections.observableArrayList(cities));
        end.setItems(FXCollections.observableArrayList(cities));

      dateConvert();

      ContextMenu cm_bookflight=new ContextMenu();
      cm_bookflight.getItems().add(bookflight);

      FlightTable.setContextMenu(cm_bookflight);

        String[] flightpara=new String[] {"id","com","model","stime","etime","start","dist","price","left"};

        ObservableList<TableColumn> flight_observableList=FlightTable.getColumns();

        for(int i=0;i<flight_observableList.size();i++)
        {
            //绑定
            flight_observableList.get(i).setCellValueFactory(new PropertyValueFactory<Flight,String>(flightpara[i])); //与Flight中的属性关联
        }

        List<Map<String,Object>> selectedlist=flightUtils.SelectAllFlight();
        for (int i = 0; i < selectedlist.size(); i++) {
            Flight tmp = new Flight();
            tmp.setId(selectedlist.get(i).get("f_id").toString());
            tmp.setCom(selectedlist.get(i).get("f_com").toString());
            tmp.setEtime(     CutPoint0(  selectedlist.get(i).get("f_etime").toString()) );
            tmp.setStime(CutPoint0(selectedlist.get(i).get("f_stime").toString()));
            tmp.setModel(   selectedlist.get(i).get("f_model").toString());
            tmp.setStart(selectedlist.get(i).get("f_start").toString());
            tmp.setDist(selectedlist.get(i).get("f_dist").toString());
            tmp.setPrice(Float.parseFloat(selectedlist.get(i).get("f_price").toString()));
            tmp.setLeft(Integer.parseInt(selectedlist.get(i).get("f_left").toString()));
            FlightObList.add(tmp);
        }

        FlightTable.setItems(FlightObList);

    }

  void InitUserControl(){
       button_logout =(Button)root.lookup("#button_logout");

  }

  void InitPassengerControl(){
      PsgTable=(TableView) root.lookup("#passenger_table");
      PsgName=(TextField)root.lookup("#text_psgname");
      Psgid=(TextField)root.lookup("#text_psgid");
      ContextMenu cm_psgtable=new ContextMenu();
      cm_psgtable.getItems().add(addpsg);
      cm_psgtable.getItems().add(deleltepsg);
      PsgTable.setContextMenu(cm_psgtable);
      PsgObList=FXCollections.observableArrayList();


      String[] psgpara=new String[] {"name","id"};

      ObservableList<TableColumn> psg_observableList=PsgTable.getColumns();

          //绑定
          psg_observableList.get(0).setCellValueFactory(new PropertyValueFactory<Passenger,String>(psgpara[0])); //与Flight中的属性关联
           psg_observableList.get(1).setCellValueFactory(new PropertyValueFactory<Passenger,String>(psgpara[1])); //与Flight中的属性关联


      List<Map<String,Object>> selectedlist=passengerUtils.SelectAllPsg(useraccount);



      System.out.println(selectedlist);
      for(int i=0;i<selectedlist.size();i++){
          Passenger tmp=new Passenger();
          tmp.setId(selectedlist.get(i).get("id").toString());
          tmp.setName(selectedlist.get(i).get("name").toString());
          PsgObList.add(tmp);
      }
      PsgTable.setItems(PsgObList);


  }

  void Flight_Buttonevent(){

      button_searchflight.setOnAction(event -> {
          String start_p="%"+start.getEditor().getText()+"%";  //从控件获取 起点 终点
        String end_p="%"+end.getEditor().getText()+"%";
        String f_time="%"+datePicker.getEditor().getText()+"%";

        //传递参数
        List<Object> params=new ArrayList<Object>();
        params.add(start_p);
        params.add(end_p);
        params.add(f_time);

       List<Map<String,Object>> selectedlist=flightUtils.Select_Flight_By_start_end_ftime(params);
          //List<Map<String,Object>> selectedlist=flightUtils.SelectAllFlight();
          FlightObList.clear();
          for (int i = 0; i < selectedlist.size(); i++) {
              Flight tmp = new Flight();
              tmp.setId(selectedlist.get(i).get("f_id").toString());
              tmp.setCom(selectedlist.get(i).get("f_com").toString());
              tmp.setEtime(     CutPoint0(  selectedlist.get(i).get("f_etime").toString()) );
              tmp.setStime(CutPoint0(selectedlist.get(i).get("f_stime").toString()));
              tmp.setModel(   selectedlist.get(i).get("f_model").toString());
              tmp.setStart(selectedlist.get(i).get("f_start").toString());
              tmp.setDist(selectedlist.get(i).get("f_dist").toString());
              tmp.setPrice(Float.parseFloat(selectedlist.get(i).get("f_price").toString()));
              tmp.setLeft(Integer.parseInt(selectedlist.get(i).get("f_left").toString()));
              FlightObList.add(tmp);
          }


      });

  }

  void User_Buttonevent(){

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

    String CutPoint0(String time)
    {
        return time.substring(0,time.length()-5);
    }

}
