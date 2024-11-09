import java.awt.*;
import java.util.Random;
import javax.swing.*;

class MotorVehicle{
  private static Image[] car_images = {new ImageIcon("car1.png").getImage(),new ImageIcon("car2.png").getImage(),new ImageIcon("car3.png").getImage(),new ImageIcon("car4.png").getImage()};
  private static Image[] car_imagesL = {new ImageIcon("car1L.png").getImage(),new ImageIcon("car2L.png").getImage(),new ImageIcon("car3L.png").getImage(),new ImageIcon("car4L.png").getImage()};
  private static int[][] car_sizes = {{100,50},{100,50},{100,50},{100,50}};
  private static int[] car_speeds = {5,20,1,10};

  public static int RIGHT = 1;
  public static int LEFT = 0;

  private static Image[] log_images = {new ImageIcon("log.png").getImage(),new ImageIcon("log.png").getImage(),new ImageIcon("log.png").getImage()};
  private static int[][] log_sizes = {{300, 50},{200, 50},{100, 50}};
  private static int[] log_speeds = {2,4,7};

  private static Image[] turtle_images = {new ImageIcon("turtles.png").getImage()};
  private static Image[] turtleright_images = {new ImageIcon("turtlesright.png").getImage()};
  private static int[][] turt_sizes = {{200,50}};
  private static int[] turt_speeds = {3};
  private static int[] turt_dive_delays = {200};
  private static int[] turt_rise_delays = {50};
  
  private int car_type;
  private Rectangle hitbox;
  private Image image;
  private Image aboutdiveimage;
  private int speed;
  private int direction;
  private int x;
  private int y;

  private boolean is_log;
  private boolean is_turtle = false;
  private boolean is_frog = false;
  private int dive_counter;
  private int rise_counter;
  private int ticker;
  private boolean is_above = true;
  
  public MotorVehicle(int car_type, int x, int y, int direction){
    this.car_type = car_type;
    if(direction == RIGHT){
      this.image = car_images[car_type].getScaledInstance(car_sizes[car_type][0], car_sizes[car_type][1], Image.SCALE_DEFAULT);
    }
    else{
      this.image = car_imagesL[car_type].getScaledInstance(car_sizes[car_type][0], car_sizes[car_type][1], Image.SCALE_DEFAULT);
    }
    this.speed = car_speeds[car_type];
    this.x = x;
    this.y = y;
    this.direction = direction;
    this.hitbox = new Rectangle(x,y,car_sizes[car_type][0], car_sizes[car_type][1]);
    this.is_log = false;
  }

  public MotorVehicle(int car_type, int x, int y, int direction, String log_status){
    Random r = new Random();
    this.car_type = car_type;
    int select = r.nextInt(0, this.log_images.length);
    this.image = log_images[select].getScaledInstance(log_sizes[select][0], log_sizes[select][1], Image.SCALE_DEFAULT);
    this.speed = log_speeds[car_type];
    this.x = x;
    this.y = y;
    this.direction = direction;
    this.hitbox = new Rectangle(x,y,log_sizes[select][0], log_sizes[select][1]);
    this.is_log = true;
  }

  public MotorVehicle(int car_type, int x, int y, int direction, boolean frog_status){
    Random r = new Random();
    this.car_type = car_type;
    this.image = new ImageIcon("poorfrog.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
    this.speed = log_speeds[car_type];
    this.x = x+10;
    this.y = y;
    this.direction = direction;
    this.hitbox = new Rectangle(this.x,this.y,30, 30);
    this.is_log = false;
    this.is_frog = true;
  }

  public MotorVehicle(int car_type, int x, int y, int direction, String log_status, String turtle_status){
    this.car_type = car_type;
    if(direction == MotorVehicle.RIGHT){
      this.image = turtleright_images[car_type].getScaledInstance(turt_sizes[car_type][0], turt_sizes[car_type][1], Image.SCALE_DEFAULT);
    }
    else{
      this.image = turtle_images[car_type].getScaledInstance(turt_sizes[car_type][0], turt_sizes[car_type][1], Image.SCALE_DEFAULT);
    }
    this.aboutdiveimage = new ImageIcon("halfturtle.png").getImage();
    this.speed = turt_speeds[car_type];
    this.x = x;
    this.y = y;
    this.direction = direction;
    this.hitbox = new Rectangle(x,y,turt_sizes[car_type][0], turt_sizes[car_type][1]);
    this.is_log = true;
    this.is_turtle = true;
    this.dive_counter = turt_dive_delays[car_type];
    this.rise_counter = turt_rise_delays[car_type];
    Random r = new Random();
    this.ticker = (int)Math.round(r.nextDouble()*this.dive_counter);
  }

  public boolean isLog(){return this.is_log;}
  public boolean isTurtle(){return this.is_turtle;}
  public boolean isFrog(){return this.is_frog;}
  public int getSpeed(){
    if(this.direction == LEFT){
      return -this.speed;
    }
    return this.speed;
  }
  
  public Image getImage(){return this.image;}
  public int getX(){return this.x;}
  public int getY(){return this.y;}
  public Rectangle getHitbox(){return this.hitbox;}
  
  public void move(){
    if(this.direction == RIGHT){
      this.x += this.speed;
      this.hitbox.x += this.speed;
    }
    else if (this.direction == LEFT){
      this.x -= this.speed;
      this.hitbox.x -= this.speed;
    }
    if(this.is_turtle){
      if(this.is_above){
        if(this.ticker>= this.dive_counter){
          this.is_above = false;
          this.is_log = false;
          this.ticker = 0;
        }
      }
      else{
        if(this.ticker >= this.rise_counter){
          this.is_above = true;
          this.is_log = true;
          this.ticker = 0;
        }
      }
      this.ticker++;
    }
  }
  
  public boolean checkBounds(){
    if(this.direction == RIGHT){
      if(this.x > 900){
        return true;
      }
    }
    else if (this.direction == LEFT){
      if(this.x < -400){
        return true;
      }
    }
    return false;
  }
  
  public void draw(Graphics g){
    if(this.is_turtle){
      if(this.is_above){
        if(this.dive_counter-this.ticker<100){
          g.drawImage(this.aboutdiveimage, this.x, this.y, null);
        }
        else{
          g.drawImage(this.image, this.x, this.y, null);
        }
      }
      else{
        if(this.rise_counter-this.ticker<20){
          g.drawImage(this.aboutdiveimage, this.x, this.y, null);
        }
      }
    }
    else{
      g.drawImage(this.image, this.x, this.y, null);
      //Graphics2D g2 = (Graphics2D)g;
      //g2.draw(this.hitbox);
    }
  }
  
}