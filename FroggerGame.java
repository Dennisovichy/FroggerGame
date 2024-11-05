import java.awt.*;
import java.awt.event.*;
import java.util.*;
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
 public static final int INTRO=0, GAME=1, END=2;
 private int screen = INTRO;
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
  
  int[] lanes = {2,3,4,5,7};
  int[][] delays = {{300},{120},{400},{80}, {20,60}};
  int[] types = {0,0,0,0, 1};
  int[] directions = {MotorVehicle.RIGHT,MotorVehicle.LEFT,MotorVehicle.LEFT,MotorVehicle.LEFT, MotorVehicle.RIGHT};
  boolean[] logs = {true, false,true,false, false};
  boolean[] turts = {false,true,false,false,false};
  HazardSpawner car_spawner = new HazardSpawner(lanes, delays, types, directions, car_manager, logs, turts);
  Map map1 = new Map(4,car_manager, car_spawner);

  int[] lanes2 = {2,3,4,5,7};
  int[][] delays2 = {{300},{120},{400},{80}, {20,60}};
  int[] types2 = {0,0,0,0, 1};
  int[] directions2 = {MotorVehicle.RIGHT,MotorVehicle.LEFT,MotorVehicle.LEFT,MotorVehicle.LEFT, MotorVehicle.RIGHT};
  boolean[] logs2 = {true, false,true,false, false};
  boolean[] turts2 = {false,true,false,false,false};
  HazardSpawner car_spawner2 = new HazardSpawner(lanes2, delays2, types2, directions2, car_manager, logs2, turts2);
  Map map2 = new Map(4,car_manager, car_spawner2);

  int[] lanes3 = {2,3,4,5,7};
  int[][] delays3 = {{300},{120},{400},{80}, {20,60}};
  int[] types3 = {0,0,0,0, 1};
  int[] directions3 = {MotorVehicle.RIGHT,MotorVehicle.LEFT,MotorVehicle.LEFT,MotorVehicle.LEFT, MotorVehicle.RIGHT};
  boolean[] logs3 = {true, false,true,false, false};
  boolean[] turts3 = {false,true,false,false,false};
  HazardSpawner car_spawner3 = new HazardSpawner(lanes3, delays3, types3, directions3, car_manager, logs3, turts3);
  Map map3 = new Map(4,car_manager, car_spawner3);
  lives = 5;
  score = 0;
  int[] data_go = {lives,score};

  maps = new ArrayList<Map>();
  maps.add(map1);
  maps.add(map2);
  maps.add(map3);
  level1 = new Level(maps.get(levelnum), data_go);
  
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
   if(level1.checkClear()){
    int[] data_go = {level1.getLives(),level1.getScore()};
    levelnum++;
    level1.clearHazards();
    level1 = new Level(maps.get(levelnum),data_go);
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
 }

 @Override
 public void mouseReleased(MouseEvent e){}

 @Override
 public void paint(Graphics g){
  if(screen == INTRO){
   g.setColor(new Color(0,0,0));
   g.fillRect(0,0,getWidth(), getHeight());     
  }
  else if(screen == GAME){
   // The last parameter is an ImageObserver. Back when images were not loaded
   // right away you would specify what object would be notified when it was loaded.
   // We are not doing that, so null will always be fine.
   level1.draw(g);
   g.setColor(new Color(0,0,0));
   g.fillRect(0, 750, 9000, 100);
   for(int i = 0; i<level1.getLives(); i++){
    g.drawImage(frog_icon, 50+i*40, 752, null);
   }
   //Graphics2D g2 = (Graphics2D)g;
   //g2.draw(ball.getHitbox());
   //System.out.println(car_manager.getMotorVehicle(0).getHitbox().height);
  }
    }
}