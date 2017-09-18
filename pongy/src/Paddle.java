import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;


public class Paddle extends Entity
{
    private static final int STARTING_WIDTH=10;
    private static final int STARTING_HEIGHT=40;
    private static final float STARTING_SPEED=.005f;

    private Rectangle box;
    private float speed=STARTING_SPEED; // pixels per ms.
    
    public Paddle()
    {
        box = new Rectangle((Display.getWidth()-STARTING_WIDTH)/2,
                            (Display.getHeight()-STARTING_HEIGHT)/2,
                            STARTING_WIDTH, STARTING_HEIGHT);
    }


    // track the mouse
    public void update(float delta)
    {
        // mouse location
        int mx = Mouse.getX();
        int my = Display.getHeight() - Mouse.getY();
        

        // box center location
        int bx = box.getX() + box.getWidth()/2;
        int by = box.getY() + box.getHeight()/2;

        float dx = (mx - bx)*delta*speed;
        float dy = (my - by)*delta*speed;

        box.translate((int)dx, (int)dy);
        
    }


    public void draw()
    {
        float x=(float)box.getX();
        float y=(float)box.getY();
        float width = (float)box.getWidth();
        float height = (float)box.getHeight();

        // draw this rectangle using no sprite
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL11.glColor3f(1, 1,1);

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x+width, y);
        GL11.glVertex2f(x+width, y+height);
        GL11.glVertex2f(x, y+height);

        GL11.glEnd();
        
    }


    // override Entity method since we can answer this question
    public boolean intersects(Rectangle other)
    {
        return box.intersects(other);
    }

}
