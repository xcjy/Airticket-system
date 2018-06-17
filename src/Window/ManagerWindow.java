package Window;

import Entity.Flight;
import Entity.Order;
import Entity.User;
import Utils.FlightUtils;
import Utils.OrderUtils;
import Utils.UserUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;


import java.util.*;

public class ManagerWindow {

    private FlightUtils flightUtils;
    private UserUtils userUtils;
    private OrderUtils orderUtils;

    private Parent root;

    private TableView FlightTable;
    private TableView UserTable;
    private TableView OrderTable;


    private ObservableList<Flight> FlightObList;
    private ObservableList<User> UserObList;
    private ObservableList<Order> OrderObList;


    private JFXButton button_searchflight;

    private JFXButton button_logout;

    private JFXButton button_searchuser;

    private JFXButton button_searchorder;






    private JFXTextField searchFlightTextfield;
    private JFXTextField searchUserTextfield;
    private JFXTextField searchOrderTextfield;


    private JFXComboBox<String> flightParams;
    private JFXComboBox<String> userparams;
    private JFXComboBox<String> orderparams;


    private Map<String,String> flightMap;
    private Map<String,String> userMap;
    private Map<String,String> orderMap;

    private Stage FlightStage;

    private MenuItem addflight=new MenuItem("添加航班");
    private MenuItem deleteflight=new MenuItem("删除航班");
    private MenuItem deleteuser=new MenuItem("删除用户");
    private MenuItem deleteOrder=new MenuItem("删除订单");


    public ManagerWindow(){

         FlightStage=new Stage();
        try{
             root = FXMLLoader.load(getClass().getResource("../fxml/Manager.fxml"));
        } catch (Exception e){
            e.printStackTrace();
        }

        Scene scene=new Scene(root,1024,768);
        FlightStage.setTitle("管理员");
        FlightStage.setScene(scene);
        FlightStage.initStyle(StageStyle.UTILITY);
        FlightStage.show();


        /* 连接数据库*/
      flightUtils=new FlightUtils();
      userUtils=new UserUtils();
      orderUtils=new OrderUtils();

        InitFlightControl();
        Flight_Buttonevent();
        InitUserControl();
        User_Buttonevent();

        InitOrderControl();
        Order_Buttonevent();



    }



   //初始化控件
    public void InitFlightControl(){



        //寻找控件
        FlightTable=(TableView)root.lookup("#FlightTable");
        FlightObList=FXCollections.observableArrayList();

        button_searchflight=(JFXButton) root.lookup("#button_searchflight");
        button_logout=(JFXButton) root.lookup("#button_logout");

        searchFlightTextfield=(JFXTextField) root.lookup("#searchFlightTextfield");
        flightParams =(JFXComboBox<String>)root.lookup("#flightparams");


        initFlightComboBox();
        flightParams.setItems(FXCollections.observableArrayList(flightMap.keySet()));
        flightParams.getSelectionModel().select(0);


        //设置右键
        ContextMenu cm_flighttable=new ContextMenu();
        cm_flighttable.getItems().add(addflight);
        cm_flighttable.getItems().add(deleteflight);


        FlightTable.setContextMenu(cm_flighttable);


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

   EditFlightTable(flight_observableList);

        /*从数据库中获取 航班信息 加入到FlightList*/

        List<Map<String,Object>> list =flightUtils.SelectAllFlight();

       RefreshFlightTable(list);
       FlightTable.setItems(FlightObList);
    }

    public void InitUserControl(){
        //寻找控件
        UserTable=(TableView)root.lookup("#UserTable");
        UserObList=FXCollections.observableArrayList();

        button_searchuser=(JFXButton) root.lookup("#button_searchuser");

        searchUserTextfield=(JFXTextField) root.lookup("#searchUserTextfield");
        userparams =(JFXComboBox<String>)root.lookup("#userparams");

        initUserComboBox();

        userparams.setItems(FXCollections.observableArrayList(userMap.keySet()));
        userparams.getSelectionModel().select(0);

       ContextMenu cm_usertable=new ContextMenu();
       cm_usertable.getItems().add(deleteuser);
       UserTable.setContextMenu(cm_usertable);

        /*绑定User  与 observablelist*/
        String[] userpara=new String[] {"user","password","name","sex","identity","phone","email"};

        ObservableList<TableColumn> user_observableList=UserTable.getColumns();

        for(int i=0;i<user_observableList.size();i++)
        {
            //先绑定
            user_observableList.get(i).setCellValueFactory(new PropertyValueFactory<User,String>(userpara[i])); //与User中的属性关联

            user_observableList.get(i).setCellFactory(TextFieldTableCell.<User>forTableColumn());  // 设置成表格可编辑
        }

      EditUserTable(user_observableList);

        List<Map<String,Object>> selectuserlist= userUtils.SelectAllUser();


        for(int i=0;i<selectuserlist.size();i++){
            User tmpuser =new User();
            tmpuser.setIdentity(selectuserlist.get(i).get("sfz").toString());
            tmpuser.setUser(selectuserlist.get(i).get("user").toString());
            tmpuser.setPassword(selectuserlist.get(i).get("pass").toString());
            tmpuser.setSex(selectuserlist.get(i).get("sex").toString());
            tmpuser.setName(selectuserlist.get(i).get("name").toString());
            tmpuser.setPhone(selectuserlist.get(i).get("phone").toString());
            tmpuser.setEmail(selectuserlist.get(i).get("email").toString());
            UserObList.add(tmpuser);
        }
      UserTable.setItems(UserObList);



    }

    public void InitOrderControl(){

        //寻找控件
        OrderTable=(TableView)root.lookup("#OrderTable");
        OrderObList=FXCollections.observableArrayList();


        button_searchorder=(JFXButton) root.lookup("#button_searchorder");

        searchOrderTextfield=(JFXTextField) root.lookup("#searchOrderTextfield");
        orderparams =(JFXComboBox<String>)root.lookup("#orderparams");

        initOrderComboBox();

        orderparams.setItems(FXCollections.observableArrayList(orderMap.keySet()));
        orderparams.getSelectionModel().select(0);

        //设置右键
        ContextMenu cm_ordertable=new ContextMenu();
        cm_ordertable.getItems().add(deleteOrder);
        OrderTable.setContextMenu(cm_ordertable);



        String []orderpara=new String[]{"orderid","p_name","p_id","f_id","f_com","f_model","f_stime","f_etime","f_start","f_end","f_price"};
        ObservableList<TableColumn> Order_observableList=OrderTable.getColumns();
        for(int i=0;i<Order_observableList.size();i++) {
            Order_observableList.get(i).setCellValueFactory(new PropertyValueFactory<Order,String>(orderpara[i])); //与Order众的属性关联

            Order_observableList.get(i).setCellFactory(TextFieldTableCell.<Order>forTableColumn());  //设置表格可编辑
        }

      EditOrderTable(Order_observableList);  //编辑表格事件


        List<Map<String,Object>> list =orderUtils.SelectAllOrder();

      RefreshOrderTable(list);
        OrderTable.setItems(OrderObList);


    }



    private void EditFlightTable(ObservableList<TableColumn> flight_observableList)
    {

        flight_observableList.get(0).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                //String sql="UPDATE `airlineticket`.`flight` SET "+ attr +"= ? WHERE `f_id`=?";
                // 界面修改航班信息
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
    }

    private void EditUserTable( ObservableList<TableColumn> user_observableList)
    {
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

        user_observableList.get(5).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((User) UserTable.getSelectionModel().getSelectedItem()).getIdentity());

                if( userUtils.UpDate_A_By_ID("phone",paras) ){
                    ((User) UserTable.getSelectionModel().getSelectedItem()).setPhone(event.getNewValue().toString());
                }

            }
        });

        user_observableList.get(6).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((User) UserTable.getSelectionModel().getSelectedItem()).getIdentity());

                if( userUtils.UpDate_A_By_ID("email",paras) ){
                    ((User) UserTable.getSelectionModel().getSelectedItem()).setEmail(event.getNewValue().toString());
                }

            }
        });


    }
    private void EditOrderTable(ObservableList<TableColumn> Order_observableList)
    {
        Order_observableList.get(0).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                //String sql="UPDATE `airlineticket`.`flight` SET "+ attr +"= ? WHERE `f_id`=?";
                // 界面修改订单信息
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((Order) OrderTable.getSelectionModel().getSelectedItem()).getOrderid()); //获取订单id

                if( orderUtils.UpDate_A_By_ID("orderid",paras) ){
                    ((Order) OrderTable.getSelectionModel().getSelectedItem()).setOrderid(event.getNewValue().toString());
                }
            }
        });

        Order_observableList.get(1).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                // 界面修改订单信息
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((Order) OrderTable.getSelectionModel().getSelectedItem()).getOrderid()); //获取订单id

                if( orderUtils.UpDate_A_By_ID("p_name",paras) ){
                    ((Order) OrderTable.getSelectionModel().getSelectedItem()).setP_name(event.getNewValue().toString());
                }
            }
        });

        Order_observableList.get(2).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                // 界面修改订单信息
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((Order) OrderTable.getSelectionModel().getSelectedItem()).getOrderid()); //获取订单id

                if( orderUtils.UpDate_A_By_ID("p_id",paras) ){
                    ((Order) OrderTable.getSelectionModel().getSelectedItem()).setP_id(event.getNewValue().toString());
                }
            }
        });

        Order_observableList.get(3).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                // 界面修改订单信息
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((Order) OrderTable.getSelectionModel().getSelectedItem()).getOrderid()); //获取订单id

                if( orderUtils.UpDate_A_By_ID("f_id",paras) ){
                    ((Order) OrderTable.getSelectionModel().getSelectedItem()).setF_id(event.getNewValue().toString());
                }
            }
        });

        Order_observableList.get(4).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                // 界面修改订单信息
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((Order) OrderTable.getSelectionModel().getSelectedItem()).getOrderid()); //获取订单id

                if( orderUtils.UpDate_A_By_ID("f_com",paras) ){
                    ((Order) OrderTable.getSelectionModel().getSelectedItem()).setF_com(event.getNewValue().toString());
                }
            }
        });

        Order_observableList.get(5).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                // 界面修改订单信息
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((Order) OrderTable.getSelectionModel().getSelectedItem()).getOrderid()); //获取订单id

                if( orderUtils.UpDate_A_By_ID("f_model",paras) ){
                    ((Order) OrderTable.getSelectionModel().getSelectedItem()).setF_model(event.getNewValue().toString());
                }
            }
        });

        Order_observableList.get(6).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                // 界面修改订单信息
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((Order) OrderTable.getSelectionModel().getSelectedItem()).getOrderid()); //获取订单id

                if( orderUtils.UpDate_A_By_ID("f_stime",paras) ){
                    ((Order) OrderTable.getSelectionModel().getSelectedItem()).setF_stime(event.getNewValue().toString());
                }
            }
        });

        Order_observableList.get(7).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                // 界面修改订单信息
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((Order) OrderTable.getSelectionModel().getSelectedItem()).getOrderid()); //获取订单id

                if( orderUtils.UpDate_A_By_ID("f_etime",paras) ){
                    ((Order) OrderTable.getSelectionModel().getSelectedItem()).setF_etime(event.getNewValue().toString());
                }
            }
        });

        Order_observableList.get(8).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                // 界面修改订单信息
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((Order) OrderTable.getSelectionModel().getSelectedItem()).getOrderid()); //获取订单id

                if( orderUtils.UpDate_A_By_ID("f_start",paras) ){
                    ((Order) OrderTable.getSelectionModel().getSelectedItem()).setF_start(event.getNewValue().toString());
                }
            }
        });

        Order_observableList.get(9).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                // 界面修改订单信息
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((Order) OrderTable.getSelectionModel().getSelectedItem()).getOrderid()); //获取订单id

                if( orderUtils.UpDate_A_By_ID("f_end",paras) ){
                    ((Order) OrderTable.getSelectionModel().getSelectedItem()).setF_end(event.getNewValue().toString());
                }
            }
        });

        Order_observableList.get(10).setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                // 界面修改订单信息
                String a_value=event.getNewValue().toString();  //获取文本框修改的值
                List<Object> paras=new ArrayList<Object>();      //传参
                paras.add(a_value);
                paras.add( ((Order) OrderTable.getSelectionModel().getSelectedItem()).getOrderid()); //获取订单id

                if( orderUtils.UpDate_A_By_ID("f_price",paras) ){
                    ((Order) OrderTable.getSelectionModel().getSelectedItem()).setF_price(event.getNewValue().toString());
                }
            }
        });


    }




    //初始化ComboBox
    private void initFlightComboBox()
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

    private void initUserComboBox(){
        userMap=new HashMap<>();
        userMap.put("用户名","user");
        userMap.put("姓名","name");
        userMap.put("身份证","sfz");
        userMap.put("电话","phone");
        userMap.put("电子邮件","email");
    }

    private void initOrderComboBox(){
        orderMap=new HashMap<>();
        orderMap.put("订单编号","orderid");
        orderMap.put("身份证","p_id");
        orderMap.put("航班编号","f_id");
        orderMap.put("姓名","p_name");
        orderMap.put("航空公司","f_com");
        orderMap.put("起点","f_start");
        orderMap.put("终点","f_end");


    }

    void RefreshFlightTable(   List<Map<String,Object>> selectedlist) {
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
    }

    void RefreshOrderTable(List<Map<String,Object>> list){
        OrderObList.clear();
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
    }

    private void Flight_Buttonevent() {


        //查询航班事件
        button_searchflight.setOnAction(event -> {
            //  获取要检索的属性
            String attribute = flightMap.get(flightParams.getValue());
            //获取要检索的关键字
            String keywords = "%" + searchFlightTextfield.getText() + "%";
            //传参  执行SQL语句
            List<Object> likeparams = new ArrayList<Object>();
            likeparams.add(keywords);
            List<Map<String, Object>> selectedlist = flightUtils.Select_Where_A_like_B(attribute, likeparams);

          RefreshFlightTable(selectedlist);

        });

        //添加航班事件
        addflight.setOnAction(event -> {

            //自定义Dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("添加航班");
            dialog.setHeaderText("请填写航班信息");


            ButtonType loginButtonType = new ButtonType("确认", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 70, 10, 10));

            TextField f_id = new TextField();
            f_id.setPromptText("航班编号");
            TextField f_com = new TextField();
            f_com.setPromptText("航空公司");

            TextField f_model = new TextField();
            f_model.setPromptText("机型");

            TextField f_stime = new TextField();
            f_stime.setPromptText("起飞时间");

            TextField f_etime = new TextField();
            f_etime.setPromptText("到达时间");

            TextField f_start = new TextField();
            f_start.setPromptText("起点");

            TextField f_dist = new TextField();
            f_dist.setPromptText("终点");

            TextField f_price = new TextField();
            f_price.setPromptText("价格");

            TextField f_left = new TextField();
            f_left.setPromptText("票数");

            grid.add(new Label("航班编号:"), 0, 0);
            grid.add(f_id, 1, 0);
            grid.add(new Label("航空公司:"), 0, 1);
            grid.add(f_com, 1, 1);
            grid.add(new Label("机型:"), 0, 2);
            grid.add(f_model, 1, 2);
            grid.add(new Label("起飞时间:"), 0, 3);
            grid.add(f_stime, 1, 3);
            grid.add(new Label("到达时间:"), 0, 4);
            grid.add(f_etime, 1, 4);

            grid.add(new Label("起点:"), 0, 5);
            grid.add(f_start, 1, 5);

            grid.add(new Label("终点:"), 0, 6);
            grid.add(f_dist, 1, 6);

            grid.add(new Label("价格:"), 0, 7);
            grid.add(f_price, 1, 7);

            grid.add(new Label("票数:"), 0, 8);
            grid.add(f_left, 1, 8);




            dialog.getDialogPane().setContent(grid);

// 默认光标在上
            Platform.runLater(() -> f_id.requestFocus());

            Optional<ButtonType> result = dialog.showAndWait();


            //如果是确认键
            if (      result.get().getButtonData()==ButtonBar.ButtonData.OK_DONE)
            {
                Flight tmp = new Flight();
                tmp.setId(f_id.getText());
                tmp.setCom(f_com.getText());
                tmp.setModel(f_model.getText());
                tmp.setStime(f_stime.getText());
                tmp.setEtime(f_etime.getText());
                tmp.setStart(f_start.getText());
                tmp.setDist(f_dist.getText());
                tmp.setPrice( Float.parseFloat(  f_price.getText()));
                tmp.setLeft(Integer.parseInt(f_left.getText()));

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
            }

        });

        //删除选中航班事件
        deleteflight.setOnAction(event -> {
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
         try {
             LW.start(s1);
         }
         catch (Exception e){
             e.printStackTrace();
         }

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
               tmp.setSex(selectedlist.get(i).get("sex").toString());
               tmp.setIdentity(selectedlist.get(i).get("sfz").toString());
               tmp.setPhone(selectedlist.get(i).get("phone").toString());
               tmp.setEmail(selectedlist.get(i).get("email").toString());
               UserObList.add(tmp);
           }



       });

       //删除用户
       deleteOrder.setOnAction(event -> {
           Order selected = (Order) OrderTable.getSelectionModel().getSelectedItem();
           if (selected != null) {
               //若数据库删除成功 那就删除
               List<Object> paras = new ArrayList<Object>();
               paras.add(selected.getOrderid() );

               if(orderUtils.DeleteOrderById(paras)){
                   OrderObList.remove(selected);
               }
           }
       });




    }

    private void Order_Buttonevent(){
        button_searchorder.setOnAction(event -> {

            //  获取要检索的属性
            String attribute=orderMap.get(orderparams.getValue());
            //获取要检索的关键字
            String keywords = "%" + searchOrderTextfield.getText() + "%";
            //传参  执行SQL语句
            List<Object> likeparams = new ArrayList<Object>();
            likeparams.add(keywords);
            List<Map<String, Object>> list = orderUtils.Select_Where_A_like_B(attribute,likeparams);
           RefreshOrderTable(list);



        });

        //删除用户
        deleteuser.setOnAction(event -> {
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

    //格式化日期
    String CutPoint0(String time)
    {
         return time.substring(0,time.length()-5);
    }

}
