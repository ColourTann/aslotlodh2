package game;

import game.screens.gameScreen.GameScreen;
import game.screens.pause.InputBlocker;
import game.screens.pause.PauseScreen;
import game.util.Colours;
import game.util.Draw;
import game.util.Fonts;
import game.util.Screen;
import game.util.Sounds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class Main extends ApplicationAdapter {
	public static int width=600,height=420;
	SpriteBatch batch;
	Stage stage;
	OrthographicCamera cam;
	public static TextureAtlas atlas;
	public static Main self;
	public static int scale=1;
	public static boolean debug = true;
	Screen currentScreen;
	Screen previousScreen;
	FrameBuffer buffer;
	public static float ticks;
	public enum MainState{Normal, Paused}
	@Override
	public void create () {
		self=this;
		
		Sounds.setup();
		Fonts.setup();
		
		
		
		buffer = new FrameBuffer(Format.RGBA8888, Main.width, Main.height, false);
		atlas= new TextureAtlas(Gdx.files.internal("atlas_image.atlas"));
		stage = new Stage(new FitViewport(Main.width, Main.height));
		cam =(OrthographicCamera) stage.getCamera();
		batch = (SpriteBatch) stage.getBatch();
		Gdx.input.setInputProcessor(stage);
		stage.addListener(new InputListener(){
			public boolean keyDown (InputEvent event, int keycode) {
				
				switch(keycode){
				case Keys.ESCAPE:
					toggleMenu();
					return false;
				}
				
				return true;
			}

			
		});

		setScale(scale);
		setScreen(new GameScreen());	
	
	}
	
	public void setScale(int scale){
		Main.scale=scale;
		int newWidth = width*scale;
		int newHeight= height*scale;
		Gdx.graphics.setDisplayMode(newWidth, newHeight, false);
		stage.getViewport().update(newWidth, newHeight);
	}
	
	public void toggleMenu() {
		if(state!=MainState.Paused){
			stage.addActor(InputBlocker.get());
			stage.addActor(PauseScreen.get());
			setState(MainState.Paused);
		}
		else {
			InputBlocker.get().remove();
			PauseScreen.get().remove();
			setState(MainState.Normal);
		}

	}
	
	
	
	private MainState state=MainState.Normal;
	public MainState getState(){
		return state;
	}
	public void setState(MainState state){
		this.state=state;
	}
	public enum TransitionType{LEFT};
	public void setScreen(Screen screen, TransitionType type, Interpolation interp, float speed){
		if(screen==currentScreen)return;
		setScreen(screen);
		switch(type){
		case LEFT:
			screen.setPosition(Main.width, 0);
//			Action a = ;
			screen.addAction(Actions.moveTo(0, 0, speed, interp));
			previousScreen.addAction(Actions.moveTo(-Main.width, 0, speed, interp));
			break;
		}
		previousScreen.addAction(Actions.after(Actions.removeActor()));
	}
	
	public void setScreen(Screen screen){
		previousScreen=currentScreen;
		currentScreen=screen;
		stage.addActor(screen);
	}

	@Override
	public void render () {

		update(Gdx.graphics.getDeltaTime());
		
		buffer.bind();
		buffer.begin();
		batch.begin();
		batch.setColor(Colours.dark);
		batch.setProjectionMatrix(cam.combined);
		Draw.fillRectangle(batch, 0, 0, Main.width, Main.height);
		batch.end();
		stage.draw();
		batch.begin();

		if(Main.debug)drawFPS(batch);
		batch.end();
		buffer.end();

		batch.begin();
		batch.setColor(1,1,1,1);
		buffer.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		Draw.drawRotatedScaledFlipped(batch, buffer.getColorBufferTexture(), 0, 0, 1, 1, 0, false, true);
		batch.end();
	}
	
	public void drawFPS(Batch batch){
		Fonts.font.setColor(Colours.light);
		Fonts.font.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 0, Main.height);
	}
	
	
	public void update(float delta){
		ticks+=delta;
		Sounds.tickFaders(delta);
		stage.act(delta);
	}

}
