import java.awt.*;
import java.util.*;
import javax.swing.*;

class Map{
  private int[] lanes = new int[15];
  private ArrayList<Cave> caves;
  private int waterlanes;
  private HazardManager car_manager;
  private HazardSpawner car_spawner;
  private Image back = new ImageIcon("background.png").getImage();
  private Image vater_image = new ImageIcon("water.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
  private Image gras_image = new ImageIcon("gras.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
  private Image road_image = new ImageIcon("road.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
  private Image cave_image = new ImageIcon("cave.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
  private Image frog_image = new ImageIcon("frog_real.png").getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);

  public Map(int waterlanes, HazardManager manage, HazardSpawner spawner){
    this.waterlanes = waterlanes;
    this.caves = new ArrayList<Cave>();
    for(int i = 0; i<5; i++){
      this.caves.add(new Cave(80+i*160));
    }
    for(int i = waterlanes; i < lanes.length; i++){
      this.lanes[i] = 1;
    }
    for(int lane: spawner.getCarLanes()){
      if(lane>waterlanes){
        this.lanes[lane-1] = 2;
      }
    }
    this.car_manager = manage;
    this.car_spawner = spawner;
    System.out.println(Arrays.toString(this.lanes));
  }

  public HazardManager getHazards(){return this.car_manager;}
  public ArrayList<Cave> getCaves(){return this.caves;}
  public int getWater(){return this.waterlanes;}

  public void updateMap(){
    this.car_manager.moveMotorVehicles();
    this.car_spawner.update();
  }

  public boolean checkCleared(){
    for(Cave cave: caves){
      if(!cave.getOccupied()){
        return false;
      }
    }
    return true;
  }

  public void drawMap(Graphics g){
    g.drawImage(this.back, 0, 0, null);
    for(int i = 0; i<this.lanes.length;i++){
      for(int x = 0; x<16; x++){
        if(this.lanes[i] == 0){
          g.drawImage(this.vater_image, x*50, i*50, null);
        }
        else if(this.lanes[i] == 1){
          g.drawImage(this.gras_image, x*50, i*50, null);
        }
        else if(this.lanes[i] == 2){
          g.drawImage(this.road_image, x*50, i*50, null);
        }
      }
    }
    for(int x = 0; x<5; x++){
      g.drawImage(cave_image, caves.get(x).getX(), caves.get(x).getY(), null);
      if(caves.get(x).getOccupied()){
        g.drawImage(frog_image, caves.get(x).getX(), caves.get(x).getY(), null);
      }
    }
    car_manager.drawMotorVehicles(g);
  }
}

class Cave{
  private boolean occupied = false;
  private Rectangle hitbox;
  private int x,y;

  public Cave(int x){
    this.x = x;
    this.y = 0;
    hitbox = new Rectangle(this.x+20, this.y+20, 10, 10);
  }

  public int getX(){return this.x;}
  public int getY(){return this.y;}
  public boolean getOccupied(){return this.occupied;}
  public void setOccupied(){this.occupied = true;}
  public Rectangle getHitbox(){return this.hitbox;}
}