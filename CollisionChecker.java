import java.awt.*;
import java.util.*;

class CollisionChecker{
  public static boolean checkCollision(Frog frog, HazardManager hazard_list){
    Rectangle frogbox = frog.getHitbox();
    for(int i = 0; i < hazard_list.getNumMotorVehicle(); i++){
      if(frogbox.intersects(hazard_list.getMotorVehicle(i).getHitbox())){
        if(hazard_list.getMotorVehicle(i).isLog() == false && hazard_list.getMotorVehicle(i).isTurtle() == false){
          System.out.println("You have died");
          return true;
        }
      }
    }
    return false;
  }
  public static boolean checkDrown(Frog frog, Map layout, HazardManager hazard_list){
    if(frog.isJumping()){
      return false;
    }

    Rectangle frogbox = frog.getSmallbox();
    for(int i = 0; i < hazard_list.getNumMotorVehicle(); i++){
      if(hazard_list.getMotorVehicle(i).isLog() == true){
        if(frogbox.intersects(hazard_list.getMotorVehicle(i).getHitbox())){
          System.out.println("On a log");
          frog.moveLog(hazard_list.getMotorVehicle(i).getSpeed());
          return false;
        }
      }
    }
    
    if(frog.getY() <= (layout.getWater()*50-50)){
      System.out.println("In water");
      return true;
    }
    
    return false;
  }
  public static boolean checkSafe(Frog frog, ArrayList<Cave> caves){
    if(frog.isJumping()){
      return false;
    }
    Rectangle frogbox = frog.getSmallbox();
    for(int i = 0; i<5; i++){
      if(!caves.get(i).getOccupied()){
        if(frogbox.intersects(caves.get(i).getHitbox())){
          System.out.println("Safe");
          caves.get(i).setOccupied();
          return true;
        }
      }
    }
    return false;
  }
}