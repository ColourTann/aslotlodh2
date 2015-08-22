package game.screens.testScreens;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;

import game.util.Screen;
import game.util.Slider;
import game.util.Sounds;
import game.util.TextBox;

public class SoundScreen extends Screen{
	private static SoundScreen self;
	public static SoundScreen get(){
		if(self==null) self= new SoundScreen();
		return self;
	}
	public SoundScreen() {
		
		for(int x=1;x<=3;x++){
			final int index = x;
			TextBox tb = new TextBox("sound effect "+x);
			tb.makeMouseable();
			tb.addClickAction(new Runnable() {
				@Override
				public void run() {
					switch(index){
					case 1: Sounds.get("win", Sound.class).play(Slider.SFX.getValue()); break;
					case 2:Sounds.get("jump0", Sound.class).play(Slider.SFX.getValue()); break;
					case 3:Sounds.get("land0", Sound.class).play(Slider.SFX.getValue()); break;
					}
					
				}
			});
			tb.setPosition(x*100, 50, Align.center);
			addActor(tb);
		}
		
		for(int x=1;x<=2;x++){
			final int index = x;
			TextBox tb = new TextBox("music "+x);
			tb.makeMouseable();
			tb.addClickAction(new Runnable() {
				@Override
				public void run() {
					
					Sounds.playMusic(Sounds.get(index+"", Music.class)); 
					
					
				}
			});
			tb.setPosition(x*100, 150, Align.center);
			addActor(tb);
		}
		
	}
	
	@Override
	public void preDraw(Batch batch) {
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
