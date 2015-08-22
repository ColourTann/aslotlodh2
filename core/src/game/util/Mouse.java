package game.util;

import game.Main;

import com.badlogic.gdx.Gdx;

public class Mouse {

	public static int getX(){
		return Gdx.input.getX()/Main.scale;
	}
	
	public static int getY(){
		return Main.height-(Gdx.input.getY()/Main.scale);
	}
}
