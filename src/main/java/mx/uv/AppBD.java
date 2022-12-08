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
            System.out.println("aaa");
            Datos u = gson.fromJson(datosJugador, Datos.class);
            System.out.println("bbbbbb");
            if(u==null){
                res.status(400);
                return "Error, no se creo la cuenta";
            }
            res.status(200);
            return DAO.crearJugador(u);
        });

        post("/actualizar", (req, res) -> {
            Datos u = new Datos(req.queryParams("dueno"), req.queryParams("vida"),
            req.queryParams("energia"), req.queryParams("suciedad"), req.queryParams("hambre"));
            
            if(DAO.actualizar(u)){
                res.status(200);
                return true;
            }else{
                res.status(400);
                return false;
            }
        });

        post("/eliminar", (req, res) -> {
            if(!DAO.eliminar(req.queryParams("user"), req.queryParams("contra"))){
                res.status(400);
                return false;
            }
            res.status(200);
            return true;
        });

        post("/iniciarSesion", (req, res) -> {
            Datos u = DAO.iniciarSesion(req.queryParams("user"), req.queryParams("contra"));

            if(u==null){
                res.status(400);
                return false;
            }
            JsonObject oRespuesta = new JsonObject();
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
