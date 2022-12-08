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

    public static String crearJugador(Datos u){
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
            stm.setInt(5, 100);
            stm.setInt(6, 100);
            stm.setInt(7, 0);
            stm.setInt(8, 0);

            if(stm.executeUpdate() > 0)
                msj+="El User se agrego";
            else
                msj+="El User no se agrego";
        }catch(Exception e){
            System.out.println(e);
        }
        return msj;
    }

    public static Boolean iniciarSesion(String nameUser, String password){
        Statement stm = null;
        ResultSet rs = null;
        List<Datos> resultado = new ArrayList<>();
        Connection cc = null;
        String msj="";
        cc=c.getConnection();
        try{
            String sql = "select id from datos where usuario ='"+nameUser+"' and contrasena='"+password+"';";
            stm = (Statement)cc.createStatement();
            rs =stm.executeQuery(sql);

            while(rs.next()){
                Datos u = new Datos(rs.getInt("id"));
                if(u!=null){
                    return true;
                }
            }

        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }


    public static Datos dameUser(String nameUser, String password){
        Statement stm = null;
        ResultSet rs = null;
        List<Datos> resultado = new ArrayList<>();
        Connection cc = null;
        cc=c.getConnection();
        try{
            String sql = "select usuario, energia, hambre, mascota, pokemon, suciedad, vida  from datos where usuario ='"+nameUser+"' and contrasena='"+password+"';";

            stm = (Statement)cc.createStatement();
            rs =stm.executeQuery(sql);

            while(rs.next()){
                Datos u = new Datos(rs.getInt("id"));
                if(u!=null){
                    return u;
                }
            }

        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static Boolean actualizar(Datos u){
        Statement stm = null;
        ResultSet rs = null;
        List<Datos> resultado = new ArrayList<>();
        Connection cc = null;
        String msj="";
        cc=c.getConnection();
        try{
            String query = "update datos set vida = ?, energia = ?, suciedad = ?, hambre = ? where usuario = ?;";
      PreparedStatement preparedStmt = cc.prepareStatement(query);
      preparedStmt.setInt   (1, u.getVida());
      preparedStmt.setInt(2, u.getEnergia());
      preparedStmt.setInt(3, u.getSuciedad());
      preparedStmt.setInt(4, u.getHambre());
      preparedStmt.setString(5, u.getUsuario());

      // execute the java preparedstatement
      preparedStmt.executeUpdate();
      return true;


            // String sql = "update datos set vida ='"+u.getVida()+"', energia ="+u.getEnergia()+"', suciedad='"+u.getSuciedad()+"', hambre = '"+
            // u.getHambre()+" where usuario='"+u.getUsuario()+"';'";
            // stm = (Statement)cc.createStatement();
            // rs =stm.executeQuery(sql);
            // return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
}
