package game.util;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Fonts {
	
	public static GlyphLayout bounds = new GlyphLayout();
	
	public static BitmapFont helvetipixel; //14
	public static BitmapFont battlenet; //13
	public static BitmapFont pixelarial; //12
	public static BitmapFont tinyunicode; //12
	public static BitmapFont visitor; //9
	public static BitmapFont o4b3; //8
	public static BitmapFont pixelmix; //8
	public static BitmapFont pressstartp2; //8
	

	public static BitmapFont font;
	public static BitmapFont largeFont;
	
	public static BitmapFont[] fontSizes = new BitmapFont[10];
	
	public static void setup(){
		o4b3 = loadFont("o4b3");
		helvetipixel= loadFont("helvetipixel");
		pixelarial= loadFont("pixelarial");
		pixelmix= loadFont("pixelmix");
		tinyunicode= loadFont("tinyunicode");
		visitor= loadFont("visitor");
		battlenet= loadFont("battlenet");
		pressstartp2= loadFont("pressstartp2");
		font = battlenet;
		
		for(int i=0;i<10;i++){
			fontSizes[i]=loadFont("battlenet", i+1);
		}
		
	}
	
	private static BitmapFont loadFont(String name){
		return loadFont(name,1);
	}
	
	private static BitmapFont loadFont(String name, int scale){
		BitmapFont f = new BitmapFont(Gdx.files.internal("fonts/"+name+".fnt"));
		f.getData().setScale(scale);
		f.setUseIntegerPositions(true);
		return f;
	}
}
