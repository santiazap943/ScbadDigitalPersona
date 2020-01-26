/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;
import Persistencia.ConexionBD;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import Persistencia.ProfesorjavDAO;
import Presentacion.Captura;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author Santy
 */
public class Profesorjav {
     private int id;
    private String nombre;
    private String apellido;
    private byte huella[];
    private String correo;
    private ProfesorjavDAO profesorjavDAO;
    ConexionBD con=new ConexionBD();
      public Profesorjav(int id, String nombre, String apellido, byte huella[], String correo){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.huella = huella;
        this.correo = correo;
        profesorjavDAO = new ProfesorjavDAO(id,nombre,apellido,huella,correo);
     }
      
    public void guardarHuella(){
     ByteArrayInputStream datosHuella = new ByteArrayInputStream(this.huella);
     Integer tamañoHuella=huella.length;
      try {
     Connection c=con.conectar(); //establece la conexion con la BD
     PreparedStatement guardarStmt = c.prepareStatement(profesorjavDAO.guardarHuella());
     guardarStmt.setString(1,this.nombre);
     guardarStmt.setBinaryStream(2, datosHuella,tamañoHuella);     
     System.out.println(guardarStmt.toString());
     guardarStmt.execute();
     guardarStmt.close();
     JOptionPane.showMessageDialog(null,"Huella Guardada Correctamente");
     con.desconectar();
    } catch (SQLException ex) {
     //Si ocurre un error lo indica en la consolax
     System.err.println("Error al guardar los datos de la huella.");
     }finally{
     con.desconectar();
     }
    }
     @SuppressWarnings("static-access")
   public void identificarHuella(DPFPVerification result, DPFPFeatureSet featuresverificacion, DPFPTemplate template){
       Captura ob =  new Captura();
       try {
            //Establece los valores para la sentencia SQL
            Connection c = con.conectar();

            //Obtiene todas las huellas de la bd
            PreparedStatement identificarStmt = c.prepareStatement(profesorjavDAO.identificarHuella());
            ResultSet rs = identificarStmt.executeQuery();
            //Si se encuentra el nombre en la base de datos
            while (rs.next()) {
                //Lee la plantilla de la base de datos
                byte templateBuffer[] = rs.getBytes("huella");
                String nombre = rs.getString("nombre");
                //Crea una nueva plantilla a partir de la guardada en la base de datos
                DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
                //Envia la plantilla creada al objeto contendor de Template del componente de huella digital
                ob.setTemplate(referenceTemplate);

                // Compara las caracteriticas de la huella recientemente capturda con la
                // alguna plantilla guardada en la base de datos que coincide con ese tipo
                DPFPVerificationResult result2 = result.verify(featuresverificacion, ob.getTemplate());
                //compara las plantilas (actual vs bd)
                //Si encuentra correspondencia dibuja el mapa
                //e indica el nombre de la persona que coincidió.
                if (result2.isVerified()) {
                    //crea la imagen de los datos guardado de las huellas guardadas en la base de datos
                    System.out.println("Las huella capturada es de " + nombre+"Verificacion de Huella");
                    return;
                }
            }
            //Si no encuentra alguna huella correspondiente al nombre lo indica con un mensaje
            System.out.println("No existe ningún registro que coincida con la huella");
            ob.setTemplate(null);
        } catch (SQLException e) {
            //Si ocurre un error lo indica en la consola
            System.err.println("Error al identificar huella dactilar." + e.getMessage());
        } finally {
            con.desconectar();
        }
   }
}
