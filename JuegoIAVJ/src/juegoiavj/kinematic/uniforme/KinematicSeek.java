/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic.uniforme;

import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.KinematicSteering;
import juegoiavj.kinematic.SteeringA;
import juegoiavj.math.Vector;

/**
 *
 * @author Samuel
 */
public class KinematicSeek extends KinematicSteering{
    private Kinematic personaje;
    private Kinematic objetivo;
    private double maxSpeed;
    
    public KinematicSeek(Kinematic personaje, Kinematic objetivo, double maxSpeed){
        this.personaje = personaje;
        this.objetivo = objetivo;
        this.maxSpeed = maxSpeed;
    }
    
    public SteeringA getSteering(){
        // Create the sctructure for output
        SteeringA steering = new SteeringA();
        
        //Get the direction to the target
        Vector velocidad = objetivo.getPosicion().clone();
        velocidad.restar(personaje.getPosicion());
        steering.setLin(velocidad);

        //The velocity is along this direction, at full speed
        steering.getLin().normalizar();
        steering.getLin().multiEscalar(maxSpeed);
        
        //Face in the direction we want to move
        personaje.setOrientacion(getNewOrientacion(personaje.getOrientacion(), steering.getLin()));
        
        //Output the steering
        steering.setAng(0);
        return steering;
    }

    private double getNewOrientacion(double orientacion, Vector vel) {
        //System.out.println(vel);
        if(vel.getModulo()>0){
            System.out.println("SeekOrientation: "+Math.atan2(vel.getY(),-vel.getX()));
            return  Math.atan2(vel.getX(),-vel.getY());
            //return Math.atan2(vel.getY(), -vel.getX());
            //return Math.atan2(-vel.getX(), vel.getY());
        }
            //return Math.atan2(Y, X)
            //return  Math.atan2(-vel.getX(),vel.getY());
        else
            return orientacion;
    }
    
    
}
