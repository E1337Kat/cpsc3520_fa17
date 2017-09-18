import org.lwjgl.util.Rectangle;

class Entity
{
    public void update(float delta)
    {
        
    }


    public void draw()
    {
        
    }
    
    // override this if you want to be able to see if your entity interacts
    // with another rectangle
    public boolean intersects(Rectangle other)
    {
        return false;
    }

}
