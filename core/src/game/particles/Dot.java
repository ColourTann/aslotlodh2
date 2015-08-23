package game.particles;

import game.util.Colours;
import game.util.Draw;
import game.util.Particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Dot extends Particle{

	public Dot(float x, float y, float life, Color colour) {
		this.x=x; this.y=y; 
		setupLife(life);
		this.colour=colour;
	}
	
	@Override
	public void tick(float delta) {
	}

	@Override
	public void draw(Batch batch) {
		Colours.setBatchColour(batch, colour, ratio);
		Draw.fillRectangle(batch, x, y, 1, 1);
	}

	@Override
	public void onDestroy() {
	}

}
