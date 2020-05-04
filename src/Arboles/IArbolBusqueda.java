/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Arboles;

import java.util.List;

/**
 *
 * @author Abdias
 * @param <T>
 */
public interface IArbolBusqueda<T extends Comparable<T>> {
    boolean insertar(T dato);
    boolean eliminar(T dato);
    void modificar(T datoAnt, T datoAct);
    T buscar(T dato);
    List<T> recorridoInOrden();
    List<T> recorridoPreOrden();
    List<T> recorridoPostOrden();
    List<T> recorridoPorNiveles();
    List<T> recorridoEnInOrden();
    List<T> recorridoEnPreOrden();
    List<T> recorridoEnPostOrden();
    int size();
    int altura();
    int nivel();
    void vaciar();
    boolean esArbolVacio();
    int cantidadNodosConHI();
    int cantidadNodosConHI(int nivel);
    int numeroDeDatosVacios();
    int numeroDeDatosVaciosNivel(int nivel);
    int hijosCompletos();    
}
