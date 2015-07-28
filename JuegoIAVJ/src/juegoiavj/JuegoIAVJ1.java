/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj;

import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.uniforme.KinematicArrive;
import juegoiavj.math.Vector;
import java.util.Date;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 *ublic class JuegoIAV
 * @author Samuel Navas
 */
public class JuegoIAVJ1 {
    
    private boolean runFlag = false;
    double fps = 0.0;
    double lastFPS = getTime();
    
    // Kinematics
    Kinematic jugador1;
    Kinematic jugador2;
    KinematicArrive steering;
    
    /** position of quad */
   // float x = 400, y = 300;
    /** angle of quad rotation */
    float rotation = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JuegoIAVJ1 displayExample = new JuegoIAVJ1();
	displayExample.run();
    }
    
    public void run(){
        runFlag = true;
        startup(); 
        double t = 0.0; final double dt = 0.01;
        double currTime = getTime();
        double accumulator = 0.0;
        fps = 0.0;
        lastFPS = getTime();
        initGL();
        //jugador1 = new Kinematic(400, 300, 0);
        //jugador2 = new Kinematic(100, 200, 0);
        steering = new KinematicArrive(jugador2, jugador1, 10.0, 5.0);
        while(runFlag && !Display.isCloseRequested()){
            double newTime = getTime();
            double frameTime = newTime - currTime;
            currTime = newTime;
            accumulator += frameTime;
            while(accumulator >= dt){
                update(t, dt);
                accumulator -= dt;
                t += dt;
            }
            draw();
        }
        shutdown();
    }
    
    public void startup() {
		try {
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}	
	}

    private void update(double t, double dt) {
        //throw new UnsupportedOperationException("Not yet implemented");
        // Update OpenGL
        // rotate quad
	//rotation += 0.15f * dt;
	
        double x1 = jugador1.getPosicion().getX();
        double y1 = jugador1.getPosicion().getY();
        
	if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) x1 -= 0.35f * dt;
	if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) x1 += 0.35f * dt;
		
	if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) y1 -= 0.35f * dt;
	if (Keyboard.isKeyDown(Keyboard.KEY_UP)) y1 += 0.35f * dt;
		
	// keep quad on the screen
	if (x1 < 0) x1 = 0;
	if (x1 > 800) x1 = 800;
	if (y1 < 0) y1 = 0;
	if (y1 > 600) y1 = 600;
        
        Vector posicion = jugador1.getPosicion();
        posicion.setX(x1);
        posicion.setY(y1);
        jugador1.setPosicion(posicion);
        
        // jugador 2
        //jugador2.update(steering.getSteering(), dt);

        updateFPS();
    }

    private void draw() {
        renderGL();
        Display.update();
        Display.sync(60);
    }
    
    private void shutdown(){
        Display.destroy();
    }
    
    private void processInput(){
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    private double getTime() {
        //return System.nanoTime() / 1000000000;
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private void initGL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
	GL11.glLoadIdentity();
	GL11.glOrtho(0, 800, 0, 600, 1, -1);
	GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    private void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    private void renderGL() {
        	// Clear The Screen And The Depth Buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
                
		// R,G,B,A Set The Color To Blue One Time Only
		GL11.glColor3f(0.5f, 0.5f, 1.0f);

                float x1 = (float) jugador1.getPosicion().getX();
                float y1 = (float) jugador1.getPosicion().getY();
                float r1 = (float) jugador1.getOrientacion();
                
                // draw char 1
		GL11.glPushMatrix();
			GL11.glTranslatef(x1, y1, 0);
			GL11.glRotatef(r1, 0f, 0f, 1f);
			GL11.glTranslatef(-x1, -y1, 0);
			
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(x1 - 10, y1 - 10);
				GL11.glVertex2f(x1 + 10, y1 - 10);
				GL11.glVertex2f(x1 + 10, y1 + 10);
				GL11.glVertex2f(x1 - 10, y1 + 10);
			GL11.glEnd();
		GL11.glPopMatrix();
              
                
                // draw char 2 (controlable)
                GL11.glColor3f(0.7f, 0.1f, 0.2f);
                
                float x2 = (float) jugador2.getPosicion().getX();
                float y2 = (float) jugador2.getPosicion().getY();
                float r2 = (float) jugador2.getOrientacion();
		// draw quad
		GL11.glPushMatrix();
			GL11.glTranslatef(x2, y2, 0);
			GL11.glRotatef(r2, 0f, 0f, 1f);
			GL11.glTranslatef(-x2, -y2, 0);
			
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(x2 - 10, y2 - 10);
				GL11.glVertex2f(x2 + 10, y2 - 10);
				GL11.glVertex2f(x2 + 10, y2 + 10);
				GL11.glVertex2f(x2 - 10, y2 + 10);
			GL11.glEnd();
		GL11.glPopMatrix();
    }
}
