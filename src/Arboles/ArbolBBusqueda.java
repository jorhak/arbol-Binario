/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Arboles;

import java.util.Stack;

/**
 *
 * @author Abdias
 */
public class ArbolBBusqueda<T extends Comparable<T>> extends ArbolMViasBusqueda<T> {
    private int nroMinDeDatos;
    private int nroMinDeHijos;
    private int nroMaxDeDatos;
    private int nroMaxDeHijos;
    public ArbolBBusqueda(int orden) throws Exception {
        super(orden);
        this.nroMaxDeDatos = orden - 1;
        this.nroMinDeDatos = nroMaxDeDatos / 2;
        this.nroMinDeHijos = nroMinDeDatos + 1;
        this.nroMaxDeHijos = orden;
    }

    @Override
    public boolean insertar(T dato) {
        if (esNodoVacio(this.raiz)) {
            this.raiz = new NodoMVias<>(this.orden);
            ((NodoMVias<T>) this.raiz).insertarDatoEnElNodo(dato);
            return true;
        }
        Stack<NodoMVias<T>> pilaDePadres = new Stack<>();
        NodoMVias<T> nodoActual = (NodoMVias<T>) raiz;
        while (!esNodoVacio(nodoActual)) {
            if (esHoja(nodoActual)) {
                if (nodoActual.estaDatoEnElNodo(dato)) {
                    return false;
                }
                nodoActual.insertarDatoEnElNodo(dato);
                if (nodoActual.getNroDatos() <= this.nroMaxDeDatos) {
                    break;
                } else {
                    dividir(nodoActual, pilaDePadres);
                    break;
                }
            } else {
                if (nodoActual.estaDatoEnElNodo(dato)) {
                    return false;
                }
                int tamanhoNodoActual = nodoActual.getNroDatos();
                int posUltimaLlaveNodos = tamanhoNodoActual - 1;
                T mayor = nodoActual.getDato(posUltimaLlaveNodos);
                if (dato.compareTo(mayor) > 0) {
                    pilaDePadres.push(nodoActual);
                    nodoActual = (NodoMVias<T>) nodoActual.getHijo(tamanhoNodoActual);
                } else {
                    NodoMVias<T> nodoAux = nodoActual;
                    for (int i = 0; i < nodoActual.getNroDatos() && nodoAux == nodoActual;
                            i++) {
                        T datoEnTurno = nodoActual.getDato(i);
                        if (dato.compareTo(datoEnTurno) < 0) {
                            nodoAux = (NodoMVias<T>) nodoActual.getHijo(i);
                        }

                    }
                    pilaDePadres.push(nodoActual);
                    nodoActual = nodoAux;
                }

            }
        }
        return true;
    }

    private void dividir(NodoMVias<T> nodoActual, Stack<NodoMVias<T>> pilaDePadres) {
        int indiceDatoDelMedio = this.nroMinDeDatos;
        NodoMVias<T> nuevoHijoIzquierdo = new NodoMVias<>(this.orden);
        NodoMVias<T> nuevoHijoDerecho = new NodoMVias<>(this.orden);
        for (int i = 0; i < indiceDatoDelMedio; i++) {
            nuevoHijoIzquierdo.insertarDatoEnElNodo(nodoActual.getDato(i));
        }
        for (int i = indiceDatoDelMedio + 1; i < orden; i++) {
            nuevoHijoDerecho.insertarDatoEnElNodo(nodoActual.getDato(i));
        }
        if (!esHoja(nodoActual)) {
            for (int i = 0; i <= indiceDatoDelMedio; i++) {
                nuevoHijoIzquierdo.insertarHijo((NodoMVias<T>) nodoActual.getHijo(i));
            }
            for (int i = indiceDatoDelMedio + 1; i <= orden; i++) {
                nuevoHijoDerecho.insertarHijo((NodoMVias<T>) nodoActual.getHijo(i));
            }
        }
        NodoMVias<T> nodoPadre = !pilaDePadres.isEmpty() ? pilaDePadres.pop() : null;
        if (esNodoVacio(nodoPadre)) {
            NodoMVias<T> nuevaRaiz = new NodoMVias<T>(this.orden);
            nuevaRaiz.insertarDatoEnElNodo(nodoActual.getDato(indiceDatoDelMedio));
            nuevaRaiz.insertarHijo(nuevoHijoIzquierdo);
            nuevaRaiz.insertarHijo(nuevoHijoDerecho);
            this.raiz = nuevaRaiz;
        } else {
            nodoPadre.insertarDatoEnElNodo(nodoActual.getDato(indiceDatoDelMedio));
            nodoPadre.removerHijo(nodoActual);
            nodoPadre.insertarHijo(nuevoHijoIzquierdo);
            nodoPadre.insertarHijo(nuevoHijoDerecho);

            if (nodoPadre.getNroDatos() > this.nroMaxDeDatos) {
                dividir(nodoPadre, pilaDePadres);
            }
        }
    }

    @Override
    public boolean eliminar(T datoAEliminar) {
        Stack<NodoMVias<T>> pilaDePadres = new Stack<>();
        NodoMVias<T> nodoActual = buscarNodoDelDato(datoAEliminar, pilaDePadres);
        if (esNodoVacio(nodoActual)) {
            return false;
        }
        if (esHoja(nodoActual)) {
            nodoActual.removerDato(datoAEliminar);

            if (!pilaDePadres.isEmpty()) {
                if (nodoActual.getNroDatos() < this.nroMinDeDatos) {
                    prestarseOFusionar(nodoActual, pilaDePadres);
                }
            } else {
                if (nodoActual.estaNodoSinDato()) {
                    this.raiz = null;
                }
            }
        } else { // no es hoja
            int posicionAReemplazar = nodoActual.getIndiceDeDato(datoAEliminar);
            NodoMVias<T> hijoDeLaMismaPosicion = (NodoMVias<T>) nodoActual.getHijo(posicionAReemplazar);
            pilaDePadres.push(nodoActual);
            NodoMVias<T> nodoDelPredecesor = buscarNodoPredecesor(hijoDeLaMismaPosicion, pilaDePadres);
            T predecesor = nodoDelPredecesor.eliminarLlaveMayorDelNodo();
            nodoActual.setDato(posicionAReemplazar, predecesor);
            if (nodoDelPredecesor.getNroDatos() < this.nroMinDeDatos) {
                prestarseOFusionar(nodoDelPredecesor, pilaDePadres);
            }
        }
        return true;
    }

    public NodoMVias<T> buscarNodoPredecesor(NodoMVias<T> hijoDeLaMismaPosicion, Stack<NodoMVias<T>> pilaDePadres) {
        NodoMVias<T> nodoActual = hijoDeLaMismaPosicion;
        while (!esHoja(nodoActual)) {
            pilaDePadres.push(nodoActual);
            nodoActual = (NodoMVias<T>) nodoActual.getHijo(nodoActual.getNroDatos());
        }
        return nodoActual;
    }

    public NodoMVias<T> buscarNodoDelDato(T dato, Stack<NodoMVias<T>> pilaDePadres) {
        NodoMVias<T> nodoActual = (NodoMVias<T>) this.raiz;
        for (int i = 0; i <= nodoActual.getNroDatos(); i++) {
            if (i < nodoActual.getNroDatos()) {
                if (dato.compareTo(nodoActual.getDato(i)) == 0) {
                    return nodoActual;
                }
                if (dato.compareTo(nodoActual.getDato(i)) < 0) {
                    pilaDePadres.push(nodoActual);
                    nodoActual = (NodoMVias<T>) nodoActual.getHijo(i);
                    i = - 1;
                }
            }
            if (i == nodoActual.getNroDatos()) {
                pilaDePadres.push(nodoActual);
                nodoActual = (NodoMVias<T>) nodoActual.getHijo(i);
                i = - 1;
            }
        }
        return null;
    }

    public void prestarseOFusionar(NodoMVias<T> nodoConProblema, Stack<NodoMVias<T>> pilaDePadres) {
        //Prestarse
        NodoMVias<T> nodoPadre = (NodoMVias<T>) pilaDePadres.pop();
        int posicHijo = nodoPadre.getPosicionDeHijo(nodoConProblema);
        T llaveDelNodoPadre = nodoPadre.getDato(posicHijo);
        if (posicHijo == 0 || (posicHijo > 0 && posicHijo < nodoPadre.getNroDatos())) {
            NodoMVias<T> nodoHermanoDerecho = (NodoMVias<T>) nodoPadre.getHijo(posicHijo + 1);
            if (nodoHermanoDerecho.getNroDatos() > nroMinDeDatos) {
                llaveDelNodoPadre = nodoPadre.getDato(posicHijo);
                nodoConProblema.insertarDatoEnElNodo(llaveDelNodoPadre);
                T llaveHermanoDerecho = nodoHermanoDerecho.getDato(0);
                if (!esHoja(nodoHermanoDerecho)) {
                    NodoMVias<T> hijoDeLaPosicion = (NodoMVias<T>) nodoHermanoDerecho.getHijo(posicHijo);
                    nodoConProblema.insertarHijo(hijoDeLaPosicion);
                }
                nodoHermanoDerecho.removerDato(llaveHermanoDerecho);
                nodoPadre.setDato(posicHijo, llaveHermanoDerecho);
                return;
            }

        } else if (posicHijo == nodoPadre.getNroDatos() || (posicHijo > 0 && posicHijo < nodoPadre.getNroDatos())) {
            llaveDelNodoPadre = nodoPadre.getDato(posicHijo - 1);
            NodoMVias<T> nodoHermanoIzquierdo = (NodoMVias<T>) nodoPadre.getHijo(posicHijo - 1);
            if (nodoHermanoIzquierdo.getNroDatos() > nroMinDeDatos) {
                nodoConProblema.insertarDatoEnElNodo(llaveDelNodoPadre);
                T llaveHermanoIzquierdo = nodoHermanoIzquierdo.getDato(nodoHermanoIzquierdo.getNroDatos() - 1);
                if (!esHoja(nodoHermanoIzquierdo)) {
                    NodoMVias<T> hijoDeLaPosicion = (NodoMVias<T>) nodoHermanoIzquierdo.getHijo(posicHijo);
                    nodoConProblema.insertarHijo(hijoDeLaPosicion);
                }
                nodoHermanoIzquierdo.removerDato(llaveHermanoIzquierdo);
                nodoPadre.setDato(posicHijo - 1, llaveHermanoIzquierdo);
                return;
            }

        } 

        //Fusionar
        if (posicHijo == 0 || (posicHijo > 0 && posicHijo < nodoPadre.getNroDatos())) {
            NodoMVias<T> nodoHermanoDerecho = (NodoMVias<T>) nodoPadre.getHijo(posicHijo + 1);
            NodoMVias<T> nodoNuevo = new NodoMVias<>(this.orden);
            //NodoHermano Derecho Datos e hijos
            for (int i = 0; i < nodoHermanoDerecho.getNroDatos(); i++) {
                T dato = nodoHermanoDerecho.getDato(i);
                nodoNuevo.insertarDatoEnElNodo(dato);
            }
            if (!esHoja(nodoHermanoDerecho)) {
                for (int i = 0; i < nodoHermanoDerecho.getNroHijos(); i++) {
                    NodoMVias<T> nodoHijo = (NodoMVias<T>) nodoHermanoDerecho.getHijo(i);
                    nodoNuevo.insertarHijo(nodoHijo);
                }
            }
            //Dato del padre
            nodoNuevo.insertarDatoEnElNodo(llaveDelNodoPadre);
            //nodo con Problema datos e hijos
            for (int i = 0; i < nodoConProblema.getNroDatos(); i++) {
                T dato = nodoConProblema.getDato(i);
                nodoNuevo.insertarDatoEnElNodo(dato);
            }

            if (!esHoja(nodoConProblema)) {
                for (int i = 0; i <= nodoConProblema.getNroDatos(); i++) {
                    NodoMVias<T> nodoHijo = (NodoMVias<T>) nodoConProblema.getHijo(i);
                    nodoNuevo.insertarHijo(nodoHijo);
                }
            }
            nodoPadre.removerDato(llaveDelNodoPadre);
            nodoPadre.setHijo(posicHijo, nodoNuevo);
        } else if (posicHijo == nodoPadre.getNroDatos()) {
            llaveDelNodoPadre = nodoPadre.getDato(posicHijo - 1);
            NodoMVias<T> nodoHermanoIzquierdo = (NodoMVias<T>) nodoPadre.getHijo(posicHijo - 1);
            NodoMVias<T> nodoNuevo = new NodoMVias<>(this.orden);
            //Nodo hermano izquierdo datos e hijos
            for (int i = 0; i < nodoHermanoIzquierdo.getNroDatos(); i++) {
                T dato = nodoHermanoIzquierdo.getDato(i);
                nodoNuevo.insertarDatoEnElNodo(dato);
            }

            if (!esHoja(nodoHermanoIzquierdo)) {
                for (int i = 0; i <= nodoHermanoIzquierdo.getNroDatos(); i++) {
                    NodoMVias<T> nodoHijo = (NodoMVias<T>) nodoHermanoIzquierdo.getHijo(i);
                    nodoNuevo.insertarHijo(nodoHijo);
                }
            }
            // dato del padre
            nodoNuevo.insertarDatoEnElNodo(llaveDelNodoPadre);
            // Nodo con problemas datos e hijos
            for (int i = 0; i < nodoConProblema.getNroDatos(); i++) {
                T dato = nodoConProblema.getDato(i);
                nodoNuevo.insertarDatoEnElNodo(dato);
            }
            if (!esHoja(nodoConProblema)) {
                for (int i = 0; i <= nodoConProblema.getNroDatos(); i++) {
                    NodoMVias<T> nodoHijo = (NodoMVias<T>) nodoConProblema.getHijo(i);
                    nodoNuevo.insertarHijo(nodoHijo);
                }
            }

            nodoPadre.removerDato(llaveDelNodoPadre);
            nodoPadre.setHijo(nodoPadre.getNroDatos(), nodoNuevo);
        }
        if (!pilaDePadres.isEmpty()) {
            if (nodoPadre.getNroDatos() < nroMinDeDatos) {
                prestarseOFusionar(nodoPadre, pilaDePadres);
            }
        }
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
}
