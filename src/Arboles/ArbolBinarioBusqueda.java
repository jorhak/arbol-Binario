/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Arboles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Abdias
 */
public class ArbolBinarioBusqueda<T extends Comparable<T>> implements IArbolBusqueda<T> {
    protected NodoBinario<T> raiz;
    
    public ArbolBinarioBusqueda(){}
    
    
    public ArbolBinarioBusqueda(List<T> inOrden, List<T> otroRecorrido, boolean esConPostOreden) throws Exception{
        if(inOrden == null|| otroRecorrido == null || inOrden.isEmpty() || otroRecorrido.isEmpty()
                || inOrden.size() != otroRecorrido.size()){
            throw new Exception("");
        }
        if(esConPostOreden){
            this.raiz = reconstruirConPostOrden(inOrden, otroRecorrido);
        }else{
            this.raiz = reconstruirConPreOrden(inOrden, otroRecorrido);
        }
    }
    
    private NodoBinario<T> reconstruirConPostOrden(List<T> inOrden, List<T> postOrden){
        if(postOrden.isEmpty()){
            return null;
        }
        int ultimaPosicion = postOrden.size()-1;
        T datoParaElPadreDelSubArbol = postOrden.get(ultimaPosicion);
        int posicionEnElInOrden = buscarDatoEnLista(inOrden, datoParaElPadreDelSubArbol);
        NodoBinario<T> mayorNodoPadre = new NodoBinario<>(datoParaElPadreDelSubArbol);

        List<T> listaInOrdenAIzq = inOrden.subList(0, posicionEnElInOrden);
        List<T> listaPostOrdenAIzq = postOrden.subList(0, posicionEnElInOrden);
        NodoBinario<T> nodoHijoIzquierdo = reconstruirConPostOrden(listaInOrdenAIzq, listaPostOrdenAIzq);

        List<T> listaInOrdenADer = inOrden.subList(posicionEnElInOrden + 1, postOrden.size());
        List<T> listaPostOrdenADer = postOrden.subList(posicionEnElInOrden, ultimaPosicion);
        NodoBinario<T> nodoHijoDerecho = reconstruirConPostOrden(listaInOrdenADer, listaPostOrdenADer);
        
        mayorNodoPadre.setHijoIzquierdo(nodoHijoIzquierdo);
        mayorNodoPadre.setHijoDerecho(nodoHijoDerecho);
        return mayorNodoPadre;
    }
    
    private int buscarDatoEnLista(List<T> lista, T dato){
        for(int i=0; i < lista.size(); i++){
            T datoDeLista = lista.get(i);
            if(datoDeLista.compareTo(dato) == 0){
                return i;
            }
        }
        return -1;
    }
    private NodoBinario<T> reconstruirConPreOrden(List<T> inOrden, List<T> preOrden){
        return null;
    }
    
    protected boolean esNodoVacio(NodoBinario<T> nodoActual){
        return nodoActual==null;
    }
    
    protected boolean esHoja(NodoBinario<T> nodoActual){
        return esNodoVacio(nodoActual.getHijoIzquierdo()) &&
               esNodoVacio(nodoActual.getHijoDerecho()); 
    }
    
    @Override
    public void vaciar() {
        this.raiz=null;
    }

    @Override
    public boolean esArbolVacio() {
        return esNodoVacio(raiz);
    }
    
    @Override
    public boolean insertar(T dato) {
       if(esArbolVacio()){
           this.raiz = new NodoBinario<>(dato);
           return true;
       }
       NodoBinario<T> nodoAnterior = null;
       NodoBinario<T> nodoActual = this.raiz;
       while(!esNodoVacio(nodoActual)){
           nodoAnterior = nodoActual;
           if(dato.compareTo(nodoActual.getDato()) > 0){
               nodoActual = nodoActual.getHijoDerecho();
           }else if(dato.compareTo(nodoActual.getDato()) < 0){
               nodoActual = nodoActual.getHijoIzquierdo();
           }else{
               return false;
           }
       }//fin bucle
       NodoBinario<T> nuevoNodo = new NodoBinario<>(dato);
       if(dato.compareTo(nodoAnterior.getDato())>0){
           nodoAnterior.setHijoDerecho(nuevoNodo);
       }else{
           nodoAnterior.setHijoIzquierdo(nuevoNodo);
       }
       return true;
    }

    @Override
    public boolean eliminar(T dato) {
        try
        {
            this.raiz = eliminar(this.raiz, dato);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
    
    private NodoBinario<T> eliminar(NodoBinario<T> nodoActual, T dato)throws Exception{
        if(esNodoVacio(nodoActual)){
            throw new Exception();
        }
        T datoDelNodo = nodoActual.getDato();
        if(dato.compareTo(datoDelNodo) > 0){
            NodoBinario<T> supuestoNuevoHijoDerecho = eliminar(nodoActual.getHijoDerecho(),dato);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return nodoActual;
        }
        if(dato.compareTo(datoDelNodo) < 0){
            NodoBinario<T> supuestoNuevoHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(),dato);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return nodoActual;
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
        
        if(!esNodoVacio(nodoActual.getHijoIzquierdo()) &&  esNodoVacio(nodoActual.getHijoDerecho())){
            NodoBinario<T> nodoAuxiliar = nodoActual.getHijoIzquierdo();
            nodoActual.setHijoIzquierdo(null);
            return nodoAuxiliar;
        }
        
        //caso 3
        T datoSucesor = buscarSucesor(nodoActual.getHijoDerecho());
        NodoBinario<T> supuestoNuevoHijoDerecho = eliminar(nodoActual.getHijoDerecho(),datoSucesor);
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

    @Override
    public T buscar(T dato) {
        return buscar(this.raiz,dato);
    }
    
    private T buscar(NodoBinario<T> nodoActual, T dato){
        if(esNodoVacio(nodoActual)){
            return null;
        }
        
        if(dato.compareTo(nodoActual.getDato())>0){
            return buscar(nodoActual.getHijoDerecho(),dato);
        }
        if(dato.compareTo(nodoActual.getDato())<0){
            return buscar(nodoActual.getHijoIzquierdo(),dato);
        }
        return nodoActual.getDato();
    }

    @Override
    public List<T> recorridoInOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoInOrdenR(raiz,recorrido);
        return recorrido;
    }
    
    private void recorridoInOrdenR(NodoBinario<T> nodoActual, List<T> recorrido) {
       if(!esNodoVacio(nodoActual)){
           recorridoInOrdenR(nodoActual.getHijoIzquierdo(), recorrido);
           recorrido.add(nodoActual.getDato());
           recorridoInOrdenR(nodoActual.getHijoDerecho(), recorrido);
       }
    }

    @Override
    public List<T> recorridoPreOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoPreOrdenR(raiz, recorrido);
        return recorrido;
    }
    
    private void recorridoPreOrdenR(NodoBinario<T> nodoActual, List<T> recorrido){
        if(!esNodoVacio(nodoActual)){
            recorrido.add(nodoActual.getDato());
            recorridoPreOrdenR(nodoActual.getHijoIzquierdo(), recorrido);
            recorridoPreOrdenR(nodoActual.getHijoDerecho(), recorrido);
        }
    }

    @Override
    public List<T> recorridoPostOrden() {
        List<T> recorrido= new ArrayList<>();
        recorridoPostOrdenR(raiz, recorrido);
        return recorrido;
    }
    
    private void recorridoPostOrdenR(NodoBinario<T> nodoActual, List<T> recorrido){
        if(!esNodoVacio(nodoActual)){
            recorridoPostOrdenR(nodoActual.getHijoIzquierdo(), recorrido);
            recorridoPostOrdenR(nodoActual.getHijoDerecho(), recorrido);
            recorrido.add(nodoActual.getDato());
        }
    }

    @Override
    public List<T> recorridoPorNiveles() {
        List<T> recorrido = new ArrayList<>();
        if(esArbolVacio()){
            return recorrido;
        }
        Queue<NodoBinario<T>> cola = new LinkedList<>();
        cola.offer(this.raiz);
        while(!cola.isEmpty()){
            NodoBinario<T> nodoActual = cola.poll();
            recorrido.add(nodoActual.getDato());
            if(!esNodoVacio(nodoActual.getHijoIzquierdo())){
                cola.offer(nodoActual.getHijoIzquierdo());
            }
            if(!esNodoVacio(nodoActual.getHijoDerecho())){
                cola.offer(nodoActual.getHijoDerecho());
            }
        }
        return recorrido;
    }

    @Override
    public int size() {
        List<T> recorrido = new ArrayList<>();
        if(esArbolVacio()){
            return 0;
        }
        Queue<NodoBinario<T>> cola = new LinkedList<>();
        cola.offer(this.raiz);
        int cantidadDeNodos=0;
        while(!cola.isEmpty()){
            NodoBinario<T> nodoActual = cola.poll();
            cantidadDeNodos++;
            if(!esNodoVacio(nodoActual.getHijoDerecho())){
                cola.offer(nodoActual.getHijoDerecho());
            }
            if(!esNodoVacio(nodoActual.getHijoIzquierdo())){
                cola.offer(nodoActual.getHijoIzquierdo());
            }
        }
        return cantidadDeNodos;
    }

    @Override
    public int altura() {
        int altura = 0;
        return altura(this.raiz,altura);
    }
    
    private int altura(NodoBinario<T> nodoActual, int altura){
        if(esNodoVacio(nodoActual)){
            return 0;
        }
        int alturaDerecha = altura(nodoActual.getHijoDerecho(),altura);
        int alturaIzquierda = altura(nodoActual.getHijoIzquierdo(),altura);
        if(alturaDerecha>alturaIzquierda){
            return alturaDerecha+=1;
        }else{
            return alturaIzquierda+=1;
        }
    }

    @Override
    public int nivel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String mostrarRecorrido(List<T> recorrido){
        String cadena = "";
        for(int i=0; i<recorrido.size(); i++){
            cadena+=recorrido.get(i)+", ";
        }
        return cadena;
    }
    
    @Override
    public List<T> recorridoEnInOrden() {
        List<T> recorrido = new ArrayList<>();
        if(esArbolVacio()){
            return recorrido;
        }
        Stack<NodoBinario<T>> pila = new Stack<>();
        NodoBinario<T> nodoActual = this.raiz;
        while(!esNodoVacio(nodoActual)){
            pila.push(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();
        }            
        while(!pila.isEmpty()){
            nodoActual = pila.pop();
            recorrido.add(nodoActual.getDato());
            if(!esNodoVacio(nodoActual.getHijoDerecho())){
                nodoActual = nodoActual.getHijoDerecho();
                while(!esNodoVacio(nodoActual)){
                    pila.push(nodoActual);
                    nodoActual = nodoActual.getHijoIzquierdo();
                }//fin while
            }//fin if
        }//fin while
        return recorrido;
    }

    @Override
    public List<T> recorridoEnPreOrden() {
        List<T> recorrido = new ArrayList<>();
        if(esArbolVacio()){
            return recorrido;
        }
        Stack<NodoBinario<T>> pila = new Stack<>();
        pila.push(this.raiz);
        while(!pila.isEmpty()){
            NodoBinario<T> nodoActual = pila.pop();
            recorrido.add(nodoActual.getDato());
            if(!esNodoVacio(nodoActual.getHijoDerecho())){
                pila.push(nodoActual.getHijoDerecho());
            }
            if(!esNodoVacio(nodoActual.getHijoIzquierdo())){
                pila.push(nodoActual.getHijoIzquierdo());
            }
        }//fin while
        return recorrido;
    }

    @Override
    public List<T> recorridoEnPostOrden() {
        List<T> recorrido = new ArrayList<>();
        if(esArbolVacio()){
            return recorrido;
        }
        Stack<NodoBinario<T>> pila = new Stack<>();
        NodoBinario<T> nodoActual= this.raiz;
        meterEnPilaPostOrden(nodoActual, pila);
        
        while(!pila.isEmpty()){
            nodoActual = pila.pop();
            recorrido.add(nodoActual.getDato());
            
            if(!pila.isEmpty()){
                NodoBinario<T> nodoAuxiliar = pila.peek();
                if(nodoActual != nodoAuxiliar.getHijoDerecho()){
                    meterEnPilaPostOrden(nodoAuxiliar.getHijoDerecho(), pila);
                }
            }
            
        }
        
        return recorrido;
    }

    private void meterEnPilaPostOrden(NodoBinario<T> nodoActual, Stack<NodoBinario<T>> pila) {
        while(!esNodoVacio(nodoActual)){
            pila.push(nodoActual);
            if(!esNodoVacio(nodoActual.getHijoIzquierdo())){
                nodoActual = nodoActual.getHijoIzquierdo();
            }else{
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
    }

    @Override
    public int cantidadNodosConHI() {
        return cantidadNodosConHI(this.raiz);
    }
    private int cantidadNodosConHI(NodoBinario<T> nodoActual){
        if(esNodoVacio(nodoActual)){
            return 0;
        }
        int cantNodosIzq = cantidadNodosConHI(nodoActual.getHijoIzquierdo());
        int cantNodosDer = cantidadNodosConHI(nodoActual.getHijoDerecho());
        
        if(!esNodoVacio(nodoActual.getHijoIzquierdo())){
            return cantNodosIzq + cantNodosDer + 1;
        }
        return cantNodosIzq + cantNodosDer;
    }

    @Override
    public int cantidadNodosConHI(int nivel) {
        return cantidadNodosConHI(this.raiz, nivel);
    }
    private int cantidadNodosConHI(NodoBinario<T> nodoActual, int nivel){
        if(esNodoVacio(nodoActual)){
            return 0;
        }
        if(nivel==0){
            return 0;
        }
        
        while(nivel!=0){
            cantidadNodosConHI(nodoActual.getHijoIzquierdo(), nivel-1);
            cantidadNodosConHI(nodoActual.getHijoDerecho(), nivel-1);
        }
        int cantNodosIzq = cantidadNodosConHI(nodoActual.getHijoIzquierdo(), nivel);
        int cantNodosDer = cantidadNodosConHI(nodoActual.getHijoDerecho(), nivel); 
        
        if(!esNodoVacio(nodoActual.getHijoIzquierdo())){
                return cantNodosIzq + cantNodosDer +1;
            }
        return 0;
    }

    @Override
    public int numeroDeDatosVacios() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hijosCompletos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int numeroDeDatosVaciosNivel(int nivel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(T datoAnt, T datoAct) {
        modificar(raiz,datoAnt,datoAct);
    }
    private void modificar(NodoBinario<T> nodoActual,T datoAnt, T datoAct){
        NodoBinario<T> nodoAModificar = buscar1(nodoActual, datoAnt);
        nodoAModificar.setDato(datoAct);
    }
    
    private NodoBinario<T> buscar1(NodoBinario<T> nodoActual, T dato){
        if(esNodoVacio(nodoActual)){
            return null;
        }
        
        if(dato.compareTo(nodoActual.getDato())>0){
            return buscar1(nodoActual.getHijoDerecho(),dato);
        }
        if(dato.compareTo(nodoActual.getDato())<0){
            return buscar1(nodoActual.getHijoIzquierdo(),dato);
        }
        return nodoActual;
    }


}
