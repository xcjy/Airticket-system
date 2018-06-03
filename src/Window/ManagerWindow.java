package Window;

import Entity.Flight;
import Entity.User;
import Utils.FlightUtils;
import Utils.UserUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerWindow {

    private FlightUtils flightUtils;
    private UserUtils userUtils;

    private Parent root;

    private TableView FlightTable;
    private TableView UserTable;

    private ObservableList<Flight> FlightObList;
    private ObservableList<User> UserObList;

    private Button button_addflight;
    private Button button_deleteflight;
    private Button button_searchflight;
    private Button button_logout;

    private Button button_searchuser;
    private Button button_adduser;
    private Button button_deleteuser;


    private List<TextField> flighttextField;
    private List<TextField> usertextField;


    private TextField searchFlightTextfield;
    private TextField searchUserTextfield;

    private ComboBox<String> flightParams;
    private ComboBox<String> userparams;

    private Map<String,String> flightMap;
    private Map<String,String> userMap;

    private Stage FlightStage;

    public ManagerWindow(){
         FlightStage=new Stage();
        try{
             root = FXMLLoader.load(getClass().getResource("../fxml/Manager.fxml"));
        } catch (Exception e){
            e.printStackTrace();
        }

        Scene scene=new Scene(root,1024,768);
        FlightStage.setTitle("Hello,管理员");
        FlightStage.setScene(scene);
        FlightStage.show();

        /* 连接数据库*/
      flightUtils=new FlightUtils();
      userUtils=new UserUtils();

        InitFlightControl();
        Flight_Buttonevent();
        InitUserControl();
        User_Buttonevent();
    }



   //初始化控件
    public void InitFlightControl(){
        //寻找控件
        FlightTable=(TableView)root.lookup("#FlightTable");
        FlightObList=FXCollections.observableArrayList();
        button_addflight=(Button) root.lookup("#button_addflight");
        button_deleteflight=(Button) root.lookup("#button_deleteflight");
        button_searchflight=(Button) root.lookup("#button_searchflight");
        button_logout=(Button)root.lookup("#button_logout");

        searchFlightTextfield=(TextField) root.lookup("#searchFlightTextfield");
        flightParams =(ComboBox<String>)root.lookup("#flightparams");
        initFlightComboBox();
        flightParams.setItems(FXCollections.observableArrayList(flightMap.keySet()));
        flightParams.getSelectionModel().select(0);
        flighttextField=new ArrayList<TextField>();
        for(int i=0;i<9;i++)
            flighttextField.add( (TextField)root.lookup("#flightTextfield_"+i));


          /*绑定Flight 与 observablelist*/
        String[] flightpara=new String[] {"id","com","model","stime","etime","start","dist","price","left"};

        ObservableList<TableColumn> flight_observableList=FlightTable.getColumns();

        for(int i=0;i<flight_observableList.size();i++)
        {
            //先绑定
            flight_observableList.get(i).setCellValueFactory(new PropertyValueFactory<Flight,String>(flightpara[i])); //与Flight中的属性关联
            if(i!=7&&i!=8)
            flight_observableList.get(i).setCellFactory(TextFieldTableCell.<Flight>forTableColumn());  // 设置成表格可编辑
        }
        flight_observableList.get(7).setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter())); // float 转string 出错 默认不带转换器
        flight_observableList.get(8).setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));  //同上


        // 界面修改航班信息
        flight_observableList.get(0).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                //String sql="UPDATE `airlineticket`.`flight` SET "+ attr +"= ? WHERE `f_id`=?";
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((Flight) FlightTable.getSelectionModel().getSelectedItem()).getId());

                if( flightUtils.UpDate_A_By_ID("f_id",paras) ){
                    ((Flight) FlightTable.getSelectionModel().getSelectedItem()).setId(event.getNewValue().toString());
                }
            }
        });
        flight_observableList.get(1).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                //String sql="UPDATE `airlineticket`.`flight` SET "+ attr +"= ? WHERE `f_id`=?";
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((Flight) FlightTable.getSelectionModel().getSelectedItem()).getId());

                if( flightUtils.UpDate_A_By_ID("f_com",paras) ){
                    ((Flight) FlightTable.getSelectionModel().getSelectedItem()).setCom(event.getNewValue().toString());
                }
            }
        });
        flight_observableList.get(2).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                //String sql="UPDATE `airlineticket`.`flight` SET "+ attr +"= ? WHERE `f_id`=?";
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(event.getNewValue().toString());  // //获取文本框修改的值
                paras.add( ((Flight) FlightTable.getSelectionModel().getSelectedItem()).getId());
                if( flightUtils.UpDate_A_By_ID("f_model",paras) ){
                    ((Flight) FlightTable.getSelectionModel().getSelectedItem()).setModel(event.getNewValue().toString());
                }
            }
        });
        flight_observableList.get(3).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                //String sql="UPDATE `airlineticket`.`flight` SET "+ attr +"= ? WHERE `f_id`=?";
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(event.getNewValue().toString());  // //获取文本框修改的值
                paras.add( ((Flight) FlightTable.getSelectionModel().getSelectedItem()).getId());
                if( flightUtils.UpDate_A_By_ID("f_stime",paras) ){
                    ((Flight) FlightTable.getSelectionModel().getSelectedItem()).setStime(event.getNewValue().toString());
                }
            }
        });
        flight_observableList.get(4).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                //String sql="UPDATE `airlineticket`.`flight` SET "+ attr +"= ? WHERE `f_id`=?";
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(event.getNewValue().toString());  // //获取文本框修改的值
                paras.add( ((Flight) FlightTable.getSelectionModel().getSelectedItem()).getId());
                if( flightUtils.UpDate_A_By_ID("f_etime",paras) ){
                    ((Flight) FlightTable.getSelectionModel().getSelectedItem()).setEtime(event.getNewValue().toString());
                }
            }
        });
        flight_observableList.get(5).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                //String sql="UPDATE `airlineticket`.`flight` SET "+ attr +"= ? WHERE `f_id`=?";
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(event.getNewValue().toString());  // //获取文本框修改的值
                paras.add( ((Flight) FlightTable.getSelectionModel().getSelectedItem()).getId());
                if( flightUtils.UpDate_A_By_ID("f_start",paras) ){
                    ((Flight) FlightTable.getSelectionModel().getSelectedItem()).setStart(event.getNewValue().toString());
                }
            }
        });
        flight_observableList.get(6).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                //String sql="UPDATE `airlineticket`.`flight` SET "+ attr +"= ? WHERE `f_id`=?";
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(event.getNewValue().toString());  // //获取文本框修改的值
                paras.add( ((Flight) FlightTable.getSelectionModel().getSelectedItem()).getId());
                if( flightUtils.UpDate_A_By_ID("f_dist",paras) ){
                    ((Flight) FlightTable.getSelectionModel().getSelectedItem()).setDist(event.getNewValue().toString());
                }
            }
        });
        flight_observableList.get(7).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                //String sql="UPDATE `airlineticket`.`flight` SET "+ attr +"= ? WHERE `f_id`=?";
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(event.getNewValue().toString());  // //获取文本框修改的值
                paras.add( ((Flight) FlightTable.getSelectionModel().getSelectedItem()).getId());
                if( flightUtils.UpDate_A_By_ID("f_price",paras) ){
                    ((Flight) FlightTable.getSelectionModel().getSelectedItem()).setPrice(Float.parseFloat(event.getNewValue().toString()));
                }
            }
        });
        flight_observableList.get(8).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                //String sql="UPDATE `airlineticket`.`flight` SET "+ attr +"= ? WHERE `f_id`=?";
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(event.getNewValue().toString());  // //获取文本框修改的值
                paras.add( ((Flight) FlightTable.getSelectionModel().getSelectedItem()).getId());
                if( flightUtils.UpDate_A_By_ID("f_left",paras) ){
                    ((Flight) FlightTable.getSelectionModel().getSelectedItem()).setLeft(Integer.parseInt(event.getNewValue().toString()));
                }
            }
        });

        /*从数据库中获取 航班信息 加入到FlightList*/

        List<Map<String,Object>> list =flightUtils.SelectAllFlight();

        for(int i=0;i<list.size();i++)
        {
            Flight tmp=new Flight();

            tmp.setId(list.get(i).get("f_id").toString());
            tmp.setCom(list.get(i).get("f_com").toString());
            tmp.setEtime(  CutPoint0(list.get(i).get("f_etime").toString()));  //去掉.0  时间转换为字符串
            tmp.setStime(CutPoint0(list.get(i).get("f_stime").toString()));
            tmp.setModel(list.get(i).get("f_model").toString());
            tmp.setStart(list.get(i).get("f_start").toString());
            tmp.setDist(list.get(i).get("f_dist").toString());
            tmp.setPrice(  Float.parseFloat( list.get(i).get("f_price").toString()));
            tmp.setLeft(Integer.parseInt(list.get(i).get("f_left").toString()));
            FlightObList.add(tmp);
        }
       FlightTable.setItems(FlightObList);
    }

    public void InitUserControl(){
        //寻找控件
        UserTable=(TableView)root.lookup("#UserTable");
        UserObList=FXCollections.observableArrayList();

        button_deleteuser=(Button) root.lookup("#button_deleteuser");
        button_searchuser=(Button) root.lookup("#button_searchuser");

        searchUserTextfield=(TextField) root.lookup("#searchUserTextfield");
        userparams =(ComboBox<String>)root.lookup("#userparams");

        initUserComboBox();

        userparams.setItems(FXCollections.observableArrayList(userMap.keySet()));
        userparams.getSelectionModel().select(0);



        /*绑定User  与 observablelist*/
        String[] userpara=new String[] {"user","password","name","sex","identity"};

        ObservableList<TableColumn> user_observableList=UserTable.getColumns();
   // System.out.println(user_observableList.size());

        for(int i=0;i<user_observableList.size();i++)
        {
            //先绑定
            user_observableList.get(i).setCellValueFactory(new PropertyValueFactory<User,String>(userpara[i])); //与User中的属性关联

            user_observableList.get(i).setCellFactory(TextFieldTableCell.<User>forTableColumn());  // 设置成表格可编辑
        }

        // 界面修改用户信息
         user_observableList.get(0).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
             @Override
             public void handle(TableColumn.CellEditEvent event) {
                 String a_value=event.getNewValue().toString();  //获取文本框修改的值
                 List<Object> paras=new ArrayList<Object>();      //传参
                 paras.add(a_value);
                 paras.add( ((User) UserTable.getSelectionModel().getSelectedItem()).getIdentity());

                 if( userUtils.UpDate_A_By_ID("user",paras) ){
                     ((User) UserTable.getSelectionModel().getSelectedItem()).setUser(event.getNewValue().toString());
                 }

             }
         });

        user_observableList.get(1).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((User) UserTable.getSelectionModel().getSelectedItem()).getIdentity());

                if( userUtils.UpDate_A_By_ID("pass",paras) ){
                    ((User) UserTable.getSelectionModel().getSelectedItem()).setPassword(event.getNewValue().toString());
                }

            }


        });

        user_observableList.get(2).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((User) UserTable.getSelectionModel().getSelectedItem()).getIdentity());

                if( userUtils.UpDate_A_By_ID("name",paras) ){
                    ((User) UserTable.getSelectionModel().getSelectedItem()).setName(event.getNewValue().toString());
                }

            }
        });

        user_observableList.get(3).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((User) UserTable.getSelectionModel().getSelectedItem()).getIdentity());

                if( userUtils.UpDate_A_By_ID("sex",paras) ){
                    ((User) UserTable.getSelectionModel().getSelectedItem()).setSex(event.getNewValue().toString());
                }

            }
        });

        user_observableList.get(4).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((User) UserTable.getSelectionModel().getSelectedItem()).getIdentity());

                if( userUtils.UpDate_A_By_ID("sfz",paras) ){
                    ((User) UserTable.getSelectionModel().getSelectedItem()).setIdentity(event.getNewValue().toString());
                }

            }
        });

        List<Map<String,Object>> selectuserlist= userUtils.SelectAllUser();

        System.out.println(selectuserlist.get(0).get(""));

        for(int i=0;i<selectuserlist.size();i++){
            User tmpuser =new User();
            tmpuser.setIdentity(selectuserlist.get(i).get("sfz").toString());
            tmpuser.setUser(selectuserlist.get(i).get("user").toString());
            tmpuser.setPassword(selectuserlist.get(i).get("pass").toString());
            tmpuser.setSex(selectuserlist.get(i).get("sex").toString());
            tmpuser.setName(selectuserlist.get(i).get("name").toString());

            UserObList.add(tmpuser);
        }
      UserTable.setItems(UserObList);



    }

    //初始化ComboBox
    public void initFlightComboBox()
    {
        flightMap=new HashMap<>();
        flightMap.put("航班编号","f_id");
        flightMap.put("航班公司","f_com");
        flightMap.put("机型","f_model");
        flightMap.put("起飞时间","f_stime");
        flightMap.put("到达时间","f_etime");
        flightMap.put("起点","f_start");
        flightMap.put("终点","f_dist");
        flightMap.put("价格","f_price");
        flightMap.put("余票","f_left");

    }

    public void initUserComboBox(){
        userMap=new HashMap<>();
        userMap.put("用户名","user");
        userMap.put("姓名","name");
        userMap.put("身份证","sfz");

    }


    private void Flight_Buttonevent() {
        //查询航班事件
        button_searchflight.setOnAction(event -> {
            //  获取要检索的属性
            String attribute = flightMap.get(flightParams.getValue());
           // System.out.println(attribute);
            //获取要检索的关键字
            String keywords = "%" + searchFlightTextfield.getText() + "%";
            //System.out.println(keywords);
            //传参  执行SQL语句
            List<Object> likeparams = new ArrayList<Object>();
            likeparams.add(keywords);
            List<Map<String, Object>> selectedlist = flightUtils.Select_Where_A_like_B(attribute, likeparams);
         //   System.out.println(selectedlist);

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

            // FlightTable.setItems(FlightList);
        });

        //添加航班事件
        button_addflight.setOnAction(event ->
        {
            System.out.println(flighttextField.get(0).getText());
            Flight tmp = new Flight();
            tmp.setId(flighttextField.get(0).getText());
            tmp.setCom(flighttextField.get(1).getText());
            tmp.setModel(flighttextField.get(2).getText());
            tmp.setStime(flighttextField.get(3).getText());
            tmp.setEtime(flighttextField.get(4).getText());
            tmp.setStart(flighttextField.get(5).getText());
            tmp.setDist(flighttextField.get(6).getText());
            tmp.setPrice(Float.parseFloat(flighttextField.get(7).getText()));
            tmp.setLeft(Integer.parseInt(flighttextField.get(8).getText()));

            List<Object> paras = new ArrayList<Object>();
            paras.add(tmp.getId());
            paras.add(tmp.getCom());
            paras.add(tmp.getModel());
            paras.add(tmp.getStime());
            paras.add(tmp.getEtime());
            paras.add(tmp.getStart());
            paras.add(tmp.getDist());
            paras.add(tmp.getPrice());
            paras.add(tmp.getLeft());
            //若数据库添加成功 则显示到列表
            if (flightUtils.InsertFlight(paras)) {
                FlightObList.add(tmp);
            }
        });

        //删除选中航班事件
        button_deleteflight.setOnAction(event ->
        {
            Flight selected = (Flight) FlightTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                //若数据库删除成功 那就删除
                List<Object> paras = new ArrayList<Object>();
                paras.add(selected.getId());
                if (flightUtils.DeleteFlightById(paras))
                    FlightObList.remove(selected);
            }
        });
      button_logout.setOnAction(event -> {
          FlightStage.hide();
         LoginWindow LW= new LoginWindow();
         Stage s1=new Stage();
           LW.start(s1);
      });




    }

    private  void User_Buttonevent(){
       button_searchuser.setOnAction(event -> {

           //  获取要检索的属性
           String attribute=userMap.get(userparams.getValue());
           //获取要检索的关键字
           String keywords = "%" + searchUserTextfield.getText() + "%";

           //传参  执行SQL语句
           List<Object> likeparams = new ArrayList<Object>();
           likeparams.add(keywords);
           List<Map<String, Object>> selectedlist = userUtils.Select_Where_A_like_B(attribute,likeparams);

           UserObList.clear();
           for (int i = 0; i < selectedlist.size(); i++) {
               User tmp =new User();
               tmp.setUser(selectedlist.get(i).get("user").toString());
               tmp.setPassword(selectedlist.get(i).get("pass").toString());
               tmp.setName(selectedlist.get(i).get("name").toString());
               tmp.setSex(selectedlist.get(i).get("old").toString());
               tmp.setIdentity(selectedlist.get(i).get("sfz").toString());
               UserObList.add(tmp);
           }



       });

      button_deleteuser.setOnAction(event -> {

              User selected = (User) UserTable.getSelectionModel().getSelectedItem();
              if (selected != null) {
                  //若数据库删除成功 那就删除
                  List<Object> paras = new ArrayList<Object>();
                  paras.add(selected.getIdentity() );

                      if(userUtils.DeleteUserById(paras)){
                        UserObList.remove(selected);
                      }
              }

      });




    }

    String CutPoint0(String time)
    {
         return time.substring(0,time.length()-5);
    }

}
