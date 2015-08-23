package game.particles;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;

import game.screens.gameScreen.GameScreen;
import game.util.Colours;
import game.util.Draw;
import game.util.Particle;

public class Swirler extends Particle{

	float angle=(float) (Math.random()*Math.PI*2);
	float spin = rand(4, 8);
	float speed = rand(60, 50);
	float dist;
	float startX, startY;
	public Swirler(float x, float y) {
		this.startX=x; this.startY=y;
		this.x=x; this.y=y;
		setupLife(rand(.3f, .8f));
	}
	
	@Override
	public void tick(float delta) {
		speed-=delta*30;
		angle+=delta*spin;
		dist+=delta*speed;
		x= startX+(float) (Math.sin(angle)*dist);
		y=startY+(float) (Math.cos(angle)*dist);
	}

	@Override
	public void draw(Batch batch) {
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		Colours.setBatchColour(batch, Colours.green, ratio);
		Draw.fillCircle(batch, x, y, 2);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void onDestroy() {
	}

}
