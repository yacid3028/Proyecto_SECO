package seco;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class conexionbd {

    public static Connection conectar() {

        Connection conexion = null;

        try {

            String ruta = "jdbc:ucanaccess://" + new File("db/seco_db.accdb").getAbsolutePath();

            conexion = DriverManager.getConnection(ruta);

            System.out.println("todo good");

        } catch (Exception e) {

            System.out.println("Error de conexión");
            e.printStackTrace();

        }

        return conexion;
    }
}