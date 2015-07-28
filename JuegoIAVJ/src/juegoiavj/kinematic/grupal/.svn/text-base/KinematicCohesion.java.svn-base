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
import juegoiavj.kinematic.acelerado.KinematicSeekA;
import juegoiavj.kinematic.uniforme.KinematicSeek;
import juegoiavj.math.Vector;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Samuel
 */
public class KinematicCohesion extends KinematicSeekA{
    private Kinematic character;
    private List<Kinematic> targets;
    private double threshold;
    private KinematicFlock flock;
    
    public KinematicCohesion(Kinematic character, KinematicFlock flock, double threshold){
        super(character,null,threshold);
        this.character = character;
        this.targets = targets;
        this.threshold = threshold;
        this.flock = flock;
    }
    
    // TODO: PREGUNTAR SOBRE HEADING AL PROFESOR
    
    public SteeringA getSteering(){
        /*
        int count = 0;
        Vector centerOfMass = new Vector();
        
        for(Kinematic target : targets){
            Vector direction = target.getPosicion().clone();
            direction.restar(character.getPosicion());
            double distance = Math.abs(direction.getModulo());
            if (distance > threshold){
                centerOfMass.sumar(target.getPosicion());
                count++;
            }
        }
        if (count==0) return null;
        centerOfMass.divEscalar(count);
        Kinematic cof = new Kinematic(centerOfMass.getX(), centerOfMass.getY());
        KinematicSeek steeringS = new KinematicSeek(character, cof, 0.1);
        return steeringS.getSteering();*/
        
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
    
}
