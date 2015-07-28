/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic.acelerado.delegado;

import java.util.logging.Level;
import java.util.logging.Logger;
import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.acelerado.KinematicSeekA;
import juegoiavj.kinematic.SteeringA;
import juegoiavj.math.Vector;
import juegoiavj.util.Block;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Samuel
 */
public class KinematicWallAvoidance extends KinematicSeekA{
    
    private Kinematic collisionDetector;
    private double avoidDistance;
    private double lookAhead;
    
    public KinematicWallAvoidance(Kinematic personaje, Kinematic objetivo, 
            double maxAceleracion, Kinematic collisionDetector, double avoidDistance,
            double lookAhead){
        super(personaje, objetivo, maxAceleracion);
        this.collisionDetector = collisionDetector;
        this.avoidDistance = avoidDistance;
        this.lookAhead = lookAhead;
    }
    
    public SteeringA getSteering(){
        //1. Calculate the collision ray vector
        
        // Calculate the collision ray vector
        /*
        Vector rayVector = getPersonaje().getVelocidad().clone();
        rayVector.normalizar();
        rayVector.multiEscalar(lookAhead);
        super.getPersonaje().updateRay(getPersonaje().getVelocidad());
        * */
        
        // Find the collision
        Vector2f collision = getPersonaje().entityCollisionWith();
        if (collision==null) return null;
        else{
            try {
                Vector2f normal = collision.getNormal();
                normal.scale((float) lookAhead);
                collision.add(normal);
                setObjetivo(new Kinematic(collision.getX(), collision.getY()));
            } catch (SlickException ex) {
                Logger.getLogger(KinematicWallAvoidance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getSteering();
    }
}
