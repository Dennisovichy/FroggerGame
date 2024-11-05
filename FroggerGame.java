import java.awt.*;
import java.awt.event.*;
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
 Timer timer;
 Image back;
 private Frog ball; 
 private HazardManager car_manager;
 private HazardSpawner car_spawner;
 private Map map1;
 private Level level1;
 public int lives;
 public int score;
 
 public GamePanel(){
  /* Java graphics started with a slow Internet in mind. There was a simple
   * getImage command, but it did not load the image right away to avoid freezing
   * the web page. There are a few ways to force the image to be loaded immediately, 
   * This is the way I prefer.
   */
  back = new ImageIcon("background.png").getImage();
  keys = new boolean[KeyEvent.KEY_LAST+1];
  ball = new Frog();
  car_manager = new HazardManager();
  
  int[] lanes = {2,3,5,7};
  int[][] delays = {{300},{120},{80}, {20,60}};
  int[] types = {0,0,0, 1};
  int[] directions = {MotorVehicle.RIGHT,MotorVehicle.LEFT,MotorVehicle.LEFT, MotorVehicle.RIGHT};
  boolean[] logs = {true, false,false, false};
  boolean[] turts = {false,true,false,false};
  car_spawner = new HazardSpawner(lanes, delays, types, directions, car_manager, logs, turts);
  car_manager.addMotorVehicle(0, 0, 300, MotorVehicle.RIGHT);
  car_manager.addMotorVehicle(1, 700, 150, MotorVehicle.LEFT);
  lives = 5;
  score = 0;
  int[] data_go = {lives,score};

  map1 = new Map(4,car_manager, car_spawner);
  level1 = new Level(map1, data_go);
  
  setPreferredSize(new Dimension(800, 750));
  setFocusable(true);
  requestFocus();
  addKeyListener(this);
  addMouseListener(this);
  timer = new Timer(20, this);
  timer.start();
 }

 public void move(){
  if(screen == INTRO){
   
  }
  else if(screen == GAME){
   level1.update(keys, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
   System.out.println(level1.getLives());
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
   g.setColor(new Color(137,196,234));
   g.fillRect(0,0,getWidth(), getHeight());     
  }
  else if(screen == GAME){
   // The last parameter is an ImageObserver. Back when images were not loaded
   // right away you would specify what object would be notified when it was loaded.
   // We are not doing that, so null will always be fine.
   level1.draw(g);
   //Graphics2D g2 = (Graphics2D)g;
   //g2.draw(ball.getHitbox());
   //System.out.println(car_manager.getMotorVehicle(0).getHitbox().height);
  }
    }
}