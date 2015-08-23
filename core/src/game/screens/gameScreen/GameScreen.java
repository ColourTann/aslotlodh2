package game.screens.gameScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

import game.Main;
import game.particles.AOEDebug;
import game.particles.Swirler;
import game.screens.gameScreen.entity.Entity;
import game.screens.gameScreen.entity.Entity.Order;
import game.screens.gameScreen.entity.Minion;
import game.screens.gameScreen.entity.Entity.Team;
import game.screens.gameScreen.entity.Minion.MinionType;
import game.screens.gameScreen.entity.Tower;
import game.screens.gameScreen.entity.hero.Hero;
import game.screens.gameScreen.entity.hero.Rockman;
import game.screens.gameScreen.entity.hero.Sorceress;
import game.util.Colours;
import game.util.Draw;
import game.util.Fonts;
import game.util.Mouse;
import game.util.Screen;
import game.util.ScrollingText;
import game.util.Sounds;
import game.util.TextBox;

public class GameScreen extends Screen{


	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	public static ArrayList<Hero> heroes= new ArrayList<Hero>();
	public static GameScreen self;
	TextBox tb;
	Minion player;
	public ScrollingText st = new ScrollingText();


	static{
		TextBox.setImage("archer", Main.atlas.findRegion("player"));
	}
	TextBox tutorial;
	public GameScreen(boolean rocko) {
		Sounds.playMusic(Sounds.get("loop", Music.class));
		self=this;
		for(Team t: Team.values()){
			Tower tower = new Tower(t);
			addEntity(tower);
			Hero h=null;
			if((t==Team.Left)!=rocko)h=new Sorceress(t);
			else h=new Rockman(t);
			heroes.add(h);
			h.respawn();
		}
		spawnWave();
		st.setPosition(5, Fonts.font.getLineHeight());
		addActor(st);
		for(int i=0;i<2;i++)heroes.get(i).setEnemyHero(heroes.get(1-i));

		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		
				if(player==null||player.dead)return false;
				if(button==0){
					((Minion)player).untarget();
					player.moveTowards(new Vector2(Mouse.getX(), Mouse.getY()), Order.Move);
				}
				if(button==1){
					((Minion)player).untarget();
					Vector2 pos = new Vector2(Mouse.getX(), Mouse.getY());
					Entity closest=null;
					float bestDist=0;
					for(Entity e: entities){
						if(e.team==player.team)continue;
						float dist =pos.dst(e.position);
						if(bestDist==0|| dist<bestDist){
							closest=e;
							bestDist=dist;
							
						}
					}
					player.attack(closest);
				}


				return false;
			}
		});


		tutorial = new TextBox("This is you:   [archer][n][n]Left click to move[n]Right click an enemy to attack[n]Destroy the enemy tower to win![n]Click this to continue", 200);
		addActor(tutorial);
		tutorial.addClickAction(new Runnable() {
			
			@Override
			public void run() {
				pause(false);
				tutorial.remove();
				tutorial=null;
			}
		});
		tutorial.setPosition(Main.width/2-tutorial.getWidth()/2, Main.height-tutorial.getHeight());
		pause(true);

	}


	private void addEntity(Entity e) {
		entities.add(e);
		addActor(e);
	}

	private void spawnWave() {
		for(Team t: Team.values()){
			for(int i=0;i<6;i++){
				Minion m = new Minion(Minion.MinionType.values()[i/3], t);
				addEntity(m);
			}
		}
	}

	static int gap=50;
	public static int getMid(){
		return (Main.height-gap)/2+gap;
	}
	
	@Override
	public void preDraw(Batch batch) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(Colours.brown);
		Draw.fillRectangle(batch, getX(), getY()+gap, getWidth(), getHeight()-gap);



	}

	@Override
	public void postDraw(Batch batch) {

	}

	float ticks=0;



	@Override
	public void preTick(float delta) {
		if(Math.random()>.997){
			st.addRandomMessage();
		}
		ticks+=delta;
		if(ticks>=12){
			spawnWave();
			ticks=0;
		}
		Collections.sort(entities, Entity.entityComparator);
		for(Entity e:entities)e.toFront();

	}

	@Override
	public void postTick(float delta) {
		if(player==null||player.dead){
			findNewPlayer();
		}

	}

	public void areaDamage(float x, float y, int radius, int damage, Team team) {
		for(int i=entities.size()-1;i>=0;i--){
			Entity e = entities.get(i);
			if(e.team==team){
				if(e.position.dst(x, y)<radius){
					e.damage(damage);
				}
			}
		}
	}

	public static void findNewPlayer() {
		for(Entity e:entities){
			if(e.team!=Team.Left)continue;
			if(e instanceof Minion){
				Minion m = (Minion)e;
				if(m.type==MinionType.Ranged){
					m.setPlayer();
					self.player=m;
					return;
				}
			}
		}
	}

	public void teamDefeated(Team team) {
		pause(true);
		String title = "";
		if(team==Team.Left){
			title="Defeat";
		}
		else{
			title="Victory!";
		}
		TextBox tb = new TextBox(title);
		tb.setPosition(Main.width/2, Main.height/2, Align.center);
		addActor(tb);
		TextBox restart = new TextBox("press esc to restart");
		restart.setPosition(Main.width/2, Main.height/4, Align.center);
		addActor(restart);

	}

}
