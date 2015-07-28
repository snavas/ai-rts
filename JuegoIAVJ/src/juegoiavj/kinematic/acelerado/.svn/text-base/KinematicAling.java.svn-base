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
public class KinematicAling extends KinematicSteering{
    private Kinematic personaje;
    private Kinematic objetivo;
    private double maxAceleracionAngular;
    private double maxRotacion;
    private double radioObjetivo;
    private double radioDeDesaceleracion;
    private double timeToTarget;
    
    public KinematicAling(Kinematic personaje, Kinematic objetivo, double maxAceleracionAngular, double maxRotacion, 
            double radioObjetivo, double radioDeDesaceleracion){
        this.personaje = personaje;
        this.objetivo = objetivo;
        this.maxAceleracionAngular = maxAceleracionAngular;
        this.maxRotacion = maxRotacion;
        this.radioObjetivo = radioObjetivo;
        this.radioDeDesaceleracion = radioDeDesaceleracion;
        this.timeToTarget = 0.1;
    }
    
    public SteeringA getSteering(){
        SteeringA steering =new SteeringA();
        
        //Give the native direction to the target
        double rotacion = objetivo.getOrientacion()-personaje.getOrientacion();
        
        // Map the result to the (-pi, pi) interval
        rotacion = mapToRange(rotacion);
        double tamañoDeRotacion = Math.abs(rotacion);
        double targetRotation = 0;
        
        // @TODO : repasar, Check if we are there, return no steering
        if(tamañoDeRotacion < radioObjetivo) return null;
        // If we are outside the slowradius use then maximun rotaiton
        if(tamañoDeRotacion > radioDeDesaceleracion) targetRotation = maxRotacion;
        // Otherwise calculated the scaled rotation
        else targetRotation = maxRotacion*tamañoDeRotacion/radioDeDesaceleracion;
        
        // The final target rotation combines speed (already in the variable) and direction
        targetRotation*= rotacion/tamañoDeRotacion;
        
        // Acceleration tries to get to the target rotation
        steering.setAng(targetRotation-personaje.getRotacion());
        double angular = steering.getAng();
        steering.setAng(angular/timeToTarget);
        
        // Check if the acceleration is too great
        double aceleracionAngular = Math.abs(steering.getAng());
        if (aceleracionAngular>maxAceleracionAngular){
            angular = steering.getAng();
            angular /= aceleracionAngular;
            angular *= maxAceleracionAngular;
            steering.setAng(angular);
        }
        
        //Output the steering
        steering.getLin().setX(0);
        steering.getLin().setY(0);
        return steering;   
    }

    private double mapToRange(double rotacion) {
        if(rotacion>Math.PI) rotacion -= 2*Math.PI;
        else if (rotacion< -Math.PI) rotacion += 2*Math.PI;
        //System.out.println("maptorange="+rotacion);
        return rotacion;
    }

    public Kinematic getPersonaje() {
        return personaje;
    }

    public double getMaxAceleracionAngular() {
        return maxAceleracionAngular;
    }

    public double getMaxRotacion() {
        return maxRotacion;
    }

    public double getRadioObjetivo() {
        return radioObjetivo;
    }

    public Kinematic getObjetivo() {
        return objetivo;
    }
    
    public void setObjetivo(Kinematic objetivo) {
        this.objetivo = objetivo;
    }
    
    public double getRadioDeDesaceleracion() {
        return radioDeDesaceleracion;
    }

    public double getTimeToTarget() {
        return timeToTarget;
    }

    
    
}