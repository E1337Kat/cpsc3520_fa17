import java.util.HashSet;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

public class E_MouseSprite extends E_Entity {
    
    protected static enum State  { LEVEL, JUMPING, FALLING };
    private static enum Direction { LEFT, RIGHT };
    
    private Rectangle box;
    private Texture sprite;
    private State state = State.FALLING;
    private Direction dir = Direction.RIGHT;
    private int jumpTime;
    private boolean moving;

    public E_MouseSprite(float width) {

        try {
            sprite =
                TextureLoader.getTexture("PNG",
                                         ResourceLoader.getResourceAsStream("res/duck.png"));
            box =  new Rectangle(0,0,
                                 (int)width, 
                                 (int)(width * sprite.getImageHeight() / sprite.getImageWidth()));
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
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.getTextureID());
        GL11.glColor3f(1, 1,1);

        GL11.glBegin(GL11.GL_QUADS);

        if (dir == Direction.RIGHT) {
            GL11.glTexCoord2f(0,0);
            GL11.glVertex2f(x, y);

            GL11.glTexCoord2f(1,0);
            GL11.glVertex2f(x+w, y);

            GL11.glTexCoord2f(1,1);
            GL11.glVertex2f(x+w, y+h);

            GL11.glTexCoord2f(0,1);
            GL11.glVertex2f(x, y+h);
        } else {
            GL11.glTexCoord2f(0,0);
            GL11.glVertex2f(x, y);

            GL11.glTexCoord2f(1,0);
            GL11.glVertex2f(x+w, y);

            GL11.glTexCoord2f(1,1);
            GL11.glVertex2f(x+w, y+h);

            GL11.glTexCoord2f(0,1);
            GL11.glVertex2f(x, y+h);
        }

        GL11.glEnd();

        // unbind the sprite so that other objects can be drawn
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

    }
    
    @Override
    public void update(float delta) {
        
        if (state == State.LEVEL) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                dir = Direction.LEFT;
                box.translate((int)(-.50*delta), 0);
                if (box.getX() < 0) {
                        box.setX(0);
                    }
            }
            
            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                dir = Direction.RIGHT;
                box.translate((int)(.50*delta), 0);
                if (box.getX() + box.getWidth() > Display.getWidth()) {
                        box.setX(Display.getWidth() - box.getWidth());
                    }
            }
            
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && 
                    (!Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || 
                        !Keyboard.isKeyDown(Keyboard.KEY_LEFT))) {
                moving = false;
                jumpTime = 10;
                state = State.JUMPING;
            }
            
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) &&
                    (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || 
                        Keyboard.isKeyDown(Keyboard.KEY_LEFT))) {
                moving = true;
                jumpTime = 35;
                state = State.JUMPING;
            }
            
        }
        
        if (state == State.JUMPING) {
            if (jumpTime <= 0) {
                state = State.FALLING;
            }
            jumpTime--;
            if (moving) {
                if (dir == Direction.LEFT) {
                    for (double i=0; i < 0.50; i=i+0.1) {
                        box.translate((int)(-(i)*delta), (int)(-i*delta));
                        
                        if (box.getX() < 0) {
                            box.setX(0);
                            state = State.FALLING;
                        }
                    }
                }
                if (dir == Direction.RIGHT) {
                    for (double i=0; i < .50; i=i+0.1) {
                        box.translate((int)((i)*delta), (int)(-i*delta));
                        if (box.getX() + box.getWidth() > Display.getWidth()) {
                            box.setX(Display.getWidth() - box.getWidth());
                            state = State.FALLING;
                        }
                    }
                }
            } else {
                box.translate(0, (int)(-.50*delta));
            }
            
        }
        
        if (state == State.FALLING) {
            if (moving) {
                if (dir == Direction.LEFT) {
                    for (double i=0; i < 0.50; i=i+0.1) {
                        box.translate((int)(-i*delta), (int)(i*delta));
                        i=i+.01;
                        if (box.getX() < 0) {
                            box.setX(0);
                            state = State.FALLING;
                        }
                    }
                }
                if (dir == Direction.RIGHT) {
                    for (double i=0; i < .50; i=i+0.1) {
                        box.translate((int)(i*delta), (int)(i*delta));
                        i=i+.01;
                        if (box.getX() + box.getWidth() > Display.getWidth()) {
                            box.setX(Display.getWidth() - box.getWidth());
                            state = State.FALLING;
                        }
                    }
                }
            } else {
                box.translate(0, (int)(.50*delta));
            }
            if (box.getY() +box.getHeight() > Display.getHeight()) {
                box.setY(Display.getHeight() - box.getHeight());
                state = State.LEVEL;
            }
        }
    }


}
