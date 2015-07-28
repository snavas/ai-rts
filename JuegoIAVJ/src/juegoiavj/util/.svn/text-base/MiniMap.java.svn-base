/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoiavj.util;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Samuel
 */
public class MiniMap {
/*
    private static MiniMap instance;

    public static MiniMap getInstance() {
        if (instance == null) {
            instance = new MiniMap();
        }
        return instance;
    }
    private static final int WIDTH = 128;
    private static final int HEIGHT = 128;
    private Image miniMap;
    private Graphics mapGraphics;

    private MiniMap() {
        // Singleton
    }

    public void init() {
        // creates the minimap image
        // renders the map on the minimap white/black

        // 1. create miniMap Image with mapWidth/mapHeight
        MapContentManager m = MapContentManager.getInstance();
        int mapWidth = m.getMapWidth();
        int mapHeight = m.getMapHeight();
        try {
            miniMap = new Image(mapWidth, mapHeight);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        // 2. fill image pixels with cell-walkable attributes

        try {
            mapGraphics = miniMap.getGraphics();
        } catch (SlickException e) {
            e.printStackTrace();
        }

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                Cell cell = m.getCell(new TilePosition(x, y));
                if (cell.isWall()) {
                    Color.gray.a = 0.6f;
                    mapGraphics.setColor(Color.gray);
                } else {
                    Color.darkGray.a = 0.6f;
                    mapGraphics.setColor(Color.darkGray);
                }
                mapGraphics.drawRect(x, y, 1, 1);
            }
        }

        mapGraphics.flush();

        Color.gray.a = 1f;
        Color.darkGray.a = 1f;

        // miniMap = miniMap.getScaledCopy(4f);

    }

    public void render(Graphics g) {

        int x = 0;
        int y = Start.SCREENHEIGHT / 2 - this.HEIGHT / 2;

        PositionComponent p = Player.getInstance().getPositionComponent();
        int px = (int) p.x;
        int py = (int) p.y;

        int tileW = MapRenderer.getInstance().getDisplayWidthInTiles() * 4;

        // DRAW MINIMAP

        Image sub = miniMap.getSubImage(px - tileW / 2, py - tileW / 2, tileW,
                tileW).getScaledCopy(WIDTH, HEIGHT);
        sub.draw(x, y);

        // DRAW PLAYER LOCATION

        g.setColor(Color.white);
        int size = 3;
        g.fillRect(x + WIDTH / 2 - size / 2, y + WIDTH / 2 - size / 2, size,
                size);

        // TODO fog of war
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int getWidth() {
        return WIDTH;
    }*/
}