package game.screens.testScreens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;

import game.Main;
import game.util.Fonts;
import game.util.Screen;
import game.util.TextBox;

public class StartScreen extends Screen{



	private static StartScreen self;
	TextBox tb;
	public static StartScreen get(){
		if(self==null) self= new StartScreen();
		return self;
	}

	public StartScreen() {
		TextBox.setImage("test", Main.atlas.findRegion("shiptiny"));
		TextBox.setImage("grass", Main.atlas.findRegion("grass"));
		TextBox.setImage("spiral", Main.atlas.findRegion("spiral"));
		TextBox.setImage("happy", Main.atlas.findRegion("happy"));
		TextBox.setImage("lasers", Main.atlas.findRegion("lasers"));
		TextBox.setImage("cat", Main.atlas.findRegion("cat"));
		
		tb = new TextBox("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmo " 
				+ "Lorem [grass] ipsum dolor [grass][happy][lasers][grass] sit amet, [spiral] consectetur [happy][happy] adipiscing elit, "
				+ "sed do eiusmod tempor incididunt ut [lasers]labore et dolore magna aliqua. Ut enim  ad minim veniam, quis nostrud exercitation"
				+ " ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate "
				+ "velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt "
				+ "in culpa qui officia deserunt [cat][cat]mollit anim id est laborum.", 200);
		tb.makeResizable();
		tb.setPosition(50, 50);
		addActor(tb);
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
