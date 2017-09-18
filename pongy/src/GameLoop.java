import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.LWJGLException;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;



public class GameLoop
{
    public static final int TARGET_FPS=100;
    public static final int SCR_WIDTH=800;
    public static final int SCR_HEIGHT=600;
    public static final int NUM_BULLETS=10;

    public static void main(String[] args) throws LWJGLException, java.io.IOException
    {
        initGL(SCR_WIDTH, SCR_HEIGHT);

        Mouse.setGrabbed(true);
        AudioManager audio = AudioManager.getInstance();

        audio.loadSample("bounce", "res/bounce.wav");
        audio.loadSample("fire", "res/shoot.wav");
        audio.loadSample("score", "res/end.wav");
        audio.loadLoop("ambience", "res/loop.ogg");


        List<BulletSource> sources = new LinkedList<>();
        List<Bullet> bullets = new LinkedList<>();
        
        Paddle player = new Paddle();
        ScoreBoard score = new ScoreBoard();

        for (int i=0; i<NUM_BULLETS; i++)
        {
            sources.add(new BulletSource(Display.getHeight()/NUM_BULLETS*i+20,
                                         bullets,
                                         5000, i * 300+2000, player, score, Display.getWidth()
                            ));
        }
        
        audio.play("ambience");


        long time = (Sys.getTime()*1000)/Sys.getTimerResolution(); // ms
        while (! Display.isCloseRequested())
        {
            long time2 = (Sys.getTime()*1000)/
                Sys.getTimerResolution(); // ms
            float delta = (float)(time2-time);
            // System.out.println(delta);

            player.update(delta);

            for  (BulletSource bs : sources)
            {
                bs.update(delta);
            }

            for  (Bullet b : bullets)
            {
                b.update(delta);
            }


            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            player.draw();
            for (Bullet b : bullets)
            {
                b.draw();
            }

            for (Iterator<Bullet> bi = bullets.iterator();
                 bi.hasNext(); )
            {
                Bullet b = bi.next();

                if (b.done())
                {
                    System.out.println("deleting bullet");
                    bi.remove();
                }
            }


            // UPDATE DISPLAY
            Display.update();
            Display.sync(TARGET_FPS);
            time = time2;

            audio.update();
        }

        audio.destroy();
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
