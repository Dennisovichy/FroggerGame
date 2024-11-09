//CollisionChecker.java
//Dennis Qi
//Static methods for collision detection between player, cars, logs and water

import java.awt.*;
import java.util.*;

class CollisionChecker{ //class to handle collision checks
  public static boolean checkCollision(Frog frog, HazardManager hazard_list){
    Rectangle frogbox = frog.getSmallbox(); //get the hitbox of frog
    for(int i = 0; i < hazard_list.getNumMotorVehicle(); i++){ //for every car
      if(frogbox.intersects(hazard_list.getMotorVehicle(i).getHitbox())){ //if intersect
        if(hazard_list.getMotorVehicle(i).isLog() == false && hazard_list.getMotorVehicle(i).isTurtle() == false && hazard_list.getMotorVehicle(i).isFrog() == false){ //if it is a car
          System.out.println("You have died");//return true
          return true;
        }
      }
    }
    return false;
  }
  public static boolean checkRescue(Frog frog, HazardManager hazard_list){ //the same as checkCollision, but checks for collision with frogs instead
    Rectangle frogbox = frog.getHitbox();
    for(int i = 0; i < hazard_list.getNumMotorVehicle(); i++){
      if(hazard_list.getMotorVehicle(i).isFrog()){
        if(frogbox.intersects(hazard_list.getMotorVehicle(i).getHitbox())){
          System.out.println("Awooga");
          hazard_list.killMotorVehicle(i); //delete the frog
          frog.setEnraged(); //make frog into extra points mode
          return true;
        }
      }
    }
    return false;
  }
  public static boolean checkDrown(Frog frog, Map layout, HazardManager hazard_list){ //check if frog drowns
    if(frog.isJumping()){ //only drown if not jumping thru air
      return false;
    }

    Rectangle frogbox = frog.getSmallbox(); //if the frog is on a log, then return false
    for(int i = 0; i < hazard_list.getNumMotorVehicle(); i++){
      if(hazard_list.getMotorVehicle(i).isLog() == true){
        if(frogbox.intersects(hazard_list.getMotorVehicle(i).getHitbox())){
          frog.moveLog(hazard_list.getMotorVehicle(i).getSpeed()); //move the frog as well
          return false;
        }
      }
    }
    
    if(frog.getY() <= (layout.getWater()*50-50)){ //above the waterline: drown
      return true;
    }
    
    return false;
  }
  public static boolean checkSafe(Frog frog, ArrayList<Cave> caves, Level leveling){ //checks if the frog has reached the safe zone
    if(frog.isJumping()){
      return false;
    }
    Rectangle frogbox = frog.getSmallbox();
    for(int i = 0; i<5; i++){ //for every cave, check if intersect. additionally check if extra point conditions are met, and award those points.
      if(!caves.get(i).getOccupied() && caves.get(i).gator_present==false){
        if(frogbox.intersects(caves.get(i).getHitbox())){
          leveling.addScore(300);
          if(caves.get(i).fly_present){
            leveling.addScore(200);
            caves.get(i).fly_present = false;
          }
          if(frog.isEnraged()){
            leveling.addScore(200);
          }
          System.out.println("Safe");
          caves.get(i).setOccupied();
          return true;
        }
      }
    }
    return false;
  }
}