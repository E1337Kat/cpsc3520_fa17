import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

class MB_Entity
{
    private static enum State  { START, LEFT, RIGHT, DOWN, UP };

    private Rectangle box;
    private State state;
    private float speed;        // pixels/ms

    public MB_Entity(float speed)
    {
        box = new Rectangle(10, 10, 10, 10);
        state = State.START;
        this.speed = speed;
    }

    public void draw()
    {
        float x = (float)box.getX();
        float y = (float)box.getY();
        float w = (float)box.getWidth();
        float h = (float)box.getWidth();


        // draw the square
            
        // pick color
        switch (state) {
            case START:
            state = State.RIGHT;
            GL11.glColor3f(0, 1, 0);
        case RIGHT:
            GL11.glColor3f(1, 0, 0);
            break;
        case DOWN:
            GL11.glColor3f(0, 0, 1);
            break;
        case LEFT:
            GL11.glColor3f(1, 0, 1);
            break;
        case UP:
            GL11.glColor3f(0, 1, 0);
            break;
        }
        
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x+w, y);
        GL11.glVertex2f(x+w, y+w);
        GL11.glVertex2f(x, y+w);

        GL11.glEnd();

    }

    public void update(int delta)
    {
        switch (state)
        {
        case START:
            state = State.LEFT;
        case RIGHT:
            box.translate((int)(speed*delta), 0);
            if (box.getX() >= 790) {
                state = State.UP;
            }
            break;
        case DOWN:
            box.translate(0, (int)(speed*delta));
            if (box.getY() >= 590) {
                state = State.RIGHT;
            }
            break;
        case LEFT:
            box.translate((int)(-speed*delta), 0);
            if (box.getX() <= 0) {
                state = State.DOWN;
            }
            break;
        case UP:
            box.translate(0, (int)(-speed*delta));
            if (box.getY() <= 0) {
                state = State.LEFT;
            }
            break;
        }
        
    }



    


}
