/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

/**
 *
 * @author Santy
 */
public class ProfesorjavDAO {
    private int id;
    private String nombre;
    private String apellido;
    private byte huella[];
    private String correo;
    
    public ProfesorjavDAO(int id, String nombre, String apellido, byte huella[], String correo){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.huella = huella;
        this.correo = correo;
    }
    
    public String guardarHuella(){
        return "INSERT INTO profesor(nombre, huella) values(?,?)";
    }
    public String identificarHuella(){
        return "SELECT nombre,huella FROM profesor";
    } 
}
