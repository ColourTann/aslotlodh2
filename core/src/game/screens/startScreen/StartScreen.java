package game.screens.startScreen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;

import game.Main;
import game.screens.gameScreen.entity.Entity.Team;
import game.screens.gameScreen.entity.hero.Hero;
import game.screens.gameScreen.entity.hero.Rockman;
import game.screens.gameScreen.entity.hero.Sorceress;
import game.util.Fonts;
import game.util.Screen;
import game.util.Slider;
import game.util.Sounds;
import game.util.TextBox;

public class StartScreen extends Screen{

	Hero[] heroes = new Hero[]{new Sorceress(Team.Left), new Rockman(Team.Right)};
	static long id;
	public StartScreen() {
		for(int i=0;i<2;i++){
			HeroChoice hc = new HeroChoice(heroes[i]);
			hc.setPosition((i+1)*Main.width/3, 80, Align.center);
			addActor(hc);
		}
		id =s.play(1);
		
//		TextBox tb = new TextBox("Ancient Storm Legends of the League of Defence Heroes 2", 180);
//		tb.setPosition(getWidth()/2, getHeight()-50, Align.center);
//		addActor(tb);
	}
	static Sound s = Sounds.get("intro", Sound.class);
	@Override
	public void preDraw(Batch batch) {
		Fonts.font.draw(batch, "Ancient Storm: Legends of the League of Defence Heroes 2", Main.width/2, getHeight()-20, 0, Align.center, false);
		
		Fonts.font.draw(batch, "Choose your hero", Main.width/2, getHeight()-50, 0, Align.center, false);
	}

	public static void stopSound(){
		s.stop(id);
	}
	
	@Override
	public void postDraw(Batch batch) {
		
	}

	@Override
	public void preTick(float delta) {
	}

	@Override
	public void postTick(float delta) {
	}

}
