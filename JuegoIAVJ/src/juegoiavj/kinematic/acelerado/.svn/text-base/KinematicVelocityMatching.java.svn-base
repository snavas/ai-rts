/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic.acelerado;


import juegoiavj.kinematic.SteeringA;
import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.KinematicSteering;
import juegoiavj.math.Vector;

/**
 *
 * @author Samuel
 */
public class KinematicVelocityMatching extends KinematicSteering{
    private Kinematic personaje;
    private Kinematic objetivo;
    private double maxAceleracion;
    private double timeToTarget;
    
    public KinematicVelocityMatching(Kinematic personaje, Kinematic objetivo, double maxAceleracion){
        this.personaje = personaje;
        this.objetivo = objetivo;
        this.maxAceleracion = maxAceleracion;
        this.timeToTarget = 0.1;
    }
    
    public SteeringA getSteering(){
        // Create the structure to hold our output
        SteeringA steering =new SteeringA();
        
        // Acceleration tries to get to the target velocity
        Vector linear = new Vector(objetivo.getVelocidad());
        linear.restar(personaje.getVelocidad());
        linear.divEscalar(timeToTarget);
        steering.setLin(linear);
        
        // Check if the acceleration is too fast
        if (steering.getLin().getModulo()>maxAceleracion){
            steering.getLin().normalizar();
            steering.getLin().multiEscalar(maxAceleracion);
        }
        
        //Output the steering
        steering.setAng(0);
        return steering;   
    }
    
    
}