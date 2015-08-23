package game.particles;

import game.Main;
import game.screens.gameScreen.GameScreen;
import game.screens.gameScreen.entity.Entity;
import game.screens.gameScreen.entity.Entity.Team;
import game.util.Colours;
import game.util.Draw;
import game.util.Particle;
import game.util.Slider;
import game.util.Sounds;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class Boulder extends Particle{
	Interpolation terp = Interpolation.linear;
	Vector2 origin;
	Vector2 target;
	float rotation;
	Team team;
	private float rotationSpeed=2;
	static Sound impact= Sounds.get("impact", Sound.class);
	public Boulder(Vector2 origin, Vector2 target, Team team) {
		this.team=team;
		this.x=origin.x; this.y=origin.y;
		setupLife(target.cpy().sub(origin).len()/200);
		this.origin=origin;
		this.target=target;	
	}
	
	@Override
	public void tick(float delta) {
		rotation+=rotationSpeed*delta;
		this.x=terp.apply(origin.x, target.x, 1-ratio);
		this.y=terp.apply(origin.y, target.y, 1-ratio);
	}
	TextureRegion tr = Main.atlas.findRegion("boulder");
	@Override
	public void draw(Batch batch) {
		batch.setColor(1,1,1,1);
		Draw.drawCenteredRotated(batch, tr, x, y, rotation);
	}

	@Override
	public void onDestroy() {
		impact.play(Slider.SFX.getValue());
		for(int i=0;i<30;i++){
			GameScreen.self.addParticle(new Blast(x, y, Colours.brown));
		}
		int radius=25;
		GameScreen.self.shake(7);
		GameScreen.self.areaDamage(target.x, target.y, radius, 15, team);
		for(int i=GameScreen.self.entities.size()-1;i>=0;i--){
		Entity e = GameScreen.self.entities.get(i);
			if(e.team!=team)continue;
			float distance =target.dst(e.position);
			if(distance>radius)continue;
			e.push(target.x, target.y, 20);
		}
	}

}
