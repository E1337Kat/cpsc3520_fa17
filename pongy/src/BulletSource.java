import java.util.List;


/** This class represents a row of bullets.  It launches new bullets periodically by adding them to the bullet list
 */
public class BulletSource extends Entity
{
    private static float DEFAULT_BULLET_SPEED=.2f;

    private int y;              // vertical position
    private List<Bullet> destList; // entity list for spawned bullets
    private float bulletSpeed=DEFAULT_BULLET_SPEED; // how fast should the bullets go
    private float triggerTime;                      // how often should bullets be fired
    private float triggerElapsed;                   // timer offset
    private Paddle user;                            // user for bullets to intersect with
    private ScoreBoard score;                       // game state
    private int maxX;                               // screen width

    public BulletSource(int y, List<Bullet> entities, float triggerTime, float triggerStart,
                        Paddle user, ScoreBoard score, int maxX)
    {
        this.y = y;
        this.destList = entities;
        this.triggerTime = triggerTime;
        this.triggerElapsed = triggerStart;
        this.user = user;
        this.score = score;
        this.maxX=  maxX;
    }

    public void setBulletSpeed(float bs)
    {
        bulletSpeed = bs;
    }
    
    public void setTriggerTime(float tt)
    {
        triggerTime = tt;
    }
    
    // initiate new Bullet creation
    private void fire()
    {
        Bullet b = new Bullet(y, bulletSpeed, user, score, maxX, this);
        destList.add(b);
        AudioManager.getInstance().play("fire");
    }

    // callback for when bullet is finished with journey
    public void notify(int loc)
    {
        // update speed based on loc?
    }


    // decide whether or not to fire
    public void update(float delta)
    {
        if (triggerElapsed > triggerTime)
        {
            triggerElapsed -= triggerTime;

            fire();
        }
        
        
        triggerElapsed += delta;
    }

}
