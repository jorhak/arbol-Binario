/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logica;
import Datos.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Abdias
 */
public class LDiccionario extends DataBase {
    
    
    public DefaultTableModel mostrar(String buscar){
        String consulta = "";
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Palabras");
        String [] datos = new String[1];
        if(buscar.contains("")){
            consulta = "Select Palabra from diccionario order by Palabra asc";
        }else{
            consulta = "Select Palabra from diccionario where Palabra like '%"+buscar+"&' order by Palabra asc";
        }
        
        try {
            Statement stm = getConexion().createStatement();
            ResultSet rs = stm.executeQuery(consulta);
            while(rs.next()){
                datos[0] = rs.getString("Palabra");
                modelo.addRow(datos);
            }
            return modelo;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }
    
    public boolean insertar(DDiccionario dic){
        String consulta = "Insert into diccionario(IdDiccionario,Palabra,Descripcion,Ejemplo) values(?,?,?,?)";
        try {
            PreparedStatement pstm = getConexion().prepareStatement(consulta);
            pstm.setInt(1, dic.getIdDiccionario());
            pstm.setString(2, dic.getPalabra());
            pstm.setString(3, dic.getDescripcion());
            pstm.setString(4, dic.getEjemplo());
            pstm.executeUpdate();
            pstm.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
    }
    
    public boolean editar(DDiccionario dic){
        String consulta = "Update diccionario set Descripcion='"+dic.getDescripcion()+"', Ejemplo='"+dic.getEjemplo()+"' where IdDiccionario='"+dic.getIdDiccionario()+"' and Palabra='"+dic.getPalabra()+"'";
        try {
            PreparedStatement pstm = getConexion().prepareStatement(consulta);
            pstm.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
    }
    
    public boolean eliminar(DDiccionario dic){
        String consulta = "Delete from diccionario where IdDiccionario='"+dic.getIdDiccionario()+"' and Palabra ='"+dic.getPalabra()+"'";
        try {
            PreparedStatement pstm = getConexion().prepareStatement(consulta);
            pstm.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
    }
    
}
