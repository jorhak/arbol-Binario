/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Presentacion;
import Arboles.*;
/**
 *
 * @author Abdias
 */
public class prueba {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArbolBinarioBusqueda<Integer> a = new ArbolBinarioBusqueda<>();
        a.insertar(55);
        a.insertar(34);
        a.insertar(66);
        a.insertar(12);
        a.insertar(46);
        a.insertar(98);
        a.insertar(44);
        System.out.println(a.mostrarRecorrido(a.recorridoPorNiveles()));
        a.modificar(12, 44);
        System.out.println(a.mostrarRecorrido(a.recorridoPorNiveles()));
    }
    
}
