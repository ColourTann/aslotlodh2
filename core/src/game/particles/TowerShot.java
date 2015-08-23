package game.particles;

import game.screens.gameScreen.GameScreen;
import game.screens.gameScreen.entity.Entity;
import game.util.Colours;
import game.util.Draw;
import game.util.Particle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class TowerShot extends Particle{

	float angle=(float) (Math.random()*Math.PI*2);
	float spin = 8;
	Interpolation terp = Interpolation.linear;
	float dist=8;
	Vector2 locus;
	public TowerShot(Vector2 locus) {
		this.locus=locus;
		this.x=locus.x; this.y=locus.y;
	}
	
	@Override
	public void tick(float delta) {
		time+=delta;
		if(target==null){
		angle+=delta*spin;
		x= (float) (locus.x+Math.sin(angle)*dist);
		y= (float) (locus.y+Math.cos(angle)*dist);
		}
		else{
			this.x=terp.apply(origin.x, target.position.x, 1-ratio);
			this.y=terp.apply(origin.y, target.position.y, 1-ratio);
		}
		
		
		int diff=2;
		for(int i=0;i<3;i++)GameScreen.self.addParticle(new Dot(x+Particle.rand(-diff, diff), y+Particle.rand(-diff, diff), .3f, Colours.dark));
	}

	@Override
	public void draw(Batch batch) {
		batch.setColor(Colours.dark);
		Draw.fillCircle(batch, x, y, 2);
	}

	@Override
	public void onDestroy() {
		target.damage(10);
	}

	Entity target;
	Vector2 origin;
	public float time;
	public void fireAt(Entity e) {
		origin = new Vector2(x,y);
		setupLife(e.position.cpy().sub(origin).len()/200);
		target=e;
	}
	

}
