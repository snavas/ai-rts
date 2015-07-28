/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic.acelerado.delegado;

import java.util.logging.Level;
import java.util.logging.Logger;
import juegoiavj.JuegoIAVJ;
import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.Path;
import juegoiavj.kinematic.acelerado.KinematicSeekA;
import juegoiavj.kinematic.SteeringA;
import juegoiavj.math.Vector;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Samuel
 */
public class KinematicFollowPath extends KinematicSeekA{
    
    private Path path;
    private double pathOffset;
    private Vector currentParam;
    private JuegoIAVJ world;
    
    public KinematicFollowPath(Kinematic personaje, double maxAceleracion,
            double pathOffset, Path path, JuegoIAVJ j){
        super(personaje,null,maxAceleracion);
        this.pathOffset = pathOffset;
        this.currentParam = path.getFirstParam();
        this.path = path;
        world = j;
    }
    
    public SteeringA getSteering(){
        
        // 1. Calculate the target to delegate to face
        
        // Find the current position of the path
        currentParam = path.getParam(getPersonaje().getPosicion(),currentParam);
        Vector targetParam = currentParam.clone();
        //world.debugPoint1 = targetParam.clone();
        // Le sumamos el offset
        targetParam = path.addOffset(pathOffset, targetParam);
        //world.debugPoint2 = targetParam.clone();
        try {
            Kinematic tarjet = new Kinematic(targetParam.getX(), targetParam.getY());
            this.setObjetivo(tarjet);
        } catch (SlickException ex) {
            Logger.getLogger(KinematicFollowPath.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.getSteering();
    }

    public Path getPath() {
        return path;
    }
    
}
