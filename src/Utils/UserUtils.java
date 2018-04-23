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
    public int changepw(String a,String b,String c,String d)throws SQLException
    {
        int n=0;

        String sql="select * from user;";
       // Statement statement=con.createStatement();
       // ResultSet resultSet = statement.executeQuery(sql);
        List<Map<String,Object>> result= new ArrayList<>();
        result=this.findModeResult(sql,null);
        String User;
        String Sfz;
        boolean ex=false;
       // while(resultSet.next())
        for(int i=0;i<result.size();i++)
        {
         //   User=resultSet.getString("user");
         //   Sfz=resultSet.getString("sfz");
            User=result.get(i).get("user").toString();
            Sfz=result.get(i).get("sfz").toString();

            if(User.equals(a)){
                ex=true;
                if(Sfz.equals(d)) {
                    if(b.equals(c)){
                      //  sql = "UPDATE `airlineticket`.`user` SET `pass`="+"'"+b+"'"+" WHERE `sfz`='1';";
                      sql=   "UPDATE `airlineticket`.`user` SET `pass`= ? WHERE `sfz`=?";

                      //  PreparedStatement pre=con.prepareStatement(sql);
                       // pre.executeUpdate();
                        List<Object> params=new ArrayList<>();
                        params.add(b);
                        params.add(d);
                        this.updateByPreparedStatement(sql,params);
                        // System.out.print(sql);
                        //修改成功
                        n=0;
                    }
                    if(!b.equals(c))n=1;
                }
                else n=2;//身份证号码不匹配
            }
        }
        if(!ex)n=3;//该账号没有注册

     //   resultSet.close();
        return n;
    }

    public int isexst_admin(String a,String b)throws SQLException {
        String sql="select * from admin;";
        List<Map<String,Object>> result= new ArrayList<>();
        result= this.findModeResult(sql,null);
        int c= 0;
        String User;
        String paw;
        boolean ex=false;
        for(int i=0;i<result.size();i++)
        {
            User=result.get(i).get("identifier").toString();
            paw=result.get(i).get("adpss").toString();
            if(User.equals(a)){
                ex=true;
                c=1;
                if(paw.equals(b))c=2;
            }
        }
        return c;
    }


}
