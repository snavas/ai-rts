/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic.acelerado.delegado;

import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.acelerado.KinematicAling;
import juegoiavj.kinematic.SteeringA;

/**
 *
 * @author Samuel
 */
public class KinematicLook extends KinematicAling{
    
    
    public KinematicLook(Kinematic personaje, Kinematic objetivo, double maxAceleracionAngular,
            double maxRotacion, double radioObjetivo, double radioDeDesaceleracion){
        super(personaje,objetivo,maxAceleracionAngular,maxRotacion,radioObjetivo,radioDeDesaceleracion);
    }
    
    public SteeringA getSteering(){
        
        // 1. Calculate the target to delegate to aling
        
        // Check for a zero direction, and make no change if so
        if (getPersonaje().getVelocidad().getModulo()==0) return null;
        
        // Otherwise set the target based on the velocity
        getObjetivo().setOrientacion(Math.atan2(getPersonaje().getVelocidad().getX(),
                -getPersonaje().getVelocidad().getY()));
        
        //Seek Math.atan2(vel.getX(),-vel.getY());
        
        // 2. Delegate to aling
        return super.getSteering();  
    }
    
}
