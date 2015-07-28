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
 * @author Sandra
 */
public class KinematicArriveA extends KinematicSteering{
    
    private Kinematic personaje;
    private Kinematic objetivo;
    private double maxAceleracion;
    private double maxSpeed;
    private double targetRadio;
    private double slowRadio;
    private double timeToTarget;
    
  public KinematicArriveA(Kinematic personaje,Kinematic objetivo,double maxSpeed, double targetRadio,double slowRadio,double maxAceleracion){
      this.personaje = personaje;
        this.objetivo = objetivo;
        this.maxSpeed=maxSpeed;
        this.targetRadio=targetRadio;
        this.slowRadio=slowRadio;
        this.maxAceleracion = maxAceleracion;
        this.timeToTarget = 0.1;
  } 
    public SteeringA getSteering(){
        SteeringA steering = new SteeringA();
        
        Vector direccion= objetivo.getPosicion().clone();
        direccion.restar(personaje.getPosicion());
        double distance=direccion.getModulo();
        ;
        
        
       
        if(distance<targetRadio){
            return null;

        }
        if(distance>slowRadio){
            direccion.normalizar();
            direccion.multiEscalar(maxSpeed);
        }
        else{
          direccion.normalizar();
          direccion.multiEscalar(maxSpeed*(distance/slowRadio));
            
            
        }
         
        
        //
        Vector targetVelocity = direccion.clone();
        targetVelocity.normalizar();
        //targetVelocity.multiEscalar(targetSpeed);
        
        // Acceleration tries to get to the target velocity
        targetVelocity.restar(personaje.getVelocidad());
        targetVelocity.divEscalar(timeToTarget);
        steering.setLin(targetVelocity.clone());
        
        // Check if the acceleration is too fast
        if (steering.getLin().getModulo()>maxAceleracion){
            steering.getLin().normalizar();
            steering.getLin().multiEscalar(maxAceleracion);
        }
        
        // Output the steering
        steering.setAng(0);
        return steering;
        
        //objetivo.setVelocidad(direccion);
        /**targetVelocity.normalizar();
        targetVelocity.multiEscalar(targetSpeed);
        targetVelocity.restar(personaje.getVelocidad());
        steering.setLin(targetVelocity);**/
        //objetivo.getVelocidad().normalizar();
        //objetivo.getVelocidad().multiEscalar(targetSpeed);
        //objetivo.getVelocidad().restar(personaje.getVelocidad());
        //steering.setLin(objetivo.getVelocidad());
         /*
        steering.getLin().divEscalar(timeToTarget);
        
        if(steering.getLin().getModulo()>maxAceleracion){
        
            steering.getLin().normalizar();
            steering.getLin().multiEscalar(maxAceleracion);
        
        }
        steering.setAng(0);
        
        return steering;*/
    }



}