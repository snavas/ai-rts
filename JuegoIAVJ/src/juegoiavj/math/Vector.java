/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.math;

import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Samuel
 */
public class Vector implements Cloneable{
    private double x;
    private double y;
    
    public Vector(){
        x=0;
        y=0;
    }
    
    public Vector(double x1, double x2, double z1, double z2){
        x = x2-x1;
        y = z2-z1;
    }
    
    public Vector(Vector v1, Vector v2){
        x = v2.getX()-v1.getX();
        y = v2.getY()-v1.getY();
    }
    
    public Vector(double x, double z){
        this.x = x;
        this.y = z;
    }
    
    public Vector(Vector v){
        this.x = v.getX();
        this.y = v.getY();
    }
    
    public double getModulo(){
       return  Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
    }
    
    public void normalizar(){
        //System.out.println("X antes de normalizar "+x);
        //System.out.println("Z antes de normalizar "+y);
        //System.out.println("Modulo antes de normalizar "+getModulo());
        double modulo = getModulo();
        if(modulo!=0){
            this.x = (double) ((double) this.x) / ((double) modulo);
            this.y = (double) ((double) this.y) / ((double) modulo);
        } else {
            this.x = 0;
            this.y = 0;
        }
        //System.out.println("X despues de normalizar "+x);
        //System.out.println("Z despues de normalizar "+y);
        //System.out.println("Modulo despues de normalizar "+getModulo());
        //if (getModulo()!=1) throw new UnsupportedOperationException("Modulo de vector normalizado != 1, "+getModulo());
    }
    
    public void sumar(Vector v){
        x += v.getX();
        y += v.getY();
    }
    
    public void restar(Vector v){
        x -= v.getX();
        y -= v.getY();
    }
    
    public void multiEscalar(double e){
        x = x*e;
        y = y*e;
    }
    public void mulMatrix(double[][] matrix){
        double[][] mat=matrix;
        x=x*mat[0][0] + y*mat[0][1];
        y=x*mat[1][0] +y*mat[1][1];
        
        
        
    
    }
    public void divEscalar(double e){
        x = x/e;
        y = y/e;
    }
    
    //public void multiVector(){    
    //}
            
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double z){
        this.y = z;
    }
    
    public double prodEscalar(Vector v){
        Vector2f aux1 = this.get2f();
        Vector2f aux2 = v.get2f();
        return aux1.dot(aux2);
        //return x*v.getX() + y*v.getY();
    }
    
    @Override
    public Vector clone(){
        return new Vector(x,y);
    }

    public void sumar(double esc) {
        x += esc;
        y += esc;
    }
    
    public Vector2f get2f(){
        return new Vector2f((float) x,(float) y);
    }
    
    public double squareMagnitude(){
        return  x*x+y*y;
    }
}
