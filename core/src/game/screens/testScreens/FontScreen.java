package game.screens.testScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import game.Main;
import game.util.Fonts;
import game.util.Mouse;
import game.util.Screen;
import game.util.TextWisp;

public class FontScreen extends Screen{

	int fontX, fontY;
	
	private static FontScreen self;
	public static FontScreen get(){
		if(self==null) self= new FontScreen();
		return self;
	}
	
	@Override
	public void preDraw(Batch batch) {
	}

	@Override
	public void postDraw(Batch batch) {
		
		for(int i=0;i<4;i++){
			Fonts.fontSizes[i].draw(batch, "Different sizes!", getX()+0, getY()-30+(float) (getHeight()-(Math.pow(i+.5, 2.3)+i*1)*5));
		}
		int x= 10; int y=50;
		Fonts.battlenet.draw(batch, "Font: battlenet", getX()+x, getY()+y);
		x+=100;
		Fonts.helvetipixel.draw(batch, "Font: helvetipixel", getX()+x, y);
		x+=100;
		Fonts.pixelarial.draw(batch, "Font: pixelarial", getX()+x, getY()+y);
		x=10;
		y+=50;
		Fonts.o4b3.draw(batch, "Font: 04b3", getX()+x, getY()+y);
		x+=100;
		Fonts.visitor.draw(batch, "Font: visitor", getX()+x, getY()+y);
		x+=100;
		Fonts.pixelmix.draw(batch, "Font: pixelmix", getX()+x, getY()+y);
		x=10;
		y+=50;
		Fonts.tinyunicode.draw(batch, "Font: tinyunicode", getX()+x, getY()+y);
		x+=100;
		Fonts.pressstartp2.draw(batch, "Font: presstartp2", getX()+x, getY()+y);
		
		/*
		 * public static BitmapFont pixelfraktur; //16
	public static BitmapFont helvetipixel; //14
	public static BitmapFont battlenet; //13
	public static BitmapFont pixelarial; //12
	public static BitmapFont tinyunicode; //12
	public static BitmapFont visitor; //9
	public static BitmapFont o4b3; //8
	public static BitmapFont pixelmix; //8
		 */
		

		
	}

	@Override
	public void preTick(float delta) {
		fontX=Mouse.getX();
		fontY=Mouse.getY();
		addParticle(new TextWisp("more words!", fontX, fontY));
	}

	@Override
	public void postTick(float delta) {
	}
	
	

}
