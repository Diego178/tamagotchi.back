package mx.uv;

public class AppPrueba {
    public static void main( String[] args )
   
    {

         Datos u = new Datos("diego", "julio", "asd", "1", 3, 0, 0, 0);
        if(DAO.actualizar(u)){
            System.out.println("existe//");
        }else{
            System.out.println("no existe");
        }

    }
    
}
