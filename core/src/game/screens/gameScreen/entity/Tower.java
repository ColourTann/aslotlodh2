package game.screens.gameScreen.entity;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import game.Main;
import game.particles.TowerShot;
import game.screens.gameScreen.GameScreen;
import game.util.Draw;
import game.util.Slider;
import game.util.Sounds;

public class Tower extends Entity{
	static TextureRegion tower = Main.atlas.findRegion("tower");
	static Sound shot = Sounds.get("towershot", Sound.class);
	Vector2 locus;
	TowerShot currentShot;
	public Tower(Team team) {
		attackPriority=AttackPriority.Tower;
		this.team=team;
		range=200;
		setHP(150);
		int gap=60;
		switch(team){
		case Left:
			position.x=gap;
			break;
		case Right:
			position.x=Main.width-gap;
			break;
		default:
			break;
		}
		position.y=GameScreen.getMid()-tower.getRegionHeight()/2;
		locus=position.cpy().add(0,+tower.getRegionHeight()-13);
		setSize(tower.getRegionWidth(), tower.getRegionHeight());
	}




	@Override
	public void act(float delta) {
		super.act(delta);
		if(currentShot!=null){
			attack();
		}
		else{
			secondsUntilShoot-=delta;
			if(secondsUntilShoot<=0){
				TowerShot s = new TowerShot(locus);
				GameScreen.self.addParticle(s);
				currentShot=s;
				secondsUntilShoot=.2f;
			}
		}
			
	}



	private void attack() {
		if(currentShot.time<.5f){
			return;
		}
		
		Entity e = getNearbyEntity(false, 70, false, true);
		if(e==null)return;
		shot.play(Slider.SFX.getValue());
		currentShot.fireAt(e);
		currentShot=null;
	}

	public void push(float x, float y, int magnitude) {

	}


	@Override
	public void draw(Batch batch, float parentAlpha) {

		batch.setColor(1,1,1,1);
		Draw.draw(batch, tower, position.x-tower.getRegionWidth()/2, position.y);
		drawHPBar(batch, -4);
		if(targeted)drawReticule(batch, 0);
	}




	@Override
	public void die() {
		GameScreen.self.teamDefeated(team);
	}
}
