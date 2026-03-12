package seco.fcdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class provedoresDB {
    public void consultarProvedores(DefaultTableModel modelo) {
        Connection con=conexionbd.conect();
        String sql = "SELECT * FROM Provedores";
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                int telefono = rs.getInt("telefono");

                modelo.addRow(new Object[]{nombre, direccion, telefono});
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    } 
        

}
