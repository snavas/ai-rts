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
public class KinematicArrive extends KinematicSteering{
    private Kinematic personaje;
    private Kinematic objetivo;
    private double maxSpeed;
    private double radio;
    private double timeToTarget = 0.25;
    
    public KinematicArrive(Kinematic personaje, Kinematic objetivo, double maxSpeed, double radio){
        this.personaje = personaje;
        this.objetivo = objetivo;
        this.maxSpeed = maxSpeed;
        this.radio = radio;
    }
    
    public SteeringA getSteering(){
        // Create the sctructure for output
        SteeringA steering = new SteeringA();
        
        
        //Get the direction to the target
        Vector velocidad = new Vector(objetivo.getPosicion());
        velocidad.restar(personaje.getPosicion());
        steering.setLin(velocidad);
        
        // Check if we're within radius
        if (steering.getLin().getModulo()<radio)
                return null;

        // We need to move our target, we'd like to get there in timeToTarget seconds
        //steering.velocity /= timeToTarget
        steering.getLin().divEscalar(timeToTarget);
        
        // If this is too fast, clip it to the max speed
        if (steering.getLin().getModulo()>maxSpeed){
            steering.getLin().normalizar();
            Vector vel = steering.getLin();
            vel.multiEscalar(maxSpeed);
            steering.setLin(vel);
        }
       
        //Face in the direction we want to move
        personaje.setOrientacion(getNewOrientacion(personaje.getOrientacion(), steering.getLin()));
        
        //Output the steering
        steering.setAng(0);
        return steering;
    }

    private double getNewOrientacion(double orientacion, Vector vel) {
        if(vel.getModulo()>0){
            return  Math.atan2(vel.getX(),-vel.getY());
        }
        else
            return orientacion;
    }
    
    public void setObjetivo(Kinematic target){
        objetivo = target;
    }
    
    
}
