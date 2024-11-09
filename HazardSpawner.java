//HazardSpawner.java
//Dennis Qi
//Responsible for spawning all of the objects that come from the side of the screen

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

  private boolean wantspawnfrog = false;
  private int frogspawnlane = 0;
  
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

  public int pickLoglane(){ //randomly pick a log lane to spawn frogs on it
    ArrayList<Integer> options = new ArrayList<Integer>();
    for(int i = 0; i<this.loglanes.length;i++){
      if(this.loglanes[i] == true){
        options.add(i);
      }
    }
    Random r = new Random();
    return options.get(r.nextInt(0, options.size()));
  }

  public void SpawnFrog(){ //set up the spawning of the frog
    this.wantspawnfrog = true;
    this.frogspawnlane = pickLoglane();
  }

  public void update(){
    Random r = new Random();
    int x_pos = -200;
    for(int i = 0; i < this.lanes.length; i++){//for every lane...
      if(this.timers[i] == 0){ //is it time to spawn a car?
        if(this.directions[i] == MotorVehicle.LEFT){ //check where to spawn it
          x_pos = 1000;
        }
        else if(this.directions[i] == MotorVehicle.RIGHT){
          x_pos = -300;
        }
        if(this.loglanes[i]){ //if it's a log lane, spawn a log and if the loglane satisfies the conditions then also spawn a frog
          manager.addLog(this.enemy_types[i], x_pos, (this.lanes[i] * 50 - 50), this.directions[i]);
          if(wantspawnfrog){
            if(i == frogspawnlane){
              manager.addFrog(this.enemy_types[i], x_pos, (this.lanes[i] * 50 - 50), this.directions[i]);
              wantspawnfrog = false;
            }
          }
        }
        else if(this.turtlanes[i]){ //if turtle lane, spawn turtle
          manager.addTurt(this.enemy_types[i], x_pos, (this.lanes[i] * 50 - 50), this.directions[i]);
        }
        else{ //else spawn a regular car
          manager.addMotorVehicle(this.enemy_types[i], x_pos, (this.lanes[i] * 50 - 50), this.directions[i]);
        }
        this.delay_positions[i] = Helper.advanceArray(this.delays[i], this.delay_positions[i]); //move up the list of delays
        if(this.loglanes[i]){ //logs have random delays
          this.timers[i] = (int)(this.delays[i][this.delay_positions[i]]*(1-r.nextDouble(0.7)));
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