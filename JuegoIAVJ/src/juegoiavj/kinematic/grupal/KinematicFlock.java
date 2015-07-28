/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic.grupal;

import java.util.LinkedList;
import java.util.List;
import juegoiavj.kinematic.Kinematic;
import juegoiavj.math.Vector;

/**
 *
 * @author Samuel
 */
public class KinematicFlock {
    private List<Kinematic> vecinos;
    private double size;
    private double minDotProduct;
    
    public KinematicFlock(){
        this.minDotProduct = minDotProduct;
        vecinos = new LinkedList<Kinematic>();
        size=100;
        minDotProduct = -1.0;
    }
    
    public KinematicFlock(List<Kinematic> vecinos, double size, double minDP){
        this.vecinos=vecinos;
        this.size=size;
        this.minDotProduct = minDP;
    }
    
    public int prepareNeighourhood(Kinematic c){
        int count = 0;
        
        Vector look = null;
        if (minDotProduct>-1.0) look = c.getVectorOrientacion();
        
        for(Kinematic p : vecinos){
            
            p.setInNeighbourhood(false);
            
            if(c==p) continue;
            
            Vector distance = p.getPosicion().clone();
            distance.restar(c.getPosicion());
            if (distance.getModulo()>size) continue;
            
            if (minDotProduct>-0.1){
                Vector offset = p.getPosicion().clone();
                offset.restar(c.getPosicion());
                offset.normalizar();
                if (offset.prodEscalar(look)<minDotProduct) continue;
            }
                    
            //Vector distance = p.getPosicion().clone();
           //distance.restar(c.getPosicion());
            //if (distance.getModulo()>size) break;
            
            p.setInNeighbourhood(true);
            count++;
        }
        return count;
    }
    
    public void add(Kinematic c){
        vecinos.add(c);
    }
    
    public Vector getNeighbourhoodCenter(){
        Vector center = new Vector();
        double count = 0;
        for(Kinematic p : vecinos){
            if(p.getInNeighbourhood()){
                center.sumar(p.getPosicion());
                count++;
            }
        }
        center.multiEscalar(1/count);
        return center;
    }
    
    public Vector getNeighbourhoodAverageVelocity(){
        Vector center = new Vector();
        double count = 0;
        for(Kinematic p : vecinos){
            if(p.getInNeighbourhood()){
                center.sumar(p.getVelocidad());
                count++;
            }
        }
        center.multiEscalar(1/count);
        return center;
    }
    
}
