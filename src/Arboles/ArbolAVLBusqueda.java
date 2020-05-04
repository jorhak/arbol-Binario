/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Arboles;

/**
 *
 * @author Abdias
 */
public class ArbolAVLBusqueda<T extends Comparable<T>> extends ArbolBinarioBusqueda<T> {
    private final int Desbalance_Permitido=1;

    
    @Override
    public boolean insertar(T dato){
        try
        {
            raiz = insertar(dato, raiz);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
    
    private NodoBinario<T> insertar(T dato, NodoBinario<T> nodoActual)throws Exception{
        if(esNodoVacio(nodoActual)){
            NodoBinario<T> nuevoNodo=new NodoBinario<>(dato);
            return nuevoNodo;
        }
        
        T datoDelNodo = nodoActual.getDato();
        
        if(dato.compareTo(datoDelNodo)>0){
            nodoActual.setHijoDerecho(insertar(dato, nodoActual.getHijoDerecho()));
            return balancear(nodoActual);
        }
        
        if(dato.compareTo(datoDelNodo)<0){
            nodoActual.setHijoIzquierdo(insertar(dato,nodoActual.getHijoIzquierdo()));
            return balancear(nodoActual);
        }
         throw new Exception();
    }
    
    private NodoBinario<T> balancear(NodoBinario<T> nodoActual){
        int alturaHijoIzquierdo = altura(nodoActual.getHijoIzquierdo());
        int alturaHijoDerecho = altura(nodoActual.getHijoDerecho());
        
        if(alturaHijoIzquierdo-alturaHijoDerecho>Desbalance_Permitido){
            alturaHijoIzquierdo = altura(nodoActual.getHijoIzquierdo().getHijoIzquierdo());
            alturaHijoDerecho = altura(nodoActual.getHijoIzquierdo().getHijoDerecho());
            if(alturaHijoIzquierdo > alturaHijoDerecho){
                return rotacionSimpleALaDerecha(nodoActual);
            }else{
                
                return (rotacionDobleALaDerecha(nodoActual));
            }
        }
        
        if(alturaHijoDerecho - alturaHijoIzquierdo > Desbalance_Permitido){
            alturaHijoIzquierdo = altura(nodoActual.getHijoDerecho().getHijoIzquierdo());
            alturaHijoDerecho = altura(nodoActual.getHijoDerecho().getHijoDerecho());
            if(alturaHijoIzquierdo > alturaHijoDerecho){
                return (rotacionDobleALaIzquierda(nodoActual));
            }else{
                return rotacionSimpleALaIzquierda(nodoActual);
            }
        }
        return nodoActual;
    }
    
    private NodoBinario<T> rotacionSimpleALaIzquierda(NodoBinario<T> nodoActual){
        NodoBinario<T> nodoRetornar = nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nodoRetornar.getHijoIzquierdo());
        nodoRetornar.setHijoIzquierdo(nodoActual);
        return nodoRetornar;
    }
    
    private NodoBinario<T> rotacionSimpleALaDerecha(NodoBinario<T> nodoActual){
        NodoBinario<T> nodoRetornar = nodoActual.getHijoIzquierdo();
        nodoActual.setHijoIzquierdo(nodoRetornar.getHijoDerecho());
        nodoRetornar.setHijoDerecho(nodoActual);
        return nodoRetornar;
    }
    
    private NodoBinario<T> rotacionDobleALaIzquierda(NodoBinario<T> nodoActual){
        NodoBinario<T> nodoRetornar = nodoActual.getHijoDerecho().getHijoIzquierdo();
        NodoBinario<T> nodoAnterior = nodoActual.getHijoDerecho();
        nodoAnterior.setHijoIzquierdo(nodoRetornar.getHijoDerecho());
        nodoRetornar.setHijoDerecho(nodoAnterior);
        nodoActual.setHijoDerecho(nodoRetornar.getHijoIzquierdo());
        nodoRetornar.setHijoIzquierdo(nodoActual);
        return nodoRetornar;
    }
    
    private NodoBinario<T> rotacionDobleALaDerecha(NodoBinario<T> nodoActual){
        NodoBinario<T> nodoRetornar = nodoActual.getHijoIzquierdo().getHijoDerecho();
        NodoBinario<T> nodoAnterior = nodoActual.getHijoIzquierdo();
        nodoAnterior.setHijoDerecho(nodoRetornar.getHijoIzquierdo());
        nodoRetornar.setHijoIzquierdo(nodoAnterior);
        nodoActual.setHijoIzquierdo(nodoRetornar.getHijoDerecho());
        nodoRetornar.setHijoDerecho(nodoActual);
        return nodoRetornar;
    }
    
    private int altura(NodoBinario<T> nodoActual){
        if(esNodoVacio(nodoActual)){
            return 0;
        }
        return Math.max(altura(nodoActual.getHijoDerecho()), altura(nodoActual.getHijoIzquierdo()))+1;
    }
    
    @Override
    public boolean eliminar(T dato){
        try
        {
            raiz = eliminar(dato, raiz);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
    private NodoBinario<T> eliminar(T dato, NodoBinario<T> nodoActual)throws Exception{
        if(esNodoVacio(nodoActual)){
            throw new Exception();
        }
        T datoDelNodo = nodoActual.getDato();
        
        if(dato.compareTo(datoDelNodo)>0){
            NodoBinario<T> supuestoHijoDerecho = eliminar(dato,nodoActual.getHijoDerecho());
            nodoActual.setHijoDerecho(supuestoHijoDerecho);
            return balancear(nodoActual);
        }
        if(dato.compareTo(datoDelNodo)<0){
            NodoBinario<T> supuestoHijoIzquierdo = eliminar(dato,nodoActual.getHijoIzquierdo());
            nodoActual.setHijoIzquierdo(supuestoHijoIzquierdo);
            return balancear(nodoActual);
        }
        //caso 1
        if(esHoja(nodoActual)){
            return null;
        }
        
        //caso 2
        if(esNodoVacio(nodoActual.getHijoIzquierdo()) && !esNodoVacio(nodoActual.getHijoDerecho())){
            NodoBinario<T> nodoAuxiliar = nodoActual.getHijoDerecho();
            nodoActual.setHijoDerecho(null);
            return nodoAuxiliar;
        }
        
        if(!esNodoVacio(nodoActual.getHijoIzquierdo()) && esNodoVacio(nodoActual.getHijoDerecho())){
            NodoBinario<T> nodoAuxiliar = nodoActual.getHijoIzquierdo();
            nodoActual.setHijoIzquierdo(null);
            return nodoAuxiliar;
        }
        
        //caso 3
        T datoSucesor = buscarSucesor(nodoActual.getHijoDerecho());
        NodoBinario<T> supuestoNuevoHijoDerecho = eliminar(datoSucesor,nodoActual.getHijoDerecho());
        nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
        nodoActual.setDato(datoSucesor);
        return nodoActual;
    }
    
        private T buscarSucesor(NodoBinario<T> nodoActual){
        NodoBinario<T> nodoAuxiliar = nodoActual;
        while(!esNodoVacio(nodoActual)){
            nodoAuxiliar = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoAuxiliar.getDato();
    }
}
