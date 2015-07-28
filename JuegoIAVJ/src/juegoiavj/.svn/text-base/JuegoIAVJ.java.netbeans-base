/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj;

import juegoiavj.kinematic.uniforme.*;
import juegoiavj.math.Vector;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import juegoiavj.Formation.DefensiveCirclePattern;
import juegoiavj.Formation.FormationManager;
import juegoiavj.kinematic.BlendedSteering;
import juegoiavj.kinematic.Kinematic;
import juegoiavj.kinematic.Path;
import juegoiavj.kinematic.SteeringA;
import juegoiavj.kinematic.acelerado.*;
import juegoiavj.kinematic.acelerado.delegado.*;
import juegoiavj.pathFinding.LRTA;
import juegoiavj.pathFinding.Node;
import juegoiavj.util.Block;
import juegoiavj.util.BlockMap;
import juegoiavj.util.Camera;
import juegoiavj.util.Faction;
import juegoiavj.util.Rank;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.imageout.ImageOut;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.openal.*;
import org.lwjgl.openal.Util;
import org.lwjgl.openal.*;
import org.newdawn.slick.fills.GradientFill;

/**
 * ublic class JuegoIAV
 *
 * @author Samuel Navas
 */
public class JuegoIAVJ extends BasicGame {
    // World

    private World world;
    // Kinematics
    private FormationManager formation;
    private FormationManager formationA;
    //private Kinematic jugador1;
    private Kinematic healer1;
    private Kinematic healer2;
    private Kinematic healer3;
    private Kinematic healer4;
    private Kinematic patrulleroJ1;
    private Kinematic patrulleroS1;
    private Kinematic patrulleroJ2;
    private Kinematic patrulleroS2;
    //private Kinematic patrulleroJ3;
    //private Kinematic patrulleroS3;
    private Kinematic patrulleroJ4;
    private Kinematic patrulleroS4;
    private Kinematic Puntoanclaje;
    private Kinematic PuntoanclajeA;
    private Kinematic metralla1;
    private Kinematic metralla2;
    private Kinematic metralla3;
    private Kinematic metralla4;
    private Kinematic camara;
    private Kinematic swarm1;
    private Kinematic swarm2;
    private Kinematic swarm3;
    private Kinematic swarm4;
    private Kinematic swarm5;
    private Kinematic swarm1A;
    private Kinematic swarm2A;
    private Kinematic swarm3A;
    private Kinematic swarm4A;
    private Kinematic swarm5A;
    private Vector raton;
    private KinematicArrive sterAnclaje;
    private BlendedSteering steeringJ7;
    private int lastSteering = 0;
    List<Kinematic> meleJ;
    List<Kinematic> rangedJ;
    List<Kinematic> scoutJ;
    List<Kinematic> heavyJ;
    List<Kinematic> meleS;
    List<Kinematic> rangedS;
    List<Kinematic> scoutS;
    List<Kinematic> heavyS;
    // Game
    private Image planeCam = null;
    private Image miniMap = null;
    private float scale = 1f;
    private Music bgm;
    private Camera camera;
    private int frame;
    //
    public Vector debugPoint1 = new Vector();
    public Vector debugPoint2 = new Vector();
    public Vector wanderCircle = new Vector();
    public Vector wanderPoint1 = new Vector();
    public Vector wanderPoint2 = new Vector();
    // 
    public Vector mouse = new Vector();
    public double containerWidth;
    public double containerHeight;

    public JuegoIAVJ() {
        super("Jedis VS Siths");
    }

    public static void main(String[] argv) throws SlickException {
        try {
            AppGameContainer app = new AppGameContainer(new JuegoIAVJ());
            DisplayMode[] modes = Display.getAvailableDisplayModes();
            int x = 0;
            int y = 0;
            for (int i = 0; i < modes.length; i++) {
                DisplayMode current = modes[i];
                if (current.getWidth() > x) {
                    x = current.getWidth();
                    y = current.getHeight();
                }
            }
            app.setDisplayMode(x, y, true);
            app.setTargetFrameRate(60);
            app.setVSync(true);
            app.start();
        } catch (LWJGLException ex) {
            Logger.getLogger(JuegoIAVJ.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void init(GameContainer container) throws SlickException {
        // INFORMACION DEL MUNDO
        world.blockMap = new BlockMap("Material/mapas/gnollwood_map.tmx");
        initGraph();
        initGraph_AFFE_A();
        initGraph_AFFE_E();
        initGraph_AGAE_A();
        initGraph_AGAE_E();
        initGraph_AFAE_A();
        initGraph_AFAE_E();
        this.containerWidth = container.getWidth();
        this.containerHeight = container.getHeight();

        // INFORMACION DE FORMACIONES
        DefensiveCirclePattern pattern = new DefensiveCirclePattern();
        this.formation = new FormationManager(pattern);
        this.formationA = new FormationManager(pattern);
        //world.formation = this.formation;

        // JUGADOR CONTROLABLE
        //jugador1 = new Kinematic(1976, 8475, 100, Faction.ALLY, Rank.RANGED, world.getInstance());

        // PATRULLERO
        patrulleroJ1 = new Kinematic(2524, 3668, 100, Faction.ALLY, Rank.PATROL, world.getInstance());
        patrulleroJ2 = new Kinematic(55*32, 136*32, 100, Faction.ALLY, Rank.PATROL, world.getInstance());
        //patrulleroJ3 = new Kinematic(28*32, 120*32, 100, Faction.ALLY, Rank.PATROL, world.getInstance());
        patrulleroJ4 = new Kinematic(56*32, 102*32, 100, Faction.ALLY, Rank.PATROL, world.getInstance());
        world.alliesAlive+=3;
        patrulleroS1 = new Kinematic(10848, 8416, 100, Faction.FOE, Rank.PATROL, world.getInstance());
        patrulleroS2 = new Kinematic(351*32, 291*32, 100, Faction.FOE, Rank.PATROL, world.getInstance());
        //patrulleroS3 = new Kinematic(391*32, 273*32, 100, Faction.FOE, Rank.PATROL, world.getInstance());
        patrulleroS4 = new Kinematic(366*32, 255*32, 100, Faction.FOE, Rank.PATROL, world.getInstance());
        world.foesAlive+=3;

        // UNIDADES A RANGO
        rangedJ = new LinkedList<Kinematic>();
        for (int i = 0; i < 6; i++) {
            world.alliesAlive++;
            rangedJ.add(new Kinematic(45 * 32 + i * 64, 114 * 32, 100, Faction.ALLY, Rank.RANGED, world.getInstance()));
        }
        rangedS = new LinkedList<Kinematic>();
        for (int i = 0; i < 6; i++) {
            world.foesAlive++;
            rangedS.add(new Kinematic(353 * 32 + i * 64, 270 * 32, 100, Faction.FOE, Rank.RANGED, world.getInstance()));
        }

        // UNIDADES A MELE
        meleJ = new LinkedList<Kinematic>();
        for (int i = 0; i < 6; i++) {
            world.alliesAlive++;
            meleJ.add(new Kinematic(45 * 32 + i * 64, 116 * 32, 100, Faction.ALLY, Rank.MELE, world.getInstance()));
        }
        meleS = new LinkedList<Kinematic>();
        for (int i = 0; i < 6; i++) {
            world.foesAlive++;
            meleS.add(new Kinematic(353 * 32 + i * 64, 272 * 32, 100, Faction.FOE, Rank.MELE, world.getInstance()));
        }

        // UNIDADES EXPLROADORAS
        scoutJ = new LinkedList<Kinematic>();
        for (int i = 0; i < 6; i++) {
            world.alliesAlive++;
            scoutJ.add(new Kinematic(45 * 32 + i * 64, 118 * 32, 100, Faction.ALLY, Rank.SCOUT, world.getInstance()));
        }
        scoutS = new LinkedList<Kinematic>();
        for (int i = 0; i < 6; i++) {
            world.foesAlive++;
            scoutS.add(new Kinematic(353 * 32 + i * 64, 274 * 32, 100, Faction.FOE, Rank.SCOUT, world.getInstance()));
        }

        // UNIDADES DE ARTILLERIA
        
         heavyJ = new LinkedList<Kinematic>();
         for (int i = 0; i < 3; i++) {
         world.alliesAlive++;
         heavyJ.add(new Kinematic(45 * 32 + i * 160, 123 * 32, 100, Faction.ALLY, Rank.HEAVY, world.getInstance()));
         }
         heavyS = new LinkedList<Kinematic>();
         for (int i = 0; i < 3; i++) {
         world.foesAlive++;
         heavyS.add(new Kinematic(353 * 32 + i * 160, 279 * 32, 100, Faction.FOE, Rank.HEAVY, world.getInstance()));
         }

        // TORRETAS
        metralla1 = new Kinematic(56 * 32, 136 * 32, 300, Faction.ALLY, Rank.TURRET, world.getInstance());
        metralla2 = new Kinematic(81 * 32, 119 * 32, 300, Faction.ALLY, Rank.TURRET, world.getInstance());
        world.alliesAlive += 2;
        metralla3 = new Kinematic(340 * 32, 274 * 32, 300, Faction.FOE, Rank.TURRET, world.getInstance());
        metralla4 = new Kinematic(358 * 32, 291 * 32, 300, Faction.FOE, Rank.TURRET, world.getInstance());
        world.foesAlive += 2;

        // Sanadores
        healer1 = new Kinematic(64 * 32, 113 * 32, 300, Faction.ALLY, Rank.HEALER, world.getInstance());
        healer2 = new Kinematic(67 * 32, 113 * 32, 300, Faction.ALLY, Rank.HEALER, world.getInstance());
        world.alliesAlive += 2;
        healer3 = new Kinematic(340 * 32, 274 * 32, 300, Faction.FOE, Rank.HEALER, world.getInstance());
        healer4 = new Kinematic(358 * 32, 291 * 32, 300, Faction.FOE, Rank.HEALER, world.getInstance());
        world.foesAlive += 2;

        // CAMARA
        camara = new Kinematic(64 * 32, 113 * 32);

        // FORMACIONES
        swarm1 = new Kinematic(328 * 32, 273 * 32, 100, Faction.FOE, Rank.SWARM, world.getInstance(), null, formation);
        swarm2 = new Kinematic(328 * 32, 273 * 32, 100, Faction.FOE, Rank.SWARM, world.getInstance(), null, formation);
        swarm3 = new Kinematic(328 * 32, 273 * 32, 100, Faction.FOE, Rank.SWARM, world.getInstance(), null, formation);
        swarm4 = new Kinematic(328 * 32, 273 * 32, 100, Faction.FOE, Rank.SWARM, world.getInstance(), null, formation);
        swarm5 = new Kinematic(328 * 32, 273 * 32, 100, Faction.FOE, Rank.SWARM, world.getInstance(), null, formation);
        world.foesAlive += 5;

        swarm1A = new Kinematic(68 * 32, 118 * 32, 100, Faction.ALLY, Rank.SWARM, world.getInstance(), null, formation);
        swarm2A = new Kinematic(68 * 32, 118 * 32, 100, Faction.ALLY, Rank.SWARM, world.getInstance(), null, formation);
        swarm3A = new Kinematic(68 * 32, 118 * 32, 100, Faction.ALLY, Rank.SWARM, world.getInstance(), null, formation);
        swarm4A = new Kinematic(68 * 32, 118 * 32, 100, Faction.ALLY, Rank.SWARM, world.getInstance(), null, formation);
        swarm5A = new Kinematic(68 * 32, 118 * 32, 100, Faction.ALLY, Rank.SWARM, world.getInstance(), null, formation);
        world.alliesAlive += 5;
        //
        miniMap = new Image("Material/imagenes/influencemap.jpg");
        frame = 0;
        raton = new Vector();
        camera = new Camera(container, new TiledMap("Material/mapas/gnollwood_map.tmx", "Material/mapas"));
        bgm = new Music("Material/sonidos/StarcraftBGM.ogg");
        bgm.loop();
        //allys.add(jugador1);

        // LISTA DE ALIADOS
        world.allys.add(patrulleroJ1);
        world.allys.add(patrulleroJ2);
        //world.allys.add(patrulleroJ3);
        world.allys.add(patrulleroJ4);
        world.allys.add(metralla1);
        world.allys.add(metralla2);
        world.allys.add(healer1);
        world.allys.add(healer2);
        world.allys.addAll(rangedJ);
        world.allys.addAll(meleJ);
        world.allys.addAll(scoutJ);
        world.allys.addAll(heavyJ);

        world.swarmA.add(swarm1A);
        world.swarmA.add(swarm2A);
        world.swarmA.add(swarm3A);
        world.swarmA.add(swarm4A);
        world.swarmA.add(swarm5A);
        world.allys.addAll(world.swarmA);
        formationA.addCharater(swarm1A);
        formationA.addCharater(swarm2A);
        formationA.addCharater(swarm3A);
        formationA.addCharater(swarm4A);
        formationA.addCharater(swarm5A);

        // LISTA DE ENEMIGOS
        world.foes.add(patrulleroS1);
        world.foes.add(patrulleroS2);
        //world.foes.add(patrulleroS3);
        world.foes.add(patrulleroS4);
        world.foes.add(metralla3);
        world.foes.add(metralla4);
        world.foes.add(healer3);
        world.foes.add(healer4);
        //world.foes.add(jugador1);

        world.swarm.add(swarm1);
        world.swarm.add(swarm2);
        world.swarm.add(swarm3);
        world.swarm.add(swarm4);
        world.swarm.add(swarm5);



        world.foes.addAll(world.swarm);
        world.foes.addAll(rangedS);
        world.foes.addAll(meleS);
        world.foes.addAll(scoutS);
        world.foes.addAll(heavyS);
        //world.swarm.add(Puntoanclaje);
        formation.addCharater(swarm1);
        formation.addCharater(swarm2);
        formation.addCharater(swarm3);
        formation.addCharater(swarm4);
        formation.addCharater(swarm5);

        // PUNTO ANCLAJE
        Puntoanclaje = new Kinematic(0, 0);
        Puntoanclaje.setPosicion(formation.GetAnchorPoint().getPosicion());
        Puntoanclaje.setOrientacion(formation.GetAnchorPoint().getOrientacion());
        Puntoanclaje.setRotacion(formation.GetAnchorPoint().getRotacion());
        Puntoanclaje.setVelocidad(formation.GetAnchorPoint().getVelocidad());

        PuntoanclajeA = new Kinematic(0, 0);
        PuntoanclajeA.setPosicion(formationA.GetAnchorPoint().getPosicion());
        PuntoanclajeA.setOrientacion(formationA.GetAnchorPoint().getOrientacion());
        PuntoanclajeA.setRotacion(formationA.GetAnchorPoint().getRotacion());
        PuntoanclajeA.setVelocidad(formationA.GetAnchorPoint().getVelocidad());

        world.all.add(Puntoanclaje);
        world.all.add(PuntoanclajeA);
        Puntoanclaje.setFaction(Faction.FOE);
        PuntoanclajeA.setFaction(Faction.ALLY);

        world.all.addAll(world.allys);
        world.all.addAll(world.foes);
        for (int i = 0; i <= 50; i++) {
            Kinematic bird = new Kinematic(6720 + (i * 10), 5440 + (i * 10), 100, Faction.NEUTRAL, Rank.BIRD, world.getInstance());
            bird.setOrientacion(randomBinominial());
            bird.setRotacion(randomBinominial() / 10000);
            bird.setVelocidad(new Vector(randomBinominial(), randomBinominial()));
            world.all.add(bird);
            world.birds.add(bird);
        }

    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        frame++;

        Input input = container.getInput();
        raton.setX(input.getMouseX());
        raton.setY(input.getMouseY());

        // rotate quad
       /* double x1 = jugador1.getPosicion().getX();
         double y1 = jugador1.getPosicion().getY();

         if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
         jugador1.getSprite().rotate(-0.2f * delta);
         //jugador1.setOrientacion(plane1.getRotation());
         }
         if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
         jugador1.getSprite().rotate(0.2f * delta);
         //jugador1.setOrientacion(plane1.getRotation());
         }
         if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
         float hip = 0.4f * delta;
         float rotation = jugador1.getSprite().getRotation();
         x1 -= hip * Math.sin(Math.toRadians(rotation));
         y1 += hip * Math.cos(Math.toRadians(rotation));
         }
         if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
         float hip = 0.4f * delta;
         float rotation = jugador1.getSprite().getRotation();
         x1 += hip * Math.sin(Math.toRadians(rotation));
         y1 -= hip * Math.cos(Math.toRadians(rotation));
         }

         Vector posicion = jugador1.getPosicion();
         posicion.setX(x1);
         posicion.setY(y1);
         jugador1.setPosicion(posicion);

         System.out.println("J1: " + jugador1.getTileX() + "," + jugador1.getTileY());*/
        //jugador4.update(steeringFuerteJ.getSteering(), 0.05, delta);
        patrulleroJ1.update(delta, world.foes);
        patrulleroS1.update(delta, world.allys);
        patrulleroJ2.update(delta, world.foes);
        patrulleroS2.update(delta, world.allys);
        //patrulleroJ3.update(delta, world.foes);
        //patrulleroS3.update(delta, world.allys);
        patrulleroJ4.update(delta, world.foes);
        patrulleroS4.update(delta, world.allys);

        Puntoanclaje.update(delta, null);
        PuntoanclajeA.update(delta, null);

        formation.updateSlots(Puntoanclaje);
        formationA.updateSlots(PuntoanclajeA);
        for (Kinematic s : world.swarm) {
            s.update(delta, world.allys);
        }
        for (Kinematic s : world.swarmA) {
            s.update(delta, world.foes);
        }


        for (Kinematic s : rangedJ) {
            s.update(delta, world.foes);
        }

        for (Kinematic s : rangedS) {
            s.update(delta, world.allys);
        }

        for (Kinematic s : scoutJ) {
            s.update(delta, world.foes);
        }

        for (Kinematic s : scoutS) {
            s.update(delta, world.allys);
        }

        for (Kinematic s : meleJ) {
            s.update(delta, world.foes);
        }

        for (Kinematic s : meleS) {
            s.update(delta, world.allys);
        }

         for(Kinematic s : heavyJ){
         s.update(delta, world.foes);
         }
       
         for(Kinematic s : heavyS){
         s.update(delta, world.allys);
         }

        //updateTurrets(delta);
        metralla1.update(delta, world.foes);
        metralla2.update(delta, world.foes);
        metralla3.update(delta, world.allys);
        metralla4.update(delta, world.allys);

        healer1.update(delta, world.allys);
        healer2.update(delta, world.allys);
        healer3.update(delta, world.foes);
        healer4.update(delta, world.foes);

        // update birds
        for (Kinematic b : world.birds) {
            b.update(delta, world.birds);
        }

        if (input.isKeyPressed(Input.KEY_F1)) {
            Image target = new Image(container.getWidth(), container.getHeight());
            container.getGraphics().copyArea(target, 0, 0);
            ImageOut.write(target.getFlippedCopy(false, false), "screenshot.png", false);
            target.destroy();
        }
        
        if (input.isKeyPressed(Input.KEY_F2)) {
            world.alliesBaseHealth-=103.5;
        }
        
        if(input.isKeyPressed(Input.KEY_P)){
            for(Kinematic p: world.all){
                if(p.isSelected()&&p.getRank()==Rank.SWARM){
                    formation.removeCharacter(p);
                    formationA.removeCharacter(p);
                    p.setRange(Rank.RANGED);
                }
            }
        }
        
        if(input.isKeyPressed(Input.KEY_A)){
            for(Kinematic p:world.all){
                if(p.isSelected()&&p.getRank()==Rank.RANGED){
                    p.setRange(Rank.SWARM);
                    formation.addCharater(p);
                    formationA.addCharater(p);
                    
                }
            }
        
        }

        updateCamera(container);
        updateMouse(container);
        updateInfluenceMap();

        System.out.println("SCALE: " + scale);
        System.out.println("H: " + container.getHeight() + ", W: " + container.getWidth());
        //System.out.println("raton: " + (input.getMouseX()) + "," + (input.getMouseY()));
        System.out.println("raton: " + raton.getX() + "," + raton.getY());
        // System.out.println("Plane1 (" + jugador1.getPosicion().getX() + "," + jugador1.getPosicion().getY() + ") ROT:" + jugador1.getSprite().getRotation());
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        float x = (float) camara.getPosicion().getX();
        float y = (float) camara.getPosicion().getY();
        
        g.scale(scale, scale);
        camera.drawMap();
        camera.translateGraphics();

        //renderDebug(g);
        renderMouse(container, g);
        renderPlayers(g);
        renderHUD(container, g);
        renderInfluenceMap(g);
        
        if (world.alliesBaseHealth<=0){
            System.out.println("Ganan los aliados");
            g.setColor(Color.red);
            g.drawString("Gana el bando imperial. Pulsa Alt+F4 para terminar.", x - 675, y - 225);
            container.pause();
        }
        if (world.foesBaseHealth<=0){
            System.out.println("Ganan los enemigos");
            g.setColor(Color.blue);
            g.drawString("Ganan la alianza. Pulsa Alt+F4 para terminar.", x - 675, y - 225);
            container.pause();
        }
    }

    private void renderHUD(GameContainer container, Graphics g) {
        g.setColor(Color.white);
        float x = (float) camara.getPosicion().getX();
        float y = (float) camara.getPosicion().getY();
        g.drawString("F1. Screenshot", x - 675, y - 350);
        g.drawString("Unidades aliadas vivas:" + World.alliesAlive, x - 675, y - 325);
        g.drawString("Unidades enemigas vivas:" + World.foesAlive, x - 675, y - 300);
        g.drawString("Salud Base aliada:" + World.alliesBaseHealth, x - 675, y - 275);
        g.drawString("Salud Base enemiga:" + World.foesBaseHealth, x - 675, y - 250);
        /*
         g.drawString("Uniforme", x - 450, y + 100);
         g.drawString("A. Arrive", x - 450, y + 150);
         g.drawString("W. Wander", x - 450, y + 200);
         g.drawString("S. Seek", x - 450, y + 250);
         g.drawString("F. Flee", x - 450, y + 300);
         g.drawString("Aceleado", x - 550, y + 100);
         g.drawString("1. Seek", x - 550, y + 150);
         g.drawString("2. Aling", x - 550, y + 200);
         g.drawString("3. Flee", x - 550, y + 250);
         g.drawString("4. Arrive", x - 550, y + 300);
         g.drawString("V. Vel. Matching", x - 550, y + 350);
         g.drawString("Delegado", x - 650, y + 100);
         g.drawString("P. Pursue", x - 650, y + 150);
         g.drawString("E. Evade", x - 650, y + 200);
         g.drawString("5. Face", x - 650, y + 250);
         g.drawString("6. Wander", x - 650, y + 300);
         * */
    }

    @Deprecated
    private void renderMap(GameContainer container, Graphics g, int x, int y) {
        //int tileID = map.tmap.getTileId(-x, -y, 0);
        //map.sheet.getSprite(tileID-1, 0).draw();
        System.out.println("Empezando a renderizar mapa en x=" + x + " y=" + y);
        for (int i = x; i < 75; i++) {
            for (int j = y; j < 40; j++) {
                //System.out.println("x="+i+" y="+j);
                int tileID = world.blockMap.tmap.getTileId(i, j, 0);
                world.blockMap.sheet.getSprite(tileID - 1, 0).draw(i * 32, j * 32);
                //System.out.println("renderizando x="+i+" y="+j);
            }
        }
        /*
         for(int i=x; i<75; i++){
         for(int j=y; j<40; j++){
         //System.out.println("x="+i+" y="+j);
         int tileID = map.tmap.getTileId(i, j, 0);
         if (tileID==1) tile1.draw(i*32,j*32);
         if (tileID==2) tile2.draw(i*32,j*32);
         if (tileID==3) tile3.draw(i*32,j*32);
         if (tileID==4) tile4.draw(i*32,j*32);
         if (tileID==5) tile5.draw(i*32,j*32);
         //map.sheet.getSprite(tileID-1, 0).draw(i*32,j*32);
         System.out.println("renderizando tile x="+i+" y="+j);
         }
         }*/
    }

    private void updateCamera(GameContainer container) {
        int mx = (int) -raton.getX();
        int my = (int) -raton.getY();

        double cx = camara.getPosicion().getX();
        double cy = camara.getPosicion().getY();

        if (Math.abs(my) < container.getHeight() / 6) {
            cy -= 10;
        } else if (Math.abs(my) > 5 * container.getHeight() / 6) {
            cy += 10;
        }

        if (Math.abs(mx) < container.getWidth() / 6) {
            cx -= 10;
        } else if (Math.abs(mx) > 5 * container.getWidth() / 6) {
            cx += 10;
        }
        camara.setPosicion(cx, cy);

        /*
         if(cx>600) camara.getPosicion().setX(cx);
         else camara.getPosicion().setX(600);
         if(cy>350) camara.getPosicion().setY(cy);
         else camara.getPosicion().setY(350);
         * */

        //if(cy<container.getHeight())

        if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) {
            scale -= 0.01;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_ADD)) {
            scale += 0.01;
        }

        if (scale > 2) {
            scale = 2;
        } else if (scale < 0.5f) {
            scale = 0.5f;
        }
        System.out.println("Camara(" + camara.getPosicion().getX() + "," + camara.getPosicion().getY());
        camera.centerOn((float) camara.getPosicion().getX(), (float) camara.getPosicion().getY());
    }

    @Deprecated
    private void renderTile(GameContainer container, Graphics g) {
        int x = 32;
        Boolean cont1;
        Boolean cont2;
        for (int x1 = 0; x1 < world.blockMap.tmap.getWidth(); x1 += x) {
            int win1 = x1;
            for (int y1 = 0; y1 < world.blockMap.tmap.getHeight(); y1 += x) {
                int win2 = y1;
                if (raton.getX() > win1 && raton.getX() < win1 + x && raton.getY() > win2) {
                    if ((win1 > 31 && win2 > 31) && (win1 < 384 && win2 < 380)) {
                        cont1 = true;
                        cont2 = true;
                    } else {
                        cont1 = false;
                        cont2 = false;
                    }
                    if (cont1 && cont2) {
                        g.setColor(Color.cyan);
                        g.draw(new Rectangle(x1, y1, 32, 32));
                        //System.out.println("OLA KE ASE");
                    }
                }
            }
        }
    }

    private void updateInfluenceMap() {
        if (frame == 60) {
            world.influenceMap = new int[42][42];
            for (Kinematic p : world.allys) {
                int i = (int) p.getPosicion().getX() / 320;
                int j = (int) p.getPosicion().getY() / 320;
                world.influenceMap[i][j] += 5;
                if (i - 1 > 0) {
                    world.influenceMap[i - 1][j] += 3;
                }
                if (j + 1 < 42) {
                    world.influenceMap[i][j + 1] += 3;
                }
                if (j - 1 > 0) {
                    world.influenceMap[i][j - 1] += 3;
                }
                if (i + 1 < 42) {
                    world.influenceMap[i + 1][j] += 3;
                }//
                if (i - 1 > 0 && j - 1 > 0) {
                    world.influenceMap[i - 1][j - 1] += 1;
                }
                if (j + 1 < 42 && i + 1 < 42) {
                    world.influenceMap[i + 1][j + 1] += 1;
                }
                if (j - 1 > 0 && i + 1 < 42) {
                    world.influenceMap[i + 1][j - 1] += 1;
                }
                if (i - 1 > 0 && j + 1 < 42) {
                    world.influenceMap[i - 1][j + 1] += 1;
                }//
                if (i - 2 > 0) {
                    world.influenceMap[i - 2][j] += 1;
                }
                if (j + 2 < 42) {
                    world.influenceMap[i][j + 2] += 1;
                }
                if (j - 2 > 0) {
                    world.influenceMap[i][j - 2] += 1;
                }
                if (i + 2 < 42) {
                    world.influenceMap[i + 2][j] += 1;
                }
            }

            for (Kinematic p : world.foes) {
                int i = (int) p.getPosicion().getX() / 320;
                int j = (int) p.getPosicion().getY() / 320;
                world.influenceMap[i][j] -= 3;
                if (i - 1 > 0) {
                    world.influenceMap[i - 1][j] -= 3;
                }
                if (j + 1 < 42) {
                    world.influenceMap[i][j + 1] -= 3;
                }
                if (j - 1 > 0) {
                    world.influenceMap[i][j - 1] -= 3;
                }
                if (i + 1 < 42) {
                    world.influenceMap[i + 1][j] -= 3;
                }//
                if (i - 1 > 0 && j - 1 > 0) {
                    world.influenceMap[i - 1][j - 1] -= 1;
                }
                if (j + 1 < 42 && i + 1 < 42) {
                    world.influenceMap[i + 1][j + 1] -= 1;
                }
                if (j - 1 > 0 && i + 1 < 42) {
                    world.influenceMap[i + 1][j - 1] -= 1;
                }
                if (i - 1 > 0 && j + 1 < 42) {
                    world.influenceMap[i - 1][j + 1] -= 1;
                }//
                if (i - 2 > 0) {
                    world.influenceMap[i - 2][j] -= 1;
                }
                if (j + 2 < 42) {
                    world.influenceMap[i][j + 2] -= 1;
                }
                if (j - 2 > 0) {
                    world.influenceMap[i][j - 2] -= 1;
                }
                if (i + 2 < 42) {
                    world.influenceMap[i + 2][j] -= 1;
                }
            }

            if (frame == 60) {
                frame = 0;
            }
        }
    }

    private void renderInfluenceMap(Graphics g) {

        float x = (float) camara.getPosicion().getX() + 440;
        float y = (float) camara.getPosicion().getY() + 145;

        //float tileCamX = (float) camara.getPosicion().getX() / 320;
        //float tileCamY = (float) camara.getPosicion().getY() / 320;

        miniMap.setAlpha(0.95f);
        g.drawImage(miniMap, x, y);

        for (int i = 0; i < 42; i++) {
            for (int j = 0; j < 42; j++) {
                if (world.influenceMap[i][j] > 20) {
                    g.setColor(new Color(0f, 0f, 255f, 0.9f));
                    g.fillRect(x + (i * 5), y + (j * 5), 5, 5);
                } else if (world.influenceMap[i][j] > 10) {
                    g.setColor(new Color(0f, 0f, 255f, 0.6f));
                    g.fillRect(x + (i * 5), y + (j * 5), 5, 5);
                } else if (world.influenceMap[i][j] > 0) {
                    g.setColor(new Color(0f, 0f, 255f, 0.3f));
                    g.fillRect(x + (i * 5), y + (j * 5), 5, 5);
                } else if (world.influenceMap[i][j] < -20) {
                    g.setColor(new Color(255f, 0f, 0f, 0.9f));
                    g.fillRect(x + (i * 5), y + (j * 5), 5, 5);
                } else if (world.influenceMap[i][j] < -10) {
                    g.setColor(new Color(255f, 0f, 0f, 0.6f));
                    g.fillRect(x + (i * 5), y + (j * 5), 5, 5);
                } else if (world.influenceMap[i][j] < 0) {
                    g.setColor(new Color(255f, 0f, 0f, 0.3f));
                    g.fillRect(x + (i * 5), y + (j * 5), 5, 5);
                } else {
                    //g.setColor(new Color(0f, 0f, 0f, 0.1f));
                    //g.fillRect(x + (i * 5), y + (j * 5), 5, 5);
                }
                // ¿Demasiado costosa?
                //g.setColor(Color.black);
                //g.drawRect(x+(i*5), y+(j*5), 5, 5);
            }
        }

        renderMiniMap(g);

        g.setColor(Color.orange);
        g.drawRect(x + ((float) camara.getPosicion().getX() / 320 * 5) - 5, y + ((float) camara.getPosicion().getY() / 320 * 5) - 5, 15, 15);

        g.setColor(Color.black);
        g.drawRect(x, y, 210, 210);
    }

    private void initGraph() {
        System.out.println("INICIALIZO NODOS");
        // Init the nodes
        for (int i = 0; i < 412; i++) {
            for (int j = 0; j < 412; j++) {
                int cost = 0;
                int tileID = world.blockMap.tmap.getTileId(i, j, 0);
                switch (tileID) {
                    case 1:
                        cost = 10;
                        break;
                    case 2:
                        cost = 10;
                        break;
                    case 3:
                        cost = -1;
                        break;
                    case 4:
                        cost = 5;
                        break;
                    case 5:
                        cost = -1;
                        break;
                    default:
                }
                if (cost > 0) {
                    world.worldGraph[i][j] = new Node(cost, i, j);
                } else {
                    world.worldGraph[i][j] = null;
                }
            }
        }
        for (int i = 0; i < 412; i++) {
            for (int j = 0; j < 412; j++) {
                if (i - 1 > 0 && world.worldGraph[i - 1][j] != null && world.worldGraph[i][j] != null) {
                    world.worldGraph[i][j].addNeighbour(world.worldGraph[i - 1][j]);
                }
                if (i + 1 < 416 && world.worldGraph[i + 1][j] != null && world.worldGraph[i][j] != null) {
                    world.worldGraph[i][j].addNeighbour(world.worldGraph[i + 1][j]);
                }
                if (j - 1 > 0 && world.worldGraph[i][j - 1] != null && world.worldGraph[i][j] != null) {
                    world.worldGraph[i][j].addNeighbour(world.worldGraph[i][j - 1]);
                }
                if (j + 1 < 412 && world.worldGraph[i][j + 1] != null && world.worldGraph[i][j] != null) {
                    world.worldGraph[i][j].addNeighbour(world.worldGraph[i][j + 1]);
                }
                if (i - 1 > 0 && j - 1 > 0 && world.worldGraph[i - 1][j - 1] != null && world.worldGraph[i][j] != null) {
                    world.worldGraph[i][j].addNeighbour(world.worldGraph[i - 1][j - 1]);
                }
                if (i - 1 > 0 && j + 1 < 412 && world.worldGraph[i - 1][j + 1] != null && world.worldGraph[i][j] != null) {
                    world.worldGraph[i][j].addNeighbour(world.worldGraph[i - 1][j + 1]);
                }
                if (i + 1 > 416 && j - 1 > 0 && world.worldGraph[i + 1][j - 1] != null && world.worldGraph[i][j] != null) {
                    world.worldGraph[i][j].addNeighbour(world.worldGraph[i + 1][j - 1]);
                }
                if (i + 1 > 416 && j + 1 > 412 && world.worldGraph[i + 1][j + 1] != null && world.worldGraph[i][j] != null) {
                    world.worldGraph[i][j].addNeighbour(world.worldGraph[i + 1][j + 1]);
                }
            }
        }
    }

    private void searchLRTAS(Kinematic character, Kinematic goal) {
        // u <- s
        Node u = new Node(0, character.getTileX(), character.getTileY());
        Node g = new Node(0, goal.getTileX(), goal.getTileY());
        // while u !e T
        //LRTA rta=new LRTA();
        //List<Node> pathfinding=rta.LRTARun(u,g);
        //for(Node e  :pathfinding){
        //System.out.println(e.getX());
        //}

    }

    private void renderPlayers(Graphics g) {
        for (Kinematic p : world.all) {
            if (p.getRank() != Rank.GHOST) {
                if (!p.isDead()
                        && p.getPosicion().getX() < camara.getPosicion().getX() + 1000
                        && p.getPosicion().getX() > camara.getPosicion().getX() - 1000
                        && p.getPosicion().getY() < camara.getPosicion().getY() + 1000
                        && p.getPosicion().getY() > camara.getPosicion().getY() - 1000) {
                    float x = (float) p.getPosicion().getX() - p.getSprite().getWidth() / 2;
                    float y = (float) p.getPosicion().getY() - p.getSprite().getHeight() / 2;

                    p.getSprite().draw(x, y, 1f);
                    //g.setColor(Color.red);
                    //g.fillRect(x-10, y-5, 50, 3);
                    if (p.getRank() != Rank.BIRD) {
                        GradientFill fill = new GradientFill(x, y, new Color(255, 60, 0),
                                x + 60, y + 10, new Color(255, 180, 0));
                        Rectangle bar = new Rectangle(x - 10, y - 5, 50, 3);
                        g.fill(bar, fill);
                        g.setColor(Color.green);
                        g.fillRect(x - 10, y - 5, (float) p.getLife() / 2, 3);
                        if (!p.bersek()) {
                            g.setColor(Color.black);
                        } else {
                            g.setColor(Color.magenta);
                        }
                        g.drawRect(x - 10, y - 5, 50, 3);

                        if (p.isSelected()) {
                            g.setColor(Color.magenta);
                            g.drawRect((float) p.getPosicion().getX() - 32, (float) p.getPosicion().getY() - 32, 64, 64);
                        }
                    }

                }
            }
        }
    }

    private void renderDebug(Graphics g) {
        /*
         List<Vector> puntos = steeringFuerteJ.getPath().getGoals();
         GL11.glColor3f(0, 0, 1);
         GL11.glBegin(GL11.GL_LINE_STRIP);
         GL11.glLineWidth(10.0f);
         for (Vector p : puntos) {
         GL11.glVertex2f((float) p.getX(), (float) p.getY());
         }
         GL11.glEnd();
         * */
        for (Kinematic p : world.all) {
            //g.draw(p.getPlayerPoly());
            if (p.getRank() == Rank.RANGED) {
                g.setColor(Color.green);
                g.drawOval((float) p.getPosicion().getX() - 400, (float) p.getPosicion().getY() - 400, 800, 800);
                g.setColor(Color.yellow);
                g.drawOval((float) p.getPosicion().getX() - 200, (float) p.getPosicion().getY() - 200, 400, 400);
                g.setColor(Color.red);
                g.drawOval((float) p.getPosicion().getX() - 50, (float) p.getPosicion().getY() - 50, 100, 100);
            }
        }
        /*
         for (Kinematic p : world.all) {
         g.draw(p.getPlayerPoly());
         }*/

        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glLineWidth(10.0f);
        GL11.glVertex2f(1760, 1760 - 100);
        GL11.glVertex2f(1760 + 32, 1760 - 100);
        GL11.glVertex2f(1760 + 32, 1760 + 32);
        GL11.glVertex2f(1760, 1760 + 32);
        GL11.glVertex2f(1760, 1760 - 100);
        GL11.glEnd();
        g.setColor(Color.green);
        g.drawOval((float) metralla1.getPosicion().getX() - 300, (float) metralla1.getPosicion().getY() - 300, 600, 600);

        g.setColor(Color.red);
        g.draw(new Rectangle(1760, 1760 + 32, 32, -132));

        g.setColor(Color.blue);
        g.fillOval(1760, 1760, 32, 32);

        // PathFollowing
        g.setColor(Color.red);
        g.fillOval((float) debugPoint1.getX(), (float) debugPoint1.getY(), 16, 16);
        g.setColor(Color.orange);
        g.fillOval((float) debugPoint2.getX(), (float) debugPoint2.getY(), 16, 16);

        // Wander
        g.setColor(Color.blue);
        g.drawOval((float) wanderCircle.getX() - 200, (float) wanderCircle.getY() - 200, 400, 400);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glLineWidth(10.0f);
        GL11.glVertex2f((float) wanderPoint1.getX(), (float) wanderPoint1.getY());
        GL11.glVertex2f((float) wanderPoint2.getX(), (float) wanderPoint2.getY());
        GL11.glEnd();

        // Camera
        g.setColor(Color.pink);
        g.drawOval((float) camara.getPosicion().getX() - 50, (float) camara.getPosicion().getY() - 50, 100, 100);

    }

    private void renderMouse(GameContainer container, Graphics g) {
        g.setColor(Color.cyan);
        g.drawRect((float) mouse.getX(), (float) mouse.getY(), 32, 32);
    }

    private void updateMouse(GameContainer container) {
        int mx = (int) raton.getX();
        int my = (int) raton.getY();

        double cx = camara.getPosicion().getX();
        double cy = camara.getPosicion().getY();

        cx -= containerWidth / 2;
        cy -= containerHeight / 2;
        // CX y CY ahora apuntan a la esquina de la pantalla

        // Ahora calcularemos la posicion del raton en el juego
        mx += cx;
        my += cy;

        // Y ahora su posicion en tiles
        float tx = mx / 32;
        float ty = my / 32;

        mouse.setX(tx * 32);
        mouse.setY(ty * 32);
        System.out.println("ESTOY EN :" + tx * 32 + "," + ty * 32);
        //Input input = container.getInput();
        //if(input.isMousePressed(1)) System.out.println("BOTON DERECHO PULSADO");
    }

    public void mouseClicked(int button, int x, int y, int clickCount) {
        double cx = camara.getPosicion().getX();
        double cy = camara.getPosicion().getY();
        // CX y CY ahora apuntan a la esquina de la pantalla
        cx -= containerWidth / 2;
        cy -= containerHeight / 2;
        // Ahora calcularemos la posicion del raton en el juego
        double mx = x + cx;
        double my = y + cy;
        // Y ahora su posicion en tiles
        int tx = (int) mx / 32;
        int ty = (int) my / 32;

        if (button == 0) {
            //System.out.println("ESTOY IZQ EN :"+tx+","+ty);
            for (Kinematic p : world.all) {
                //System.out.println("MIRAMOS: "+Math.abs(p.getTileX()-tx)+","+Math.abs(p.getTileY()-ty));
                if (Math.abs(p.getTileX() - tx) <= 1 && Math.abs(p.getTileY() - ty) <= 1) {
                    //System.out.println("SELECCIONAMOS");
                    p.select();
                } else {
                    p.unselect();
                }
            }
        }

        if (button == 1) {
            System.out.println("PATHFINDING INICIADO A:" + tx + "," + ty);
            for (Kinematic p : world.all) {
                if (p.isSelected()) {
                    p.setExternalPF(tx, ty);
                }
            }
        }
    }

    public void mousePressed(int button, int x, int y) {
        if (button == 0) {
            //System.out.println("BOTON IZQ PRESSED EN:" + x + "," + y);
        }
    }

    public void mouseDragged(int oldx, int oldy, int x, int y) {
        //System.out.println("BOTON DRAGED DE:" + oldx + "," + oldy + " a " + x + "," + y);
        double cx = camara.getPosicion().getX();
        double cy = camara.getPosicion().getY();
        // CX y CY ahora apuntan a la esquina de la pantalla
        cx -= containerWidth / 2;
        cy -= containerHeight / 2;
        // Ahora calcularemos la posicion del raton en el juego
        double oldmx = oldx + cy;
        double oldmy = oldy + cy;
        double mx = x + cx;
        double my = y + cy;
        // Y ahora su posicion en tiles
        int tx = (int) mx / 32;
        int ty = (int) my / 32;
        int oldtx = (int) oldmx / 32;
        int oldty = (int) oldmy / 32;
    }

    private void renderMiniMap(Graphics g) {
        for (Kinematic p : world.all) {
            double mx = (camara.getPosicion().getX() + 440);
            double my = (camara.getPosicion().getY() + 145);

            float px = (float) ((p.getPosicion().getX() / 320 * 5) + mx);
            float py = (float) ((p.getPosicion().getY() / 320 * 5) + my);

            if (p.getFaction() == Faction.ALLY) {
                g.setColor(Color.blue);
            } else if (p.getFaction() == Faction.FOE) {
                g.setColor(Color.red);
            } else {
                g.setColor(Color.lightGray);
            }

            g.fillOval(px - 1.5f, py - 1.5f, 3, 3);
            g.setColor(Color.black);
            g.drawOval(px - 1.5f, py - 1.5f, 3, 3);
        }
    }

    private double randomBinominial() {
        return Math.random() - Math.random();
    }

    private void initGraph_AFFE_A() {
        // Init the nodes
        for (int i = 0; i < 412; i++) {
            for (int j = 0; j < 412; j++) {
                int cost = 0;
                int tileID = world.blockMap.tmap.getTileId(i, j, 0);
                switch (tileID) {
                    case 1:
                        cost = 10;
                        break;
                    case 2:
                        cost = 10;
                        break;
                    case 3:
                        cost = -1;
                        break;
                    case 4:
                        cost = 5;
                        break;
                    case 5:
                        cost = -1;
                        break;
                    default:
                }
                if (cost > 0) {
                    double tacticalCost = cost + (world.influenceMap[i * 32 / 320][j * 32 / 320] * 2);
                    if (tacticalCost > 0) {
                        world.graph_AFFE_A[i][j] = new Node(tacticalCost, i, j);
                    } else {
                        world.graph_AFFE_A[i][j] = new Node(cost, i, j);
                    }
                } else {
                    world.graph_AFFE_A[i][j] = null;
                }
            }
        }
        for (int i = 0; i < 412; i++) {
            for (int j = 0; j < 412; j++) {
                if (i - 1 > 0 && world.graph_AFFE_A[i - 1][j] != null && world.graph_AFFE_A[i][j] != null) {
                    world.graph_AFFE_A[i][j].addNeighbour(world.graph_AFFE_A[i - 1][j]);
                }
                if (i + 1 < 416 && world.graph_AFFE_A[i + 1][j] != null && world.graph_AFFE_A[i][j] != null) {
                    world.graph_AFFE_A[i][j].addNeighbour(world.graph_AFFE_A[i + 1][j]);
                }
                if (j - 1 > 0 && world.graph_AFFE_A[i][j - 1] != null && world.graph_AFFE_A[i][j] != null) {
                    world.graph_AFFE_A[i][j].addNeighbour(world.graph_AFFE_A[i][j - 1]);
                }
                if (j + 1 < 412 && world.graph_AFFE_A[i][j + 1] != null && world.graph_AFFE_A[i][j] != null) {
                    world.graph_AFFE_A[i][j].addNeighbour(world.graph_AFFE_A[i][j + 1]);
                }
                if (i - 1 > 0 && j - 1 > 0 && world.graph_AFFE_A[i - 1][j - 1] != null && world.graph_AFFE_A[i][j] != null) {
                    world.graph_AFFE_A[i][j].addNeighbour(world.graph_AFFE_A[i - 1][j - 1]);
                }
                if (i - 1 > 0 && j + 1 < 412 && world.graph_AFFE_A[i - 1][j + 1] != null && world.graph_AFFE_A[i][j] != null) {
                    world.graph_AFFE_A[i][j].addNeighbour(world.graph_AFFE_A[i - 1][j + 1]);
                }
                if (i + 1 > 416 && j - 1 > 0 && world.graph_AFFE_A[i + 1][j - 1] != null && world.graph_AFFE_A[i][j] != null) {
                    world.graph_AFFE_A[i][j].addNeighbour(world.graph_AFFE_A[i + 1][j - 1]);
                }
                if (i + 1 > 416 && j + 1 > 412 && world.graph_AFFE_A[i + 1][j + 1] != null && world.graph_AFFE_A[i][j] != null) {
                    world.graph_AFFE_A[i][j].addNeighbour(world.graph_AFFE_A[i + 1][j + 1]);
                }
            }
        }
    }

    private void initGraph_AFFE_E() {
        // Init the nodes
        for (int i = 0; i < 412; i++) {
            for (int j = 0; j < 412; j++) {
                int cost = 0;
                int tileID = world.blockMap.tmap.getTileId(i, j, 0);
                switch (tileID) {
                    case 1:
                        cost = 10;
                        break;
                    case 2:
                        cost = 10;
                        break;
                    case 3:
                        cost = -1;
                        break;
                    case 4:
                        cost = 5;
                        break;
                    case 5:
                        cost = -1;
                        break;
                    default:
                }
                if (cost > 0) {
                    double tacticalCost = cost + (-world.influenceMap[i * 32 / 320][j * 32 / 320] * 2);
                    if (tacticalCost > 0) {
                        world.graph_AFFE_E[i][j] = new Node(tacticalCost, i, j);
                    } else {
                        world.graph_AFFE_E[i][j] = new Node(cost, i, j);
                    }
                } else {
                    world.graph_AFFE_E[i][j] = null;
                }
            }
        }
        for (int i = 0; i < 412; i++) {
            for (int j = 0; j < 412; j++) {
                if (i - 1 > 0 && world.graph_AFFE_E[i - 1][j] != null && world.graph_AFFE_E[i][j] != null) {
                    world.graph_AFFE_E[i][j].addNeighbour(world.graph_AFFE_E[i - 1][j]);
                }
                if (i + 1 < 416 && world.graph_AFFE_E[i + 1][j] != null && world.graph_AFFE_E[i][j] != null) {
                    world.graph_AFFE_E[i][j].addNeighbour(world.graph_AFFE_E[i + 1][j]);
                }
                if (j - 1 > 0 && world.graph_AFFE_E[i][j - 1] != null && world.graph_AFFE_E[i][j] != null) {
                    world.graph_AFFE_E[i][j].addNeighbour(world.graph_AFFE_E[i][j - 1]);
                }
                if (j + 1 < 412 && world.graph_AFFE_E[i][j + 1] != null && world.graph_AFFE_E[i][j] != null) {
                    world.graph_AFFE_E[i][j].addNeighbour(world.graph_AFFE_E[i][j + 1]);
                }
                if (i - 1 > 0 && j - 1 > 0 && world.graph_AFFE_E[i - 1][j - 1] != null && world.graph_AFFE_E[i][j] != null) {
                    world.graph_AFFE_E[i][j].addNeighbour(world.graph_AFFE_E[i - 1][j - 1]);
                }
                if (i - 1 > 0 && j + 1 < 412 && world.graph_AFFE_E[i - 1][j + 1] != null && world.graph_AFFE_E[i][j] != null) {
                    world.graph_AFFE_E[i][j].addNeighbour(world.graph_AFFE_E[i - 1][j + 1]);
                }
                if (i + 1 > 416 && j - 1 > 0 && world.graph_AFFE_E[i + 1][j - 1] != null && world.graph_AFFE_E[i][j] != null) {
                    world.graph_AFFE_E[i][j].addNeighbour(world.graph_AFFE_E[i + 1][j - 1]);
                }
                if (i + 1 > 416 && j + 1 > 412 && world.graph_AFFE_E[i + 1][j + 1] != null && world.graph_AFFE_E[i][j] != null) {
                    world.graph_AFFE_E[i][j].addNeighbour(world.graph_AFFE_E[i + 1][j + 1]);
                }
            }
        }
    }

    private void initGraph_AGAE_A() {
        // Init the nodes
        for (int i = 0; i < 412; i++) {
            for (int j = 0; j < 412; j++) {
                int cost = 0;
                int tileID = world.blockMap.tmap.getTileId(i, j, 0);
                switch (tileID) {
                    case 1:
                        cost = 3;
                        break;
                    case 2:
                        cost = 5;
                        break;
                    case 3:
                        cost = -1;
                        break;
                    case 4:
                        cost = 10;
                        break;
                    case 5:
                        cost = -1;
                        break;
                    default:
                }
                if (cost > 0) {
                    double tacticalCost = cost + (-world.influenceMap[i * 32 / 320][j * 32 / 320] * 2);
                    if (tacticalCost > 0) {
                        world.graph_AGAE_A[i][j] = new Node(tacticalCost, i, j);
                    } else {
                        world.graph_AGAE_A[i][j] = new Node(cost, i, j);
                    }
                } else {
                    world.graph_AGAE_A[i][j] = null;
                }
            }
        }
        for (int i = 0; i < 412; i++) {
            for (int j = 0; j < 412; j++) {
                if (i - 1 > 0 && world.graph_AGAE_A[i - 1][j] != null && world.graph_AGAE_A[i][j] != null) {
                    world.graph_AGAE_A[i][j].addNeighbour(world.graph_AGAE_A[i - 1][j]);
                }
                if (i + 1 < 416 && world.graph_AGAE_A[i + 1][j] != null && world.graph_AGAE_A[i][j] != null) {
                    world.graph_AGAE_A[i][j].addNeighbour(world.graph_AGAE_A[i + 1][j]);
                }
                if (j - 1 > 0 && world.graph_AGAE_A[i][j - 1] != null && world.graph_AGAE_A[i][j] != null) {
                    world.graph_AGAE_A[i][j].addNeighbour(world.graph_AGAE_A[i][j - 1]);
                }
                if (j + 1 < 412 && world.graph_AGAE_A[i][j + 1] != null && world.graph_AGAE_A[i][j] != null) {
                    world.graph_AGAE_A[i][j].addNeighbour(world.graph_AGAE_A[i][j + 1]);
                }
                if (i - 1 > 0 && j - 1 > 0 && world.graph_AGAE_A[i - 1][j - 1] != null && world.graph_AGAE_A[i][j] != null) {
                    world.graph_AGAE_A[i][j].addNeighbour(world.graph_AGAE_A[i - 1][j - 1]);
                }
                if (i - 1 > 0 && j + 1 < 412 && world.graph_AGAE_A[i - 1][j + 1] != null && world.graph_AGAE_A[i][j] != null) {
                    world.graph_AGAE_A[i][j].addNeighbour(world.graph_AGAE_A[i - 1][j + 1]);
                }
                if (i + 1 > 416 && j - 1 > 0 && world.graph_AGAE_A[i + 1][j - 1] != null && world.graph_AGAE_A[i][j] != null) {
                    world.graph_AGAE_A[i][j].addNeighbour(world.graph_AGAE_A[i + 1][j - 1]);
                }
                if (i + 1 > 416 && j + 1 > 412 && world.graph_AGAE_A[i + 1][j + 1] != null && world.graph_AGAE_A[i][j] != null) {
                    world.graph_AGAE_A[i][j].addNeighbour(world.graph_AGAE_A[i + 1][j + 1]);
                }
            }
        }
    }

    private void initGraph_AGAE_E() {
        // Init the nodes
        for (int i = 0; i < 412; i++) {
            for (int j = 0; j < 412; j++) {
                int cost = 0;
                int tileID = world.blockMap.tmap.getTileId(i, j, 0);
                switch (tileID) {
                    case 1:
                        cost = 3;
                        break;
                    case 2:
                        cost = 5;
                        break;
                    case 3:
                        cost = -1;
                        break;
                    case 4:
                        cost = 10;
                        break;
                    case 5:
                        cost = -1;
                        break;
                    default:
                }
                if (cost > 0) {
                    double tacticalCost = cost + (world.influenceMap[i * 32 / 320][j * 32 / 320] * 2);
                    if (tacticalCost > 0) {
                        world.graph_AGAE_E[i][j] = new Node(tacticalCost, i, j);
                    } else {
                        world.graph_AGAE_E[i][j] = new Node(cost, i, j);
                    }
                } else {
                    world.graph_AGAE_E[i][j] = null;
                }
            }
        }
        for (int i = 0; i < 412; i++) {
            for (int j = 0; j < 412; j++) {
                if (i - 1 > 0 && world.graph_AGAE_E[i - 1][j] != null && world.graph_AGAE_E[i][j] != null) {
                    world.graph_AGAE_E[i][j].addNeighbour(world.graph_AGAE_E[i - 1][j]);
                }
                if (i + 1 < 416 && world.graph_AGAE_E[i + 1][j] != null && world.graph_AGAE_E[i][j] != null) {
                    world.graph_AGAE_E[i][j].addNeighbour(world.graph_AGAE_E[i + 1][j]);
                }
                if (j - 1 > 0 && world.graph_AGAE_E[i][j - 1] != null && world.graph_AGAE_E[i][j] != null) {
                    world.graph_AGAE_E[i][j].addNeighbour(world.graph_AGAE_E[i][j - 1]);
                }
                if (j + 1 < 412 && world.graph_AGAE_E[i][j + 1] != null && world.graph_AGAE_E[i][j] != null) {
                    world.graph_AGAE_E[i][j].addNeighbour(world.graph_AGAE_E[i][j + 1]);
                }
                if (i - 1 > 0 && j - 1 > 0 && world.graph_AGAE_E[i - 1][j - 1] != null && world.graph_AGAE_E[i][j] != null) {
                    world.graph_AGAE_E[i][j].addNeighbour(world.graph_AGAE_E[i - 1][j - 1]);
                }
                if (i - 1 > 0 && j + 1 < 412 && world.graph_AGAE_E[i - 1][j + 1] != null && world.graph_AGAE_E[i][j] != null) {
                    world.graph_AGAE_E[i][j].addNeighbour(world.graph_AGAE_E[i - 1][j + 1]);
                }
                if (i + 1 > 416 && j - 1 > 0 && world.graph_AGAE_E[i + 1][j - 1] != null && world.graph_AGAE_E[i][j] != null) {
                    world.graph_AGAE_E[i][j].addNeighbour(world.graph_AGAE_E[i + 1][j - 1]);
                }
                if (i + 1 > 416 && j + 1 > 412 && world.graph_AGAE_E[i + 1][j + 1] != null && world.graph_AGAE_E[i][j] != null) {
                    world.graph_AGAE_E[i][j].addNeighbour(world.graph_AGAE_E[i + 1][j + 1]);
                }
            }
        }
    }

    private void initGraph_AFAE_A() {
        // Init the nodes
        for (int i = 0; i < 412; i++) {
            for (int j = 0; j < 412; j++) {
                int cost = 0;
                int tileID = world.blockMap.tmap.getTileId(i, j, 0);
                switch (tileID) {
                    case 1:
                        cost = 10;
                        break;
                    case 2:
                        cost = 10;
                        break;
                    case 3:
                        cost = -1;
                        break;
                    case 4:
                        cost = 5;
                        break;
                    case 5:
                        cost = -1;
                        break;
                    default:
                }
                if (cost > 0) {
                    double tacticalCost = cost + (-world.influenceMap[i * 32 / 320][j * 32 / 320] * 2);
                    if (tacticalCost > 0) {
                        world.graph_AFAE_A[i][j] = new Node(tacticalCost, i, j);
                    } else {
                        world.graph_AFAE_A[i][j] = new Node(cost, i, j);
                    }
                } else {
                    world.graph_AFAE_A[i][j] = null;
                }
            }
        }
        for (int i = 0; i < 412; i++) {
            for (int j = 0; j < 412; j++) {
                if (i - 1 > 0 && world.graph_AFAE_A[i - 1][j] != null && world.graph_AFAE_A[i][j] != null) {
                    world.graph_AFAE_A[i][j].addNeighbour(world.graph_AFAE_A[i - 1][j]);
                }
                if (i + 1 < 416 && world.graph_AFAE_A[i + 1][j] != null && world.graph_AFAE_A[i][j] != null) {
                    world.graph_AFAE_A[i][j].addNeighbour(world.graph_AFAE_A[i + 1][j]);
                }
                if (j - 1 > 0 && world.graph_AFAE_A[i][j - 1] != null && world.graph_AFAE_A[i][j] != null) {
                    world.graph_AFAE_A[i][j].addNeighbour(world.graph_AFAE_A[i][j - 1]);
                }
                if (j + 1 < 412 && world.graph_AFAE_A[i][j + 1] != null && world.graph_AFAE_A[i][j] != null) {
                    world.graph_AFAE_A[i][j].addNeighbour(world.graph_AFAE_A[i][j + 1]);
                }
                if (i - 1 > 0 && j - 1 > 0 && world.graph_AFAE_A[i - 1][j - 1] != null && world.graph_AFAE_A[i][j] != null) {
                    world.graph_AFAE_A[i][j].addNeighbour(world.graph_AFAE_A[i - 1][j - 1]);
                }
                if (i - 1 > 0 && j + 1 < 412 && world.graph_AFAE_A[i - 1][j + 1] != null && world.graph_AFAE_A[i][j] != null) {
                    world.graph_AFAE_A[i][j].addNeighbour(world.graph_AFAE_A[i - 1][j + 1]);
                }
                if (i + 1 > 416 && j - 1 > 0 && world.graph_AFAE_A[i + 1][j - 1] != null && world.graph_AFAE_A[i][j] != null) {
                    world.graph_AFAE_A[i][j].addNeighbour(world.graph_AFAE_A[i + 1][j - 1]);
                }
                if (i + 1 > 416 && j + 1 > 412 && world.graph_AFAE_A[i + 1][j + 1] != null && world.graph_AFAE_A[i][j] != null) {
                    world.graph_AFAE_A[i][j].addNeighbour(world.graph_AFAE_A[i + 1][j + 1]);
                }
            }
        }
    }

    private void initGraph_AFAE_E() {
        // Init the nodes
        for (int i = 0; i < 412; i++) {
            for (int j = 0; j < 412; j++) {
                int cost = 0;
                int tileID = world.blockMap.tmap.getTileId(i, j, 0);
                switch (tileID) {
                    case 1:
                        cost = 10;
                        break;
                    case 2:
                        cost = 10;
                        break;
                    case 3:
                        cost = -1;
                        break;
                    case 4:
                        cost = 5;
                        break;
                    case 5:
                        cost = -1;
                        break;
                    default:
                }
                if (cost > 0) {
                    double tacticalCost = cost + (world.influenceMap[i * 32 / 320][j * 32 / 320] * 2);
                    if (tacticalCost > 0) {
                        world.graph_AFAE_E[i][j] = new Node(tacticalCost, i, j);
                    } else {
                        world.graph_AFAE_E[i][j] = new Node(cost, i, j);
                    }
                } else {
                    world.graph_AFAE_E[i][j] = null;
                }
            }
        }
        for (int i = 0; i < 412; i++) {
            for (int j = 0; j < 412; j++) {
                if (i - 1 > 0 && world.graph_AFAE_E[i - 1][j] != null && world.graph_AFAE_E[i][j] != null) {
                    world.graph_AFAE_E[i][j].addNeighbour(world.graph_AFAE_E[i - 1][j]);
                }
                if (i + 1 < 416 && world.graph_AFAE_E[i + 1][j] != null && world.graph_AFAE_E[i][j] != null) {
                    world.graph_AFAE_E[i][j].addNeighbour(world.graph_AFAE_E[i + 1][j]);
                }
                if (j - 1 > 0 && world.graph_AFAE_E[i][j - 1] != null && world.graph_AFAE_E[i][j] != null) {
                    world.graph_AFAE_E[i][j].addNeighbour(world.graph_AFAE_E[i][j - 1]);
                }
                if (j + 1 < 412 && world.graph_AFAE_E[i][j + 1] != null && world.graph_AFAE_E[i][j] != null) {
                    world.graph_AFAE_E[i][j].addNeighbour(world.graph_AFAE_E[i][j + 1]);
                }
                if (i - 1 > 0 && j - 1 > 0 && world.graph_AFAE_E[i - 1][j - 1] != null && world.graph_AFAE_E[i][j] != null) {
                    world.graph_AFAE_E[i][j].addNeighbour(world.graph_AFAE_E[i - 1][j - 1]);
                }
                if (i - 1 > 0 && j + 1 < 412 && world.graph_AFAE_E[i - 1][j + 1] != null && world.graph_AFAE_E[i][j] != null) {
                    world.graph_AFAE_E[i][j].addNeighbour(world.graph_AFAE_E[i - 1][j + 1]);
                }
                if (i + 1 > 416 && j - 1 > 0 && world.graph_AFAE_E[i + 1][j - 1] != null && world.graph_AFAE_E[i][j] != null) {
                    world.graph_AFAE_E[i][j].addNeighbour(world.graph_AFAE_E[i + 1][j - 1]);
                }
                if (i + 1 > 416 && j + 1 > 412 && world.graph_AFAE_E[i + 1][j + 1] != null && world.graph_AFAE_E[i][j] != null) {
                    world.graph_AFAE_E[i][j].addNeighbour(world.graph_AFAE_E[i + 1][j + 1]);
                }
            }
        }
    }
}
