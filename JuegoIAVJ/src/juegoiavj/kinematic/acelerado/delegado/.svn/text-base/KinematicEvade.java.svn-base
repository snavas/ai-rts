/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic.acelerado.delegado;

import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.acelerado.KinematicFleeA;
import juegoiavj.kinematic.SteeringA;
import juegoiavj.math.Vector;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Sandra
 */
public class KinematicEvade extends KinematicFleeA {
    
    double maxPrediction;
    
    Kinematic objetivo;
    
    public KinematicEvade(Kinematic personaje,Kinematic objetivo,double maxAceleracion,double maxPrediction){
        super(personaje,objetivo,maxAceleracion);
        this.maxPrediction=maxPrediction;
        this.objetivo = objetivo;
    }
    
    public SteeringA getSteering(){

        Vector direccion = new Vector(objetivo.getPosicion());
        direccion.restar(super.getPersonaje().getPosicion());
        
        double distance = direccion.getModulo();
        
        double speed=super.getPersonaje().getVelocidad().getModulo();
        double prediction;
        
        if(speed <= distance/maxPrediction) prediction = maxPrediction;
        else prediction = distance/speed;
        
        super.setObjetivo(objetivo);
        
        Vector velocidad = new Vector(super.getObjetivo().getVelocidad());
        Vector posicion = super.getObjetivo().getPosicion();
        velocidad.multiEscalar(prediction);
        posicion.sumar(velocidad);
        
        return super.getSteering();  
    }
    
    public void setObjetivo(Kinematic c){
        this.objetivo=c;
    }
    
}
