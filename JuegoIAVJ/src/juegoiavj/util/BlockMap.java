/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.util;

import java.util.ArrayList;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

/**
 *
 * @author Samuel
 */
public class BlockMap implements TileBasedMap {

    public static TiledMap tmap;
    public static int mapWidth;
    public static int mapHeight;
    private int square[] = {1, 1, 15, 1, 15, 15, 1, 15}; //square shaped tile
    public static ArrayList<Object> entities;
    public SpriteSheet sheet;

    public BlockMap(String ref) throws SlickException {
        entities = new ArrayList<Object>();
        tmap = new TiledMap(ref, "Material/mapas");
        //sheet = new SpriteSheet("Material/mapas/texturas.png", 32, 32);
        mapWidth = tmap.getWidth() * tmap.getTileWidth();
        mapHeight = tmap.getHeight() * tmap.getTileHeight();
        /*
         for (int x = 0; x < tmap.getWidth(); x++) {
         for (int y = 0; y < tmap.getHeight(); y++) {
         int tileID = tmap.getTileId(x, y, 0);
         if (tileID == 1 || tileID == 3 || tileID == 5) {
         entities.add(new Line(x*32,y*32,x*32,y*32+32));
         entities.add(new Line(x*32,y*32,x*32+32,y*32));
         entities.add(new Line(x*32+32,y*32+32,x*32,y*32+32));
         entities.add(new Line(x*32+32,y*32+32,x*32+32,y*32));
         }
         }
         }*/

        for (int x = 0; x < tmap.getWidth(); x++) {
            for (int y = 0; y < tmap.getHeight(); y++) {
                int tileID = tmap.getTileId(x, y, 0);
                if (tileID == 3 || tileID == 5) {
                    entities.add(
                            new Block(x * 32, y * 32, square, "square"));

                }
            }
        }
    }

    public void renderedLine(int visualY, int mapY, int layer) {
    }

    @Override
    public int getWidthInTiles() {
        return tmap.getWidth();
    }

    @Override
    public int getHeightInTiles() {
        return tmap.getHeight();
    }

    @Override
    public void pathFinderVisited(int x, int y) {
        // NO HACE FALTA IMPLEMENTAR, SOLO SE USA PARA DEBUG
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean blocked(PathFindingContext context, int tx, int ty) {
        int tileID = tmap.getTileId(tx, ty, 0);
        if (tileID == 3 || tileID == 5) {
            return true;
        }
        return false;
    }

    @Override
    public float getCost(PathFindingContext context, int tx, int ty) {
        int tileID = tmap.getTileId(tx, ty, 0);
        if (tileID == 1 || tileID == 2) {
            return 20;
        }
        if (tileID == 4) {
            return 10;
        } else {
            return Float.MAX_VALUE;
        }
    }
}