package game.screens.gameScreen.entity.hero.ability;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import game.particles.AOEDebug;
import game.particles.Icicle;
import game.screens.gameScreen.GameScreen;
import game.screens.gameScreen.entity.Entity;
import game.screens.gameScreen.entity.hero.Hero;
import game.util.Colours;

public class RockThrow extends Ability{

	public RockThrow(Hero hero) {
		super(hero, 2);
	}

	@Override
	void activate() {
		
	}

}
