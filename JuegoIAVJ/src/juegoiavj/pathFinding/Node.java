/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.pathFinding;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Samuel
 */
public class Node {
    private double cost;
    private double h;
    private double tempH;
    private double aux;
    private int x;
    private int y;
    private List<Node> neighbours;

    public double getTempH() {
        return tempH;
    }

    public void setTempH(double tempH) {
        this.tempH = tempH;
    }
    
    public Node(double cost, int x, int y){
        this.cost = cost;
        neighbours = new LinkedList<Node>();
        this.x = x;
        this.y = y;
        h = -1;
    }
    
    public void addNeighbour(Node n){
        neighbours.add(n);
    }
    
    public Boolean equals(Node n){
        return n.getX() == x && n.getY() == y;
    }
    
    public Boolean equals(List<Node> lista){
        for (Node n : lista){
            //System.out.println("i");
            if (n.getX() == x && n.getY() == y) return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public double getCost() {
        return cost;
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }

    public int getY() {
        return y;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getAux() {
        return aux;
    }

    public void setAux(double aux) {
        this.aux = aux;
    }
    
    

}
