package game.particles;

import com.badlogic.gdx.graphics.g2d.Batch;

import game.screens.gameScreen.GameScreen;
import game.screens.gameScreen.entity.Entity;
import game.util.Colours;
import game.util.Draw;

public class Fireball extends BasicShot{

	public Fireball(float x, float y, Entity target) {
		super(x, y, target, 0);
	}

	@Override
	public void onDestroy() {
		for(int i=0;i<50;i++){
			GameScreen.get().addParticle(new Blast(x, y, Colours.red));
		}
		GameScreen.get().areaDamage(x,y,20,2, target.team);
	}

	@Override
	public void draw(Batch batch) {
		batch.setColor(Colours.red);
		int size=16;
		Draw.fillRectangle(batch, x-size/2, y-size/2, size, size);
	}

}
