import java.util.Random;
import org.lwjgl.util.Rectangle;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Display;

class E_Ground extends E_Entity
{
    private static enum State  { START, LEFT, RIGHT };

    private E_Player player;
    private Rectangle box;
    private State state;
    
    //private float speed;        // pixels / ms

    public E_Ground(int place, E_Player player)
    {
        this.player = player;
        Random rand = new Random();
        int heck = (int)(10*rand.nextFloat());
        System.out.println("Ground entity generated with rand: " + heck);
        box = new Rectangle(
                            place*(heck+600), 
                            Display.getHeight()+77, 
                            500, 
                            100);
        state = State.START;
        //this.speed = speed;
    }

    public void draw()
    {
        float x = (float)box.getX();
        float y = (float)box.getY();
        float w = (float)box.getWidth();
        float h = (float)box.getWidth();


        // draw the square
            
        GL11.glColor3f(0,1,0);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x+w, y);
        GL11.glVertex2f(x+w, y+w);
        GL11.glVertex2f(x, y+w);

        GL11.glEnd();

    }

    @Override
    public void update(float delta)
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            box.translate((int)(.50*delta), 0);
            
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            box.translate((int)(-.50*delta), 0);
        }
        
        
    }
    
    @Override 
    public boolean intersects(Rectangle other) {
        return box.intersects(other);
    }
    


}
