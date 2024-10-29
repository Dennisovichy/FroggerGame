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
 
 private boolean []keys;
 Timer timer;
 Image back;
 private Frog ball; 
 private HazardManager car_manager;
 private HazardSpawner car_spawner;
 private MotorVehicle car;
 private MotorVehicle car2;
 
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
  
  
  int[] lanes = {3,7};
  int[][] delays = {{80}, {20,60}};
  int[] types = {0, 1};
  int[] directions = {MotorVehicle.LEFT, MotorVehicle.RIGHT};
  car_spawner = new HazardSpawner(lanes, delays, types, directions, car_manager);
  car_manager.addMotorVehicle(0, 0, 300, MotorVehicle.RIGHT);
  car_manager.addMotorVehicle(1, 700, 150, MotorVehicle.LEFT);
  
  setPreferredSize(new Dimension(800, 600));
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
   ball.move(keys, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
   car_manager.moveMotorVehicles();
   car_spawner.update();
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
   g.drawImage(back, 0, 0, null);
   ball.draw(g);
   //Graphics2D g2 = (Graphics2D)g;
   //g2.draw(ball.getHitbox());
   car_manager.drawMotorVehicles(g);
   CollisionChecker.checkCollision(ball, car_manager);
   //System.out.println(car_manager.getMotorVehicle(0).getHitbox().height);
  }
    }
}