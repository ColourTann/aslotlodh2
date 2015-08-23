package game.util;

import java.util.ArrayList;

import javafx.scene.text.Font;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class ScrollingText extends Actor{
	ArrayList<Message> chats = new ArrayList<Message>();
	int toDisplay=6;
	int textDiff=(int) Fonts.font.getLineHeight();
	
	
	public ScrollingText() {
	}
	
	public void addText(Username name, String text){
		chats.add(0, new Message(name, text));
	}
	
	
	static Username[] names = {new Username("JoeJoe (Darkwalker)", Colours.red), new Username("fckhd (Scarulon)", Colours.red), new Username("triple[ssf] (Sajid)", Colours.red), 
		new Username("weedfang1337 (Zip)",Colours.blue), new Username("xXxSwagMasterxXx (Blandrin)", Colours.blue), new Username("mikey777 (Valence)", Colours.blue)};
	static String[] messages = {"ugh top lane is grundling, we lose", "zzzz", "fuck this, tinko op", "no u", "ez ez", "lol this team, all burrowing to gather beads",
		"wtf", "my team sucks", "i have 0 death, you are one who keesp feeding!", "shut up", "report my team", "stfu", "ss top", "bot lane no ss!!", "lmao runs away with 70% agil, my team",
		"deinstall noob", "ugh why has nobody specced green?", "lol ur skills dan77", "report bladetinker", "bladetinker noob", "fk u", "pandin noob, doesn't repair deck", 
		"fuck why didn't you stack defusals!", "sigh", "kappa", "1v1 me bithc", "lol, took 4 of you to kill me", "haha nice ult, loser", "deinstall game drakken", "sotp farm sp!!!",
		"wtf botlane", "go back to baby sims 3", "lol this bladetinker", "bladetniker feeder", "gdmn their tinko, who fed!??", "they have so many gems", "stop autoattack creeps noob",
		"someone up courier", "lol greed", "ugh dont focus meatler", "stop farming bot bldtnker", "baldetinker- teamfight?????!!!", "focus their cqrry", "i hTE BLADETINKER"
		};
	
	public void addRandomMessage(){
		addText(names[(int) (Math.random()*names.length)], messages[(int) (Math.random()*messages.length)]);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		for(int i=0;i<toDisplay;i++){
			if(chats.size()<=i)break;
			Message m = chats.get(i);
			m.draw(batch, (int)getX(), (int) (getY()+i*textDiff), (toDisplay-i)/5f);
		}
	}
	
	public static class Username{
		public Color col; public String name;
		public Username(String name, Color col) {
			this.col=col; this.name=name+": ";
		}
	}
	
	
	public static class Message{
		Username name; String message; int offset;
		public Message(Username name, String message) {
			this.name=name; this.message=message;
			Fonts.bounds.setText(Fonts.font, name.name);
			offset= (int) Fonts.bounds.width;
		}
		
		public void draw(Batch batch, int x, int y, float alpha){
			Fonts.font.setColor(Colours.withAlpha(name.col, alpha));
			Fonts.font.draw(batch, name.name, x, y);

			
			Fonts.font.setColor(Colours.withAlpha(Colours.light, alpha));
			Fonts.font.draw(batch, message, x+offset, y);

		}
	}
}
