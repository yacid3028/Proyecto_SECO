package seco;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class conexionbd {
    public static Connection conect() {
        Connection conexion = null;
        try {
            String ruta = "jdbc:ucanaccess://db/seco__db.accdb";
            conexion = DriverManager.getConnection(ruta);
            System.out.println("todo good");
        } catch (Exception e) {
            System.out.println("error de conexion");
            e.printStackTrace();
        }

        return conexion;
    }

}
