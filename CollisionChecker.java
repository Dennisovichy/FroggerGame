import java.awt.*;

class CollisionChecker{
  public static boolean checkCollision(Frog frog, HazardManager hazard_list){
    Rectangle frogbox = frog.getHitbox();
    for(int i = 0; i < hazard_list.getNumMotorVehicle(); i++){
      if(frogbox.intersects(hazard_list.getMotorVehicle(i).getHitbox())){
        if(hazard_list.getMotorVehicle(i).isLog() == false){
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

    Rectangle frogbox = frog.getHitbox();
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
}