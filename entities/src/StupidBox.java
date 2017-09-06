import java.awt.Rectangle;
import org.lwjgl.opengl.GL11;

class StupidBox extends Entity
{
    private static enum State  { START, LEFT, RIGHT };

    private Rectangle box;
    private State state;
    private float speed;        // pixels / ms

    public StupidBox(float speed)
    {
        box = new Rectangle(10, 10, 10, 10);
        state = State.START;
        this.speed = speed;
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

    public void update(float delta)
    {
        switch (state)
        {
         case START:
             state = State.RIGHT;

         case RIGHT:

             box.translate((int)(speed*delta), 0);

             if (box.getX() >= 100)
             {
                 state = State.LEFT;
             }

             break;

         case LEFT:

             box.translate((int)(-speed*delta), 0);

             if (box.getX() <= 0)
             {
                 state = State.RIGHT;
             }
             
             break;             
        }
        
    }



    


}
