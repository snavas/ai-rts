/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.kinematic;

import java.util.LinkedList;
import java.util.List;
import juegoiavj.Formation.FormationManager;
import juegoiavj.World;
import juegoiavj.kinematic.acelerado.KinematicArriveA;
import juegoiavj.kinematic.acelerado.KinematicFleeA;
import juegoiavj.kinematic.acelerado.KinematicSeekA;
import juegoiavj.kinematic.acelerado.delegado.KinematicCollisionAvoidance;
import juegoiavj.kinematic.acelerado.delegado.KinematicEvade;
import juegoiavj.kinematic.acelerado.delegado.KinematicFace;
import juegoiavj.kinematic.acelerado.delegado.KinematicFollowPath;
import juegoiavj.kinematic.acelerado.delegado.KinematicLook;
import juegoiavj.kinematic.acelerado.delegado.KinematicPursue;
import juegoiavj.kinematic.acelerado.delegado.KinematicWallAvoidance;
import juegoiavj.kinematic.acelerado.delegado.KinematicWanderD;
import juegoiavj.kinematic.grupal.KinematicAlignment;
import juegoiavj.kinematic.grupal.KinematicCohesion;
import juegoiavj.kinematic.grupal.KinematicFlock;
import juegoiavj.kinematic.grupal.KinematicSeparation;
import juegoiavj.kinematic.uniforme.KinematicArrive;
import juegoiavj.kinematic.uniforme.KinematicWander;
import juegoiavj.kinematic.uniforme.Steering;
import juegoiavj.math.Vector;
import juegoiavj.pathFinding.LRTA;
import juegoiavj.pathFinding.LRTA_E;
import juegoiavj.pathFinding.LRTA_M;
import juegoiavj.pathFinding.Node;
import juegoiavj.util.Block;
import juegoiavj.util.BlockMap;
import juegoiavj.util.Faction;
import juegoiavj.util.Rank;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Samuel
 */
public class Kinematic {

    private int frames;
    private Kinematic leader;
    private Kinematic target;
    private World world;
    private Vector posicion;
    private double life;
    private double orientacion;
    private float orientationDegrees;
    private Vector velocidad;
    private double rotacion;
    private Line playerPoly = null;
    private Faction faction;
    private SpriteSheet sprite;
    private Image actualSheet;
    private int respawnTime;
    private float range;
    private BlendedSteering exploringBehavior;
    private BlendedSteering aimEnemy;
    private BlendedSteering fleeFromEnemy;
    private BlendedSteering patrol;
    private KinematicFace fireEnemy;
    private BlendedSteering move;
    private BlendedSteering move2;
    private BlendedSteering swarm;
    private KinematicSeekA seek;
    private Rank rank;
    private Boolean inNeighbourhood;
    private LRTA lrta;
    private Node lastObjetive;
    private FormationManager formation;
    private KinematicArrive slotArrive;
    private Boolean selected;
    private Boolean panic;
    private double bersek;
    private Vector externalPF;
    private List<Node> baseJedi;
    private List<Node> baseSith;

    public Kinematic(double x, double y, float range, Faction f, Rank rank, World w, Kinematic leader, FormationManager formation) throws SlickException {
        // Kinematic
        posicion = new Vector(x, y);
        this.orientacion = 0;
        velocidad = new Vector(0, 0);
        this.rotacion = 0;
        this.externalPF = null;
        this.faction = f;
        this.life = 100;
        this.respawnTime = 0;
        this.selected = false;
        this.panic = false;
        this.bersek = 1;
        this.range = range;
        this.world = world;
        this.rank = rank;
        this.inNeighbourhood = false;
        this.formation = formation;
        // Sprites
        if (f == Faction.ALLY) {
            if (rank == Rank.RANGED) {
                this.sprite = new SpriteSheet("Material/imagenes/JGun.png", 32, 52);
            } else if (rank == Rank.TURRET) {
                this.sprite = new SpriteSheet("Material/imagenes/JMetralla.png", 35, 105);
            } else if (rank == Rank.SWARM) {
                this.sprite = new SpriteSheet("Material/imagenes/JGun.png", 32, 52);
            } else if (rank == Rank.SCOUT) {
                this.sprite = new SpriteSheet("Material/imagenes/JScout.png", 32, 52);
            } else if (rank == Rank.HEAVY) {
                this.sprite = new SpriteSheet("Material/imagenes/JHeavy.png", 35, 65);
            } else if (rank == Rank.PATROL) {
                this.sprite = new SpriteSheet("Material/imagenes/JGun.png", 32, 52);
            } else if (rank == Rank.MELE) {
                this.sprite = new SpriteSheet("Material/imagenes/JMele.png", 32, 52);
            } else if (rank == Rank.HEALER) {
                this.sprite = new SpriteSheet("Material/imagenes/JHealer.png", 32, 52);
            }
        } else if (f == Faction.FOE) {
            if (rank == Rank.RANGED) {
                this.sprite = new SpriteSheet("Material/imagenes/SGun.png", 32, 52);
            } else if (rank == Rank.TURRET) {
                this.sprite = new SpriteSheet("Material/imagenes/SMetralla.png", 35, 105);
            } else if (rank == Rank.SWARM) {
                this.sprite = new SpriteSheet("Material/imagenes/SGun.png", 32, 52);
            } else if (rank == Rank.SCOUT) {
                this.sprite = new SpriteSheet("Material/imagenes/SScout.png", 32, 52);
            } else if (rank == Rank.HEAVY) {
                this.sprite = new SpriteSheet("Material/imagenes/SHeavy.png", 35, 65);
            } else if (rank == Rank.PATROL) {
                this.sprite = new SpriteSheet("Material/imagenes/SGun.png", 32, 52);
            } else if (rank == Rank.MELE) {
                this.sprite = new SpriteSheet("Material/imagenes/SMele.png", 32, 52);
            } else if (rank == Rank.HEALER) {
                this.sprite = new SpriteSheet("Material/imagenes/SHealer.png", 32, 52);
            }
        }
        if (f != Faction.NEUTRAL) {
            this.playerPoly = new Line((float) x, (float) y,
                    (float) x, (float) y - 100);
            this.actualSheet = sprite.getSprite(1, 0);
        } else if (rank == Rank.BIRD) {
            this.actualSheet = new Image("Material/imagenes/bird.png");
        }
        // Steerings
        this.exploringBehavior = new BlendedSteering(0.1, 0.025);
        this.exploringBehavior.addBehavior(new KinematicWander(this, 0.1, 0.025), 0.5);
        this.aimEnemy = new BlendedSteering(0.1, 0.025);
        this.aimEnemy.addBehavior(new KinematicArrive(this, null, 0.1, 10), 0.33);
        //this.aimEnemy.addBehavior(new KinematicWallAvoidance(this, this, 0.1, null, 100, 5), 0.66);
        this.fireEnemy = new KinematicFace(this, null, 0.001, 0.001, 0.001, 0.001);
        this.fleeFromEnemy = new BlendedSteering(0.1, 0.025);
        this.fleeFromEnemy.addBehavior(new KinematicLook(this, this, 0.001, 0.001, 0.001, 0.001), 0.3);
        this.fleeFromEnemy.addBehavior(new KinematicEvade(this, null, 0.1,1), 0.3);
        //this.fleeFromEnemy.addBehavior(new KinematicWallAvoidance(this, this, 0.1, null, 100, 5), 0.3);
        this.move = new BlendedSteering(0.1, 0.025);
        this.move.addBehavior(new KinematicSeekA(this, null, 0.1), 0.22);
        this.move.addBehavior(new KinematicLook(this, this, 0.005, 0.005, 0.005, 0.005), 0.66);
        this.move.addBehavior(new KinematicWander(this, 0.1, 0.025), 0.11);
        this.move2 = new BlendedSteering(0.1, 0.025);
        this.move2.addBehavior(new KinematicSeekA(this, null, 0.1), 0.5);
        this.move2.addBehavior(new KinematicLook(this, this, 0.005, 0.005, 0.005, 0.005), 0.5);
        slotArrive = new KinematicArrive(this, target, 0.2, 5);
        //slotArrive.addBehavior(new KinematicWallAvoidance(this, this, 0.1, null, 100, 5), 0.5);
        this.swarm = new BlendedSteering(0.1, 0.025);
        this.swarm.addBehavior(new KinematicLook(this, this, 0.001, 0.001, 0.001, 0.001), 0.25);
        this.swarm.addBehavior(new KinematicAlignment(this, new KinematicFlock(world.birds, 150, 0), 0.001), 0.25);
        this.swarm.addBehavior(new KinematicSeparation(this, new KinematicFlock(world.birds, 50, -1), 0.001), 0.25);
        this.swarm.addBehavior(new KinematicCohesion(this, new KinematicFlock(world.birds, 100, 0), 0.001), 0.25);
        this.leader = leader;
        this.seek=new KinematicSeekA(this,target,0.1);
        // Base Jedi 
        baseJedi = new LinkedList<Node>();
        baseJedi.add(world.worldGraph[55][118]);
        baseJedi.add(world.worldGraph[54][118]);
         baseJedi.add(world.worldGraph[56][118]);
         baseJedi.add(world.worldGraph[55][117]);
         baseJedi.add(world.worldGraph[55][119]);
         baseJedi.add(world.worldGraph[54][116]);
         baseJedi.add(world.worldGraph[57][116]);
         baseJedi.add(world.worldGraph[54][119]);
         baseJedi.add(world.worldGraph[57][119]);
        // Base Sith
        baseSith = new LinkedList<Node>();
        baseSith.add(world.worldGraph[357][273]);
        baseSith.add(world.worldGraph[356][273]);
         baseSith.add(world.worldGraph[358][273]);
         baseSith.add(world.worldGraph[357][272]);
         baseSith.add(world.worldGraph[357][274]);
         baseSith.add(world.worldGraph[358][272]);
         baseSith.add(world.worldGraph[358][274]);
         baseSith.add(world.worldGraph[356][272]);
         baseSith.add(world.worldGraph[356][274]);
        // Ruta Fuerte Jedi
        Path rutaFuerteJ = new Path();
        rutaFuerteJ.addWayPoint(80 * 32, 105 * 32);//1
        rutaFuerteJ.addWayPoint(80 * 32, 102 * 32);//2
        rutaFuerteJ.addWayPoint(34 * 32, 102 * 32);//3
        rutaFuerteJ.addWayPoint(31 * 32, 102 * 32);//4
        rutaFuerteJ.addWayPoint(31 * 32, 132 * 32);//5
        rutaFuerteJ.addWayPoint(31 * 32, 135 * 32);//6
        rutaFuerteJ.addWayPoint(75 * 32, 135 * 32);//7
        rutaFuerteJ.addWayPoint(80 * 32, 135 * 32);//8
        // Ruta Fuerte Sith
        Path rutaFuerteS = new Path();
        rutaFuerteS.addWayPoint(339 * 32, 259 * 32);//1
        rutaFuerteS.addWayPoint(339 * 32, 256 * 32);//2
        rutaFuerteS.addWayPoint(387 * 32, 256 * 32);//3
        rutaFuerteS.addWayPoint(390 * 32, 256 * 32);//4
        rutaFuerteS.addWayPoint(390 * 32, 288 * 32);//4
        rutaFuerteS.addWayPoint(390 * 32, 291 * 32);//4
        rutaFuerteS.addWayPoint(342 * 32, 291 * 32);//5
        rutaFuerteS.addWayPoint(339 * 32, 291 * 32);//5
        // Steering de patrulla
        patrol = new BlendedSteering(0.1, 0.025);
        if (faction == Faction.ALLY) {
            patrol.addBehavior(new KinematicFollowPath(this, 0.1, 50, rutaFuerteJ, null), 0.5);
        } else if (faction == Faction.FOE){
            patrol.addBehavior(new KinematicFollowPath(this, 0.1, 50, rutaFuerteS, null), 0.5);
        }
        patrol.addBehavior(new KinematicLook(this, this, 0.001, 0.001, 0.001, 0.001), 0.5);
        //patrol.addBehavior(new KinematicWallAvoidance(this, null, 0.1, null, 100, 5), 0.3);
        frames = 60;
    }

    public Kinematic(double x, double y, float range, Faction f, Rank rank, World w) throws SlickException {
        this(x, y, range, f, rank, w, null, null);
    }

    public Kinematic(double x, double y) throws SlickException {
        this(x, y, 0, Faction.NEUTRAL, Rank.GHOST, null);
    }

    @Deprecated
    public void update(SteeringA steering, double time) {
        if (steering != null && (playerPoly == null)) {
            // Update Position and orientation
            Vector vel = new Vector(velocidad);
            vel.multiEscalar(time);
            posicion.sumar(vel);
            orientacion += rotacion * time;

            // Update Rotation and velocoty
            Vector lin = new Vector(steering.getLin());
            lin.multiEscalar(time);
            velocidad.sumar(lin);

            rotacion += steering.getAng() * time;
        }
    }

    public void setSprite(SpriteSheet sprite) {
        this.sprite = sprite;
    }

    public void update(SteeringA steering, double maxSpeed, double time) {
        //if (steering != null && (playerPoly == null || !entityCollisionWith())) {
        if (steering != null) {
            // pos := pos +vel *time
            Vector v = this.velocidad.clone();
            v.multiEscalar(time);
            this.posicion.sumar(v);

            //ori := ori +rot *time
            this.orientacion += (this.rotacion * time);

            //vel :=vel +steering.lin*time
            Vector lin = steering.getLin().clone();
            lin.multiEscalar(time);
            this.velocidad.sumar(lin);

            //rot :=rot + steering.ang*time
            this.orientacion += steering.getAng() * time;

            if (velocidad.getModulo() > maxSpeed) {
                velocidad.normalizar();
                velocidad.multiEscalar(maxSpeed);
            }

            orientationDegrees = ((float) Math.toDegrees(orientacion));
            //actualSheet.setRotation(orientationDegrees);

            // Comprobamos si estamos en terrenos desfaborables para reducir la movilidad
            if (rank == Rank.MELE || rank == Rank.RANGED || rank == Rank.GHOST || rank == Rank.SWARM) {
                if (world.blockMap.tmap.getTileId(getTileX(), getTileY(), 0) < 3) {
                    velocidad.multiEscalar(0.9);
                }
            } else if (rank == Rank.HEAVY) {
                if (world.blockMap.tmap.getTileId(getTileX(), getTileY(), 0) < 3) {
                    velocidad.multiEscalar(0.75);
                }
            }
            // LA UNIDAD SCOUT NO TIENE TERRENO DESFABORABLE AUNQUE PREFIERA EL BOSQUE Y EL AGUA

            if (playerPoly != null) {
                Vector nuevoVector = new Vector(Math.sin(orientacion), -Math.cos(orientacion));
                nuevoVector.multiEscalar(100);
                Vector vectorC = new Vector(posicion.getX(), posicion.getY());
                nuevoVector.sumar(vectorC);
                playerPoly.set((float) posicion.getX(),
                        (float) posicion.getY(),
                        (float) nuevoVector.getX(),
                        (float) nuevoVector.getY());

            }

            if (isInHome()) {
                heal(0.1);
            }

            if (life < 20 && Math.random() < 0.1 && rank == Rank.MELE) {
                bersek = 1.3;
            }

            if (life < 20 && Math.random() < 0.1) {
                panic = true;
                bersek = 1;
            }

            if (life == 100) {
                panic = false;
            }

            if (rank == Rank.BIRD) {
                Vector pos = this.posicion;
                if (pos.getX() < 6048) {
                    this.posicion.setX(7552);
                }
                if (pos.getX() > 7552) {
                    this.posicion.setX(6048);
                }
                if (pos.getY() < 5120) {
                    this.posicion.setY(6304);
                }
                if (pos.getY() > 6304) {
                    this.posicion.setY(5120);
                }
            } else {
                Vector pos = this.posicion;
                if (pos.getX() < 1) {
                    this.posicion.setX(1);
                }
                if (pos.getX() > 414*32) {
                    this.posicion.setX(414*32);
                }
                if (pos.getY() < 1) {
                    this.posicion.setY(1);
                }
                if (pos.getY() > 410*32) {
                    this.posicion.setY(410*32);
                }
            }
        }
    }

    public void update(float delta, List<Kinematic> targets) throws SlickException {
        if (!isDead()) {
            Boolean disparando = false;
            if (rank == Rank.TURRET) {
                for (Kinematic p : targets) {
                    if (this.isInRadius(p, 300)) {
                        fireEnemy.setObjetivo(p);
                        update(fireEnemy.getSteering(), 0.05, delta);
                        p.hurt(0.15);
                        disparando = true;
                        break;
                    }
                }
            } else if (rank == Rank.RANGED) {
                Boolean target = false;
                Kinematic aux = null;
                double minLife = Double.MAX_VALUE;
                for (Kinematic p : targets) {
                    if (p.getLife()<= minLife && this.isInRadius(p,400)){
                        minLife = p.getLife();
                        aux = p;
                    }
                }
                if (aux!=null){
                    if (this.isInRadius(aux, 200) && !panic) {
                        if (aux.isDead() && faction==Faction.ALLY) world.foesBaseHealth -= 0.1;
                        else if (aux.isDead() && faction==Faction.FOE) world.alliesBaseHealth -= 0.1;
                        else aux.hurt(0.06);
                        disparando = true;
                        target = true;
                    } else if (this.isInRadius(aux, 400) && !panic) {
                        aimEnemy.setObjetivo(aux);
                        update(aimEnemy.getSteering(), 0.1, delta);
                        target = true;
                    }
                }
                if (target == false && externalPF != null && panic == false) {
                    System.out.println("MANDO PATHFINDING A:"+externalPF.getX()+","+externalPF.getY());
                    if (world.worldGraph[this.getTileX()][this.getTileY()] != null) {
                        List<Node> objetivos = new LinkedList<Node>();
                        objetivos.add(world.worldGraph[(int) externalPF.getX()][(int) externalPF.getY()]);
                        if(faction==Faction.ALLY) lrta = new LRTA_M(world.worldGraph[this.getTileX()][this.getTileY()], objetivos, world.worldGraph);
                        else lrta = new LRTA_M(world.worldGraph[this.getTileX()][this.getTileY()], objetivos, world.worldGraph);
                        Node resultado = lrta.LRTARun();
                        if (resultado != null) {
                            move.setObjetivo(new Kinematic(resultado.getX() * 32, resultado.getY() * 32));
                            update(move.getSteering(), 0.1, delta);
                        } else {
                            externalPF = null;
                        }
                    } else {
                        update(move.getSteering(), 0.1, delta);
                    }
                } else if (target == false && panic == false) {
                    if (world.worldGraph[this.getTileX()][this.getTileY()] != null) {
                        if ((faction == Faction.ALLY && !defenseMode())||(faction==Faction.FOE && defenseMode())) {
                            lrta = new LRTA_M(world.worldGraph[this.getTileX()][this.getTileY()], baseSith, world.worldGraph);
                        } else {
                            lrta = new LRTA_M(world.worldGraph[this.getTileX()][this.getTileY()], baseJedi, world.worldGraph);
                        }
                        Node resultado = lrta.LRTARun();
                        if (resultado != null) {
                            move.setObjetivo(new Kinematic(resultado.getX() * 32, resultado.getY() * 32));
                            update(move.getSteering(), 0.1, delta);
                        }
                    } else {
                        update(move.getSteering(), 0.1, delta);
                    }
                }
                if (panic) {
                    if (world.worldGraph[this.getTileX()][this.getTileY()] != null) {
                        if ((faction == Faction.ALLY && !defenseMode())||(faction==Faction.FOE && defenseMode())) {
                            lrta = new LRTA_M(world.worldGraph[this.getTileX()][this.getTileY()], baseJedi, world.worldGraph);
                        } else {
                            lrta = new LRTA_M(world.worldGraph[this.getTileX()][this.getTileY()], baseSith, world.worldGraph);
                        }
                        Node resultado = lrta.LRTARun();
                        if (resultado != null) {
                            move.setObjetivo(new Kinematic(resultado.getX() * 32, resultado.getY() * 32));
                            update(move.getSteering(), 0.12, delta);
                        }
                    } else {
                        update(move.getSteering(), 0.12, delta);
                    }
                }
            } else if (rank == Rank.MELE) {
               Boolean target = false;
                Kinematic aux = null;
                double minLife = Double.MAX_VALUE;
                for (Kinematic p : targets) {
                    if (p.getLife()<= minLife && this.isInRadius(p,400)){
                        minLife = p.getLife();
                        aux = p;
                    }
                }
                if (aux!=null){
                    if (this.isInRadius(aux, 30) && !panic) {
                        if (aux.isDead() && faction==Faction.ALLY) world.foesBaseHealth -= 0.1;
                        else if (aux.isDead() && faction==Faction.FOE) world.alliesBaseHealth -= 0.1;
                        else aux.hurt(0.1*bersek);
                        disparando = true;
                        target = true;
                    } else if (this.isInRadius(aux, 300) && !panic) {
                        aimEnemy.setObjetivo(aux);
                        update(aimEnemy.getSteering(), 0.07, delta);
                        target = true;
                    }
                }
                if (target == false && externalPF != null && panic == false) {
                    if (world.worldGraph[this.getTileX()][this.getTileY()] != null) {
                        List<Node> objetivos = new LinkedList<Node>();
                        objetivos.add(world.worldGraph[(int) externalPF.getX()][(int) externalPF.getY()]);
                        if(faction==Faction.ALLY) lrta = new LRTA_M(world.graph_AFFE_A[this.getTileX()][this.getTileY()], objetivos, world.worldGraph);
                        else lrta = new LRTA_M(world.graph_AFFE_E[this.getTileX()][this.getTileY()], objetivos, world.worldGraph);
                        Node resultado = lrta.LRTARun();
                        if (resultado != null) {
                            move.setObjetivo(new Kinematic(resultado.getX() * 32, resultado.getY() * 32));
                            update(move.getSteering(), 0.07, delta);
                        } else {
                            externalPF = null;
                        }
                    } else {
                        update(move.getSteering(), 0.07, delta);
                    }
                } else if (target == false && panic == false) {
                    if (world.worldGraph[this.getTileX()][this.getTileY()] != null) {
                        if ((faction == Faction.ALLY && !defenseMode())||(faction==Faction.FOE && defenseMode())) {
                            lrta = new LRTA_M(world.graph_AFFE_A[this.getTileX()][this.getTileY()], baseSith, world.worldGraph);
                        } else {
                            lrta = new LRTA_M(world.graph_AFFE_E[this.getTileX()][this.getTileY()], baseJedi, world.worldGraph);
                        }
                        Node resultado = lrta.LRTARun();
                        if (resultado != null) {
                            move.setObjetivo(new Kinematic(resultado.getX() * 32, resultado.getY() * 32));
                            update(move.getSteering(), 0.07, delta);
                        }
                    } else {
                        update(move.getSteering(), 0.07, delta);
                    }
                }
                if (panic) {
                    bersek = 1;
                    if (world.worldGraph[this.getTileX()][this.getTileY()] != null) {
                        if ((faction == Faction.ALLY && !defenseMode())||(faction==Faction.FOE && defenseMode())) {
                            lrta = new LRTA_M(world.graph_AFFE_A[this.getTileX()][this.getTileY()], baseJedi, world.worldGraph);
                        } else {
                            lrta = new LRTA_M(world.graph_AFFE_E[this.getTileX()][this.getTileY()], baseSith, world.worldGraph);
                        }
                        Node resultado = lrta.LRTARun();
                        if (resultado != null) {
                            move.setObjetivo(new Kinematic(resultado.getX() * 32, resultado.getY() * 32));
                            update(move.getSteering(), 0.09, delta);
                        }
                    } else {
                        update(move.getSteering(), 0.09, delta);
                    }
                }
            } else if (rank == Rank.HEAVY) {
                Boolean target = false;
                Kinematic aux = null;
                double minLife = Double.MAX_VALUE;
                for (Kinematic p : targets) {
                    if (p.getLife()<= minLife && this.isInRadius(p,400)){
                        minLife = p.getLife();
                        aux = p;
                    }
                }
                if (aux!=null){
                    if (this.isInRadius(aux, 200) && !panic) {
                        if (aux.isDead() && faction==Faction.ALLY) world.foesBaseHealth -= 0.1;
                        else if (aux.isDead() && faction==Faction.FOE) world.alliesBaseHealth -= 0.1;
                        else aux.hurt(0.13);
                        disparando = true;
                        target = true;
                    } else if (this.isInRadius(aux, 400) && !panic) {
                        aimEnemy.setObjetivo(aux);
                        update(aimEnemy.getSteering(), 0.065, delta);
                        target = true;
                    }
                }
                if (target == false && externalPF != null && panic == false) {
                    System.out.println("MANDO PATHFINDING A:"+externalPF.getX()+","+externalPF.getY());
                    if (world.worldGraph[this.getTileX()][this.getTileY()] != null) {
                        List<Node> objetivos = new LinkedList<Node>();
                        objetivos.add(world.worldGraph[(int) externalPF.getX()][(int) externalPF.getY()]);
                        if(faction==Faction.ALLY) lrta = new LRTA_M(world.worldGraph[this.getTileX()][this.getTileY()], objetivos, world.worldGraph);
                        else lrta = new LRTA_M(world.worldGraph[this.getTileX()][this.getTileY()], objetivos, world.worldGraph);
                        Node resultado = lrta.LRTARun();
                        if (resultado != null) {
                            move2.setObjetivo(new Kinematic(resultado.getX() * 32, resultado.getY() * 32));
                            update(move2.getSteering(), 0.065, delta);
                        } else {
                            externalPF = null;
                        }
                    } else {
                        update(move.getSteering(), 0.065, delta);
                    }
                } else if (target == false && panic == false) {
                    if (world.worldGraph[this.getTileX()][this.getTileY()] != null) {
                        if ((faction == Faction.ALLY && !defenseMode())||(faction==Faction.FOE && defenseMode())) {
                            lrta = new LRTA_M(world.worldGraph[this.getTileX()][this.getTileY()], baseSith, world.worldGraph);
                        } else {
                            lrta = new LRTA_M(world.worldGraph[this.getTileX()][this.getTileY()], baseJedi, world.worldGraph);
                        }
                        Node resultado = lrta.LRTARun();
                        if (resultado != null) {
                            move2.setObjetivo(new Kinematic(resultado.getX() * 32, resultado.getY() * 32));
                            update(move2.getSteering(), 0.065, delta);
                        }
                    } else {
                        update(move2.getSteering(), 0.065, delta);
                    }
                }
            } else if (rank == Rank.SWARM) {
                Boolean targetAux = false;
                for (Kinematic p : targets) {
                    if (this.isInRadius(p, 200)) {
                        if (p.isDead() && faction==Faction.ALLY) world.foesBaseHealth -= 0.1;
                        else if (p.isDead() && faction==Faction.FOE) world.alliesBaseHealth -= 0.1;
                        else p.hurt(0.06);
                        disparando = true;
                        targetAux = true;
                        break;
                    } else if (this.isInRadius(p, 400)) {
                        aimEnemy.setObjetivo(p);
                        update(aimEnemy.getSteering(), 0.09, delta);
                        targetAux = true;
                        break;
                    }
                }
                if (!targetAux) {
                    if (this.getTarget() == null) {
                        //System.out.println("EMPIEZA EL IF CON TARGET NULL");
                    }
                    slotArrive.setObjetivo(target);
                    if (target != this.getTarget()) {
                        //System.out.println("Objetivos distintos");
                    }
                    SteeringA steeringAux = slotArrive.getSteering();
                    update(steeringAux, 0.09, delta);
                }

            } else if (rank == Rank.SCOUT) {
                Boolean target = false;
                Kinematic aux = null;
                double minLife = Double.MAX_VALUE;
                for (Kinematic p : targets) {
                    if (p.getLife()<= minLife && this.isInRadius(p,400)){
                        minLife = p.getLife();
                        aux = p;
                    }
                }
                if (aux!=null){
                    if (this.isInRadius(aux, 200) && !panic) {
                        if (aux.isDead() && faction==Faction.ALLY) world.foesBaseHealth -= 0.1;
                        else if (aux.isDead() && faction==Faction.FOE) world.alliesBaseHealth -= 0.1;
                        else aux.hurt(0.06);
                        disparando = true;
                        target = true;
                    } else if (this.isInRadius(aux, 400) && !panic) {
                        aimEnemy.setObjetivo(aux);
                        update(aimEnemy.getSteering(), 0.1, delta);
                        target = true;
                    }
                }
                if (target == false && externalPF != null && panic == false) {
                    System.out.println("MANDO PATHFINDING A:"+externalPF.getX()+","+externalPF.getY());
                    if (world.worldGraph[this.getTileX()][this.getTileY()] != null) {
                        List<Node> objetivos = new LinkedList<Node>();
                        objetivos.add(world.worldGraph[(int) externalPF.getX()][(int) externalPF.getY()]);
                        if(faction==Faction.ALLY) lrta = new LRTA_M(world.graph_AGAE_A[this.getTileX()][this.getTileY()], objetivos, world.worldGraph);
                        else lrta = new LRTA_M(world.graph_AGAE_E[this.getTileX()][this.getTileY()], objetivos, world.worldGraph);
                        Node resultado = lrta.LRTARun();
                        if (resultado != null) {
                            move.setObjetivo(new Kinematic(resultado.getX() * 32, resultado.getY() * 32));
                            update(move.getSteering(), 0.1, delta);
                        } else {
                            externalPF = null;
                        }
                    } else {
                        update(move.getSteering(), 0.1, delta);
                    }
                } else if (target == false && panic == false) {
                    if (world.worldGraph[this.getTileX()][this.getTileY()] != null) {
                        if ((faction == Faction.ALLY && !defenseMode())||(faction==Faction.FOE && defenseMode())) {
                            lrta = new LRTA_M(world.graph_AGAE_A[this.getTileX()][this.getTileY()], baseSith, world.graph_AGAE_A);
                        } else {
                            lrta = new LRTA_M(world.graph_AGAE_E[this.getTileX()][this.getTileY()], baseJedi, world.graph_AGAE_E);
                        }
                        Node resultado = lrta.LRTARun();
                        if (resultado != null) {
                            move.setObjetivo(new Kinematic(resultado.getX() * 32, resultado.getY() * 32));
                            update(move.getSteering(), 0.1, delta);
                        }
                    } else {
                        update(move.getSteering(), 0.1, delta);
                    }
                }
                if (panic) {
                    if (world.worldGraph[this.getTileX()][this.getTileY()] != null) {
                        if ((faction == Faction.ALLY && !defenseMode())||(faction==Faction.FOE && defenseMode())) {
                            lrta = new LRTA_M(world.graph_AGAE_A[this.getTileX()][this.getTileY()], baseJedi, world.graph_AGAE_A);
                        } else {
                            lrta = new LRTA_M(world.graph_AGAE_E[this.getTileX()][this.getTileY()], baseSith, world.graph_AGAE_E);
                        }
                        Node resultado = lrta.LRTARun();
                        if (resultado != null) {
                            move.setObjetivo(new Kinematic(resultado.getX() * 32, resultado.getY() * 32));
                            update(move.getSteering(), 0.12, delta);
                        }
                    } else {
                        update(move.getSteering(), 0.12, delta);
                    }
                }
            } else if (rank == Rank.BIRD) {
                update(swarm.getSteering(), 0.1, delta);
            } else if (rank == Rank.PATROL) {
                Boolean target = false;
                Kinematic aux = null;
                double minLife = Double.MAX_VALUE;
                for (Kinematic p : targets) {
                    if (p.getLife()<= minLife && this.isInRadius(p,400)){
                        minLife = p.getLife();
                        aux = p;
                    }
                }
                if (aux!=null){
                    if (this.isInRadius(aux, 200) && !panic) {
                        aux.hurt(0.06);
                        disparando = true;
                        target = true;
                    } else if (this.isInRadius(aux, 400) && !panic) {
                        aimEnemy.setObjetivo(aux);
                        update(aimEnemy.getSteering(), 0.1, delta);
                        target = true;
                    }
                }
                if (!target) {
                    update(patrol.getSteering(), 0.065, delta);
                }
            } else if (rank == Rank.GHOST) {
                System.out.println("Posicion anclaje "+this.getPosicion().getX()+","+this.getPosicion().getY());
                if (world.worldGraph[this.getTileX()][this.getTileY()] != null) {
                   if ((faction == Faction.ALLY && !defenseMode())||(faction==Faction.FOE && defenseMode())) {
                            lrta = new LRTA_M(world.worldGraph[this.getTileX()][this.getTileY()], baseSith, world.worldGraph);
                        } else {
                            lrta = new LRTA_M(world.worldGraph[this.getTileX()][this.getTileY()], baseJedi, world.worldGraph);
                        } 
                        
                    Node resultado = lrta.LRTARun();
                    if (resultado != null) {
                        move.setObjetivo(new Kinematic(resultado.getX()*32 , resultado.getY()*32));
                        //formation.updateSlots(this);
                        update(move.getSteering(), 0.08, delta);
                        
                    }
                } else {
                   // formation.updateSlots(this);
                    update(move.getSteering(), 0.08, delta);
                    
                }
                // HEALERS
            } else if (rank == Rank.HEALER) {
                Boolean target = false;
                double minLife = Double.MAX_VALUE;
                Kinematic aux = null;           
                for (Kinematic p : targets) {
                    if (p.getLife()<= minLife && this.isInRadius(p,400)){
                        minLife = p.getLife();
                        aux = p;
                    }
                }
                if (aux!=null){
                    if (this.isInRadius(aux, 200) && aux.life<100) {
                        aux.heal(0.01);
                        disparando = true;
                        target = true;
                    } else if (this.isInRadius(aux, 400) && aux.life<100) {
                        aimEnemy.setObjetivo(aux);
                        update(aimEnemy.getSteering(), 0.1, delta);
                        target = true;
                    }
                }
                
                if (target == false && externalPF != null && panic == false) {
                    if (world.worldGraph[this.getTileX()][this.getTileY()] != null) {
                        List<Node> objetivos = new LinkedList<Node>();
                        objetivos.add(world.worldGraph[(int) externalPF.getX()][(int) externalPF.getY()]);
                        if(faction == Faction.ALLY) lrta = new LRTA_M(world.graph_AFAE_A[this.getTileX()][this.getTileY()], objetivos, world.worldGraph);
                        else lrta = new LRTA_M(world.graph_AFAE_A[this.getTileX()][this.getTileY()], objetivos, world.worldGraph);
                        Node resultado = lrta.LRTARun();
                        if (resultado != null) {
                            move.setObjetivo(new Kinematic(resultado.getX() * 32, resultado.getY() * 32));
                            update(move.getSteering(), 0.09, delta);
                        } else {
                            externalPF = null;
                        }
                    } else {
                        update(move.getSteering(), 0.09, delta);
                    }
                } else if (target == false && panic == false) {
                    if (world.worldGraph[this.getTileX()][this.getTileY()] != null) {
                        if ((faction == Faction.ALLY && !defenseMode())||(faction==Faction.FOE && defenseMode())) {
                            lrta = new LRTA_M(world.graph_AFAE_A[this.getTileX()][this.getTileY()], baseSith, world.worldGraph);
                        } else {
                            lrta = new LRTA_M(world.graph_AFAE_A[this.getTileX()][this.getTileY()], baseJedi, world.worldGraph);
                        }
                        Node resultado = lrta.LRTARun();
                        if (resultado != null) {
                            move.setObjetivo(new Kinematic(resultado.getX() * 32, resultado.getY() * 32));
                            update(move.getSteering(), 0.09, delta);
                        }
                    } else {
                        update(move.getSteering(), 0.09, delta);
                    }
                }
                if (panic) {
                    if (world.worldGraph[this.getTileX()][this.getTileY()] != null) {
                        if ((faction == Faction.ALLY && !defenseMode())||(faction==Faction.FOE && defenseMode())) {
                            lrta = new LRTA_M(world.graph_AFAE_A[this.getTileX()][this.getTileY()], baseJedi, world.graph_AGAE_A);
                        } else {
                            lrta = new LRTA_M(world.graph_AFAE_A[this.getTileX()][this.getTileY()], baseSith, world.graph_AGAE_E);
                        }
                        Node resultado = lrta.LRTARun();
                        if (resultado != null) {
                            move.setObjetivo(new Kinematic(resultado.getX() * 32, resultado.getY() * 32));
                            update(move.getSteering(), 0.12, delta);
                        }
                    } else {
                        update(move.getSteering(), 0.12, delta);
                    }
                }
            }

            // Miramos si estamos disparando o no;
            if (disparando) {
                if (frames % 4 == 0) {
                    this.actualSheet = sprite.getSprite(0, 0);
                } else {
                    this.actualSheet = sprite.getSprite(1, 0);
                }
                frames--;
                if (frames < 0) {
                    frames = 60;
                }

            } else {
                if (rank != Rank.BIRD && rank != Rank.GHOST) {
                    this.actualSheet = sprite.getSprite(1, 0);
                }
            }
            if (rank != Rank.GHOST) {
                actualSheet.setRotation(orientationDegrees);
            }
            /*
             // Miramos si estamos conquistando la base enemiga
             if (faction == Faction.ALLY && isInEnemyBase() && world.FoesAlive <= 0) {
             world.FoesBaseHealth -= 0.1;
             } else if (faction == Faction.FOE && isInEnemyBase() && world.AlliesAlive <= 0) {
             world.AlliesBaseHealth -= 0.1;
             }*/

        } else {
            if(rank!=Rank.SWARM&&rank!=Rank.TURRET){
            respawnTime--;
            if (respawnTime == 1) {
                respawn();
            }
        }
        }
    }

    public void setVelocidad(Vector velocidad) {
        this.velocidad = velocidad;
    }

    public void setRotacion(double rotacion) {
        this.rotacion = rotacion;
    }

    public Vector getVelocidad() {
        return velocidad;
    }

    public double getRotacion() {
        return rotacion;
    }

    public Vector getPosicion() {
        return posicion;
    }

    public double getOrientacion() {
        return orientacion;
    }

    public Vector getVectorOrientacion() {
        // no funciona return new Vector(Math.sin(orientacion), Math.cos(orientacion));
        //return new Vector(Math.cos(orientacion), Math.sin(orientacion));
        return new Vector(Math.sin(orientacion), -Math.cos(orientacion));
        //return new Vector( Math.cos(orientacion), -Math.sin(orientacion));3
    }

    public void setPosicion(Vector posicion) {
        this.posicion = posicion;
    }

    public void setOrientacion(double orientacion) {
        this.orientacion = orientacion;
    }

    public void setPosicion(double x, double y) {
        posicion.setX(x);
        posicion.setY(y);
    }

    public double[][] asMatrix() {
        double[][] matrizRotacion = new double[2][2];
        matrizRotacion[0][0] = Math.cos(this.rotacion);
        matrizRotacion[0][1] = -(Math.sin(this.rotacion));
        matrizRotacion[1][0] = Math.sin(this.rotacion);
        matrizRotacion[1][1] = Math.cos(this.rotacion);

        return matrizRotacion;
    }

    public int getTileX() {
        return (int) Math.floor(posicion.getX() / 32);
    }

    public int getTileY() {
        return (int) Math.floor(posicion.getY() / 32);
    }

    /*
     public Vector2f entityCollisionWith() {
     for (int i = 0; i < BlockMap.entities.size(); i++) {
     Line entity1 = (Line) BlockMap.entities.get(i);
     if (playerPoly.intersects(entity1)) {
     return playerPoly.intersect(entity1);
     }
     }
     return null;
     }*/
    public Vector2f entityCollisionWith() {
        for (int i = 0; i < BlockMap.entities.size(); i++) {
            Block entity1 = (Block) BlockMap.entities.get(i);
            if (playerPoly.intersects(entity1.poly)) {
                // descompongo el cuadrado en lineas y vemos si intersecciona con alguna
                Line line1 = new Line(entity1.getX(), entity1.getY(), entity1.getX() + 32, entity1.getY());
                if (playerPoly.intersects(line1)) {
                    return playerPoly.intersect(line1);
                }
                Line line2 = new Line(entity1.getX(), entity1.getY(), entity1.getX(), entity1.getY() + 32);
                if (playerPoly.intersects(line2)) {
                    return playerPoly.intersect(line2);
                }
                Line line3 = new Line(entity1.getX() + 32, entity1.getY() + 32, entity1.getX() + 32, entity1.getY());
                if (playerPoly.intersects(line3)) {
                    return playerPoly.intersect(line3);
                }
                Line line4 = new Line(entity1.getX() + 32, entity1.getY() + 32, entity1.getX(), entity1.getY() + 32);
                if (playerPoly.intersects(line4)) {
                    return playerPoly.intersect(line4);
                }
            }
        }
        return null;
    }

    public Boolean getInNeighbourhood() {
        return inNeighbourhood;
    }

    public void setInNeighbourhood(Boolean inNeighbourhood) {
        this.inNeighbourhood = inNeighbourhood;
    }

    public Vector getVectorHeading() {
        double h = posicion.getModulo();
        double h1 = Math.cos(orientacion);
        double h2 = Math.sin(orientacion);
        return new Vector(h * h1, h * h2);
    }

    public Image getSprite() {
        return actualSheet;
    }

    public double getLife() {
        return life;
    }

    public void setLife(double life) {
        this.life = life;
    }

    public Boolean isInRadius(Kinematic target, double r) {
        if (this == target) {
            return false;
        }
        Vector distance = posicion.clone();
        distance.restar(target.getPosicion());
        if (Math.abs(distance.getModulo()) < r) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isInHome() {
        if (faction == Faction.ALLY && posicion.getX() < 2431
                && posicion.getX() > 1086 && posicion.getY() < 4216 && posicion.getY() > 3352) {
            return true;
        } else if (faction == Faction.FOE && posicion.getX() < 12416 && posicion.getX() > 11008
                && posicion.getY() < 9184 && posicion.getY() > 8416) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isInEnemyBase() {
        if (faction == Faction.FOE && posicion.getX() < 2431
                && posicion.getX() > 1086 && posicion.getY() < 4216 && posicion.getY() > 3352) {
            return true;
        } else if (faction == Faction.ALLY && posicion.getX() < 12416 && posicion.getX() > 11008
                && posicion.getY() < 9184 && posicion.getY() > 8416) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isDead() {
        return respawnTime > 0;
    }

    public Rank getRank() {
        return rank;
    }

    public void hurt(double d) {
        if (life > 0) {
            life -= d;
        } else {
            die();
        }
    }

    public void heal(double d) {
        if (life < 100) {
            life += d;
        }
    }

    //public void setSprite(String sprite) throws SlickException {
    ///    this.sprite = new Image(sprite);
    //}
    public Shape getPlayerPoly() {
        return playerPoly;
    }

    private void die() {
        respawnTime = 10400; //2min
        
        life = 100;
        if(rank==Rank.SWARM)  formation.removeCharacter(this);
     
        if (faction == Faction.FOE) {
            posicion.setX(369 * 32);
            posicion.setY(274 * 32);
            World.foesAlive--;
        } else {
            posicion.setX(1632);
            posicion.setY(3744);
            World.alliesAlive--;
        }
        
    }

    private void respawn() {
        if(rank!=Rank.SWARM && rank!=Rank.TURRET) {
        setLife(100);
        panic = false;
        respawnTime = 0;
        if (faction == Faction.ALLY){
            World.alliesAlive++;
        } else World.foesAlive++;
        }
    }
    public Kinematic getTarget() {
        return target;
    }

    public void setTarget(Kinematic target) {
        this.target = target;
    }

    public void select() {
        selected = true;
    }

    public void unselect() {
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setExternalPF(double x, double y) {
        externalPF = new Vector(x, y);
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public Boolean bersek() {
        return bersek != 1;
    }
    
    public Boolean defenseMode(){
        if (faction == Faction.ALLY) return World.alliesAlive<=10;
        if (faction == Faction.FOE) return World.foesAlive<=10;
        return false;
    }

    public void setRange(Rank range) {
        this.rank = range;
    }
}
