import java.awt.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

class Frog{
  private int x = 350;
  private int y = 700;
  
  private static final int UP = 1;
  private static final int LEFT = 2;
  private static final int DOWN = 3;
  private static final int RIGHT = 4;
  private static final double SPEED = 10;

  private int jump_points = 0;
  private int most_up = y;
  
  private Rectangle hitbox;
  private boolean enraged = false;
  private boolean jumping = false;
  private int traveled_x = 0;
  private int traveled_y = 0;
  private int direction = UP;
  private boolean[] holding_down = {true,true,true,true};

  private Clip jumpsound;
  
  Image frog_image = new ImageIcon("frog_real.png").getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
  Image final_image = frog_image;
  
  public Frog(){
    this.hitbox = new Rectangle(x,y,50,50);
    File sound = new File("jump.wav");
    try {
      this.jumpsound = AudioSystem.getClip();
      AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
      this.jumpsound.open(ais);
      this.jumpsound.start();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public Rectangle getHitbox(){return this.hitbox;}
  public Rectangle getSmallbox(){return new Rectangle(x+24,y+24,1,1);}
  public boolean isJumping(){return this.jumping;}
  public boolean isEnraged(){return this.enraged;}
  public void setEnraged(){this.enraged = true;}
  public int getPoints(){
    int temp = this.jump_points;
    this.jump_points = 0;
    return temp;
  }

  public int getX(){return this.x;}
  public int getY(){return this.y;}

  public void draw(Graphics g){
    g.drawImage(this.frog_image, this.x, this.y, null);
  }
  public void moveLog(int x){
    this.x += x;
    this.hitbox.x += x;
  }
  public boolean move(boolean[] keys, int left, int right, int up, int down){
    if (this.jumping){
        switch (this.direction) {
            case LEFT:
                this.x -= SPEED;
                this.hitbox.x -= SPEED;
                this.traveled_x += SPEED;
                break;
            case RIGHT:
                this.x += SPEED;
                this.hitbox.x += SPEED;
                this.traveled_x += SPEED;
                break;
            case UP:
                this.y -= SPEED;
                this.hitbox.y -= SPEED;
                this.traveled_y += SPEED;
                break;
            case DOWN:
                this.y += SPEED;
                this.hitbox.y += SPEED;
                this.traveled_y += SPEED;
                break;
            default:
                break;
        }
      if(this.traveled_x >= 50 || this.traveled_y >= 50){
        this.frog_image = Helper.rotateImage(frog_image, 90);
        this.jumping = false;
        this.traveled_x = 0;
        this.traveled_y = 0;
        this.jumpsound.stop();
        this.jumpsound.setFramePosition(0);
        if(this.y < this.most_up){
          this.most_up = this.y;
          this.jump_points += 10;
        }
      }
      return false;
    }
    if (keys[left]){
      if(!this.holding_down[0]){
        this.direction = LEFT;
        this.jumping = true;
        this.jumpsound.start();
      }
      this.holding_down[0] = true;
    }
    else{this.holding_down[0] = false;}
    if(keys[right]){
      if(!this.holding_down[1]){
        this.direction = RIGHT;
        this.jumping = true;
        this.jumpsound.start();
      }
      this.holding_down[1] = true;
    }
    else{this.holding_down[1] = false;}
    if (keys[up]){
      if(!this.holding_down[2]){
        this.direction = UP;
        this.jumping = true;
        this.jumpsound.start();
      }
      this.holding_down[2] = true;
    }
    else{this.holding_down[2] = false;}
    if(keys[down]){
      if(!this.holding_down[3]){
        this.direction = DOWN;
        this.jumping = true;
        this.jumpsound.start();
      }
      this.holding_down[3] = true;
    }
    else{this.holding_down[3] = false;}
    return true;
  }
}