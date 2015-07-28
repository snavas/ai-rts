/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj;

import java.util.LinkedList;
import java.util.List;
import juegoiavj.Formation.DefensiveCirclePattern;
import juegoiavj.Formation.FormationManager;
import juegoiavj.kinematic.Kinematic;
import juegoiavj.math.Vector;
import juegoiavj.pathFinding.Node;
import juegoiavj.util.BlockMap;

/**
 *
 * @author Samuel
 */
public class World {
    public static World INSTANCE = null;
    public static BlockMap blockMap = null;
    public static List<Kinematic> allys = new LinkedList<Kinematic>();
    public static List<Kinematic> foes = new LinkedList<Kinematic>();
    public static List<Kinematic> all = new LinkedList<Kinematic>();
    public static Node[][] worldGraph = new Node[416][412];
    // Evitar bosque seguir enemigos -> AZULES
    public static Node[][] graph_AFFE_A = new Node[416][412];
    // Evitar bosque seguir enemigos -> ROJOS
    public static Node[][] graph_AFFE_E = new Node[416][412];
    // Evitar hierba evitar enemigos -> AZUKEs
    public static Node[][] graph_AGAE_A = new Node[416][412];
    // Evitar hierba evitar enemigos -> ROJOS
    public static Node[][] graph_AGAE_E = new Node[416][412];
    // Evitar bosque evitar enemigos -> AZULES
    public static Node[][] graph_AFAE_A = new Node[416][412];
    // Evitar bosques evitar enemigos -> ROJOS
    public static Node[][] graph_AFAE_E = new Node[416][412];
            
    
    public static List<Kinematic> swarm = new LinkedList<Kinematic>();
    public static List<Kinematic> swarmA=new LinkedList<Kinematic>();

    
    public static List<Kinematic> birds = new LinkedList<Kinematic>();
    public static int[][] influenceMap = new int[42][42];
    public static int alliesAlive = 0;
    public static int foesAlive = 0;
    public static double alliesBaseHealth = 1000;
    public static double foesBaseHealth = 1000;
    DefensiveCirclePattern pattern = new DefensiveCirclePattern();
    public FormationManager formation = new FormationManager(pattern);
    
    /*
    public World(){
        //List<Kinematic> allys = new LinkedList<Kinematic>();
        //List<Kinematic> foes = new LinkedList<Kinematic>();
        //Node[][] worldGraph = new Node[416][412];
    }*/
 
    
    // Private constructor suppresses 
    private World() {
        alliesBaseHealth = 1000;
        foesBaseHealth = 1000;
    }
 
    // creador sincronizado para protegerse de posibles problemas  multi-hilo
    // otra prueba para evitar instanciación múltiple 
    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new World();
        }
    }
 
    public static World getInstance() {
        createInstance();
        return INSTANCE;
    }

    public List<Kinematic> getAllys() {
        return allys;
    }

    public void setAllys(List<Kinematic> allys) {
        this.allys = allys;
    }

    public List<Kinematic> getFoes() {
        return foes;
    }

    public void setFoes(List<Kinematic> foes) {
        this.foes = foes;
    }

    public Node[][] getWorldGraph() {
        return worldGraph;
    }

    public void setWorldGraph(Node[][] worldGraph) {
        this.worldGraph = worldGraph;
    }

    public static List<Kinematic> getSwarm() {
        return swarm;
    }
    public static List<Kinematic> getSwarmA() {
        return swarmA;
    }
    
}
