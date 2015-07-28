/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.pathFinding;

import java.util.LinkedList;
import java.util.List;
import juegoiavj.World;

/**
 *
 * @author Sandra
 */
public class LRTA_M extends LRTA{

    private Node inicio;
    private List<Node> objetivos;
    private Node[][] worldGraph;

    public LRTA_M(Node inicio, List<Node> objetivos, Node[][] worldGraph) {
        this.inicio = inicio;
        this.objetivos = objetivos;
        this.worldGraph = worldGraph;
        //System.out.println("EMPEZANDO LRTA CON EL NODO INICIO:"+inicio.getX()+","+inicio.getY());
    }

    public Node LRTARun() {
        // Si ya estamos en el nodo objetivo devolvemos null
        if (inicio.equals(objetivos)) {
            return null;
        }

        // Sino comenzamos LRTA*LS
        Node u = inicio;
        while (!u.equals(objetivos)) {
            // Escoger espacio local
            //System.out.println("dentro del while");
            List<Node> searchSpace = generateLocalSpace(u);
            // Actualizar heuristicas
            valueUpdateStep(searchSpace);
            do {
                ///System.out.println("dentro del do");
                // Buscamos la mejor opción dentro del espacio local
                double finalCost = Double.MAX_VALUE;
                for (Node a : u.getNeighbours()) {
                    //System.out.println("heuristica " + a.getH() + " Coste " + a.getCost());
                    double auxCost = a.getCost() + a.getH();
                    if (auxCost < finalCost) {
                        finalCost = auxCost;
                        u = a;
                    }
                }
            } while (u.equals(searchSpace));
            // Devolvemos la mejor opción para hacer arrive a ella
            //System.out.println("LTRARUN1");
            //System.out.println("ELEGIDO  heuristica " + u.getH() + " Coste " + u.getCost());
            return u;

        }
        //System.out.println("LTRARUN2");
        return u;
    }

    private double heuristic(Node u) {
        // DISTANCIA MANHATAN
        return Math.abs(u.getX() - objetivos.get(0).getX()) + Math.abs(u.getY() - objetivos.get(0).getY());
        }

    private List<Node> generateLocalSpace(Node u) {
        List<Node> space = new LinkedList<Node>();
        space.add(u);
        //System.out.println("METEMOS " + u.getX() + "," + u.getY());
        for (Node i : u.getNeighbours()) {
            if (!i.equals(objetivos)) {
                space.add(i);
                //System.out.println("METEMOS " + i.getX() + "," + i.getY());
                for (Node j : i.getNeighbours()) {
                    if (!j.equals(objetivos) && !j.equals(space)) {
                        //System.out.println("METEMOS " + j.getX() + "," + j.getY());
                        space.add(j);
                        //for (Node k : j.getNeighbours()) {
                        //    if (!k.equals(objetivos) && !k.equals(space)) {
                        //        System.out.println("METEMOS " + k.getX() + "," + k.getY());
                        //       space.add(k);
                        //    }
                        //}
                    }
                }
            }
        }
        return space;
    }

    private void valueUpdateStep(List<Node> searchSpace) {

        // Ponemos todas las h a infinito
        for (Node u : searchSpace) {
            u.setTempH(heuristic(u));
            u.setH(Double.MAX_VALUE);
        }
        // Mientras queden H infinitas
        while (isInfinite(searchSpace)) {
            for (Node v : searchSpace) {
                if (v.getH() == Double.MAX_VALUE) {
                    // Determinamos el estado
                    // w(u,a) + h(Succ(u,a))
                    double aux1 = Double.MAX_VALUE;
                    for(Node a : v.getNeighbours()){
                        if (a.getH()==-1) a.setH(heuristic(a));
                        if (a.getCost()+a.getH()<aux1) aux1 = a.getCost()+a.getH();
                    }
                    v.setAux(Math.max(v.getTempH(), aux1));
                }
            }
            Node nodeAux = null;
            Double aux1 = Double.MAX_VALUE;
            for (Node v : searchSpace){
                if (v.getH() == Double.MAX_VALUE){
                    if (v.getAux() < aux1){
                        nodeAux = v;
                        aux1 = nodeAux.getAux();
                    }
                }
            }
            nodeAux.setH(nodeAux.getAux());
        }
    }

    private double minSuc(Node v) {
        double costeTemp = Double.MAX_VALUE;
        for (Node n : v.getNeighbours()) {
            if (n.getH() < costeTemp) {
                costeTemp = n.getH();
            }
        }
        return costeTemp;
    }

    private Boolean isInfinite(List<Node> searchSpace) {
        for (Node i : searchSpace) {
            if (i.getH() == Double.MAX_VALUE) {
                return true;
            }
        }
        return false;
    }
}