package mx.uv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static String url = "jdbc:mysql://127.0.0.1:3306/tamagotchi";
    private static String DriverName = "com.mysql.cj.jdbc.Driver";
    private static String username = "root";
    private static String pass = "137913";
    private static Connection connection = null;

    public static Connection getConnection(){
        try{
            Class.forName(DriverName);
            connection = (Connection)DriverManager.getConnection(url, username, pass);
        }catch (SQLException e){
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("No se encontro el driver");
        }
        return connection;
    }
    
}

