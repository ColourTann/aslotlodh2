package game.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import game.util.Colours;
import game.util.Draw;
import game.util.Particle;

public class AOEDebug extends Particle{
	int radius;
	
	public AOEDebug(float x, float y, int radius, Color colour, float life) {
		this.colour=colour;
		this.x=x; this.y=y;
		this.radius=radius;
		setupLife(life);
	}
	@Override
	public void tick(float delta) {
	}

	@Override
	public void draw(Batch batch) {
		Colours.setBatchColour(batch, colour, ratio);
		Draw.fillCircle(batch, x, y, radius);
	}
	@Override
	public void onDestroy() {
	}
	

}
