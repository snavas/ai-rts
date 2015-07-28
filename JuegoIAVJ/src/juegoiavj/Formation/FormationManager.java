/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.Formation;

import java.util.LinkedList;
import java.util.List;
import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.uniforme.KinematicArrive;
import juegoiavj.math.Vector;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Sandra
 */
public class FormationManager {
    
    
    private List<SlotAssignment> slotAssignments;
    private Kinematic driftOffset;
    private FormationPattern pattern;
    private KinematicArrive slotArrive;
    
    
    public FormationManager(FormationPattern pattern){
        this.pattern=pattern;
        this.slotAssignments= new LinkedList<SlotAssignment>();
    }
    
    public void updateSlotAssignments(){
    
        for(int i=0;i<slotAssignments.size();i++){
            slotAssignments.get(i).setSlotNumber(i);
            
        
        }
        this.driftOffset=pattern.getDriftOffset(slotAssignments);
    
    }
    
    public boolean addCharater(Kinematic character){
        
        int occupiedSlots=slotAssignments.size();
        
        if(pattern.supportsSlots(occupiedSlots+1)){
            SlotAssignment SlotAssig = new SlotAssignment(character);
            this.slotAssignments.add(SlotAssig);
            updateSlotAssignments();
            return true;
        }
        
         return false;   
        
        
        }
        
    public boolean removeCharacter(Kinematic character){
        double slot;
        
        for(SlotAssignment i : slotAssignments){
            
            if(i.getCharacter()==(character)){
                slot=i.getSlotNumber();
                System.out.println("Encontramos character "+i.getSlotNumber());
                
                if(slot>=0&&slot<slotAssignments.size()){
                    slotAssignments.remove(i);
                    updateSlotAssignments();
                    return true;
                }  
            }
        }
        return false;
        
    }
    
    public void updateSlots(Kinematic anchor) throws SlickException{
        double[][] orientacionMatrix=anchor.asMatrix();
        
        for(SlotAssignment a : slotAssignments){
           Kinematic relativeLoc= pattern.getSlotLocation(a.getSlotNumber());
           Kinematic location=new Kinematic(0,0);
           relativeLoc.getPosicion().mulMatrix(orientacionMatrix);
           relativeLoc.getPosicion().sumar(anchor.getPosicion());
           location.setPosicion(relativeLoc.getPosicion());
           location.setOrientacion(anchor.getOrientacion()+relativeLoc.getOrientacion());
           
           location.getPosicion().restar(driftOffset.getPosicion());
           location.setOrientacion(location.getOrientacion()-driftOffset.getOrientacion());
           
           a.getCharacter().setTarget(location);          
        }
    }
    
    public Kinematic GetAnchorPoint() throws SlickException{
        
        Kinematic anchor=new Kinematic(0,0);
        Vector posicion = new Vector();
        Vector velocidad = new Vector();
        Double orientacion=0.0;
        Double rotacion=0.0;
        
        
        for(SlotAssignment p : slotAssignments){
                posicion.sumar(p.getCharacter().getPosicion());
                velocidad.sumar(p.getCharacter().getVelocidad());
                orientacion+=p.getCharacter().getOrientacion();
                rotacion+=p.getCharacter().getRotacion();
         }
        //medias de los valores de los character
        posicion.divEscalar(slotAssignments.size());
        velocidad.divEscalar(slotAssignments.size());
        orientacion/=slotAssignments.size();
        rotacion/=slotAssignments.size();
        
        //Actualizamos punto de anclaje
        anchor.setPosicion(posicion);
        anchor.setVelocidad(velocidad);
        anchor.setOrientacion(orientacion);
        anchor.setRotacion(rotacion);
     
        return anchor;
    }  
}