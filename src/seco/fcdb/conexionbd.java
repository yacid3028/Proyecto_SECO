package seco.fcdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexionbd {
    public static Connection conect() {
        Connection con = null;
        try {
            // Se corrigió la sintaxis y se incluyó la carpeta 'db' en la ruta
            String ruta = "jdbc:ucanaccess://db\\SECO_db.accdb";

            con = DriverManager.getConnection(ruta);

        } catch (SQLException e) {
            System.err.println("Error de conexión: No se encontró el archivo o la ruta es incorrecta.");
            e.printStackTrace();
        }
        return con;
    }
}