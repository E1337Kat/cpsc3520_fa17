
import org.lwjgl.util.Rectangle;



class E_Entity
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
    
    /**
     * Gets the x position of the entity. This method must be implemented/overriden.
     * @return x position
     */
    //public int getX() {return 0;}
    
    /**
     * Gets the y position of the entity. This method must be implemented/overriden.
     * @return y position
     */
    //public int getY() {return 0;}
}
