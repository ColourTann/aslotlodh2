package game.util;



import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class TextBox extends Actor{
	private static Color dummyColor=Colours.white;
	private static BitmapFont defaultFont=Fonts.font;
	BitmapFont font = defaultFont;
	public static int gap =8;
	int wrapWidth;
	String text;
	int fontHeight;
	Runnable r;
	boolean moused;
	int align = Align.center;
	public TextBox(String text){
		Fonts.bounds.setText(font, text);
		setup(text, defaultFont, (int)Fonts.bounds.width+gap*4);
	}

	public TextBox(String text, int boxWidth) {
		setup(text, defaultFont, boxWidth);
	}

	public TextBox(String text, BitmapFont font, int boxWidth) {
		setup(text, font, boxWidth);
	}

	public void addClickAction(final Runnable r){
		makeMouseable();
		addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {	
				r.run();
				return false;
			}
		});
	}

	public void makeMouseable(){
		addListener(new InputListener(){
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				moused=true;
			}
			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				if(toActor==TextBox.this) return;
				moused=false;
			}
		});
	}

	boolean dragging;
	public void makeResizable(){
		addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(button==1){
					align=align==Align.center?Align.left:Align.center;
					setup(text, defaultFont, wrapWidth+gap*2);
					return false;
				}
				dragging=true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {	
				dragging=false;
			}
		});
	}

	private FrameBuffer buffer;
	private void setup(String text, BitmapFont font, int boxWidth){

		this.font=font;
		this.text=text;
		this.wrapWidth=boxWidth-gap*2;
		Fonts.bounds.setText(font, text, dummyColor, wrapWidth, Align.center, true);
		fontHeight= (int) (Fonts.bounds.height/2+getHeight()/2);
		fontHeight=0;
		fontHeight=(int) (getHeight()-gap);		
		setupLines(text);
	}

	@Override
	public void act(float delta) {
		if(dragging){
			setup(text, font, Mouse.getX());
		}
		super.act(delta);
	}


	@Override
	public void draw(Batch batch, float parentAlpha) {

		font.setColor(Colours.light);
		Border.draw(batch, getX(), getY(), buffer.getWidth(), buffer.getHeight(), moused);
		batch.setColor(1,1,1,1);
		Draw.drawRotatedScaledFlipped(batch, buffer.getColorBufferTexture(), (int)getX(), (int)getY(), 1, 1 ,0 ,false, true);
		//		font.draw(batch, text, getX()+gap, getY()+fontHeight, wrapWidth, Align.center, true);

		super.draw(batch, parentAlpha);
	}

	Array<Line> lines = new Array<Line>();


	SpriteBatch batch = new SpriteBatch();
	OrthographicCamera bufferCam;
	int baseLineHeight =  (int) font.getLineHeight()+2;
	private void setupLines(String entireText){

		int currentX=gap;
		int currentY=gap;
		int lineHeight = baseLineHeight;
		int spaceWidth = (int) Fonts.font.getSpaceWidth();
		char[] charArray = entireText.toCharArray();
		int previousIndex=0;

		lines.clear();

		Line currentLine= new Line();
		boolean specialMode=false;
		for(int index = 0; index<=charArray.length; index++){
			boolean newLine=false;
			boolean finishedWord=false;
			char c=0;
			if(index==charArray.length){
				finishedWord=true;
			}
			else{
				c = charArray[index];
				if(c==' '){
					finishedWord=true;
				}
				if(c=='['){
					finishedWord=true;
				}
				if(c==']'){
					finishedWord=true;
				}
			}
			if(finishedWord){
				String word = entireText.substring(previousIndex, index);
				TextureRegion tr=null;
				int length=0;
				previousIndex=index+1;
				if(specialMode){
					if(word.equals("n")){
						newLine=true;
					}
					else{
						tr = textureMap.get(word);
						length = tr.getRegionWidth();	
					}
				}
				else{
					length = getLength(word);
				}

				if(currentX+length>wrapWidth||newLine){
					currentLine.setWidth(currentX-(specialMode?0:spaceWidth));
					currentLine.setY(currentY);
					lines.add(currentLine);
					currentLine = new Line();

					currentY+=lineHeight;
					lineHeight=baseLineHeight;
					currentX=gap;
				}
				if(!newLine){
					if(specialMode){
						int diff = tr.getRegionHeight()-baseLineHeight;
						int newDiff = diff/2-(lineHeight-baseLineHeight);

						if(newDiff>0){
							lineHeight+=newDiff;
							currentY+=newDiff;
						}


						currentLine.addTextPosition(new TextPosition(tr, currentX, 0));
					}
					else currentLine.addTextPosition(new TextPosition(word, currentX, 0));
					currentX+=length;
				}
			}
			if(c=='[')specialMode=true;
			if(c==']')specialMode=false;
			if(c==' ')currentX+=spaceWidth;

		}
		if(!lines.contains(currentLine, true)){
			currentLine.setWidth(currentX-spaceWidth);
			currentLine.setY(currentY);
			lines.add(currentLine);
		}
		int bufferWidth=(int)(wrapWidth+gap*1);
		int bufferHeight=(int) (currentY+gap*2+(lineHeight-baseLineHeight));
		if(bufferWidth%2!=0)bufferWidth++;
		if(bufferHeight%2!=0)bufferHeight++;
		buffer = new FrameBuffer(Format.RGBA8888,
				bufferWidth,
				bufferHeight,
				false);


		setSize(bufferWidth, bufferHeight);
		bufferCam = new OrthographicCamera(buffer.getWidth(), buffer.getHeight());

		bufferCam.translate((int)(buffer.getWidth()/2), (int)(buffer.getHeight()/2));


		bufferCam.update();


		font.setColor(Colours.light);
		buffer.bind();
		buffer.begin();

		batch.setProjectionMatrix(bufferCam.combined);
		batch.begin();
		batch.setColor(Colours.light);


		for(Line l: lines){
			l.render(batch, align);
		}

		batch.end();





		buffer.end();

	}

	private static HashMap<String, TextureRegion> textureMap = new HashMap<String, TextureRegion>();
	public static void setImage(String id, TextureRegion texture){
		textureMap.put(id, texture);
	}

	private int getLength(String word) {
		Fonts.bounds.setText(font, word);
		return (int) Fonts.bounds.width;
	}

	private  class Line{
		int width;
		int y;
		Array<TextPosition> textPositions = new Array<TextPosition>();
		public Line() {
		}
		public void setWidth(int width) {
			this.width=width;
		}
		public void setY(int y) {
			this.y=y;
		}
		public void addTextPosition(TextPosition tp){
			textPositions.add(tp);
		}
		public void render(Batch batch, int align){

			int bonusX=0;
			if(align == Align.center){
				bonusX=(wrapWidth-width)/2;
			}

			for(TextPosition tp: textPositions) tp.render(batch, bonusX, y);
		}
	}

	private  class TextPosition{
		String text; 
		TextureRegion tr;
		int x, y;
		public TextPosition(String text, int x, int y) {
			this.text=text; 
			this.x=x; this.y=y;
		}
		public TextPosition(TextureRegion tr, int x, int y) {
			this.tr=tr; 
			this.x=x; this.y=y;
		}
		public void render(Batch batch, int bonusX, int bonusY){
			//			System.out.println("drawing " +":"+x+":"+y);
			if(tr!=null) Draw.draw(batch, tr, (int)(x+bonusX), (int)(buffer.getHeight()-bonusY-y-tr.getRegionHeight()/2-font.getCapHeight()/2));
			else font.draw(batch, text, x+bonusX, buffer.getHeight()-y-bonusY);
		}
	}

}
