package game.screens.pause;

import game.Main;
import game.Main.TransitionType;
import game.screens.gameScreen.GameScreen;
import game.screens.startScreen.StartScreen;
import game.util.Border;
import game.util.Screen;
import game.util.Slider;
import game.util.Sounds;
import game.util.TextBox;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

public class PauseScreen extends Group{
	private static int w=200,h=130;
	private static PauseScreen self;
	public static PauseScreen get(){
		if(self==null)self=new PauseScreen();
		return self;
	}
	
	private PauseScreen(){
		setSize(w,h);
		setPosition(Main.width/2-w/2, Main.height/2-h/2);
		int width = (w-TextBox.gap*4)/3;
//		addTransitionButton("sound", SoundScreen.get(), (width+TextBox.gap)*0,0, width);
//		addTransitionButton("clicking", GameScreen.get(), (width+TextBox.gap)*1,0, width);
//		addTransitionButton("fonts", FontScreen.get(), (width+TextBox.gap)*2,0, width);
		
		int numScales=4;
		width= (w-TextBox.gap*(numScales+1))/numScales;
		for(int i=0;i<numScales;i++) addScaleButton(i+1, (width+TextBox.gap)*i, TextBox.gap*2, width);
		
		Slider.SFX.setPosition(w/2-Slider.SFX.getWidth()/2, 40);
		addActor(Slider.SFX);
		
		Slider.music.setPosition(w/2-Slider.SFX.getWidth()/2, 70);
		addActor(Slider.music);
		
		TextBox tb = new TextBox("Restart");
		tb.addClickAction(new Runnable() {
			@Override
			public void run() {
				Sounds.stopMusic();
				Main.self.setScreen(new StartScreen());
				Main.self.toggleMenu();
			}
		});
		tb.setPosition(getWidth()/2, getHeight()-20, Align.center);
		addActor(tb);
		
//		Slider.music.setPosition(w/2-Slider.SFX.getWidth()/2, 60);
//		addActor(Slider.music);
	}
	
	private void addScaleButton(final int scale, int x, int y, int width){
		TextBox t = new TextBox("X "+scale, width+5);
		t.makeMouseable();
		t.addClickAction(new Runnable() {
			@Override
			public void run() {
				Main.self.setScale(scale);
			}
		});
		t.setPosition(TextBox.gap+x, -TextBox.gap+(int)(y));
		addActor(t);
	}
	
	private void addTransitionButton(String name, final Screen transitionTo, int x, int y, int width){
		TextBox t = new TextBox(name, width);
		t.makeMouseable();
		t.addClickAction(new Runnable() {
			@Override
			public void run() {
				Main.self.setScreen(transitionTo, TransitionType.LEFT, Interpolation.pow2Out, .3f);
				Main.self.toggleMenu();
			}
		});
		t.setPosition(TextBox.gap+x, -TextBox.gap+(int)(getHeight()-t.getHeight()-y));
		addActor(t);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Border.draw(batch, getX(), getY(), getWidth(), getHeight(), false);
		super.draw(batch, parentAlpha);
	}
}
