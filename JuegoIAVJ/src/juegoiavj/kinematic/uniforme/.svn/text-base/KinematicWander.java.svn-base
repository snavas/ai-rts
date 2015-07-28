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
public class KinematicWander extends KinematicSteering{
    private Kinematic personaje;
    private double maxSpeed;
    private double maxRotation;
    
    public KinematicWander(Kinematic personaje, double maxSpeed, double maxRotation){
        this.personaje = personaje;
        this.maxRotation = maxRotation;
        this.maxSpeed = maxSpeed;
    }
    
    public SteeringA getSteering(){
        // Create the sctructure for output
        SteeringA steering = new SteeringA();
        
        //Get the velocity from the vector from the orientation
        Vector velocidad = personaje.getVectorOrientacion().clone();
        velocidad.multiEscalar(maxSpeed);
        steering.setLin(velocidad);
        
        //personaje.setOrientacion(getNewOrientacion(personaje.getOrientacion(), steering.getLin()));
        //steering.setAng(0);
        
        //Change the orientation randomly
        steering.setAng(randomBinominial()*maxRotation);

        //Output the steering
        return steering;
    }

    private double randomBinominial() {
        double rotacion = Math.random()-Math.random();
        //System.out.println("WanderRotation = "+rotacion);
        return rotacion;
    }
    
        private double getNewOrientacion(double orientacion, Vector vel) {
        if(vel.getModulo()>0){
            return  Math.toDegrees(Math.atan2(vel.getX(),-vel.getY()));
        }
        else
            return orientacion;
    }
    
    
}
