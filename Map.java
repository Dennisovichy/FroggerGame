
class Map{
  private int[] lanes = new int[12];
  private HazardManager car_manager;
  private HazardSpawner car_spawner;

  public Map(int waterlanes, HazardManager manage, HazardSpawner spawner){
    for(int i = waterlanes; i < lanes.length; i++){
      this.lanes[i] = 1;
    }
    for(int lane: spawner.getCarLanes()){
        this.lanes[lane] = 2;
    }
    this.car_manager = manage;
    this.car_spawner = spawner;
  }

  public void updateMap(){
    this.car_manager.moveMotorVehicles();
    this.car_spawner.update();
  }

  public void drawMap(){

  }
}