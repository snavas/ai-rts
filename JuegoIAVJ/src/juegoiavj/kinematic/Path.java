/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic;

import java.util.LinkedList;
import java.util.List;
import juegoiavj.math.Vector;

/**
 *
 * @author Samuel
 */
public class Path {
    
    //private Kinematic personaje;
    private List<Vector> goals;
    private int actualGoal;
    private int a;
    private int b;
    
    public Path(){
        goals = new LinkedList<Vector>();
        actualGoal = 0;
    }
    
    public Vector getFirstParam(){
        //System.out.print("GETFIRSTPARAM="+goals.get(0).getX()+","+goals.get(0).getY());
        return goals.get(0);
    }

    public Vector getParam(Vector posicion, Vector currentParam) {
        a = -1;
        b = -1;
        double minDist = Double.MAX_VALUE;
        // Calculamos el punto del path más cercanos a currentParam
        for (int i=0; i<goals.size(); i++){
            Vector v = new Vector(currentParam, goals.get(i));
            if (v.getModulo()< minDist){
                a = i;
                b = i+1;
                minDist = v.getModulo();
                //System.out.println("Entro con a="+a+",b="+b+", y mod ="+v.getModulo());
                if(b==goals.size()){
                    b = 0;
                }
            } 
        }
        //System.out.println("a="+a+",b="+b);
        Vector A = goals.get(a).clone();
        Vector B = goals.get(b).clone();
        
        // Calculamos el punto perpendicular al vector AB
        // 1. V = AB/||AB||
        Vector V = new Vector(A,B);
        double mod = V.getModulo();
        V.divEscalar(mod);
        // 2. H = A + ( V · PA ) * V
        Vector AP = new Vector(A, posicion);
        V.multiEscalar(V.prodEscalar(AP));
        Vector C = A.clone();
        C.sumar(V);
        return C;
    }

    public void addWayPoint(double x, double y) {
        goals.add(new Vector(x,y));
    }

    public List<Vector> getGoals() {
        return goals;
    }

    public Vector addOffset(double pathOffset, Vector targetParam) {
        // Calculo H2 = H + Offset
        Vector A = goals.get(a).clone();
        Vector B = goals.get(b).clone();
        
        //System.out.println("A:"+A.getX()+","+A.getY());
        //System.out.println("B:"+B.getX()+","+B.getY());
        //System.out.println("H:"+targetParam.getX()+","+targetParam.getY());
        
        Vector AB = new Vector(A,B);
        double mod = AB.getModulo();
        AB.divEscalar(mod);
        AB.multiEscalar(pathOffset);
        AB.sumar(targetParam);
        
        // No hace falta, posible mejora.
        /*
        // Comprobmaos si se sale del segmento
        Vector H2 = AB.clone();
        H2.restar(A);
        Vector Q = B.clone();
        Q.restar(A);
        if(H2.getModulo()<Q.getModulo()){
            int c;
            if (b+1==goals.size()) c=0;
            else c = b+1;
            
            Vector BH = B.clone();
            BH.restar(targetParam);
            double offset2 = pathOffset - BH.getModulo();
            
            Vector BC = B.clone();
            BC.restar(goals.get(c));
            double modi = BC.getModulo();
            BC.divEscalar(modi);
            BC.multiEscalar(offset2);
            BC.sumar(B);
            AB = BC;
        }*/
        return AB.clone();
    }
    
    
    
}
