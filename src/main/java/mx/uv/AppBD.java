package mx.uv;

import static spark.Spark.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Hello world!
 *
 */
public class AppBD 
{
    private static Gson gson = new Gson();
    public static void main( String[] args )
    {
        //port(80);
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            System.out.println(accessControlRequestHeaders);
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            System.out.println(accessControlRequestMethod);
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        before((req, res) -> res.header("Access-Control-Allow-Origin", "*"));
        System.out.println("ESTÁ EN FUNCIONAMIENTO.");

        post("/crear", (req, res) -> {
            String datosJugador = req.body();
            Datos u = gson.fromJson(datosJugador, Datos.class);
            JsonObject oRespuesta = new JsonObject();
            if(u==null){
                //res.status(400);
                oRespuesta.addProperty("mensaje", false);
                return oRespuesta;
            }
            res.status(200);
            return DAO.crearJugador(u);
        });

        post("/actualizar", (req, res) -> {
            String datosJugador = req.body();
            Datos datos = gson.fromJson(datosJugador, Datos.class);
            Datos u = new Datos(datos.getUsuario(), datos.getVida() + "", datos.getEnergia() + "", datos.getSuciedad() + "", datos.getHambre() + "");
            JsonObject oRespuesta = new JsonObject();
            if(DAO.actualizar(u)){
                res.status(200);
                oRespuesta.addProperty("mensaje", true);
                return oRespuesta;
            }else{
                //res.status(400);
                oRespuesta.addProperty("mensaje", false);
                return oRespuesta;
            }
        });

        post("/eliminar", (req, res) -> {
            String datosJugador = req.body();
            Datos u = gson.fromJson(datosJugador, Datos.class);
            JsonObject oRespuesta = new JsonObject();
            if(DAO.eliminar(u.getUsuario(), u.getContrasena())){
                res.status(200);
                oRespuesta.addProperty("mensaje", true);
                return oRespuesta;
            }
            //res.status(400);
            oRespuesta.addProperty("mensaje", false);
            return oRespuesta;
        });

        post("/iniciarSesion", (req, res) -> {

            String datosJugador = req.body();
            Datos datos = gson.fromJson(datosJugador, Datos.class);
            Datos u = DAO.iniciarSesion(datos.getUsuario(), datos.getContrasena());
            JsonObject oRespuesta = new JsonObject();
            if(u==null){
                //res.status(400);
                oRespuesta.addProperty("mensaje", false);
                return oRespuesta;
            }
            oRespuesta.addProperty("vida", u.getVida());
            oRespuesta.addProperty("energia", u.getEnergia());
            oRespuesta.addProperty("suciedad", u.getSuciedad());
            oRespuesta.addProperty("hambre", u.getHambre());
            oRespuesta.addProperty("nombre", u.getMascota());
            oRespuesta.addProperty("dueno", u.getUsuario());
            oRespuesta.addProperty("pokemon", u.getPokemon());

            res.status(200);

            return oRespuesta;
        });
    }
}
