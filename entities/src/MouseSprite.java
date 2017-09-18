import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class MouseSprite extends E_Entity
{
    enum State { JUMPING, FALLING, LEVEL };

    private Rectangle box;
    private Texture sprite;
    private State state;
    private int jumpTime;

    public MouseSprite(float width)
    {

        try
        {

            sprite =
                TextureLoader.getTexture("PNG",
                                         ResourceLoader.getResourceAsStream("res/duck.png"));
        

            box =  new Rectangle(0,0,
                                 (int)width, 
                                 (int)(width * sprite.getImageHeight() / sprite.getImageWidth()));

            state = State.FALLING;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

    public void update(float delta)
    {
        if (state == State.FALLING)
        {
            box.translate(0, (int)(.5 * delta));


            if (box.getY() + box.getHeight() > Display.getHeight())
            {
                box.setY(Display.getHeight() - box.getHeight());
                state = State.LEVEL;
            }
        }

        if (state == State.LEVEL)
        {

            if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
            {
                box.translate((int)(-.50*delta), 0);
            
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
            {
                box.translate((int)(.50*delta), 0);
            }


            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
            {
                jumpTime=10;
                state = State.JUMPING;
            }
        }

        if (state == State.JUMPING)
        {
            if (jumpTime <= 0)
            {
                state = State.FALLING;
            }
            
            jumpTime--;
            box.translate(0, (int)(-.5 * delta));
        }
    }


    public void draw()
    {

        float x=(float)box.getX();
        float y=(float)box.getY();
        float w=(float)box.getWidth();
        float h=(float)box.getHeight();

        // draw this rectangle using the loaded sprite
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.getTextureID());
        GL11.glColor3f(1, 1,1);

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0,0);
        GL11.glVertex2f(x, y);

        GL11.glTexCoord2f(1,0);
        GL11.glVertex2f(x+w, y);

        GL11.glTexCoord2f(1,1);
        GL11.glVertex2f(x+w, y+h);

        GL11.glTexCoord2f(0,1);
        GL11.glVertex2f(x, y+h);

        GL11.glEnd();

        // unbind the sprite so that other objects can be drawn
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

    }


}
