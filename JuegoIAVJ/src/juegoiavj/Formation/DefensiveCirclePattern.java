/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.Formation;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import juegoiavj.kinematic.Kinematic;
import juegoiavj.math.Vector;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Samuel
 */
public class DefensiveCirclePattern implements FormationPattern{
    
    private double characterRadius;
    private int numberOfSlots;
    private int maxSlotNumber;

    public DefensiveCirclePattern() {
        characterRadius = 32;
        maxSlotNumber = Integer.MAX_VALUE;
    }

    
    public int calculateNumberOfSlots(List<SlotAssignment> slotAsignments){
        // Find the number of filled slots, it will be the highest slot number in the assignments
        /*
        int filledSlots = 0;
        for(SlotAssignment assignment : slotAsignments){
            if(assignment.getSlotNumber()>=maxSlotNumber){
                filledSlots = assignment.getSlotNumber();
            }
        }
        numberOfSlots = filledSlots +1;
        return numberOfSlots;
        * */
        numberOfSlots = slotAsignments.size();
        return numberOfSlots;
    }

    @Override
    public Kinematic getDriftOffset(List<SlotAssignment> slotAsignments){
        calculateNumberOfSlots(slotAsignments);
        Kinematic center = null;
        try {
            // Store the center of mass
            center = new Kinematic(0,0);
        } catch (SlickException ex) {
            Logger.getLogger(DefensiveCirclePattern.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Now go through each assignment, and add its contribution to the center
        for(SlotAssignment assignment : slotAsignments){
            Kinematic location = getSlotLocation(assignment.getSlotNumber());
            center.getPosicion().sumar(location.getPosicion());
            center.setOrientacion(center.getOrientacion()+location.getOrientacion());
        }
        // Divide through to get the drift offset
        int numberOfAssignments = slotAsignments.size();
        center.getPosicion().divEscalar(numberOfAssignments);
        center.setOrientacion(center.getOrientacion()/numberOfAssignments);
        return center;
    }

    @Override
    public Kinematic getSlotLocation(double slotNumber) {
        
        // We place the slots around a circle based on their slot number
       // System.out.println("Soy el numero  "+slotNumber+" y somos "+numberOfSlots);
        double angleAroundCircle = slotNumber/numberOfSlots*Math.PI*2;
        
        //System.out.println("Soy el numero  "+slotNumber+" y tengo un angulo de "+angleAroundCircle+ "y somos "+numberOfSlots);
        // The radius depends on the radius of the character, and the number
        // of characters in the circle: we want there to be no gap betwen character's shoulders.
        double radius = characterRadius / Math.sin(Math.PI/numberOfSlots);
        // Create a location, and fill its components based on the angle around circle.
        Kinematic location = null;
        try {
            location = new Kinematic(0,0);
        } catch (SlickException ex) {
            Logger.getLogger(DefensiveCirclePattern.class.getName()).log(Level.SEVERE, null, ex);
        }
        location.setPosicion(radius * Math.cos(angleAroundCircle), radius * Math.sin(angleAroundCircle));
        location.setOrientacion(angleAroundCircle);
        
        return location;
    }

    @Override
    public boolean supportsSlots(int slotCount) {
        return true;
    }
    
}
