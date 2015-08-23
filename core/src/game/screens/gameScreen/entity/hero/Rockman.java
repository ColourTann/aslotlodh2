package game.screens.gameScreen.entity.hero;

import game.Main;
import game.screens.gameScreen.entity.hero.ability.Blizzard;
import game.screens.gameScreen.entity.hero.ability.IceRain;
import game.screens.gameScreen.entity.hero.ability.Regrowth;
import game.screens.gameScreen.entity.hero.ability.RockThrow;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Rockman extends Hero{

	public Rockman(Team team) {
		super(team);
		abilities.add(new Regrowth(this));
		abilities.add(new RockThrow(this));
		
		//regrowth
		
		for(int i=0;i<3;i++){
			animation[i]=Main.atlas.findRegion("rockmananimation"+i);
		}
		texture = Main.atlas.findRegion("rockman");
		setSize(texture.getRegionWidth(), texture.getRegionHeight());
	}

	@Override
	public void preDraw(Batch batch) {
	}

	@Override
	public void die() {
	}
	
}
