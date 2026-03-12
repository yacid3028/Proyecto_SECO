package seco;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class conexionbd {
    public static Connection conect() {
        Connection conexion = null;
        try {

            String ruta = "jdbc:ucanaccess://" + new File("db/seco_db.accdb").getAbsolutePath();
            conexion = DriverManager.getConnection(ruta);
            System.out.println("Connection successful");
        } catch (Exception e) {
            System.out.println("error de conexion");
            e.printStackTrace();
        }

        return conexion;
    }

}
