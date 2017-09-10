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

    public static void main(String[] args) throws LWJGLException
    {
        initGL(SCR_WIDTH, SCR_HEIGHT);

        List<Entity> entities = new LinkedList<>();

        entities.add (new E_MouseSprite(200));
        entities.add (new E_StupidBox(.1f));
        entities.add (new E_StupidBox(.2f));


        long time = (Sys.getTime()*1000)/Sys.getTimerResolution(); // ms
        while (! Display.isCloseRequested())
        {
            long time2 = (Sys.getTime()*1000)/
                Sys.getTimerResolution(); // ms
            float delta = (float)(time2-time);
            // System.out.println(delta);

            for  (Entity e : entities)
            {
                e.update(delta);
            }


            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            for  (Entity e : entities)
            {
                e.draw();
            }


            // UPDATE DISPLAY
            Display.update();
            Display.sync(TARGET_FPS);
            time = time2;
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
