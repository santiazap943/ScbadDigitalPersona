package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ING JARC
 */
public class ConexionBD {
  /*  public String puerto = "3306";
    public String nomservidor = "162.210.96.124";
    public String db = "itiud_scbad";
    public String user = "itiud_scbad";
    public String pass = "scbad123654";
    */
    public String puerto = "3306";
    public String nomservidor = "Localhost";
    public String db = "scbad";
    public String user = "root";
    public String pass = "";
    Connection conn = null;

    public Connection conectar() {
        try {
            String ruta = "jdbc:mysql://";
            String servidor = nomservidor + ":" + puerto + "/";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(ruta + servidor + db, user, pass);

            if (conn != null) {
                System.out.println("Conección a base de datos listo...");
            } else if (conn == null) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Se produjo el siguiente error: " + e.getMessage());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Se produjo el siguiente error: " + e.getMessage());
        } finally {
            return conn;
        }
    }

    public void desconectar() {
        conn = null;
        System.out.println("Desconexion a base de datos listo...");
    }
}


    /*
public  String puerto="3306";
public  String nomservidor="localhost";
public  String db="id11877630_scbad";
public  String user="id11877630_santy";
public  String pass="123456";
public String url ="databases-auth.000webhost.com/server_sq";
Connection conn=null;

public Connection conectar(){
    try{
    String ruta="jdbc:mysql://";
    String servidor=url+":"+puerto+"/";
    Class.forName("com.mysql.jdbc.Driver");
    conn = DriverManager.getConnection(ruta+servidor+db,user,pass);
conexion a servidor**     

*/
