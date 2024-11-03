import java.awt.*;

class Level{
    Map level_map;
    Frog ball;

    public Level(Map layout){
        this.level_map = layout;
        this.ball = new Frog();
    }

    public void update(boolean[] keys, int left, int right, int up, int down){
        this.ball.move(keys, left, right, up, down);
        this.level_map.updateMap();
        CollisionChecker.checkDrown(ball, level_map, level_map.getHazards());
        if(CollisionChecker.checkCollision(this.ball, this.level_map.getHazards())){
            this.ball = new Frog();
        }
    }

    public void draw(Graphics g){
        this.level_map.drawMap(g);
        this.ball.draw(g);
    }
}