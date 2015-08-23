package game.screens.startScreen;

import game.Main;
import game.screens.gameScreen.GameScreen;
import game.screens.gameScreen.entity.hero.Hero;
import game.util.Border;
import game.util.Draw;
import game.util.Fonts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

public class HeroChoice extends Actor{
	Hero h;
	boolean moused;
	public HeroChoice(final Hero h) {
		setSize(50, 80);
		this.h=h;
		
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Main.self.setScreen(new GameScreen(h.heroName.equals("Rocko")));
				StartScreen.stopSound();
				return false;
			}
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				moused=true;
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				moused=false;
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Border.draw(batch, getX(), getY(), getWidth(), getHeight(), moused);
		Draw.drawCentered(batch, h.texture, getX()+getWidth()/2, getY()+getHeight()/2+10);
		Fonts.font.draw(batch, h.heroName, getX()+getWidth()/2, getY()+20, 0, Align.center, false);
		super.draw(batch, parentAlpha);
	}
}
