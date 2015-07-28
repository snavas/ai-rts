/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic.acelerado.delegado;

import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.acelerado.KinematicAling;
import juegoiavj.kinematic.SteeringA;
import juegoiavj.math.Vector;

/**
 *
 * @author Samuel
 */
public class KinematicFace extends KinematicAling{
    
    private Kinematic objetivo;
    
    public KinematicFace(Kinematic personaje,Kinematic objetivo,double maxSpeed,
            double targetRadio,double slowRadio,double maxAceleracion){
        super(personaje,objetivo,maxSpeed,targetRadio,slowRadio,maxAceleracion);
        this.objetivo=objetivo;
        //System.out.println("GIRANDO HACIA EL ENEMIGO");
    }
    
    public SteeringA getSteering(){
        //1. Calculate the target to delegate to aling
        //Work out the direction to target
        Vector direction = objetivo.getPosicion().clone();
        direction.restar(getPersonaje().getPosicion());
        // Check for a zero direction, and make no change if so
        if (direction.getModulo()==0){
            //System.out.println("DEVOLVIENDO NULL");
            return null;
        }
        
        //Put the target together
        objetivo.setOrientacion(Math.atan2(direction.getX(),-direction.getY()));
        setObjetivo(objetivo);
        
        //

        return super.getSteering();
    }
    
    @Override
    public void setObjetivo(Kinematic o){
        this.objetivo = o;
        super.setObjetivo(o);
    }
    
}
