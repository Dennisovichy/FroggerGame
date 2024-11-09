//Level.java
//Dennis Qi
//Overall class for the management of levels and interactions between the player and map

import java.awt.*;

class Level{
    private Map level_map;
    private Frog ball;
    private int lives;
    private int score;
    private int accum_score;
    private int max_time = 1800;
    private int ticker = max_time;

    public Level(Map layout, int[] data){
        this.level_map = layout;
        this.ball = new Frog();
        this.lives = data[0];
        this.score = data[1];
        this.accum_score = data[2];
    }

    public void update(boolean[] keys, int left, int right, int up, int down){ //update the level
        this.ball.move(keys, left, right, up, down);
        this.level_map.updateMap();
        this.score += this.ball.getPoints();
        CollisionChecker.checkRescue(ball, level_map.getHazards());
        if(CollisionChecker.checkSafe(ball, this.level_map.getCaves(),this)){ //check if player collides with things. most of the time this results in death
            this.ball = new Frog();
            this.ticker = this.max_time;
        }
        if(CollisionChecker.checkDrown(ball, level_map, level_map.getHazards())){
            this.level_map.addCorpse(ball.getX(), ball.getY(), true);
            this.ball = new Frog();
            this.lives--;
            this.ticker = this.max_time;
        }
        if(CollisionChecker.checkCollision(this.ball, this.level_map.getHazards())){
            this.level_map.addCorpse(ball.getX(), ball.getY(), false);
            this.ball = new Frog();
            this.lives--;
            this.ticker = this.max_time;
        }
        if(this.ball.getX()<=-50 || this.ball.getX()>=800 || this.ball.getY()>=750){
            this.ball = new Frog();
            this.lives--;
            this.ticker = this.max_time;
        }
        this.ticker--;
        if(this.ticker<=0){
            this.ticker = this.max_time;
            this.ball = new Frog();
            this.lives--;
        }
        if(this.accum_score>10000){
            this.accum_score-=10000;
            this.lives++;
        }
    }
    public void clearLevel(){
        this.level_map.clearCaves();
        this.level_map.clearCorpses();
        this.level_map.getHazards().clearMotorVehicles();
    }

    public boolean checkClear(){
        return this.level_map.checkCleared();
    }
    public boolean checkDead(){
        if(this.lives<0){
            return true;
        }
        return false;
    }
    public int getLives(){return this.lives;}
    public int getScore(){return this.score;}
    public int getAccumScore(){return this.accum_score;}
    public void addScore(int score){this.score += score;this.accum_score+= score;}
    public void clearHazards(){this.level_map.getHazards().clearMotorVehicles();}

    public void draw(Graphics g){
        this.level_map.drawMap(g);
        this.level_map.drawCars(g);
        this.ball.draw(g);
    }

    public void draw_bar(Graphics g){
        g.setColor(new Color(50,110,160));
        g.fillRect(250, 752, (int)(this.ticker/(1.7*(this.max_time/500))), 25);
    }
}