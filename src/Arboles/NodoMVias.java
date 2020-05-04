/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Arboles;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abdias
 */
public class NodoMVias<T extends Comparable<T>> {
    private List<T> listaDeDatos;
    private List<NodoMVias<T>> listaDeHijos;
    private int orden;
    
    public NodoMVias(int orden){
        this.orden = orden;
        this.listaDeDatos = new ArrayList<>();
        this.listaDeHijos = new ArrayList<>();
        for(int i=0; i<orden; i++){
            listaDeHijos.add(null);
            if(i<orden-1){
                listaDeDatos.add(null);
            }
        }
    }
    
    public NodoMVias(int orden, T dato){
        this(orden);
        this.listaDeDatos.set(0,dato);
    }
    
    public T getDato(int posicion){
        return this.listaDeDatos.get(posicion);
    }
    
    public void setDato(int posicion, T dato){
        this.listaDeDatos.set(posicion, dato);
    }
    
    public NodoMVias<T> getHijo(int posicion){
        return this.listaDeHijos.get(posicion);
    }
    
    public void setHijo(int posicion, NodoMVias<T> hijo){
        this.listaDeHijos.set(posicion, hijo);
    }
    
    public boolean esDatoVacio(int posicion){
        return listaDeDatos.get(posicion)==null;
    }
    
    public boolean esHijoVacio(int posicion){
        return listaDeHijos.get(posicion)==null;
    }
    
    public void setDatoVacio(int posicion){
        listaDeDatos.set(posicion, null);
    }
    
    public void setHijoVacio(int posicion){
        listaDeHijos.set(posicion,null);
    }
    
    public boolean estanDatosLlenos(){
        for(T unDato : listaDeDatos){
            if(unDato==null){
               return false;
            }
        }
        return true;
    }
    
    public int nroDeDatosVacios(){
        int cantidad=0;
        for(T unDato : listaDeDatos){
            if(unDato==null){
                cantidad++;
            }
        }
        return cantidad;
    }
    
    public int getNroDatos(){
        int cantidadDeDatos=0;
        for(T unDato : listaDeDatos){
            if(unDato!=null){
                cantidadDeDatos++;
            }
        }
        return cantidadDeDatos;
    }
    
    public int getNroHijos(){
        int cantidadDeHijos = 0;
        for(NodoMVias<T> unHijo : listaDeHijos){
            if(unHijo!=null){
                cantidadDeHijos++;
            }
        }
        return cantidadDeHijos;
    }
    
    public boolean estaDatoEnElNodo(T dato){
        for (int i = 0; i < orden; i++) {
            if(!esDatoVacio(i)){
                if(dato.compareTo(getDato(i))==0){
                    return true;
                }
            }
        }
        return false;
    }
    
    public void insertarDatoEnElNodo(T dato){
        
    }
    
    public void insertarHijo(NodoMVias<T> nodoActual){
    
    } 
    
    public void removerHijo(NodoMVias<T> nodoActual){
    
    }
    
    public void removerDato(T dato){
    
    }
    
    public boolean estaNodoSinDato(){
        return true;
    }
    
    public int getIndiceDeDato(T dato){
        return -1;
    }
    
    public int getPosicionDeHijo(NodoMVias<T> nodoActual){
        return -1;
    }
    
    public T eliminarLlaveMayorDelNodo(){
        return null;
    }
}
