package game.screens.gameScreen.entity.hero.ability;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import game.particles.Icicle;
import game.screens.gameScreen.GameScreen;
import game.screens.gameScreen.entity.hero.Hero;

public class IceRain extends Ability{

	public IceRain(Hero hero) {
		super(hero, 11);
	}

	
	@Override
	void activate() {
		Vector2 target = hero.enemyHero.position;
		Vector2 diff = target.cpy().sub(hero.position);
		int max =(int) (diff.len()/15);
		SequenceAction sa = new SequenceAction();
		
		for(int i=1;i<=max;i++){
			final Vector2 pos = hero.position.cpy().mulAdd(diff.cpy(), i/(float)max);
			sa.addAction(Actions.run(new Runnable() {
				@Override
				public void run() {
					GameScreen.get().addParticle(new Icicle(pos.x, pos.y, hero.getOpposingTeam()));
				}
			}));
			sa.addAction(Actions.delay(.03f));
						
		}
		hero.addAction(sa);
	}

}
