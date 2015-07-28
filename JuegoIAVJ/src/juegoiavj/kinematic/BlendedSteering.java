/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic;

import java.util.LinkedList;
import java.util.List;
import juegoiavj.math.Vector;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Samuel
 */
public class BlendedSteering {
    private List<Behavior> behaviors;
    private double maxAcceleration;
    private double maxRotation;
    
    public BlendedSteering(double maxAcceleration, double maxRotation){
        this.behaviors = new LinkedList<Behavior>();
        this.maxAcceleration = maxAcceleration;
        this.maxRotation = maxRotation;  
    }
    
    /*
    public SteeringA getSteering() throws SlickException{
        // Create the steering structure for accumulation
        SteeringA steering = new SteeringA();
        Vector lineal = new Vector();
        double angular = 0;
        
        // Accumulate all accelerations
        for (Behavior behavior : behaviors){
            if (behavior.getBehavior().getSteering()!=null){
                Vector steeringLin = behavior.getBehavior().getSteering().getLin().clone();
                steeringLin.multiEscalar(behavior.getWeight());
                lineal.sumar(steeringLin);
                angular += behavior.getBehavior().getSteering().getAng() * behavior.getWeight();
            }
        }
        // Crop the result and return
        if (lineal.getModulo() > maxAcceleration){
            lineal.normalizar();
            lineal.multiEscalar(maxAcceleration);
        }
        
        angular = Math.max(angular, maxRotation);
        
        
        steering.setLin(lineal);
        steering.setAng(angular);
        
        return steering;
    }*/
    
    public SteeringA getSteering() throws SlickException{
        SteeringA steering = new SteeringA();
        
        // Acumulate the accelerations
        for (Behavior behavior : behaviors){
            if(behavior.getBehavior().getSteering()!=null){
                Vector linealAux = behavior.getBehavior().getSteering().getLin().clone();
                double weightAux = behavior.getWeight();
                double angularAux1 = behavior.getBehavior().getSteering().getAng();
                //linealAux.normalizar();
                //System.out.println("lineal antes:"+linealAux.getModulo()+" + peso:"+behavior.getWeight());
                linealAux.multiEscalar(weightAux);
                //System.out.println("lineal multi:"+linealAux.getModulo());
                steering.getLin().sumar(linealAux);

                double angularAux = steering.getAng();
                angularAux += angularAux1 * weightAux;
                steering.setAng(angularAux);
            }
        }
        
       
        // Crop the result and return
        //System.out.println("lineal despues:"+steering.getLin().getModulo());
        if (steering.getLin().getModulo() > maxAcceleration){
            steering.getLin().normalizar();
            steering.getLin().multiEscalar(maxAcceleration);
        }
        
        steering.setAng(Math.min(steering.getAng(), maxRotation));
        
        return steering;
    }
    
    public void addBehavior(KinematicSteering steering, double value){
        behaviors.add(new Behavior(steering, value));
    }
    
    public void setObjetivo(Kinematic target){
        for (Behavior behavior : behaviors){
            behavior.getBehavior().setObjetivo(target);
        }
    }
    
    public void disable(){
        for (Behavior behavior : behaviors){
            behavior.getBehavior().disable();
        }
    }
}
