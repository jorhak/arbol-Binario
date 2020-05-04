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

/**
 *
 * @author Abdias
 */
public class ArbolMViasBusqueda<T extends Comparable<T>> implements IArbolBusqueda<T> {
    protected NodoMVias<T> raiz;
    protected int orden;

    public ArbolMViasBusqueda(){
        this.orden = 3;
    }
    
    public ArbolMViasBusqueda(int orden)throws Exception{
        if(orden < 3){
            throw new Exception();
        }
        this.orden = orden;
    }
    
    protected boolean esNodoVacio(NodoMVias<T> nodoActual){
        return nodoActual==null;
    }
    
    protected boolean esHoja(NodoMVias<T> nodoActual){
        for(int i=0; i<orden; i++){
            if(!nodoActual.esHijoVacio(i)){
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean insertar(T dato) {
        if(esArbolVacio()){
            this.raiz = new NodoMVias<>(orden,dato);
            return true;
        }
        NodoMVias<T> nodoActual = this.raiz;
        
        while(!esNodoVacio(nodoActual)){
            if(esHoja(nodoActual)){
                if(nodoActual.estanDatosLlenos()){
                    int posicionDelHijo = enQueHijoVa(nodoActual,dato);
                    if(posicionDelHijo != -1){//EL DATO NO EXISTE
                        NodoMVias<T> nuevoNodo = new NodoMVias<>(orden,dato);
                        nodoActual.setHijo(posicionDelHijo, nuevoNodo);
                        return true;
                    }else{//EL DATO YA EXISTE
                        return false;
                    }
                }else{//NO ESTAN LOS DATOS LLENOS
                    if(!existeDatoEnElNodo(nodoActual,dato)){
                        insertarOrdenado(nodoActual,dato);
                        return true;
                    }else{
                        return false;
                    }
                }
            }else{//NO ES HOJA
                int posicionDelHijo = enQueHijoVa(nodoActual,dato);
                if(posicionDelHijo==-1){
                    return false;
                }
                if(nodoActual.esHijoVacio(posicionDelHijo)){
                    NodoMVias<T> nuevoNodo = new NodoMVias<>(orden,dato);
                    nodoActual.setHijo(posicionDelHijo, nuevoNodo);
                    return true;
                }
                nodoActual=nodoActual.getHijo(posicionDelHijo);
            }
        }
        return false;
    }

    protected int enQueHijoVa(NodoMVias<T> nodoActual, T dato){
        for(int i=0; i<orden-1;i++){
            if(dato.compareTo(nodoActual.getDato(i))==0){
                return -1;
            }
            if(dato.compareTo(nodoActual.getDato(i))<0){
                return i;
            }
        }
        return orden-1;
    }
    
    protected boolean existeDatoEnElNodo(NodoMVias<T> nodoActual, T dato){
        for(int i=0; (i<orden-1) && !nodoActual.esDatoVacio(i); i++){
            if(dato.compareTo(nodoActual.getDato(i))==0){
                return true;
            }
        }
        return false;
    }
    
    protected void insertarOrdenado(NodoMVias<T> nodoActual, T dato){
        int posicionDelDato = enQueDatoVa(nodoActual, dato);
        for(int i=orden-2; i>posicionDelDato; i--){
            nodoActual.setDato(i, nodoActual.getDato(i-1));
        }
        nodoActual.setDato(posicionDelDato, dato);
    }
    
    protected int enQueDatoVa(NodoMVias<T> nodoActual, T dato){
        int i=0;
        while(!nodoActual.esDatoVacio(i)){
            if(dato.compareTo(nodoActual.getDato(i))<0){
                return i;
            }
            i++;
        }
        return i;
    }
    
    @Override
    public boolean eliminar(T dato) {
        try{
            this.raiz = eliminar(this.raiz,dato);
        }catch (Exception ex){
            return false;
        }
        return true;
    }
    private NodoMVias<T> eliminar(NodoMVias<T> nodoActual, T dato) throws Exception{
        if(esNodoVacio(nodoActual)){
            throw new Exception();
        }
        for(int i=0; i<orden-1; i++){
            if(!nodoActual.esDatoVacio(i)){
                if(dato.compareTo(nodoActual.getDato(i)) == 0){
                    if(esHoja(nodoActual)){
                        eliminarDatoDelNodo(nodoActual,dato);
                        if(estaNodoSinDatos(nodoActual)){
                            return null;
                        }else{
                            return nodoActual;
                        }
                    }else{
                        T datoReemplazo = buscarReemplazoPorDelante(nodoActual,dato);
                        if(datoReemplazo == null){
                            datoReemplazo = buscarReemplazoPorDetras(nodoActual,dato);
                        }
                        nodoActual = eliminar(nodoActual,datoReemplazo);
                        nodoActual.setDato(i, datoReemplazo);
                        return nodoActual;
                    }
                }else{
                    if(dato.compareTo(nodoActual.getDato(i))<0){
                        NodoMVias<T> nodoHijo = eliminar(nodoActual.getHijo(i),dato);
                        nodoActual.setHijo(i, nodoHijo);
                        return nodoActual;
                    }
                }
            }else{
                throw new Exception();
            }
        }
        nodoActual.setHijo(orden-1, eliminar(nodoActual.getHijo(orden-1),dato));
        return nodoActual;
    }
    
    private void eliminarDatoDelNodo(NodoMVias<T> nodoActual, T dato){
        int x = 0;
        for(int i=0; i<orden-1; i++){
            if(!nodoActual.esDatoVacio(i)){
                if(dato.compareTo(nodoActual.getDato(i)) == 0){
                    x = i;
                }
            }
        }
        if(x < orden-1){
            for(int j=x; j<orden-2; j++){
                nodoActual.setDato(j, nodoActual.getDato(j+1));
                nodoActual.setDato(j+1, null);
            }
        }
        if(x == orden-2){
            nodoActual.setDato(x, null);
        }
    }
    
    private boolean estaNodoSinDatos(NodoMVias<T> nodoActual){
        return nodoActual.esDatoVacio(0);
    }
    
    private T buscarReemplazoPorDelante(NodoMVias<T> nodoActual, T dato){
        int index = buscarIndice(nodoActual,dato);
        if(!hayHijosPorDelante(nodoActual,index)){
            return null;
        }
        List<T> recInOrden = recorridoEnInOrden();
        int posicion = recInOrden.indexOf(dato);
        return recInOrden.get(posicion + 1);
    }
    
    private int buscarIndice(NodoMVias<T> nodoActual, T dato){
        for(int i=0; i<orden-1; i++){
            if(dato.compareTo(nodoActual.getDato(i))==0){
                return i;
            }
        }
        return orden-1;
    }
    
    private boolean hayHijosPorDelante(NodoMVias<T> nodoActual, int dato){
        int hijos = 0;
        for(int i=0; i<orden-1; i++){
            if(!esNodoVacio(nodoActual)){
                hijos++;
            }
        }
        if(hijos == 0){
            return false;
        }
        return true;
    }
    
    private T buscarReemplazoPorDetras(NodoMVias<T> nodoActual, T dato){
        List<T> recInOrden = recorridoEnInOrden();
        int posicion = recInOrden.indexOf(dato);
        return recInOrden.get(posicion + 1);
    }
    @Override
    public T buscar(T dato) {
        T datoRetorno;
        if (esArbolVacio()) {
            return null;
        }
        NodoMVias<T> nodoAnterior = null;
        NodoMVias<T> nodoActual = raiz;
        datoRetorno = null;
        while ((datoRetorno == null) && (!esNodoVacio(nodoActual))) {
            nodoAnterior = nodoActual;
            int i = 0;
            for (i = 0; i < (orden - 1); i++) {
                if ((nodoActual.getDato(i) != null) && (dato.compareTo(nodoActual.getDato(i)) == 0)) {
                    datoRetorno = nodoActual.getDato(i);
                    break;
                }
                if ((nodoActual.getDato(i) != null) && (dato.compareTo(nodoActual.getDato(i)) < 0)) {
                    nodoActual = nodoActual.getHijo(i);
                    break;
                } else {
                    if ((nodoActual.getDato(i + 1) == null) && dato.compareTo(nodoActual.getDato(i)) > 0) {
                        nodoActual = nodoActual.getHijo(i + 1);
                        break;
                    }
                }
            }
            if (i == (orden - 1)) {
                nodoActual = nodoActual.getHijo(orden - 1);
            } else if (datoRetorno != null) {
                break;
            }
        }
        return datoRetorno;
    }

    @Override
    public List<T> recorridoInOrden() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<T> recorridoPreOrden() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<T> recorridoPostOrden() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<T> recorridoPorNiveles() {
        List<T> recorrido = new ArrayList<>();
        if(esArbolVacio()){
            return recorrido;
        }
        Queue<NodoMVias<T>> cola = new LinkedList<>();
        cola.offer(this.raiz);
        while(!cola.isEmpty()){
            NodoMVias<T> nodoActual = cola.poll();
            for(int i=0; i<=orden-1; i++){
                if(!nodoActual.esHijoVacio(i)){
                    cola.offer(nodoActual.getHijo(i));
                }
                if(i<orden-1 && !nodoActual.esDatoVacio(i)){
                    recorrido.add(nodoActual.getDato(i));
                }
            }
        }
        return recorrido;
    }

    @Override
    public List<T> recorridoEnInOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoEnInOrden(this.raiz,recorrido);
        return recorrido;
    }
    private void recorridoEnInOrden(NodoMVias<T> nodoActual, List<T> recorrido){
        if(esNodoVacio(nodoActual)){
            return ;
        }
        for(int i=0; (i<orden-1) && !nodoActual.esDatoVacio(i); i++){
            recorridoEnInOrden(nodoActual.getHijo(i), recorrido);
            recorrido.add(nodoActual.getDato(i));
        }
        recorridoEnInOrden(nodoActual.getHijo(orden-1), recorrido);
    }

    @Override
    public List<T> recorridoEnPreOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoEnPreOrden(this.raiz,recorrido);
        return recorrido;
    }
    private void recorridoEnPreOrden(NodoMVias<T> nodoActual, List<T> recorrido){
        if(esNodoVacio(nodoActual)){
            return ;
        }
        for(int i=0;(i<orden-1) && !nodoActual.esDatoVacio(i); i++){
            recorrido.add(nodoActual.getDato(i));
            recorridoEnPreOrden(nodoActual.getHijo(i), recorrido);
            
        }
        recorridoEnPreOrden(nodoActual.getHijo(orden-1), recorrido);
    }

    @Override
    public List<T> recorridoEnPostOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoEnPostOrden(this.raiz,recorrido);
        return recorrido;
    }
    private void recorridoEnPostOrden(NodoMVias<T> nodoActual, List<T> recorrido){
        if(esNodoVacio(nodoActual)){
            return ;
        }
        recorridoEnPostOrden(nodoActual.getHijo(0), recorrido);
        for(int i=0; (i < orden-1) && !nodoActual.esDatoVacio(i); i++){
            recorridoEnPostOrden(nodoActual.getHijo(i+1), recorrido);
            recorrido.add(nodoActual.getDato(i));
        }
    }
    
    public String mostrarRecorrido(List<T> recorrido){
        String cadena="";
        for(int i=0; i<recorrido.size();i++){
            cadena+=recorrido.get(i)+",";
        }
        return cadena;
    }

    @Override
    public int size() {
        return size(this.raiz)+1;
    }
    private int size(NodoMVias<T> nodoActual){
        if(esNodoVacio(nodoActual)){
            return 0;
        }
        int cantidad = 0;
        for(int i=0; i<=orden-1; i++){
            if(!nodoActual.esHijoVacio(i)){
                cantidad++;
            }
            cantidad+=size(nodoActual.getHijo(i));
        }
        return cantidad;
    }
    
    public int size1(){
        if(esArbolVacio()){
            return 0;
        }
        Queue<NodoMVias<T>> cola = new LinkedList<>();
        cola.offer(raiz);
        int cantidad = 0;
        while(!cola.isEmpty()){
            NodoMVias<T> nodoActual = cola.poll();
            cantidad++;
            for(int i=0; i<=orden-1; i++){
                if(!nodoActual.esHijoVacio(i))
                    cola.offer(nodoActual.getHijo(i));
            }
        }
        return cantidad;
    }

    @Override
    public int altura() {
       return altura(this.raiz);
    }
    private int altura(NodoMVias<T> nodoActual){
        if(esNodoVacio(nodoActual)){
            return 0;
        }
        int alturaMayor = 0;
        for(int i=0;i<=orden-1;i++){
            int alturaHijo = altura(nodoActual.getHijo(i));
            if(alturaHijo > alturaMayor){
                alturaMayor = alturaHijo;
            }
        }
        return alturaMayor + 1;
    }

    @Override
    public int nivel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public int cantidadNodosConHI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int cantidadNodosConHI(int nivel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public int numeroDeDatosVacios(){
        return numeroDeDatosVacios(this.raiz);
    }
    private int numeroDeDatosVacios(NodoMVias<T> nodoActual){
        if(esNodoVacio(nodoActual)){
            return 0;
        }
        int datosVacios = 0;
        for(int i=0; i<orden-1; i++){
            if(nodoActual.esDatoVacio(i)){
                datosVacios++;
            }
            datosVacios+=numeroDeDatosVacios(nodoActual.getHijo(i));
        }
        datosVacios+=numeroDeDatosVacios(nodoActual.getHijo(orden-1));
        return datosVacios;
    }

    @Override
    public int numeroDeDatosVaciosNivel(int nivel) {
        return numeroDeDatosVaciosNivel(this.raiz,nivel);
    }
    private int numeroDeDatosVaciosNivel(NodoMVias<T> nodoActual, int nivel){
        if(esNodoVacio(nodoActual)){
            return 0;
        }
        if(nivel<0){
            return 0;
        }
        int datosVacios = 0;
        for(int i=0; i<orden-1; i++){
            if(nodoActual.esDatoVacio(i) && nivel ==0){
                datosVacios++;
            }
            datosVacios+=numeroDeDatosVaciosNivel(nodoActual.getHijo(i), nivel-1);
        }
        datosVacios+=numeroDeDatosVaciosNivel(nodoActual.getHijo(orden-1), nivel-1);
        return datosVacios;
    }
    
    @Override
    public int hijosCompletos() {
        return hijosCompletos(this.raiz);
    }
    private int hijosCompletos(NodoMVias<T> nodoActual){
        if(esNodoVacio(nodoActual)){
            return 0;
        }
        int cantidadHijosCompletos = 0;
        boolean sw = true;
        for(int i=0; (i<=orden-1) && sw==true; i++){
            if(!nodoActual.esHijoVacio(i)){
                cantidadHijosCompletos += hijosCompletos(nodoActual.getHijo(i));
            }else{
                sw = false;
            }
        }
        if(sw==true){
            cantidadHijosCompletos++;
        }
        return cantidadHijosCompletos;
    }

    @Override
    public void modificar(T datoAnt, T datoAct) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
