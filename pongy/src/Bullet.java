import org.lwjgl.util.Rectangle;
import org.lwjgl.opengl.GL11;

/** This class represents a single bullet moving across the screen and
 * potentially bouncng off the user's paddle */
public class Bullet extends Entity {

    private int y;              // vertical position
    private float speed;        // pixels per ms
    private Paddle user;        // paddle for intersection tests
    private ScoreBoard score;   // game state
    private int distance;       // screen width

    private Rectangle box;

    private boolean deleteMe=false; // this entity should be removed
    private boolean goRight=true;   // state machine
    private BulletSource daddy;     // to notify the launcher about changes to the bullet
    private int hitLoc;             // x position when paddle intersects

    private static int WIDTH=10; // bullet dimension
    private static int HEIGHT=10;

    public Bullet(int y, float speed, Paddle user, ScoreBoard score, int distance, BulletSource daddy)
    {
        this.y = y;             // vertical position
        this.speed = speed;     // pixels per ms
        this.user = user;       // paddle
        this.score = score;
        this.distance = distance;
        this.daddy = daddy;

        box = new Rectangle(0, this.y, WIDTH, HEIGHT);

    }

    public void update(float delta)
    {
        
        // state machine 
        if (goRight)
        {
            // translate right, check for intersection with paddle or screen boundary

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
                speed *= 2;
                goRight=false;
            }
        }
        else
        {
            // translate left, check for screen boundary
            box.translate((int)(delta*speed*-1), 0);

            if (box.getX() < 0)
            {
                // tell launcher about bounce when we return to the far left
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
