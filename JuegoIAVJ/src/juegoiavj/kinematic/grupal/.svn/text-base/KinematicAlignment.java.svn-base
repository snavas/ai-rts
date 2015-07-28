/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic.grupal;

import java.util.List;
import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.KinematicSteering;
import juegoiavj.kinematic.SteeringA;
import juegoiavj.math.Vector;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Samuel
 */
public class KinematicAlignment extends KinematicSteering{
    
    private Kinematic character;
    private List<Kinematic> targets;
    private double threshold;
    private KinematicFlock flock;
    
    public KinematicAlignment(Kinematic character, KinematicFlock flock, double threshold){
        this.character = character;
        this.targets = targets;
        this.threshold = threshold;
        this.flock = flock;
    }
    
    // TODO: PREGUNTAR SOBRE HEADING AL PROFESOR
    
    public SteeringA getSteering(){
        /*
        int count = 0;
        Vector heading = new Vector();
        
        for(Kinematic target : targets){
            Vector direction = target.getPosicion().clone();
            direction.restar(character.getPosicion());
            double distance = Math.abs(direction.getModulo());
            if (distance > threshold){
                heading.sumar(target.getVectorHeading());
                count++;
            }
        }
        if (count>0){
            heading.divEscalar(count);
            heading.restar(character.getVectorHeading());
        }
        
        return new SteeringA();*/
        
        int count = flock.prepareNeighourhood(character);
        if (count<=0) return new SteeringA();
        
        Vector vel = flock.getNeighbourhoodCenter();
        vel.restar(character.getVelocidad());
        Vector outputLin = vel.clone();
        if (outputLin.squareMagnitude()>threshold*threshold){
            outputLin.normalizar();
            outputLin.multiEscalar(threshold);
        }
        
        SteeringA steering = new SteeringA();
        steering.setLin(outputLin);
        return steering;
    }
}
