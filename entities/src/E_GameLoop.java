import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import org.lwjgl.input.Keyboard;
import org.lwjgl.LWJGLException;

import java.util.List;
import java.util.LinkedList;



public class E_GameLoop
{
    public static final int TARGET_FPS=100;
    public static final int SCR_WIDTH=800;
    public static final int SCR_HEIGHT=600;
    
    private static E_Background background;
    private static E_Player player;

    public static void main(String[] args) throws LWJGLException
    {
        initGL(SCR_WIDTH, SCR_HEIGHT);

        // Load Background
        List<E_Background> tiles = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            tiles.add(new E_Background(i));
        }
        
        // Load Ground tiles
        List<E_Entity> ground = new LinkedList<>();
        for (int i = 0; i < 10; i++ ) {
            ground.add (new E_Ground(i, player));
        }
        
        // Load Player
        player = new E_Player(100);


        int x1 = player.getX();
        int y1 = player.getY();
        
        long time = (Sys.getTime()*1000)/Sys.getTimerResolution(); // ms
        while (! Display.isCloseRequested())
        {
            int x2 = player.getX();
            int y2 = player.getY();
            long time2 = (Sys.getTime()*1000)/
                Sys.getTimerResolution(); // ms
            float delta = (float)(time2-time);
            // System.out.println(delta);

            // Update the background tiles
            for (E_Background b : tiles) {
                b.update(delta);
            }
            
            // Update the Player
            player.update(delta);
            
            if ( x2 != x1 ) {
                System.out.println("Player x: " + player.getX());

            }
            if ( y2 != y1 ) {
                System.out.println("Player y: " + player.getY());
            }
            
            // Update the other entities
            for  (E_Entity e : ground) {
                e.update(delta);
            }


            Display.update();
            int translate_y = player.getY() - Display.getHeight() / 2;
            int translate_x = player.getX() + Display.getWidth() / 2;
            Display.sync(TARGET_FPS);
            
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            // 50% Parallax scrolling
            // Pushes current matrix used for screen operations, then
            // translates it by half. 
            GL11.glPushMatrix();
            GL11.glTranslatef(translate_x / 2, -translate_y / 2, 0);
            
            // Draw the background
            for (E_Background b : tiles) {
                b.draw();
            }
            GL11.glPopMatrix();
            // END 50% Parallax
            
            // Can add additional stuff between matrix stuff as needed.
            
            // 100% Parallax scrolling
            // 
            GL11.glPushMatrix();
            GL11.glTranslatef(translate_x, -translate_y, 0);
            
            // Draw the Player
            player.draw();
            
            // Draw the other entities
            for  (E_Entity e : ground) {
                e.draw();
            }
            GL11.glPopMatrix();
            // END 100% Parallax


            // Update delta
            time = time2;
            x1 = x2;
            y1 = y2;
        }

        Display.destroy();
    }
    

    public static void initGL(int width, int height) throws LWJGLException
    {
        // open window of appropriate size
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.create();
        Display.setVSyncEnabled(true);
        
        // enable 2D textures
        GL11.glEnable(GL11.GL_TEXTURE_2D);              
     
        // set "clear" color to black
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);         

        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
         
        // set viewport to entire window
        GL11.glViewport(0,0,width,height);
         
        // set up orthographic projectionr
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        // GLU.gluPerspective(90f, 1.333f, 2f, -2f);
        // GL11.glTranslated(0, 0, -500);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
}
