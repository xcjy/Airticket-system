package Utils;
import java.sql.*;

public class UserUtils extends MysqlUtils{

    public UserUtils() {
       this.getConnection();
    }

    public int isexst_user(String a,String b)throws SQLException {
        String sql="select * from user;";
        Statement statement=connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int c= 0;
        String User;
        String paw;
        boolean ex=false;
        while(resultSet.next())
        {
            User=resultSet.getString("user");
            paw=resultSet.getString("pass");
            if(User.equals(a)){
                ex=true;
                c=1;
                if(paw.equals(b))c=2;
            }
        }
        resultSet.close();
        return c;
    }

    public  int re_user(String a,String b,String c,String d,String e,String f,String g,int aggree  )throws SQLException
    {
        String sql="select * from user;";
        Statement statement=connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int re=3;
        if(aggree==0)re=0;
        if(aggree==1){
            if(re==3&&(!b.equals(c)))re=2;
            else {
                String user;
                while (resultSet.next()) {
                    user = resultSet.getString("user");
                    if (user.equals(a)) {
                        re = 1;
                        break;
                    }
                }
                resultSet.close();
            }
        }
        if(re==3)
        {
          //  Statement statement1=con.createStatement();
            sql="insert into user values("+"'"+a+"'"+","+"'"+b+"'"+","+"'"+d+"'"+","+"'"+e+"'"+","+"'"+f+"'"+","+"'"+g+"'"+")";
            //System.out.print(sql);
           // ResultSet resultSet1 = statement1.executeQuery(sql);
            PreparedStatement pre=connection.prepareStatement(sql);
            pre.executeUpdate();
        }
        return re;
    }
}
