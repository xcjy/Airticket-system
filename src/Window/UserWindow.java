package Window;

import Entity.Flight;
import Entity.Order;
import Entity.Passenger;
import Utils.FlightUtils;
import Utils.OrderUtils;
import Utils.PassengerUtils;
import Utils.UserUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserWindow {
    private  String useraccount;

     private Stage UserStage;
     private Parent root;
     private FlightUtils flightUtils;
     private PassengerUtils passengerUtils;
     private OrderUtils orderUtils;
     private UserUtils userUtils;

     private Button button_logout;
     private Button button_searchflight;
     private Button button_saveinfo;
     private Button button_ModifyPsw;

     private TextField name;
    private TextField phone;
    private TextField email;
    private PasswordField OriginPwd;
    private PasswordField NowPwd;


     private ComboBox<String> start;
     private ComboBox<String> end;

     private DatePicker datePicker;
     private TableView FlightTable;
     private TableView PsgTable;
     private TableView OrderTable;


     private ObservableList<Flight> FlightObList=FXCollections.observableArrayList();;
     private ObservableList<Passenger> PsgObList=FXCollections.observableArrayList();;
     private ObservableList<Order> OrderObList=FXCollections.observableArrayList();


     private MenuItem bookflight=new MenuItem("订票");
     private MenuItem addpsg=new MenuItem("添加乘机人");
    private MenuItem deleltepsg=new MenuItem("删除乘机人");
    private MenuItem deleteorder=new MenuItem("退订");

    /*  构造函数*/
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
        orderUtils=new OrderUtils();
        userUtils=new UserUtils();

        InitFlightControl();
        Flight_Buttonevent();

        InitPassengerControl();
        PSG_Buttonevent();

        InitOrderControl();
        Order_Buttonevent();

        InitUserInfo();
        User_Buttonevent();
    }

    void InitFlightControl(){
        button_logout =(Button)root.lookup("#button_logout");

        button_searchflight=(Button)root.lookup("#button_searchflight");

        start=( ComboBox<String>)root.lookup("#startparams");
        end=( ComboBox<String>)root.lookup("#endparams");
        datePicker =(DatePicker) root.lookup("#datapicker");
        FlightTable=(TableView) root.lookup("#flight_table");
        //FlightObList=FXCollections.observableArrayList();

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

        RefreshFlightTable(selectedlist);
        FlightTable.setItems(FlightObList);

    }

    void RefreshFlightTable( List<Map<String,Object>> selectedlist){
        FlightObList.clear();
        for (int i = 0; i < selectedlist.size(); i++) {
            //剩余票数大于0 才添加进来
            if((Integer)selectedlist.get(i).get("f_left")>0) {
                Flight tmp = new Flight();
                tmp.setId(selectedlist.get(i).get("f_id").toString());
                tmp.setCom(selectedlist.get(i).get("f_com").toString());
                tmp.setEtime(CutPoint0(selectedlist.get(i).get("f_etime").toString()));
                tmp.setStime(CutPoint0(selectedlist.get(i).get("f_stime").toString()));
                tmp.setModel(selectedlist.get(i).get("f_model").toString());
                tmp.setStart(selectedlist.get(i).get("f_start").toString());
                tmp.setDist(selectedlist.get(i).get("f_dist").toString());
                tmp.setPrice(Float.parseFloat(selectedlist.get(i).get("f_price").toString()));
                tmp.setLeft(Integer.parseInt(selectedlist.get(i).get("f_left").toString()));
                FlightObList.add(tmp);
            }
        }

    }

  void InitPassengerControl(){
      PsgTable=(TableView) root.lookup("#passenger_table");


      ContextMenu cm_psgtable=new ContextMenu();
      cm_psgtable.getItems().add(addpsg);
      cm_psgtable.getItems().add(deleltepsg);
      PsgTable.setContextMenu(cm_psgtable);



      String[] psgpara=new String[] {"name","id"};

      ObservableList<TableColumn> psg_observableList=PsgTable.getColumns();

          //绑定
      for(int i=0;i<psg_observableList.size();i++) {
          psg_observableList.get(i).setCellValueFactory(new PropertyValueFactory<Passenger, String>(psgpara[i])); //与Flight中的属性关联
          psg_observableList.get(i).setCellFactory(TextFieldTableCell.<Passenger>forTableColumn());  // 设置成表格可编辑
      }

      psg_observableList.get(0).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
          @Override
          public void handle(TableColumn.CellEditEvent event) {
              // 界面修改乘机人信息
              String a_value=event.getNewValue().toString();  //获取文本框修改的值
              List<Object> paras=new ArrayList<Object>();      //传参
              paras.add(a_value);
              paras.add( ((Passenger) PsgTable.getSelectionModel().getSelectedItem()).getId()); //获取乘机人id

              if( passengerUtils.UpDate_A_By_ID("name",paras) ){
                  ((Passenger) PsgTable.getSelectionModel().getSelectedItem()).setName(event.getNewValue().toString());
              }
          }
      });

      psg_observableList.get(1).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
          @Override
          public void handle(TableColumn.CellEditEvent event) {
              // 界面修改乘机人信息
              String a_value=event.getNewValue().toString();  //获取文本框修改的值
              List<Object> paras=new ArrayList<Object>();      //传参
              paras.add(a_value);
              paras.add( ((Passenger) PsgTable.getSelectionModel().getSelectedItem()).getId()); //获取乘机人id

              if( passengerUtils.UpDate_A_By_ID("id",paras) ){
                  ((Passenger) PsgTable.getSelectionModel().getSelectedItem()).setId(event.getNewValue().toString());
              }
          }
      });


      List<Map<String,Object>> selectedlist=passengerUtils.SelectAllPsg(useraccount);


      for(int i=0;i<selectedlist.size();i++){
          Passenger tmp=new Passenger();
          tmp.setId(selectedlist.get(i).get("id").toString());
          tmp.setName(selectedlist.get(i).get("name").toString());
          PsgObList.add(tmp);
      }
      PsgTable.setItems(PsgObList);


  }

  void Flight_Buttonevent(){

      button_logout.setOnAction(event -> {
          UserStage.hide();
          LoginWindow LW= new LoginWindow();
          Stage s1=new Stage();
          try{
              LW.start(s1);}
          catch (Exception e){
              e.printStackTrace();
          }

      });

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

       RefreshFlightTable(selectedlist);

      });

      bookflight.setOnAction(event -> {
          Flight selectedflight = (Flight) FlightTable.getSelectionModel().getSelectedItem();
          Passenger selectedpsg = (Passenger) PsgTable.getSelectionModel().getSelectedItem();

          // 如果剩余票数大于0
          if(selectedflight.getLeft()>0)
          {
          List<Object> params=new ArrayList<Object>();

          //生成10位随机数
          Random r = new Random();
          long num = Math.abs(r.nextLong() % 10000000000L);
          String orderid=Long.toString(num);
         //添加参数
          params.add(orderid);
          params.add(selectedpsg.getName());
          params.add(selectedpsg.getId());
          params.add(selectedflight.getId());
          params.add(selectedflight.getCom());
          params.add(selectedflight.getModel());
          params.add(selectedflight.getStime());
          params.add(selectedflight.getEtime());
          params.add(selectedflight.getStart());
          params.add(selectedflight.getDist());
          params.add(selectedflight.getPrice());
          params.add(useraccount);

          orderUtils.InsertOrder(params);

          //更新航班剩余票数

          List<Object> params2=new ArrayList<Object>();
          params2.add(selectedflight.getLeft()-1);
          params2.add(selectedflight.getId());
         if(  flightUtils.UpDate_A_By_ID("f_left",params2) ){
             selectedflight.setLeft(selectedflight.getLeft()-1);
         }
              List<Map<String,Object>> selectedlist=flightUtils.SelectAllFlight();
              RefreshFlightTable(selectedlist);

         /*   刷新我的订单列表*/

                  Order tmp=new Order();
                  tmp.setOrderid(orderid);
                  tmp.setP_name(selectedpsg.getName());
                  tmp.setP_id(selectedpsg.getId());
                  tmp.setF_id(selectedflight.getId());
                  tmp.setF_com(selectedflight.getCom());
                  tmp.setF_model(selectedflight.getModel());
                  tmp.setF_stime((selectedflight.getStime()));
                  tmp.setF_etime((selectedflight.getEtime()));
                  tmp.setF_start(selectedflight.getStart());
                  tmp.setF_end(selectedflight.getDist());
                  tmp.setF_price( String.valueOf( selectedflight.getPrice() ) );
                  OrderObList.add(tmp);
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle(" ");
              alert.setHeaderText("");
              alert.setContentText("订票成功,请到我的订单查看详情");

              alert.showAndWait();

      }


  });

  }


  void InitOrderControl(){
        OrderTable=(TableView)root.lookup("#OrderTable");

      ContextMenu cm_ordertable=new ContextMenu();
      cm_ordertable.getItems().add(deleteorder);
      OrderTable.setContextMenu(cm_ordertable);

      String []orderpara=new String[]{"orderid","p_name","p_id","f_id","f_com","f_model","f_stime","f_etime","f_start","f_end","f_price"};
      ObservableList<TableColumn> Order_observableList=OrderTable.getColumns();
      for(int i=0;i<Order_observableList.size();i++) {
          Order_observableList.get(i).setCellValueFactory(new PropertyValueFactory<Order,String>(orderpara[i])); //与Order众的属性关联
      }

      List<Object> params = new ArrayList<Object>();
      params.add(useraccount);

      List<Map<String,Object>> list =orderUtils.SelectAllOrderByuser(params);

      for(int i=0;i<list.size();i++)
      {
          Order tmp=new Order();
          tmp.setOrderid(list.get(i).get("orderid").toString());
          tmp.setP_name(list.get(i).get("p_name").toString());
          tmp.setP_id(list.get(i).get("p_id").toString());
          tmp.setF_id(list.get(i).get("f_id").toString());
          tmp.setF_com(list.get(i).get("f_com").toString());
          tmp.setF_model(list.get(i).get("f_model").toString());
          tmp.setF_stime(CutPoint0(list.get(i).get("f_stime").toString()));
          tmp.setF_etime(CutPoint0(list.get(i).get("f_etime").toString()));
          tmp.setF_start(list.get(i).get("f_start").toString());
          tmp.setF_end(list.get(i).get("f_end").toString());
          tmp.setF_price(list.get(i).get("f_price").toString());
          OrderObList.add(tmp);
      }
      OrderTable.setItems(OrderObList);




  }

  void Order_Buttonevent(){
   deleteorder.setOnAction(event -> {

       Order selected = (Order) OrderTable.getSelectionModel().getSelectedItem();
       if (selected != null) {
           //若数据库删除成功 那就删除
           List<Object> paras = new ArrayList<Object>();
           paras.add(selected.getOrderid() );

           if(orderUtils.DeleteOrderById(paras)){
               OrderObList.remove(selected);
           }
       }

     //获取 要退订的航班 剩余票数
       List<Object> params3 = new ArrayList<Object>();
        params3.add(selected.getF_id());

       List<Map<String, Object>> list= flightUtils.Select_Where_A_like_B("f_id",params3);
       int left= (int)list.get(0).get("f_left");


       List<Object> params2 = new ArrayList<Object>();
       params2.add(left+1);
       params2.add(selected.getF_id());



       flightUtils.UpDate_A_By_ID("f_left",params2);
       List<Map<String,Object>> selectedlist=flightUtils.SelectAllFlight();

       RefreshFlightTable(selectedlist);

       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle(" ");
       alert.setHeaderText("");
       alert.setContentText("退订成功");

       alert.showAndWait();



   });
  }

  void PSG_Buttonevent(){

        addpsg.setOnAction(event -> {
            //自定义Dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("添加乘机人信息");
            dialog.setHeaderText("请填写乘机人信息");


            ButtonType loginButtonType = new ButtonType("确认", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField name = new TextField();
            name.setPromptText("姓名");
            TextField pid = new TextField();
            pid.setPromptText("身份证");



            grid.add(new Label("乘机人姓名:"), 0, 0);
            grid.add(name, 1, 0);
            grid.add(new Label("乘机人身份证:"), 0, 1);
            grid.add(pid, 1, 1);


            dialog.getDialogPane().setContent(grid);

// 默认光标在上
            Platform.runLater(() -> name.requestFocus());

            Optional<ButtonType> result = dialog.showAndWait();

            //如果是确认键
            if (      result.get().getButtonData()==ButtonBar.ButtonData.OK_DONE)
            {
                List<Object> params = new ArrayList<Object>();
                params.add(pid.getText());
                params.add(name.getText());
                params.add(useraccount);

                passengerUtils.InsertPsg(params);

                Passenger tmp=new Passenger();
                tmp.setId(pid.getText());
                tmp.setName(name.getText());
                PsgObList.add(tmp);
            }




        });

        deleltepsg.setOnAction(event -> {
            Passenger selected = (Passenger) PsgTable.getSelectionModel().getSelectedItem();
         List<Object> params=new ArrayList<Object>();
           params.add(selected.getId());
            if(passengerUtils.DeletePsgById(params)){
                PsgObList.remove(selected);
            }

        });
  }

  void InitUserInfo(){
      button_saveinfo =(Button)root.lookup("#Button_SaveInfo");
      button_ModifyPsw=(Button)root.lookup("#ModifyPwd");
      name=(TextField)root.lookup("#textfield_name");
      phone=(TextField)root.lookup("#textfield_phone");
      email=(TextField)root.lookup("#textfield_email");
      OriginPwd=(PasswordField)root.lookup("#OriginPwd");
      NowPwd=(PasswordField)root.lookup("#NewPwd");


      List<Object> paras = new ArrayList<Object>();
      paras.add(useraccount);

      List<Map<String, Object>> selectedlist= userUtils.Select_User_By_Account(paras);
      name.setText(selectedlist.get(0).get("name").toString());
      phone.setText(selectedlist.get(0).get("phone").toString());
      email.setText(selectedlist.get(0).get("email").toString());

  }

  void User_Buttonevent(){
    button_saveinfo.setOnAction(event -> {
     //   String sql="UPDATE `airlineticket`.`user` SET "+ attr +"= ? WHERE `user`=?";
        List<Object> paras = new ArrayList<Object>();
        paras.add(name.getText());
        paras.add(useraccount);
        userUtils.UpDate_A_By_Account("name",paras);
        paras.clear();

        paras.add(phone.getText());
        paras.add(useraccount);
        userUtils.UpDate_A_By_Account("phone",paras);

        paras.clear();
        paras.add(email.getText());
        paras.add(useraccount);
        userUtils.UpDate_A_By_Account("email",paras);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(" ");
        alert.setHeaderText("");
        alert.setContentText("保存信息成功");

        alert.showAndWait();
    });


    button_ModifyPsw.setOnAction(event -> {
        List<Object> paras = new ArrayList<Object>();
        paras.add(useraccount);
        List<Map<String, Object>> selectedlist= userUtils.Select_User_By_Account(paras);

        // 验证原密码
        String passwd=selectedlist.get(0).get("pass").toString();
        if(  passwd.equals(OriginPwd.getText() )){
            paras.clear();
            paras.add(NowPwd.getText());
            paras.add(useraccount);
           userUtils.UpDate_A_By_Account("pass",paras);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" ");
            alert.setHeaderText("");
            alert.setContentText("修改密码成功");

            alert.showAndWait();
       }
       else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("原密码错误");

            alert.showAndWait();
        }
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

  /*  改变时间格式*/
    String CutPoint0(String time)
    {
        return time.substring(0,time.length()-5);
    }

}
