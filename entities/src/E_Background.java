/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author ellie
 */
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Rectangle;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class E_Background extends E_Entity {
    
    //protected static enum State  { LEVEL, JUMPING, FALLING };
    private static enum Direction { LEFT, RIGHT };
    
    private int maxJumpHeight;
    private Rectangle box;
    private Texture back;
    //private State state = State.FALLING;
    private Direction dir = Direction.RIGHT;
    private int jumpTime;
    private boolean moving;

    public E_Background(int pos) {

        try {
            back =
                TextureLoader.getTexture("PNG",
                                         ResourceLoader.getResourceAsStream("res/desert.png"));
            box =  new Rectangle(   
                                    pos*back.getImageWidth(),
                                    0,
                                    back.getImageWidth(), 
                                    Display.getHeight()
                                );
            
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void draw() {

        
        float x=(float)box.getX();
        float y=(float)box.getY();
        float w=(float)box.getWidth();
        float h=(float)box.getHeight();

        // draw this rectangle using the loaded sprite
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, back.getTextureID());
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
    
    @Override
    public void update(float delta) {
        
        //if (state == State.LEVEL) {
        
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            dir = Direction.RIGHT;
            box.translate((int)(.10*delta), 0);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            dir = Direction.LEFT;
            box.translate((int)(-.10*delta), 0);
            
        }
        
            
            /*
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) &&
                    (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || 
                        Keyboard.isKeyDown(Keyboard.KEY_LEFT))) {
                moving = true;
                jumpTime = 10;
                state = State.JUMPING;
            }
            */
        
    }
    
    //@Override
    public int getX () {
        return box.getX();
    }
    
    //@Override
    public int getY () {
        return box.getY();
    }
}

