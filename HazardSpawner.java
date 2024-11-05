import java.util.*;

class HazardSpawner{
  private int[] lanes;
  private int[] timers;
  private int[] delay_positions;
  private int[][] delays;
  private int[] enemy_types;
  private int[] directions;
  private HazardManager manager;
  private boolean[] loglanes;
  private boolean[] turtlanes;
  
  public HazardSpawner(int[] lanes, int[][] delays, int[] enemy_types, int[] directions, HazardManager manager, boolean[] logs, boolean[] turts){
    this.timers = new int[lanes.length];
    this.delay_positions = new int[lanes.length];
    this.delays = delays;
    this.lanes = lanes;
    this.enemy_types = enemy_types;
    this.directions = directions;
    this.manager = manager;
    this.loglanes = logs;
    this.turtlanes = turts;
  }

  public int[] getCarLanes(){return this.lanes;}

  public void update(){
    Random r = new Random();
    int x_pos = -200;
    for(int i = 0; i < this.lanes.length; i++){
      if(this.timers[i] == 0){
        if(this.directions[i] == MotorVehicle.LEFT){
          x_pos = 1000;
        }
        else if(this.directions[i] == MotorVehicle.RIGHT){
          x_pos = -300;
        }
        if(this.loglanes[i]){
          manager.addLog(this.enemy_types[i], x_pos, (this.lanes[i] * 50 - 50), this.directions[i]);
        }
        else if(this.turtlanes[i]){
          manager.addTurt(this.enemy_types[i], x_pos, (this.lanes[i] * 50 - 50), this.directions[i]);
        }
        else{
          manager.addMotorVehicle(this.enemy_types[i], x_pos, (this.lanes[i] * 50 - 50), this.directions[i]);
        }
        this.delay_positions[i] = Helper.advanceArray(this.delays[i], this.delay_positions[i]);
        if(this.loglanes[i]){
          this.timers[i] = (int)(this.delays[i][this.delay_positions[i]]*(1-r.nextDouble(0.5)));
        }
        else{
          this.timers[i] = this.delays[i][this.delay_positions[i]];
        }
      }
      else{
        this.timers[i] -= 1;
      }
    }
  }
}