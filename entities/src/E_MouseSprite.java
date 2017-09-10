import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

public class E_MouseSprite extends E_Entity
{
    private Rectangle box;
    private Texture sprite;

    public E_MouseSprite(float width)
    {

        try
        {

            sprite =
                TextureLoader.getTexture("PNG",
                                         ResourceLoader.getResourceAsStream("res/ctanis.png"));
        

            box =  new Rectangle(0,0,
                                 (int)width, 
                                 (int)(width * sprite.getImageHeight() / sprite.getImageWidth()));

        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
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
