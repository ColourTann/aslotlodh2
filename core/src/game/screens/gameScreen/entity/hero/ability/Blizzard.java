package game.screens.gameScreen.entity.hero.ability;



import game.particles.AOEDebug;
import game.particles.Icicle;
import game.screens.gameScreen.GameScreen;
import game.screens.gameScreen.entity.Entity;
import game.screens.gameScreen.entity.hero.Hero;
import game.util.Colours;
import game.util.Functions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class Blizzard extends Ability{
	
	public Blizzard(Hero hero) {
		super(hero, 9);
		range=120;
	}



	@Override
	boolean activate() {
		Functions.shuffle(GameScreen.self.entities);
		Entity target=null;
		for(Entity e:GameScreen.self.entities){
			if(e.team!=hero.team&&e.position.dst(hero.position)<range){
				target=e;
				break;
			}
		}
		if(target==null)return false;
		
		GameScreen.self.addParticle(new AOEDebug(target.position.x, target.position.y, 20, Colours.withAlpha(Colours.blueLight,.08f),1));
		
		SequenceAction sa = new SequenceAction();
		for(int i=0;i<=8;i++){
			Vector2 direction = new Vector2(1,0).rotateRad((float) (Math.random()*Math.PI*2));
			direction.scl((float) (Math.random()*20));
			final Vector2 pos = target.position.cpy().add(direction);
			sa.addAction(Actions.run(new Runnable() {
				@Override
				public void run() {
					GameScreen.self.addParticle(new Icicle(pos.x, pos.y, hero.getOpposingTeam()));
				}
			}));
			sa.addAction(Actions.delay(.08f));				
		}
		hero.addAction(sa);
		return true;
	}

}
