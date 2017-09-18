import org.lwjgl.opengl.GL11;
import java.io.InputStream;
import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;
  

public class ScoreBoard extends Entity {

    private int point;
    private int hit;
    private TrueTypeFont font;

    public ScoreBoard()
    {
        try
        {
            //font=new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 12), false);
            InputStream input = ResourceLoader.getResourceAsStream("res/font.ttf");
            Font f=Font.createFont(Font.TRUETYPE_FONT, input);
            f = f.deriveFont(24f);
            font = new TrueTypeFont(f, false);            
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }


    public void registerPoint()
    {
        point++;
        AudioManager.getInstance().play("score");
    }


    public void registerHit()
    {
        hit++;
        System.out.println("score: " + point + " / " + hit);
        AudioManager.getInstance().play("hit");
    }

    // public void draw()
    // {
    //     // eventually we can put the score info on the screen somewhere
    //     // GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_FILL );
    //     // GL11.glEnable(GL11.GL_TEXTURE_2D);
    //     GL11.glColor3f(.5f,.5f,.5f);
    //     font.drawString(0, 0, "Score: " + point + "/" + hit, Color.red);
    //     // GL11.glDisable(GL11.GL_TEXTURE_2D);            

    // }
    

}
