import java.awt.*;
import javax.swing.*;

class CollisionChecker{
  public static boolean checkCollision(Frog frog, HazardManager hazard_list){
    Rectangle frogbox = frog.getHitbox();
    for(int i = 0; i < hazard_list.getNumMotorVehicle(); i++){
      if(frogbox.intersects(hazard_list.getMotorVehicle(i).getHitbox())){
        System.out.println("You have died");
      }
    }
    return false;
  }
}