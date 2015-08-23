package game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;

public class TextWisp extends Particle{
	public String text;
	public TextWisp(String text, Color colour, int x, int y) {
		this.colour=colour;
		this.x=x;
		this.y=y;
		this.text=text;
		setupLife(1);
	}
	static float speed=80;
	@Override
	public void tick(float delta) {
		y+=delta*speed;
	}
	
	public void refresh(){
		setupLife(.5f);
	}
	
	public void setText(String text){
		this.text=text;
		refresh();
	}

	@Override
	public void draw(Batch batch) {
		Fonts.font.setColor(colour);
		Fonts.font.getColor().a=ratio;
		Fonts.font.draw(batch, text, x, y, 0, Align.center, false);
	}

	@Override
	public void onDestroy() {
	}
}
