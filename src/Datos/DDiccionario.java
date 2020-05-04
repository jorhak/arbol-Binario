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
public class DDiccionario implements Comparable<DDiccionario>{
    private int IdDiccionario;
    private String Palabra;
    private String Descripcion;
    private String Ejemplo;

    public DDiccionario() {
    }

        public DDiccionario(int IdDiccionario, String Palabra, String Descripcion, String Ejemplo) {
        this.IdDiccionario = IdDiccionario;
        this.Palabra = Palabra;
        this.Descripcion = Descripcion;
        this.Ejemplo = Ejemplo;
    }

    public int getIdDiccionario() {
        return IdDiccionario;
    }

    public String getPalabra() {
        return Palabra;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public String getEjemplo() {
        return Ejemplo;
    }

    public void setIdDiccionario(int IdDiccionario) {
        this.IdDiccionario = IdDiccionario;
    }

    public void setPalabra(String Palabra) {
        this.Palabra = Palabra;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public void setEjemplo(String Ejemplo) {
        this.Ejemplo = Ejemplo;
    }

    @Override
    public int compareTo(DDiccionario other) {
        DDiccionario dic = (DDiccionario) other;
        return this.Palabra.compareTo(other.Palabra);
    }

}
