package game.screens.gameScreen.entity.hero.ability;



import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import game.particles.Boulder;
import game.screens.gameScreen.GameScreen;
import game.screens.gameScreen.entity.Entity;
import game.screens.gameScreen.entity.hero.Hero;
import game.util.Functions;
import game.util.Slider;
import game.util.Sounds;

public class RockThrow extends Ability{

	public RockThrow(Hero hero) {
		super(hero, 7);
		range=120;
	}

	static Sound toss = Sounds.get("throw", Sound.class);
	@Override
	boolean activate() {
		Functions.shuffle(GameScreen.entities);
		Entity target=null;
		for(Entity e:GameScreen.entities){
			if(e.team!=hero.team&&e.position.dst(hero.position)<range){
				target=e;
				break;
			}
		}
		final Entity actualTarget=target;
		if(target==null)return false;
		hero.addAction(Actions.delay(.4f, Actions.run(new Runnable() {
			
			@Override
			public void run() {
				GameScreen.self.addParticle(new Boulder(hero.position.cpy().add(0, 30), actualTarget.position.cpy(), hero.getOpposingTeam()));
			}
		})));
		toss.play(Slider.SFX.getValue());
		return true;
	}

}
