package game.particles;

import game.screens.gameScreen.entity.Entity;
import game.util.Particle;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class BasicShot extends Particle{

	Entity target;
	int startX, startY;
	int damage;
	public BasicShot(float x, float y, Entity target, int damage) {
		this.x=x; this.y=y;
		this.target=target;
		this.damage=damage;
	}
	
	@Override
	public abstract void onDestroy();
	
	static float speed=150;
	@Override
	public void tick(float delta) {
		
		float dx = target.position.x-x;
		float dy = target.position.y-y;
		float distance = (float) Math.sqrt(dx*dx+dy*dy);
		if(distance<=5){
			onDestroy();
			dead=true;
			return;
		}
		dx/=(distance);
		dy/=(distance);
		
		x+=dx*delta*speed;
		y+=dy*delta*speed;
	}

	@Override
	public abstract void draw(Batch batch);
	

}
