package game.screens.gameScreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import game.screens.gameScreen.minion.Minion;
import game.screens.gameScreen.minion.Minion.Order;
import game.screens.gameScreen.minion.Minion.Team;
import game.util.Mouse;
import game.util.Screen;
import game.util.TextBox;

public class GameScreen extends Screen{



	private static GameScreen self;
	TextBox tb;
	Minion player;
	public static GameScreen get(){
		if(self==null) self= new GameScreen();
		return self;
	}

	public GameScreen() {
		
		for(int i=0;i<6;i++){
			for(Team t: Team.values()){
			Minion m = new Minion(t);
			addActor(m);
			}
		}
		
		player = new Minion(Team.Left);
		addActor(player);
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				player.moveTowards(new Vector2(Mouse.getX(), Mouse.getY()), Order.Move);
				return false;
			}
		});
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
