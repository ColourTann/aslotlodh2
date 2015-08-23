package game.particles;

import com.badlogic.gdx.graphics.g2d.Batch;

import game.screens.gameScreen.entity.Entity;
import game.util.Colours;
import game.util.Draw;

public class HeroShot extends BasicShot{

	public HeroShot(int x, int y, Entity target) {
		super(x, y, target, 6);
	}

	@Override
	public void onDestroy() {
		target.damage(damage);
	}

	@Override
	public void draw(Batch batch) {
		batch.setColor(Colours.pink);
		int size=8;
		Draw.fillRectangle(batch, x-size/2, y-size/2, size, size);
	}

}
