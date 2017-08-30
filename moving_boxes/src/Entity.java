import java.awt.Rectangle;
import org.lwjgl.opengl.GL11;

class Entity
{
    private Rectangle box;

    public Entity()
    {
        box = new Rectangle(10, 10, 10, 10);
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

    public void update()
    {
        // look for key presses

        // if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        // {
        //     x++;
        // }

        // if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        // {
        //     x--;
        // }

        // if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
        // {
        //     y++;
        // }

        // if (Keyboard.isKeyDown(Keyboard.KEY_UP))
        // {
        //     y--;
        // }

        
    }



    


}
