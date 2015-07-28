/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic.acelerado.delegado;

import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.acelerado.KinematicSeekA;
import juegoiavj.math.Vector;
import juegoiavj.kinematic.SteeringA;

/**
 *
 * @author Sandra
 */
public class KinematicPursue extends KinematicSeekA{
    double maxPrediction;
    Kinematic objetivo;
    
    public KinematicPursue(Kinematic personaje,Kinematic objetivo,double maxAceleracion,double maxPrediction){
        super(personaje,objetivo,maxAceleracion);
        this.maxPrediction=maxPrediction;
        this.objetivo = objetivo;
    }
    
    public SteeringA getSteering(){
        
        Vector direccion = new Vector(objetivo.getPosicion());
        direccion.restar(getPersonaje().getPosicion());
        
        double distance = direccion.getModulo();
        
        double speed=getPersonaje().getVelocidad().getModulo();
        double prediction;
        
        if(speed <= distance/maxPrediction) prediction = maxPrediction;
        else prediction = distance/speed;
        
        super.setObjetivo(objetivo);
        
        Vector velocidad = new Vector(getObjetivo().getVelocidad());
        Vector posicion = getObjetivo().getPosicion();
        velocidad.multiEscalar(prediction);
        posicion.sumar(velocidad);
        
        return super.getSteering();  
    }
    
    @Override
    public void setObjetivo(Kinematic objetivo){
        this.objetivo = objetivo;
    }
    
}
