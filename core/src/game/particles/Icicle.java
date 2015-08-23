package game.particles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;

import game.Main;
import game.screens.gameScreen.GameScreen;
import game.screens.gameScreen.entity.Entity;
import game.screens.gameScreen.entity.Entity.Team;
import game.util.Colours;
import game.util.Draw;
import game.util.Particle;

public class Icicle extends Particle{
	static TextureRegion icicle = Main.atlas.findRegion("icicle");
	int height=200;
	Interpolation terp = Interpolation.pow2In;
	float startY, targetY;
	Team target;
	public Icicle(float x, float y, Team target) {
		this.target=target;
		this.x=x; 
		this.startY=y+height;
		this.targetY=y;
		this.y=startY;
		setupLife(1);
	}
	
	@Override
	public void tick(float delta) {
		float tempRatio=((1-ratio)*3);
		if(tempRatio>1){
			tempRatio=1;
		}
		this.y=terp.apply(startY, targetY, tempRatio);
		if(tempRatio==1){
			if(!explot)explode();
		}
	}

	boolean explot;
	private void explode() {
		for(int i=0;i<20;i++){
			GameScreen.get().addParticle(new Blast(x, y, Colours.blueLight));
		}
		GameScreen.get().areaDamage(x, y, 10, 10, target);
		GameScreen.get().shake(2);
		explot=true;
	}

	@Override
	public void draw(Batch batch) {
		batch.setColor(1,1,1,Math.min(1, ratio*2));
		Draw.drawCentered(batch, icicle, x, y+icicle.getRegionHeight()/2);
	}

	@Override
	public void onDestroy() {
	}

}
