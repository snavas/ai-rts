/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic.acelerado;

/**
 *
 * @author Sandra
 */



import juegoiavj.kinematic.SteeringA;
import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.KinematicSteering;
import juegoiavj.math.Vector;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Sandra
 */
public class KinematicFleeA extends KinematicSteering{
    private Kinematic personaje;
    private Kinematic objetivo;
    private double maxAceleracion;
    
    public KinematicFleeA(Kinematic personaje, Kinematic objetivo, double maxAceleracion){
        this.personaje = personaje;
        this.objetivo = objetivo;
        this.maxAceleracion = maxAceleracion;
    }
    
    public SteeringA getSteering(){
        SteeringA steering =new SteeringA();
        
        //Get the direccion to the target
        Vector v = new Vector(personaje.getPosicion());
        v.restar(objetivo.getPosicion());
        steering.setLin(v);
        
        //Give full acelleration along this direction
        steering.getLin().normalizar();
        steering.getLin().multiEscalar(maxAceleracion);
        
        //Output the steering
        steering.setAng(0);
        return steering;   
    }

    public Kinematic getPersonaje() {
        return personaje;
    }

    public Kinematic getObjetivo() {
        return objetivo;
    }

    public double getMaxAceleracion() {
        return maxAceleracion;
    }

    public void setObjetivo(Kinematic objetivo) {
        this.objetivo = objetivo;
    }

    public void setMaxAceleracion(double maxAceleracion) {
        this.maxAceleracion = maxAceleracion;
    }
    
}