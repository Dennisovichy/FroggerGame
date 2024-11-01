import java.awt.*;
import java.util.Arrays;
import javax.swing.*;

class Map{
  private int[] lanes = new int[12];
  private int waterlanes;
  private HazardManager car_manager;
  private HazardSpawner car_spawner;
  private Image back = new ImageIcon("background.png").getImage();
  private Image vater_image = new ImageIcon("water.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
  private Image gras_image = new ImageIcon("gras.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
  private Image road_image = new ImageIcon("road.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);

  public Map(int waterlanes, HazardManager manage, HazardSpawner spawner){
    this.waterlanes = waterlanes;
    for(int i = waterlanes; i < lanes.length; i++){
      this.lanes[i] = 1;
    }
    for(int lane: spawner.getCarLanes()){
        this.lanes[lane-1] = 2;
    }
    this.car_manager = manage;
    this.car_spawner = spawner;
    System.out.println(Arrays.toString(this.lanes));
  }

  public HazardManager getHazards(){return this.car_manager;}
  public int getWater(){return this.waterlanes;}

  public void updateMap(){
    this.car_manager.moveMotorVehicles();
    this.car_spawner.update();
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
    car_manager.drawMotorVehicles(g);
  }
}