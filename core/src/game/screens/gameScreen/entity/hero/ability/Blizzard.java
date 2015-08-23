package game.screens.gameScreen.entity.hero.ability;

import java.util.Collections;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import game.particles.AOEDebug;
import game.particles.Icicle;
import game.screens.gameScreen.GameScreen;
import game.screens.gameScreen.entity.Entity;
import game.screens.gameScreen.entity.hero.Hero;
import game.util.Colours;
import game.util.Particle;

public class Blizzard extends Ability{

	public Blizzard(Hero hero) {
		super(hero, 7);
		
	}



	@Override
	void activate() {
		Collections.shuffle(GameScreen.entities);
		Entity target=null;
		for(Entity e:GameScreen.entities){
			if(e.team!=hero.team){
				target=e;
				break;
			}
		}
		if(target==null)return;
		
		GameScreen.get().addParticle(new AOEDebug(target.position.x, target.position.y, 20, Colours.withAlpha(Colours.blueLight,.08f),1));
		
		SequenceAction sa = new SequenceAction();
		for(int i=0;i<=5;i++){
			Vector2 direction = new Vector2(1,0).rotateRad((float) (Math.random()*Math.PI*2));
			direction.scl((float) (Math.random()*20));
			final Vector2 pos = target.position.cpy().add(direction);
			sa.addAction(Actions.run(new Runnable() {
				@Override
				public void run() {
					GameScreen.get().addParticle(new Icicle(pos.x, pos.y, hero.getOpposingTeam()));
				}
			}));
			sa.addAction(Actions.delay(.13f));				
		}
		hero.addAction(sa);
	}

}
