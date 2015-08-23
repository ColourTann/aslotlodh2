package game.screens.gameScreen.entity.hero;

import game.Main;
import game.screens.gameScreen.entity.hero.ability.Blizzard;
import game.screens.gameScreen.entity.hero.ability.IceRain;
import game.util.Colours;
import game.util.ScrollingText.Username;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Sorceress extends Hero{
	 
	public Sorceress(Team team) {
		super(team);
		heroName="Frieza";
		username = new Username("niyEden (Frieza)", team==Team.Left?Colours.red:Colours.blue);
		
		abilities.add(new IceRain(this));
		abilities.add(new Blizzard(this));
		for(int i=0;i<3;i++){
			animation[i]=Main.atlas.findRegion("sorcressstaff"+i);
		}
		texture = Main.atlas.findRegion("sorceress");
		setSize(texture.getRegionWidth(), texture.getRegionHeight());
	}
	@Override
	public void preDraw(Batch batch) {
	}


}
