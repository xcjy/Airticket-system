package Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlightUtils extends MysqlUtils{

    public FlightUtils(){
       this.getConnection();
    }


    /* 添加一个航班 参数为List数组*/
    public  boolean InsertFlight(List<Object> params)
    {

        String sql="INSERT INTO `airlineticket`.`flight` VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";
        try {
              this.updateByPreparedStatement(sql, params);
             return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /*  删除一个航班 通过ID */
    public boolean DeleteFlightById(List<Object>params)
    {
        String sql="DELETE FROM `airlineticket`.`flight` WHERE `f_id`=?";
        try {
             this.updateByPreparedStatement(sql, params);
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
           return false;
        }

    }

    /* 更新航班信息通过ID*/
    public boolean UpDate_A_By_ID (String attr ,List <Object> params)
    {

       // String sql="UPDATE `airlineticket`.`flight` SET `f_id`= ? WHERE `f_id`=?";
        String sql="UPDATE `airlineticket`.`flight` SET "+ attr +"= ? WHERE `f_id`=?";
        try {
             this.updateByPreparedStatement(sql, params);
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //模糊查询航班  attribute属性 paras为参数
    public List<Map<String,Object>> Select_Where_A_like_B(String attribute,List<Object> paras)
    {            // like '%?%'
        String sql="SELECT * FROM airlineticket.flight where "+attribute+" like ?";
        List< Map<String,Object> >  list = new ArrayList<  Map<String,Object>>();
        try{
            list=this.findModeResult(sql, paras);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return  list;
    }

    public List<Map<String,Object>> Select_Flight_By_start_end_ftime(List<Object> paras)
    {
       String sql=" SELECT * FROM airlineticket.flight where f_start like ? and f_dist like ? and f_stime like ?";
        List< Map<String,Object> >  list = new ArrayList<  Map<String,Object>>();
        try{
            list=this.findModeResult(sql, paras);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return  list;

    }
    //查询全部航班
     public List<Map<String, Object>> SelectAllFlight()
     {
          String sql ="SELECT * FROM airlineticket.flight";
          List< Map<String,Object> >  list = new ArrayList<  Map<String,Object>>();

          try {
               list =this.findModeResult(sql, null);
          }
          catch (SQLException e){
              e.printStackTrace();
          }
         return list;

     }



}
