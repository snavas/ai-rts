/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj;

import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.uniforme.KinematicSeek;
import juegoiavj.kinematic.uniforme.KinematicArrive;
import juegoiavj.kinematic.uniforme.KinematicFlee;
import juegoiavj.kinematic.uniforme.KinematicWander;
import juegoiavj.math.Vector;
import java.util.Date;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * ublic class JuegoIAV
 *
 * @author Samuel Navas
 */
public class JuegoIAVJ11 {

    // Kinematics
    Kinematic jugador1;
    Kinematic jugador2;
    KinematicArrive steeringA;
    KinematicWander steeringW;
    KinematicFlee steeringF;
    KinematicSeek steeringS;
    int lastSteering = 0;
    /**
     * time at last frame
     */
    long lastFrame;
    /**
     * frames per second
     */
    int fps;
    /**
     * last fps time
     */
    long lastFPS;

    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        //jugador1 = new Kinematic(400, 300, 0);
        //jugador2 = new Kinematic(100, 200, 0);
        steeringA = new KinematicArrive(jugador2, jugador1, 0.05, 0);
        steeringW = new KinematicWander(jugador2, 0.05, 0.005);
        steeringF = new KinematicFlee(jugador2, jugador1, 0.05);
        steeringS = new KinematicSeek(jugador2, jugador1, 0.05);
        

        initGL(); // init OpenGL
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer

        while (!Display.isCloseRequested()) {
            int delta = getDelta();

            update(delta);
            renderGL();

            Display.update();
            Display.sync(60); // cap fps to 60fps
        }

        Display.destroy();
    }

    public void update(int delta) {
        // rotate quad
        double x1 = jugador1.getPosicion().getX();
        double y1 = jugador1.getPosicion().getY();

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            x1 -= 0.35f * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            x1 += 0.35f * delta;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            y1 -= 0.35f * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            y1 += 0.35f * delta;
        }

        // keep quad on the screen
        if (x1 < 10) {
            x1 = 10;
        }
        if (x1 > 790) {
            x1 = 790;
        }
        if (y1 < 10) {
            y1 = 10;
        }
        if (y1 > 590) {
            y1 = 590;
        }

        Vector posicion = jugador1.getPosicion();
        posicion.setX(x1);
        posicion.setY(y1);
        jugador1.setPosicion(posicion);

        // jugador 2
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) lastSteering = 1;
	if (Keyboard.isKeyDown(Keyboard.KEY_A)) lastSteering = 2;
	if (Keyboard.isKeyDown(Keyboard.KEY_F)) lastSteering = 3;
	if (Keyboard.isKeyDown(Keyboard.KEY_S)) lastSteering = 4;
        
        /*
        switch(lastSteering) {
            case 1: jugador2.update(steeringW.getSteering(), delta);
                break;
            case 2: jugador2.update(steeringA.getSteering(), delta);
                break;
            case 3: jugador2.update(steeringF.getSteering(), delta);
                break;
            case 4: jugador2.update(steeringS.getSteering(), delta);
                break;
            default:
        }*/
        
        updateFPS(); // update FPS Counter
    }

    /**
     * Calculate how many milliseconds have passed since last frame.
     *
     * @return milliseconds passed since last frame
     */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Get the accurate system time
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("Jedis VS Siths (FPS: " + fps+")");
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public void initGL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 0, 600, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    public void renderGL() {
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

    public static void main(String[] argv) {
        JuegoIAVJ11 juego = new JuegoIAVJ11();
        juego.start();
    }
}
