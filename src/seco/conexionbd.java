package seco.fcdb;

import java.sql.Connection;
import java.sql.DriverManager;

public class conexionbd {

    public static Connection conectar() {

        Connection conexion = null;

        try {

            String ruta = "jdbc:ucanaccess://C:\\Users\\Saul Jansenson\\Desktop\\seco\\Proyecto_SECO\\db\\Inventario proyecto.accdb";

            conexion = DriverManager.getConnection(ruta);

            System.out.println("todo good");

        } catch (Exception e) {

            System.out.println("Error de conexión");
            e.printStackTrace();

        }

        return conexion;
    }
}