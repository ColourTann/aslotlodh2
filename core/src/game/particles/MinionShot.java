package game.particles;

import com.badlogic.gdx.graphics.g2d.Batch;

import game.screens.gameScreen.entity.Entity;
import game.util.Colours;
import game.util.Draw;

public class MinionShot extends BasicShot{
	
	public MinionShot(int x, int y, Entity target, int damage) {
		super(x, y, target, damage);
		
	}

	@Override
	public void onDestroy() {
		target.damage(damage);
	}

	@Override
	public void draw(Batch batch) {
		batch.setColor(Colours.light);
		int size=2;
		Draw.fillRectangle(batch, x-size/2, y-size/2, size, size);
	}
	
	

}
