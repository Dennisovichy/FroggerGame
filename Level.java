import java.awt.*;

class Level{
    private Map level_map;
    private Frog ball;
    private int lives;
    private int score;

    public Level(Map layout, int[] data){
        this.level_map = layout;
        this.ball = new Frog();
        this.lives = data[0];
        this.score = data[1];
    }

    public void update(boolean[] keys, int left, int right, int up, int down){
        this.ball.move(keys, left, right, up, down);
        this.level_map.updateMap();
        this.score += this.ball.getPoints();
        CollisionChecker.checkRescue(ball, level_map.getHazards());
        if(CollisionChecker.checkSafe(ball, this.level_map.getCaves(),this)){
            this.ball = new Frog();
        }
        if(CollisionChecker.checkDrown(ball, level_map, level_map.getHazards())){
            this.ball = new Frog();
            this.lives--;
        }
        if(CollisionChecker.checkCollision(this.ball, this.level_map.getHazards())){
            this.ball = new Frog();
            this.lives--;
        }
    }

    public boolean checkClear(){
        return this.level_map.checkCleared();
    }
    public int getLives(){return this.lives;}
    public int getScore(){return this.score;}
    public void addScore(int score){this.score += score;}
    public void clearHazards(){this.level_map.getHazards().clearMotorVehicles();}

    public void draw(Graphics g){
        this.level_map.drawMap(g);
        this.ball.draw(g);
    }
}