/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic;

import juegoiavj.math.Vector;

/**
 *
 * @author Sandra
 */
public class SteeringA {
    
    private Vector lineal;
    private double angular;
    
    public SteeringA(){
        lineal = new Vector();
        angular = 0;
    }
    
    public SteeringA(Vector v, double r){
        this.lineal = v;
        this.angular = r;
    }
    
    public Vector getLin(){
        return lineal;
    }
    
    public double getAng(){
        return angular;
    }
    
    public void setLin(Vector v){
        lineal = v;
    }
    
    public void setAng(double r){
        angular = r;
    }
    
}
