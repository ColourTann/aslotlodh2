package game.screens.gameScreen.entity.hero.ability;

import java.util.Collections;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import game.particles.Boulder;
import game.screens.gameScreen.GameScreen;
import game.screens.gameScreen.entity.Entity;
import game.screens.gameScreen.entity.hero.Hero;

public class RockThrow extends Ability{

	public RockThrow(Hero hero) {
		super(hero, 7);
		range=120;
	}

	@Override
	void activate() {
		Collections.shuffle(GameScreen.entities);
		Entity target=null;
		for(Entity e:GameScreen.entities){
			if(e.team!=hero.team&&e.position.dst(hero.position)<range){
				target=e;
				break;
			}
		}
		final Entity actualTarget=target;
		if(target==null)return;
		hero.addAction(Actions.delay(.4f, Actions.run(new Runnable() {
			
			@Override
			public void run() {
				GameScreen.get().addParticle(new Boulder(hero.position.cpy().add(0, 30), actualTarget.position.cpy(), hero.getOpposingTeam()));
			}
		})));
		
	}

}
