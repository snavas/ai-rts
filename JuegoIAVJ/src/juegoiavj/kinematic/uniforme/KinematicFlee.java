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
public class KinematicFlee  extends KinematicSteering{
    private Kinematic personaje;
    private Kinematic objetivo;
    private double maxSpeed;
    
    public KinematicFlee(Kinematic personaje, Kinematic objetivo, double maxSpeed){
        this.personaje = personaje;
        this.objetivo = objetivo;
        this.maxSpeed = maxSpeed;
    }
    
    public SteeringA getSteering(){
        // Create the sctructure for output
        SteeringA steering = new SteeringA();
        
        //Get the direction to the target
        Vector velocidad = new Vector(personaje.getPosicion());
        velocidad.restar(objetivo.getPosicion());
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
        if(vel.getModulo()>0){
            return  Math.atan2(vel.getX(),-vel.getY());
        }
        else
            return orientacion;
    }
    
    
}
