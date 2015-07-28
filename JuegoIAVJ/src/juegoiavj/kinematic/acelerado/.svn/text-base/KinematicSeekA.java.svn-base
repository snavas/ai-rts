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
public class KinematicSeekA extends KinematicSteering{
    private Kinematic personaje;

    public Kinematic getPersonaje() {
        return personaje;
    }

    public Kinematic getObjetivo() {
        return objetivo;
    }

    public void setPersonaje(Kinematic personaje) {
        this.personaje = personaje;
    }

    public void setObjetivo(Kinematic objetivo) {
        this.objetivo = objetivo;
    }

    public void setMaxAceleracion(double maxAceleracion) {
        this.maxAceleracion = maxAceleracion;
    }

    public double getMaxAceleracion() {
        return maxAceleracion;
    }
    private Kinematic objetivo;
    private double maxAceleracion;
    
    public KinematicSeekA(Kinematic personaje, Kinematic objetivo, double maxAceleracion){
        this.personaje = personaje;
        this.objetivo = objetivo;
        this.maxAceleracion = maxAceleracion;
    }
    
    public SteeringA getSteering(){
        SteeringA steering =new SteeringA();
        
        //Get the direccion to the target
        Vector v = new Vector(objetivo.getPosicion());
        v.restar(personaje.getPosicion());
        steering.setLin(v);
        
        //Give full acelleration along this direction
        steering.getLin().normalizar();
        steering.getLin().multiEscalar(maxAceleracion);
        //System.out.print("SEEK LIN:"+steering.getLin().getX()+","+steering.getLin().getY());
        //Output the steering
        steering.setAng(0);
        return steering;   
    }
    
    
}