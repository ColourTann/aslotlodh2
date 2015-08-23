package game.screens.gameScreen.entity.hero.ability;

import game.particles.Swirler;
import game.screens.gameScreen.GameScreen;
import game.screens.gameScreen.entity.Entity;
import game.screens.gameScreen.entity.hero.Hero;
import game.util.Slider;
import game.util.Sounds;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class Regrowth extends Ability{

	public Regrowth(Hero hero) {
		super(hero, 13);
	}
	
	static Sound regen = Sounds.get("regen", Sound.class);

	@Override
	boolean activate() {
		regen.play(Slider.SFX.getValue());
		for(Entity e : GameScreen.self.entities){
			if(e.team!=hero.team)continue;
			float distance =hero.position.dst(e.position);
			if(distance>50)continue;
			e.heal(20);				
		}
		
		SequenceAction sa = new SequenceAction();
		for(int i=0;i<=3;i++){
			sa.addAction(Actions.run(new Runnable() {
				@Override
				public void run() {
					for(int n=0;n<30;n++)GameScreen.self.addParticle(new Swirler(hero.position.x, hero.position.y));
				}
			}));
			sa.addAction(Actions.delay(.15f));			
		}
		hero.addAction(sa);
		return true;
	}

}
