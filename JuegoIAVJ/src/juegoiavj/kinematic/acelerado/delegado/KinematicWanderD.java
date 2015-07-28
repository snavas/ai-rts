/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic.acelerado.delegado;

import java.util.logging.Level;
import java.util.logging.Logger;
import juegoiavj.JuegoIAVJ;
import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.SteeringA;
import juegoiavj.kinematic.acelerado.KinematicSeekA;
import juegoiavj.math.Vector;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Samuel
 */
public class KinematicWanderD extends KinematicFace {
    // Holds the radius and the offset of the wander
    // Circle

    private double wanderOffset;
    private double wanderRadius;
    // Holds the maximun rate at which the wander orientation can change
    private double wanderRate;
    // Holds the current orientation of the wander target
    private double wanderOrientation;
    // Holds the maximun acceleration of the character
    private double maxAcceleration;
    
    private JuegoIAVJ j;

    public KinematicWanderD(Kinematic personaje, Kinematic objetivo, double maxSpeed,
            double targetRadio, double slowRadio, double maxAceleracion, double wanderRadius,
            double wanderRate, double wanderOffset, JuegoIAVJ j) {
        super(personaje, objetivo, maxSpeed, targetRadio, slowRadio, maxAceleracion);
        this.wanderOrientation = personaje.getOrientacion();
        this.wanderRate = wanderRate;
        this.wanderOffset = wanderOffset;
        this.wanderRadius = wanderRadius;
        this.maxAcceleration = maxAceleracion;
        this.j = j;
    }

    /*
     public KinematicWanderD(Kinematic personaje, Kinematic objetivo, double maxSpeed){
     super(personaje, objetivo, maxSpeed);
     }*/
    public SteeringA getSteering() {
        // ALGORITMO DEL LIBRO
        // 1. Calculate the target to delegate to face

        // Update the wander orientation
        wanderOrientation += randomBinominial() * wanderRate;

        // Calculate the combined target orientation
        double targetOrientation = wanderOrientation + getPersonaje().getOrientacion();

        // Calculate the center of the wander circle
        Vector target = getPersonaje().getVectorOrientacion().clone();
        target.multiEscalar(wanderOffset);
        target.sumar(getPersonaje().getPosicion());
        j.wanderCircle=target.clone();

        // Calculate the target location
        Vector targetOri = new Vector(-Math.sin(targetOrientation), Math.cos(targetOrientation));
        targetOri.multiEscalar(wanderRadius);
        target.sumar(targetOri);
        j.wanderPoint1 = target.clone();
        j.wanderPoint2 = super.getPersonaje().getPosicion().clone();
        
        //System.out.println("target:"+target.getModulo()+", targetOri:"+targetOrientation);
        try {
            // 2. Delegate to face
            //System.out.println("WANDERD PERSONAJE: "+super.getPersonaje().getPosicion().getX()+","+super.getPersonaje().getPosicion().getY());
            //System.out.println("WANDERD OBJETIVOO: "+target.getX()+","+target.getY());
            super.setObjetivo(new Kinematic(target.getX(), target.getY()));
        } catch (SlickException ex) {
            Logger.getLogger(KinematicWanderD.class.getName()).log(Level.SEVERE, null, ex);
        }
        SteeringA steering = super.getSteering();
        
        // 3. Now set the linear acceleration to be at full acceleration in the 
        // direction of the orientation
        if (steering != null) {
            Vector charOri = getPersonaje().getVectorOrientacion().clone();
            //System.out.println("VECTOR ORI: "+getPersonaje().getVectorOrientacion().getModulo());
            //System.out.println("MAX ACCELE: "+maxAcceleration);
            charOri.multiEscalar(maxAcceleration);
            steering.setLin(charOri);
            //System.out.println("WANDERD LINEAL: "+steering.getLin().getModulo());
        } else {
            //System.out.println("FACE NULL");
        }
        //System.out.println("steering.lin:"+steering.getLin().getModulo()+", steering.ang:"+steering.getAng());
        return steering;
    }

    private double randomBinominial() {
        return Math.random() - Math.random();
    }
}
