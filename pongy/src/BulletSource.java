import java.util.List;


public class BulletSource extends Entity
{
    private static float DEFAULT_BULLET_SPEED=.2f;

    private int y;
    private List<Bullet> destList;
    private float bulletSpeed=DEFAULT_BULLET_SPEED;
    private float triggerTime;
    private float triggerElapsed;
    private Paddle user;
    private ScoreBoard score;
    private int maxX;

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
    

    private void fire()
    {
        Bullet b = new Bullet(y, bulletSpeed, user, score, maxX, this);
        destList.add(b);
        AudioManager.getInstance().play("fire");
    }


    public void notify(int loc)
    {
        // update speed based on loc?
    }


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
