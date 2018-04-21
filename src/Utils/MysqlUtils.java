package Utils;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlUtils {
       /*  数据库连接信息 */
          private static  final String DRIVER="com.mysql.jdbc.Driver";
          //? 这里指明编码格式并且不使用ssl
          private static  final String URL="jdbc:mysql://localhost:3306/airlineticket?useUnicode=true&characterEncoding=utf-8&useSSL=false";
          private static  final String USERNAME="root";
          private static  final String PASSWORD="123456";

          public Connection connection;
          private PreparedStatement preparedStatement;
          private ResultSet resultSet;

          public MysqlUtils(){
              try{
                  //注册JDBC驱动
                  Class.forName(DRIVER);
              }
                 catch (Exception e){
                   e.printStackTrace();
                 }
          }
          //获取连接
        public void getConnection(){
              try{
                  connection=DriverManager.getConnection(URL,USERNAME,PASSWORD);
              }
              catch (SQLException e){
                e.printStackTrace();
              }
        }
          //关闭连接
        public void closeConnection(){
              try{
              connection.close();}
              catch (SQLException e){
                  e.printStackTrace();
              }
        }

        /*  实现增、删、改*/
    public boolean updateByPreparedStatement(String sql, List<Object>params)throws SQLException{
        boolean flag = false;
        int result = -1;
        preparedStatement = connection.prepareStatement(sql);
        int index = 1;
        if(params != null && !params.isEmpty()){
            for(int i=0; i<params.size(); i++){
                preparedStatement.setObject(index++, params.get(i));
            }
        }
        result = preparedStatement.executeUpdate();  //返回受影响的行数
        flag = result > 0 ? true : false;
        return flag;
    }




       /*查询多条记录*/
    public List<Map<String, Object>> findModeResult(String sql, List<Object> params) throws SQLException{
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int index = 1;
        preparedStatement = connection.prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i = 0; i<params.size(); i++){
                preparedStatement.setObject(index++, params.get(i));  //填上SQL的?
            }
        }
        resultSet = preparedStatement.executeQuery();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();      //获取ResultSet有多少列

        while(resultSet.next()){
            Map<String, Object> map = new HashMap<String, Object>();
            for(int i=0; i<cols_len; i++){                    //将一条记录装入list
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);
                if(cols_value == null){
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }
        return list;
    }






}
