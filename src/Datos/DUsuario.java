/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Datos;

/**
 *
 * @author Abdias
 */
public class DUsuario extends DPersona {
    private String contraseña;

    public DUsuario() {
    }

    public DUsuario(String contraseña, String nombres, String apellidos) {
        super(nombres, apellidos);
        this.contraseña = contraseña;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    
}
