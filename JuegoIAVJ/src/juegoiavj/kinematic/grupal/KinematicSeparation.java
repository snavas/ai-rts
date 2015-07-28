/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic.grupal;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.KinematicSteering;
import juegoiavj.kinematic.SteeringA;
import juegoiavj.kinematic.acelerado.KinematicFleeA;
import juegoiavj.math.Vector;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Samuel
 */
public class KinematicSeparation extends KinematicFleeA{
    private Kinematic character;
    private List<Kinematic> targets;
    private KinematicFlock flock;
    private double threshold;
    private double decayCofficient;
    private double maxAcceleration;
    
    public KinematicSeparation(Kinematic character, KinematicFlock flock, double maxAcceleration){
        super(character,null,maxAcceleration);
        this.character = character;
        this.targets = targets;
        this.threshold = threshold;
        this.decayCofficient = decayCofficient;
        this.maxAcceleration = maxAcceleration;
        this.flock = flock;
    }
    
    public SteeringA getSteering(){
        /*
        // METODO DEL PRIMER LIBRO
        // The steering variable holds the output
        SteeringA steering = new SteeringA();
        
        // Loop through each target
        for(Kinematic target : targets){
            // Check if the target is close
            Vector direction = target.getPosicion().clone();
            //System.out.println("target direction module:"+direction.getModulo());
            direction.restar(character.getPosicion());
            Double distance = direction.getModulo();
            if (distance < threshold){
                // Calculate the strength of repulsion
                //System.out.println("distance:"+distance+", threshold:"+threshold);
                double strength = Math.min(decayCofficient / (distance * distance), maxAcceleration);
                // Add the acceleration
                direction.normalizar();
                direction.multiEscalar(strength);
                steering.getLin().sumar(direction);
                //System.out.println("steering.lin:"+steering.getLin().getModulo()+", steering.ang:"+steering.getAng()+", strength:"+strength);
            }
            // We have gone through all targets, return the result
            
        }
        return steering;
    }*/
    
    /* METODO DEL SEGUNDO LIBRO
    public SteeringA getSteering(){
        // The steering variable holds the output
        SteeringA steering = new SteeringA();
        
        // Loop through each target
        for(Kinematic target : targets){
            Vector toAgent = target.getPosicion().clone();
            toAgent.restar(character.getPosicion());
            
            double distance = toAgent.getModulo();
            toAgent.normalizar();
            toAgent.divEscalar(distance);
            
            steering.getLin().sumar(toAgent);
            
        }
        // We have gone through all targets, return the result
        return steering;
    }*/
        
    // METODO DE AIMCORE
        int count = flock.prepareNeighourhood(character);
        if (count<=0) return new SteeringA();
        
        Vector cofm = flock.getNeighbourhoodCenter();
        try {
            super.setObjetivo(new Kinematic(cofm.getX(),cofm.getY()));
        } catch (SlickException ex) {
            Logger.getLogger(KinematicSeparation.class.getName()).log(Level.SEVERE, null, ex);
        }
    return super.getSteering();
   
    }
    
    @Override
    public void setObjetivo(Kinematic o){
        // null
    }
}
