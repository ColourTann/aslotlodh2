package game.screens.gameScreen.minion;

import game.Main;
import game.util.Colours;
import game.util.Draw;
import game.util.Particle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Minion extends Actor{
	Vector2 position= new Vector2();
	Vector2 targetPosition= new Vector2();
	
	public enum Order{Move, AttackMove, Attack}
	public enum Team{Left, Right}
	Order order=Order.Move;
	Team team;
	static int randomFactor=50;
	public Minion(Team team) {
		this.team=team;
		Vector2 target =null;
		switch(team){
		case Left:
			position = new Vector2(0, Main.height/2);
			target=new Vector2(Main.width+50, Main.height/2);
			break;
		case Right:
			position = new Vector2(Main.width, Main.height/2);
			target=new Vector2(-50, Main.height/2);
			break;
		default:
			break;
		}
		position.add(Particle.rand(-randomFactor, randomFactor), Particle.rand(-randomFactor, randomFactor));
		moveTowards(target, Order.AttackMove);
	}
	
	
	public void moveTowards(Vector2 target, Order order){
		this.targetPosition=target;
		this.order=order;
	}
	
	static float speed=100;
	@Override
	public void act(float delta) {
		if(targetPosition==null)return;
		
	
		
		Vector2 subtracted = targetPosition.cpy().sub(position);
		float toMoveThisTurn = delta*speed;
		if(subtracted.dst(0, 0)<toMoveThisTurn){
			position=targetPosition;
			targetPosition=null;
			return;
		}
		Vector2 direction = targetPosition.cpy().sub(position).nor();
		position.mulAdd(direction, delta*speed);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.light);
		Draw.drawCenteredScaled(batch, Draw.getSq(), position.x, position.y, 15, 35);
	}
}
