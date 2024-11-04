import java.util.*;
import java.awt.*;
import javax.swing.*;

class HazardManager{
  private ArrayList<MotorVehicle> vehicles_list = new ArrayList<MotorVehicle>();
  
  public HazardManager(){
    
  }
  
  public int getNumMotorVehicle(){
    return vehicles_list.size();
  }
  
  public MotorVehicle getMotorVehicle(int pos){
    return vehicles_list.get(pos);
  }
  
  public void addMotorVehicle(int car_type, int x, int y, int direction){
    MotorVehicle car = new MotorVehicle(car_type, x, y, direction);
    vehicles_list.add(car);
  }

  public void addLog(int log_type, int x, int y, int direction){
    MotorVehicle log = new MotorVehicle(log_type, x, y, direction, "this is a log");
    vehicles_list.add(log);
  }

  public void addTurt(int log_type, int x, int y, int direction){
    MotorVehicle turt = new MotorVehicle(log_type, x, y, direction, "this is a log", "this is a turtle");
    vehicles_list.add(turt);
  }
  
  public void moveMotorVehicles(){
    for(int i = 0; i < vehicles_list.size(); i++){
      vehicles_list.get(i).move();
    }
    for(int x = 0; x < vehicles_list.size(); x++){
      if(vehicles_list.get(x).checkBounds()){
        vehicles_list.remove(x);
        x = 0;
      }
    }
  }
  
  public void drawMotorVehicles(Graphics g){
    for(int i = 0; i < vehicles_list.size(); i++){
      
      g.drawImage(vehicles_list.get(i).getImage(), vehicles_list.get(i).getX(), vehicles_list.get(i).getY(), null);
      g.setColor(Color.RED);
      //Graphics2D g2 = (Graphics2D)g;
      //g2.draw(vehicles_list.get(i).getHitbox());
    }
  }
}