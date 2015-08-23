package game.particles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;


import game.util.Particle;

public class Boulder extends Particle{
	Interpolation terp = Interpolation.linear;
	Vector2 origin;
	Vector2 target;
	public Boulder(Vector2 origin, Vector2 target) {
		this.x=origin.x; this.y=origin.y;
		setupLife(target.cpy().sub(origin).len()/100);
		this.origin=origin;
		this.target=target;	
	}
	
	@Override
	public void tick(float delta) {
		this.x=terp.apply(origin.x, target.x, 1-ratio);
		this.y=terp.apply(origin.y, target.y, 1-ratio);
	}

	@Override
	public void draw(Batch batch) {
		
	}

	@Override
	public void onDestroy() {
	}

}
