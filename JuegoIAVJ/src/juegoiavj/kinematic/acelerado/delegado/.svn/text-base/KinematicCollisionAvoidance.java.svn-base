/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic.acelerado.delegado;

import java.util.List;
import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.KinematicSteering;
import juegoiavj.kinematic.SteeringA;
import juegoiavj.math.Vector;

/**
 *
 * @author Samuel
 */
public class KinematicCollisionAvoidance /*extends KinematicEvade*/ extends KinematicSteering{

    private Kinematic character;
    private double maxAcceleration;
    private List<Kinematic> targets;
    private double radius;

    public KinematicCollisionAvoidance(Kinematic character, List<Kinematic> targets, double radius, double maxAcceleration) {
        //super(character,null,0.05,0.05);
        this.character = character;
        this.targets = targets;
        this.radius = radius;
        this.maxAcceleration = maxAcceleration;
    }

    public SteeringA getSteering() {
        //1. find the target that's closest to collision

        // Store the first collision time
        double shortestTime = Double.MAX_VALUE;

        // Store the target that collides then, and other data that we will
        // need and can avoid recalculating

        Kinematic firstTarget = null;
        double firstMinSeparation = Double.MAX_VALUE;
        double firstDistance = 0;
        Vector firstRelativePos = null;
        Vector firstRelativeVel = null;
        double distance = 0;
        Vector relativePos = null;

        // Loop through each target
        for (Kinematic target : targets) {
            // Calculate the time to collision
            if (target.getPosicion().getModulo() == character.getPosicion().getModulo()) {
                System.out.println("Soy yo mismo, ignorar traza");
            } else {
                System.out.println("empezando collision avoidance:");
                System.out.println("Objetivo a evita:" + target.getPosicion().getX() + "," + target.getPosicion().getY());
                System.out.println("Personaje que ev:" + character.getPosicion().getX() + "," + character.getPosicion().getY());
            }

            relativePos = target.getPosicion().clone();
            relativePos.restar(character.getPosicion());

            System.out.println("Distancia entre ellos:" + relativePos.getModulo());

            Vector relativeVel = target.getVelocidad().clone();
            relativeVel.restar(character.getVelocidad());
            //relativeVel.multiEscalar(10);

            double relativeSpeed = relativeVel.getModulo();

            System.out.println("Velocidad entre ellos:" + relativeSpeed);

            double timeToCollision = relativePos.prodEscalar(relativeVel)
                    / (relativeSpeed * relativeSpeed);

            if (relativeSpeed == 0) {
                timeToCollision = Double.MAX_VALUE;
            }

            System.out.println("Time to collision:" + timeToCollision);
            System.out.println("Prod escalar:" + relativePos.prodEscalar(relativeVel));

            // Check if it is going to be a collision at all 
            distance = relativePos.getModulo();
            double minSeparation = distance - relativeSpeed * timeToCollision;

            System.out.println("Min separation:" + minSeparation);
            System.out.println("2*radius:" + (2 * radius));

            // SI ES UN CANDIDATO CONTINUAMOS PARA VER SI ES EL QUE MAS CERCA ESTÁ PRA COLISIONAR
            if (minSeparation > 2 * radius) {
                continue;
            }
            // Check if is the shortest
            if ((timeToCollision > 0) && (timeToCollision < shortestTime)) {
                System.out.println("TIME TO COLLISION <= SHORTEST TIME");
                // sTORE THE TME, TARGET AND OTHER DATA
                shortestTime = timeToCollision;
                firstTarget = target;
                firstMinSeparation = minSeparation;
                firstDistance = distance;
                firstRelativePos = relativePos;
                firstRelativeVel = relativeVel;
                firstRelativeVel.multiEscalar(10);
            } else {
                System.out.println("ESTAMOS YA COLISIONANDO SHUR");
                System.out.println("Time to collision:" + timeToCollision);
                System.out.println("shortest time:" + shortestTime);
            }
        }
        System.out.println("HEMOS SALIDO DEL FOR");

        // 2. Calculate the steering
        // if we have no target, then exit
        if (firstTarget == null) {
            System.out.println("FIRSTARGET = NULL");
            return null;
        }


        System.out.println("HOYGAN, HAY COLISION!");

        // If we're hoinh to hit exactly, or if we're already collinding, then
        // do the steering based on current position
        if ((firstMinSeparation <= 0) || (firstDistance < 2 * radius)) {
            System.out.println("ESTAMOS MUY CERCA, REACCION LOCAL");
            relativePos = firstTarget.getPosicion();
            relativePos.restar(character.getPosicion());
        } else {
            System.out.println("ESTAMOS BIEN, REACCION PREDICTIVA");
            relativePos = firstRelativeVel;
            relativePos.multiEscalar(shortestTime);
            relativePos.sumar(firstRelativePos);
        }
        // Avoid the target
        relativePos.normalizar();
        relativePos.multiEscalar(maxAcceleration);

        System.out.println("terminando collision avoidance:");
        System.out.println("nueva lin:" + relativePos.getX() + "," + relativePos.getY());
        //System.out.println("antigua lin:"+character.getPosicion().getX()+","+character.getPosicion().getY());

        SteeringA steering = new SteeringA();
        steering.setLin(relativePos);
        steering.setAng(0);

        // Return the steering
        return steering;

        /*
         super.setObjetivo(firstTarget);
         return super.getSteering();
        
        
         double minDistance = Double.MAX_VALUE;
         Kinematic firstTarget = null;
         for(Kinematic target : targets){
         if(character.getPosicion().getModulo()!=target.getPosicion().getModulo()){
         System.out.println("empezando collision avoidance:");
         System.out.println("Objetivo a evita:"+target.getPosicion().getX()+","+target.getPosicion().getY());
         System.out.println("Personaje que ev:"+character.getPosicion().getX()+","+character.getPosicion().getY());
         Vector distance = target.getPosicion().clone();
         distance.restar(character.getPosicion().clone());
         System.out.println("Distancia ="+distance.getModulo());
         if(distance.getModulo()!=0.0 && distance.getModulo() < minDistance && distance.getModulo() < 30){
         minDistance = distance.getModulo();
         firstTarget = target;
         System.out.println("Entro al bucle con:"+target.getPosicion().getX()+","+target.getPosicion().getY());
         System.out.println("y mindist: "+minDistance);
         }
         }
         }
        
         if (firstTarget==null){
         return null;
         }
         System.out.println("El personaje:"+character.getPosicion().getX()+","+character.getPosicion().getY());
         System.out.println("hace un evade de: "+firstTarget.getPosicion().getX()+","+firstTarget.getPosicion().getY());
         System.out.println("con mindist: "+minDistance);
         super.setObjetivo(firstTarget);
         SteeringA steering = super.getSteering();
         System.out.println("aplicar steering evade:"+steering.getLin().getX()+","+steering.getLin().getY());
         return steering;
         */
    }
}
