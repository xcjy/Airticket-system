package Utils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUtils extends MysqlUtils{

    public UserUtils() {
       this.getConnection();
    }

    public int isexst_user(String a,String b)throws SQLException {
        String sql="select * from user;";
       // Statement statement=connection.createStatement();
        //ResultSet resultSet = statement.executeQuery(sql);


        List<Map<String,Object>> result= new ArrayList<>();
        result= this.findModeResult(sql,null);
        int c= 0;
        String User;
        String paw;
        boolean ex=false;
       // while(resultSet.next())
         for(int i=0;i<result.size();i++)
        {
          //  User=resultSet.getString("user");
          //  paw=resultSet.getString("pass");
            User=result.get(i).get("user").toString();
            paw=result.get(i).get("pass").toString();

            if(User.equals(a)){
                ex=true;
                c=1;
                if(paw.equals(b))c=2;
            }
        }
     //   resultSet.close();
        return c;
    }

    public  int re_user(String a,String b,String c,String d,String e,String f,String g,int aggree  )throws SQLException
    {
        String sql="select * from user;";
        //Statement statement=connection.createStatement();
        //ResultSet resultSet = statement.executeQuery(sql);
        List<Map<String,Object>> result= new ArrayList<>();
        result=this.findModeResult(sql,null);

        int re=3;
        if(aggree==0)re=0;
        if(aggree==1){
            if(re==3&&(!b.equals(c)))re=2;
            else {
                String user;
              //  while (resultSet.next()) {
                for(int i=0;i<result.size();i++) {
                    //user = resultSet.getString("user");
                    user = result.get(i).get("user").toString();
                    if (user.equals(a)) {
                        re = 1;
                        break;
                    }
                }
              //  }
             //   resultSet.close();
            }
        }
        if(re==3)
        {
          //  Statement statement1=con.createStatement();
          //  sql="insert into user values("+"'"+a+"'"+","+"'"+b+"'"+","+"'"+d+"'"+","+"'"+e+"'"+","+"'"+f+"'"+","+"'"+g+"'"+")";
            sql="insert into user values(?,?,?,?,?,?)";
            List<Object> params=new ArrayList<>();
            params.add(a);
            params.add(b);
            params.add(d);
            params.add(e);
            params.add(f);
            params.add(g);
            this.updateByPreparedStatement(sql,params);

            //System.out.print(sql);
           // ResultSet resultSet1 = statement1.executeQuery(sql);
           // PreparedStatement pre=connection.prepareStatement(sql);
           // pre.executeUpdate();
        }
        return re;
    }
}
