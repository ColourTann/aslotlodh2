package game.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import game.util.Colours;
import game.util.Draw;
import game.util.Particle;

public class Blast extends Particle{
	static float speed=50;
	static float speedVariance=50;
	static float drag=.25f;
	
	public Blast(float x, float y, Color c) {
		this.colour=c;
		this.x=x; this.y=y;
		dx=(float) Math.random();
		dy=(float) Math.sqrt(1-dx*dx);
		if(Math.random()>.5)dx=-dx;
		if(Math.random()>.5)dy=-dy;
		dx*=Particle.rand(speed, speed+speedVariance);
		dy*=Particle.rand(speed, speed+speedVariance);
		setupLife(rand(.3f, .65f));
	}
	
	@Override
	public void tick(float delta) {
		x+=dx*delta;
		y+=dy*delta;
		doDrag(drag, delta);
	}

	@Override
	public void draw(Batch batch) {
		Colours.setBatchColour(batch, colour, ratio);
		int size=2;
		Draw.fillRectangle(batch, x-size/2, y-size/2, size, size);
	}

	@Override
	public void onDestroy() {
	}

}
