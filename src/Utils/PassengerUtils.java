package Utils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PassengerUtils extends MysqlUtils {

    public PassengerUtils() {
        this.getConnection();
    }

     public List<Map<String,Object>> SelectAllPsg(String user)
     {
         String sql="SELECT * FROM airlineticket.passenger where user='"+user+"'";
         List< Map<String,Object> >  list = new ArrayList<  Map<String,Object>>();

         try {
             list =this.findModeResult(sql, null);
         }
         catch (SQLException e){
             e.printStackTrace();
         }
         return list;
     }






    public List<Map<String,Object>> SelectAllUser()
    {
        String sql="SELECT * FROM airlineticket.user";
        List< Map<String,Object> >  list = new ArrayList<  Map<String,Object>>();

        try {
            list =this.findModeResult(sql, null);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    /*  删除一个航班 通过ID */
    public boolean DeleteUserById(List<Object>params)
    {
        String sql="DELETE FROM `airlineticket`.`user` WHERE `sfz`=?";
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
        String sql="UPDATE `airlineticket`.`user` SET "+ attr +"= ? WHERE `sfz`=?";
        try {
            this.updateByPreparedStatement(sql, params);
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
