import java.awt.*;
import java.util.Random;
import javax.swing.*;

class MotorVehicle{
  private static Image[] car_images = {new ImageIcon("test_car.jpg").getImage(),new ImageIcon("test_car2.jpg").getImage()};
  private static int[][] car_sizes = {{100,50},{100,50}};
  private static int[] car_speeds = {5,8};

  public static int RIGHT = 1;
  public static int LEFT = 0;

  private static Image[] log_images = {new ImageIcon("log.jpg").getImage()};
  private static int[][] log_sizes = {{300, 50}};
  private static int[] log_speeds = {2};

  private static Image[] turtle_images = {new ImageIcon("turt.jpg").getImage()};
  private static int[][] turt_sizes = {{200,50}};
  private static int[] turt_speeds = {3};
  private static int[] turt_dive_delays = {200};
  private static int[] turt_rise_delays = {50};
  
  private int car_type;
  private Rectangle hitbox;
  private Image image;
  private int speed;
  private int direction;
  private int x;
  private int y;

  private boolean is_log;
  private boolean is_turtle = false;
  private int dive_counter;
  private int rise_counter;
  private int ticker;
  private boolean is_above = true;
  
  public MotorVehicle(int car_type, int x, int y, int direction){
    this.car_type = car_type;
    this.image = car_images[car_type].getScaledInstance(car_sizes[car_type][0], car_sizes[car_type][1], Image.SCALE_DEFAULT);
    this.speed = car_speeds[car_type];
    this.x = x;
    this.y = y;
    this.direction = direction;
    this.hitbox = new Rectangle(x,y,car_sizes[car_type][0], car_sizes[car_type][1]);
    this.is_log = false;
  }

  public MotorVehicle(int car_type, int x, int y, int direction, String log_status){
    this.car_type = car_type;
    this.image = log_images[car_type].getScaledInstance(log_sizes[car_type][0], log_sizes[car_type][1], Image.SCALE_DEFAULT);
    this.speed = log_speeds[car_type];
    this.x = x;
    this.y = y;
    this.direction = direction;
    this.hitbox = new Rectangle(x,y,log_sizes[car_type][0], log_sizes[car_type][1]);
    this.is_log = true;
  }

  public MotorVehicle(int car_type, int x, int y, int direction, String log_status, String turtle_status){
    this.car_type = car_type;
    this.image = turtle_images[car_type].getScaledInstance(turt_sizes[car_type][0], turt_sizes[car_type][1], Image.SCALE_DEFAULT);
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
      if(this.x < -200){
        return true;
      }
    }
    return false;
  }
  
  public void draw(Graphics g){
    g.drawImage(this.image, this.x, this.y, null);
    Graphics2D g2 = (Graphics2D)g;
    g2.draw(this.hitbox);
  }
  
}