import org.lwjgl.util.Rectangle;
import org.lwjgl.opengl.GL11;

public class Bullet extends Entity {

    private int y;
    private float speed;
    private Paddle user;
    private ScoreBoard score;
    private int distance;
    private Rectangle box;
    private boolean deleteMe=false;
    private boolean goRight=true;
    private BulletSource daddy;
    private int hitLoc;

    private static int WIDTH=10;
    private static int HEIGHT=10;

    public Bullet(int y, float speed, Paddle user, ScoreBoard score, int distance, BulletSource daddy)
    {
        this.y = y;
        this.speed = speed;
        this.user = user;
        this.score = score;
        this.distance = distance;
        this.daddy = daddy;

        box = new Rectangle(0, this.y, WIDTH, HEIGHT);

    }

    public void update(float delta)
    {
        if (goRight)
        {

            box.translate((int)(delta*speed), 0);

            if (box.getX() >= distance)
            {
                score.registerHit();
                deleteMe=true;
            }

            if (user.intersects(box))
            {
                AudioManager.getInstance().play("bounce");
                hitLoc = box.getX();
                goRight=false;
            }
        }
        else
        {
            box.translate((int)(delta*speed*-1), 0);

            if (box.getX() < 0)
            {
                daddy.notify(hitLoc);
                deleteMe=true;
            }
        }
        

        
        
    }


    public boolean done()
    {
        return deleteMe;        
    }
    

    public void draw()
    {
        float x=(float)box.getX();
        float y=(float)box.getY();
        float width = (float)box.getWidth();
        float height = (float)box.getHeight();

        // draw this rectangle using no sprite
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL11.glColor3f(0, 1,0);

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x+width, y);
        GL11.glVertex2f(x+width, y+height);
        GL11.glVertex2f(x, y+height);

        GL11.glEnd();

    }


}
