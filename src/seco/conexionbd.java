package seco;

import java.sql.Connection;
import java.sql.DriverManager;

public class conexionbd {
    public static Connection conect() {
        Connection conexion = null;
        try {

            String ruta = "jdbc:ucanaccess://db/SECO_db.accdb";
            conexion = DriverManager.getConnection(ruta);

        } catch (Exception e) {
            System.out.println("error de conexion");
            e.printStackTrace();
        }

        return conexion;
    }

}
