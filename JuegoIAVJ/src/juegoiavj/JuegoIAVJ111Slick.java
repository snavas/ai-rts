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
import org.newdawn.slick.*;
import org.newdawn.slick.imageout.ImageOut;

/**
 * ublic class JuegoIAV
 *
 * @author Samuel Navas
 */
public class JuegoIAVJ111Slick extends BasicGame{
    // Kinematics
    Kinematic jugador1;
    Kinematic jugador2;
    KinematicArrive steeringA;
    KinematicWander steeringW;
    KinematicFlee steeringF;
    KinematicSeek steeringS;
    int lastSteering = 0;
    Image plane1 = null;
    Image plane2 = null;
    Image land = null;
    
    public JuegoIAVJ111Slick(){
        super("Jedis VS Siths");
    }

    public static void main(String[] argv) throws SlickException {
        AppGameContainer app =  new AppGameContainer(new JuegoIAVJ111Slick());
        app.setDisplayMode(800, 600, false);
        app.setTargetFrameRate(60);
        //app.setVSync(true);
        app.start(); 
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        //jugador1 = new Kinematic(400, 300, 0);
        //jugador2 = new Kinematic(100, 200, 0);
        steeringA = new KinematicArrive(jugador2, jugador1, 0.05, 0);
        steeringW = new KinematicWander(jugador2, 0.05, 0.005);
        steeringF = new KinematicFlee(jugador2, jugador1, 0.05);
        steeringS = new KinematicSeek(jugador2, jugador1, 0.05);
        plane1 = new Image("plane1.png");
        plane2 = new Image("plane2.png");
        land = new Image("Basic_slick_game_background.jpg");
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        //throw new UnsupportedOperationException("Not supported yet.");
        Input input = container.getInput();
        
        // rotate quad
        double x1 = jugador1.getPosicion().getX();
        double y1 = jugador1.getPosicion().getY();

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            plane1.rotate(-0.2f * delta);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            plane1.rotate(0.2f * delta);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            float hip = 0.4f * delta;
            float rotation = plane1.getRotation(); 
            x1-= hip * Math.sin(Math.toRadians(rotation));
            y1+= hip * Math.cos(Math.toRadians(rotation));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            float hip = 0.4f * delta;
            float rotation = plane1.getRotation(); 
            x1+= hip * Math.sin(Math.toRadians(rotation));
            y1-= hip * Math.cos(Math.toRadians(rotation));
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
        
        //plane2.rotate((float) jugador2.getOrientacion());
        
        if (input.isKeyPressed(Input.KEY_F1)) {
            Image target = new Image(container.getWidth(), container.getHeight());
            container.getGraphics().copyArea(target, 0, 0);
            ImageOut.write(target.getFlippedCopy(false, true), "screenshot.png", false);
            target.destroy();
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        //throw new UnsupportedOperationException("Not supported yet.");
       // g.drawString("Hello World", 100, 100);
        land.draw(0, 0);
        plane1.draw((float) jugador1.getPosicion().getX(), (float) jugador1.getPosicion().getY(), 0.75f);
        plane2.draw((float) jugador2.getPosicion().getX(), (float) jugador2.getPosicion().getY(), 0.75f);
    }
}
