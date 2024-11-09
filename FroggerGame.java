import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class FroggerGame extends JFrame{
 GamePanel game= new GamePanel();
  
  public FroggerGame() {
    super("Frogger");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(game);
    pack();
    setVisible(true);
  }
    
  public static void main(String[] arguments) {
    new FroggerGame();  
  }
}

class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener{
 public static final int INTRO=0, GAME=1, END=2, TRANSITION=3;
 private int screen = INTRO;
 private Image title = new ImageIcon("title.png").getImage();
 private Image transition = new ImageIcon("transition.png").getImage();
 private Image gameover = new ImageIcon("gameover.png").getImage();
 public static final int lanes_wide = 16;
 public static final int lanes_tall = 15;
 
 private boolean []keys;
 javax.swing.Timer timer;
 Image back;
 Image frog_icon;
 private Frog ball; 
 private HazardManager car_manager;
 private int levelnum = 0;
 private Level level1;
 private ArrayList<Map> maps;
 public int lives;
 public int score;
 public int accum_score;
 File fntFile = new File("VCR_OCD_MONO_1.001.ttf");
 Font score_font = new Font("Comic Sans MS", Font.PLAIN, 30);
 
 public GamePanel(){
  /* Java graphics started with a slow Internet in mind. There was a simple
   * getImage command, but it did not load the image right away to avoid freezing
   * the web page. There are a few ways to force the image to be loaded immediately, 
   * This is the way I prefer.
   */
  back = new ImageIcon("background.png").getImage();
  frog_icon = new ImageIcon("frog_real.png").getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
  keys = new boolean[KeyEvent.KEY_LAST+1];
  ball = new Frog();
  car_manager = new HazardManager();
  
  //initialize all of the levels
  int[] lanes = {2,3,4,6,7,10};
  int[][] delays = {{300},{120},{300},{80}, {20,60},{50}};
  int[] types = {0,0,0,0, 1,0};
  int[] directions = {MotorVehicle.RIGHT,MotorVehicle.RIGHT,MotorVehicle.LEFT,MotorVehicle.LEFT, MotorVehicle.RIGHT, MotorVehicle.LEFT};
  boolean[] logs = {true, false,true,false, false,false};
  boolean[] turts = {false,true,false,false,false,false};
  HazardSpawner car_spawner = new HazardSpawner(lanes, delays, types, directions, car_manager, logs, turts);
  Map map1 = new Map(4,car_manager, car_spawner,false);

  int[] lanes2 = {2,3,4,5,7,8,9,11};
  int[][] delays2 = {{100},{120},{300},{300},{60},{60},{200},{20,20,20,20,20,300}};
  int[] types2 = {0,1,2,0, 0,0,1,3};
  int[] directions2 = {MotorVehicle.RIGHT,MotorVehicle.LEFT,MotorVehicle.LEFT, MotorVehicle.RIGHT,MotorVehicle.RIGHT,MotorVehicle.LEFT,MotorVehicle.LEFT, MotorVehicle.RIGHT};
  boolean[] logs2 = {false, true,true,true,false,false,false,false};
  boolean[] turts2 = {true,false,false,false,false,false,false,false};
  HazardSpawner car_spawner2 = new HazardSpawner(lanes2, delays2, types2, directions2, car_manager, logs2, turts2);
  Map map2 = new Map(5,car_manager, car_spawner2,true);

  int[] lanes3 = {2,3,4,5,6,7,9,10,11,12};
  int[][] delays3 = {{300},{120},{400},{80}, {130},{200},{200},{200},{70},{100}};
  int[] types3 = {0,0,0,0,0,0,1,2,3,0};
  int[] directions3 = {MotorVehicle.RIGHT,MotorVehicle.LEFT,MotorVehicle.LEFT,MotorVehicle.LEFT, MotorVehicle.RIGHT, MotorVehicle.LEFT,MotorVehicle.LEFT,MotorVehicle.LEFT, MotorVehicle.RIGHT, MotorVehicle.LEFT};
  boolean[] logs3 = {true, false,true,false, false,true,false,false,false,false};
  boolean[] turts3 = {false,true,false,true,true,false,false,false,false,false};
  HazardSpawner car_spawner3 = new HazardSpawner(lanes3, delays3, types3, directions3, car_manager, logs3, turts3);
  Map map3 = new Map(7,car_manager, car_spawner3,true);
  lives = 5;
  score = 0;
  accum_score = 0;
  int[] data_go = {lives,score, accum_score}; //this is just stupid. array is for transferring data between levels

  maps = new ArrayList<Map>();
  maps.add(map1);
  maps.add(map2);
  maps.add(map3);
  level1 = new Level(maps.get(levelnum), data_go); //level1 is the current level, not specifically level1.
  
  setPreferredSize(new Dimension(800, 780));
  setFocusable(true);
  requestFocus();
  addKeyListener(this);
  addMouseListener(this);
  timer = new javax.swing.Timer(20, this);
  timer.start();
 }

 public void move(){
  if(screen == INTRO){
   
  }
  else if(screen == GAME){
   level1.update(keys, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
   if(level1.checkDead()){ //if die
    int[] data_go = {5,0,0}; //reset datas
    levelnum = 0; //reset map
    level1 = new Level(maps.get(levelnum),data_go);
    level1.clearLevel();
    screen = END;
   }
   if(level1.checkClear()){
    int[] data_go = {level1.getLives(),level1.getScore(),level1.getAccumScore()}; //get the data from the previous level
    levelnum++;
    level1.clearHazards();
    if(levelnum==maps.size()){ //if the map list is out, end the game
      int[] data_new = {5,0,0};
      levelnum = 0;
      level1 = new Level(maps.get(levelnum),data_new);
      level1.clearLevel();
      screen = END;
    }
    else{ //else, go to the next level
      level1 = new Level(maps.get(levelnum),data_go);
      screen = TRANSITION;
    }
   }
  }

 }
 
 @Override
 public void actionPerformed(ActionEvent e){
  move(); 
  repaint(); 

 }
 
 @Override
 public void keyReleased(KeyEvent ke){
  int key = ke.getKeyCode();
  keys[key] = false;
 } 
 
 @Override
 public void keyPressed(KeyEvent ke){
  int key = ke.getKeyCode();
  keys[key] = true;
 }
 
 @Override
 public void keyTyped(KeyEvent ke){}
 @Override
 public void mouseClicked(MouseEvent e){}

 @Override
 public void mouseEntered(MouseEvent e){}

 @Override
 public void mouseExited(MouseEvent e){}

 @Override
 public void mousePressed(MouseEvent e){
  if(screen == INTRO){
   screen = GAME;
  } 
  if(screen == TRANSITION){
   screen = GAME;
  } 
  if(screen == END){
   screen = GAME;
  } 
 }

 @Override
 public void mouseReleased(MouseEvent e){}

 @Override
 public void paint(Graphics g){
  if(screen == INTRO){
   g.drawImage(title, 0, 0, null);   
  }
  else if(screen == TRANSITION){
    g.drawImage(transition,0,0,null);
  }
  else if(screen == END){
    g.drawImage(gameover,0,0,null);
  }
  else if(screen == GAME){
  
   level1.draw(g); //draw map and player
   g.setColor(new Color(0,0,0));
   g.fillRect(0, 750, 9000, 100); //draw black bar at the bottom
   for(int i = 0; i<level1.getLives(); i++){ //draw how many lives you have
    g.drawImage(frog_icon, 50+i*40, 752, null);
   }
   g.setColor(new Color(255,255,255));
   g.setFont(score_font); //draw score
   g.drawString("Score:"+level1.getScore(), 600, 775);
   level1.draw_bar(g); //draw the timer that shows how much time you have left
   
  }
    }
}