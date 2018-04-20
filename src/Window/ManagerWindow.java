package Window;

import Entity.Flight;
import Utils.FlightUtils;
import Utils.MysqlUtils;
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


import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerWindow {
    private FlightUtils flightUtils;
    private Parent root;

    private TableView FlightTable;
    private ObservableList<Flight> FlightObList;

    private Button button_addflight;
    private Button button_deleteflight;
    private Button button_searchflight;
    private List<TextField> flighttextField;
    private TextField searchTextField;

    private ComboBox<String> flightParams;
    private Map<String,String> flightMap;


    public ManagerWindow(){
        Stage FlightStage=new Stage();
        try{
             root = FXMLLoader.load(getClass().getResource("../fxml/flight.fxml"));
        } catch (Exception e){
            e.printStackTrace();
        }

        Scene scene=new Scene(root,1024,768);
        FlightStage.setTitle("hello");
        FlightStage.setScene(scene);
        FlightStage.show();

        /* 连接数据库*/
      flightUtils=new FlightUtils();

        InitControl();
        Buttonevent();
    }


   //初始化控件
    public void InitControl(){
        FlightTable=(TableView)root.lookup("#FlightTable");
        FlightObList=FXCollections.observableArrayList();

          /*绑定Flight 与 observablelist*/
        String[] flightpara=new String[] {"id","com","model","stime","etime","start","dist","price"};

        ObservableList<TableColumn> flight_observableList=FlightTable.getColumns();


        for(int i=0;i<flight_observableList.size();i++)
        {
            //先绑定
            flight_observableList.get(i).setCellValueFactory(new PropertyValueFactory<Flight,String>(flightpara[i])); //与Flight中的属性关联
            if(i!=7)
            flight_observableList.get(i).setCellFactory(TextFieldTableCell.<Flight>forTableColumn());  // 设置成表格可编辑
        }
        flight_observableList.get(7).setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter())); // float 转string 出错 默认不带转换器
        for(int i=0;i<8;i++)
            System.out.println(flight_observableList.get(i).getColumns());

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

        /*从数据库中获取 航班信息 加入到FlightList*/

        List<Map<String,Object>> list =flightUtils.SelectAllFlight();

        for(int i=0;i<list.size();i++)
        {
            Flight tmp=new Flight();

            tmp.setId(list.get(i).get("f_id").toString());
            tmp.setCom(list.get(i).get("f_com").toString());
            tmp.setEtime(list.get(i).get("f_etime").toString());
            tmp.setStime(list.get(i).get("f_stime").toString());
            tmp.setModel(list.get(i).get("f_model").toString());
            tmp.setStart(list.get(i).get("f_start").toString());
            tmp.setDist(list.get(i).get("f_dist").toString());
            tmp.setPrice(  Float.parseFloat( list.get(i).get("f_price").toString()));

            FlightObList.add(tmp);
        }
       FlightTable.setItems(FlightObList);


        /*      初始化控件  */
        button_addflight=(Button) root.lookup("#button_addflight");
        button_deleteflight=(Button) root.lookup("#button_deleteflight");
        button_searchflight=(Button) root.lookup("#button_search");
        searchTextField=(TextField) root.lookup("#searchTextfield");
        flightParams =(ComboBox<String>)root.lookup("#flightparams");
        initComboBox();

        flightParams.setItems(FXCollections.observableArrayList(flightMap.keySet()));


        flightParams.getSelectionModel().select(0);


         flighttextField=new ArrayList<TextField>();
        for(int i=0;i<8;i++)
            flighttextField.add( (TextField)root.lookup("#flightTextfield_"+i));

    }

    //初始化ComboBox
    public void initComboBox()
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

    }


    private void Buttonevent() {
        //查询航班事件
        button_searchflight.setOnAction(event -> {
            //  获取要检索的属性
            String attribute = flightMap.get(flightParams.getValue());
           // System.out.println(attribute);
            //获取要检索的关键字
            String keywords = "%" + searchTextField.getText() + "%";
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
                tmp.setEtime(selectedlist.get(i).get("f_etime").toString());
                tmp.setStime(selectedlist.get(i).get("f_stime").toString());
                tmp.setModel(selectedlist.get(i).get("f_model").toString());
                tmp.setStart(selectedlist.get(i).get("f_start").toString());
                tmp.setDist(selectedlist.get(i).get("f_dist").toString());
                tmp.setPrice(Float.parseFloat(selectedlist.get(i).get("f_price").toString()));
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

            List<Object> paras = new ArrayList<Object>();
            paras.add(tmp.getId());
            paras.add(tmp.getCom());
            paras.add(tmp.getModel());
            paras.add(tmp.getStime());
            paras.add(tmp.getEtime());
            paras.add(tmp.getStart());
            paras.add(tmp.getDist());
            paras.add(tmp.getPrice());
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




    }

}
