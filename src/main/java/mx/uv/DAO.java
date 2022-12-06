package mx.uv;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {
    public static Conexion c = new  Conexion();

    public static List<Datos> dameUsers(){
        Statement stm = null;
        ResultSet rs = null;
        List<Datos> resultado = new ArrayList<>();
        Connection cc = null;
        cc = c.getConnection();
        try{
            String sql = "select * from datos";
            stm = (Statement)cc.createStatement();
            rs =stm.executeQuery(sql);
            while(rs.next()){
                Datos u = new Datos(rs.getString("usuario"), rs.getString("pokemon")
                        , rs.getString("contrasena"), rs.getString("mascota"), rs.getInt("vida")
                        , rs.getInt("energia"), rs.getInt("hambre"), rs.getInt("suciedad"));
                resultado.add(u);
            }
            rs = null;
        }catch(Exception e){
            System.out.println(e);
        } finally {
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException e){
                    System.out.println(e);
                }
            }
        }

        return resultado;
    }

    public static String crearUser(Datos u){
        PreparedStatement stm = null;
        Connection cc = null;
        String msj="";

        cc=c.getConnection();
        try{
            String sql = "insert into datos (usuario, pokemon, contrasena, mascota, vida, energia, hambre, suciedad) " +
                    "values (?,?,?,?,?,?,?,?);";
            stm = (PreparedStatement) cc.prepareStatement(sql);
            stm.setString(1, u.getUsuario());
            stm.setString(2, u.getPokemon());
            stm.setString(3, u.getContrasena());
            stm.setString(4, u.getMascota());
            stm.setInt(5, u.getVida());
            stm.setInt(6, u.getEnergia());
            stm.setInt(7, u.getHambre());
            stm.setInt(8, u.getSuciedad());

            if(stm.executeUpdate() > 0)
                msj+="El User se agrego";
            else
                msj+="El User no se agrego";
        }catch(Exception e){
            System.out.println(e);
        }
        return msj;
    }

    public static String iniciarSesion(String nameUser, String password){
        Statement stm = null;
        ResultSet rs = null;
        List<Datos> resultado = new ArrayList<>();
        Connection cc = null;
        String msj="";
        cc=c.getConnection();
        try{
            String sql = "select id from datos where usuario ='"+nameUser+"' and contrasena= '"+password+"';";
            stm = (Statement)cc.createStatement();
            rs =stm.executeQuery(sql);

            Datos u = new Datos(rs.getString("usuario"), rs.getString("pokemon")
                        , rs.getString("contrasena"), rs.getString("mascota"), rs.getInt("vida")
                        , rs.getInt("energia"), rs.getInt("hambre"), rs.getInt("suciedad"));
             System.out.println(u.toString());

        }catch(Exception e){
            System.out.println(e);
        }
        return msj;
    }
}
