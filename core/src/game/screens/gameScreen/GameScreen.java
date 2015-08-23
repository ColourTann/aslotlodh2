package game.screens.gameScreen;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

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
import game.util.Mouse;
import game.util.Screen;
import game.util.TextBox;

public class GameScreen extends Screen{


	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	public static ArrayList<Hero> heroes= new ArrayList<Hero>();
	private static GameScreen self;
	TextBox tb;
	Minion player;
	public static GameScreen get(){
		if(self==null) self= new GameScreen();
		return self;
	}

	public GameScreen() {
		for(Team t: Team.values()){
//			Tower tower = new Tower(t);
//			addEntity(tower);
			Hero h=null;
			if(t==Team.Left)h=new Sorceress(t);
			else h=new Rockman(t);
			heroes.add(h);
			addEntity(h);
		}
		spawnWave();

		for(int i=0;i<2;i++)heroes.get(i).setEnemyHero(heroes.get(1-i));
		
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(player==null||player.dead)return false;
				Order order = null;
				if(button==0)order=Order.Move;
				if(button==1)order=Order.AttackMove;
				player.moveTowards(new Vector2(Mouse.getX(), Mouse.getY()), order);
				
				
				
//				areaDamage(Mouse.getX(), Mouse.getY(), 20, 20, Team.Left);
				
				return false;
			}
		});

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
			if(t==Team.Left){
				if(player==null||player.dead){
					player= (Minion) entities.get(entities.size()-4);
					player.setPlayer();
				}
			}
		}
		for(Hero h:heroes)h.toFront();

		
	}

	@Override
	public void preDraw(Batch batch) {
		batch.setColor(Colours.brown);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		int gap=20;
		batch.setColor(Colours.greenDark);
		Draw.fillRectangle(batch, getX(), getY()+gap, getWidth(), getHeight()-gap*2);
	}

	@Override
	public void postDraw(Batch batch) {

	}

	float ticks=0;
	@Override
	public void preTick(float delta) {
		ticks+=delta;
		if(ticks>=12){
			spawnWave();
			ticks=0;
		}

	}

	@Override
	public void postTick(float delta) {
		
	}

	public void areaDamage(float x, float y, int radius, int damage, Team team) {
//		addParticle(new AOEDebug(x, y, radius, Colours.red));
		for(int i=entities.size()-1;i>=0;i--){
			Entity e = entities.get(i);
			if(e.team==team){
				if(e.position.dst(x, y)<radius){
					e.damage(damage);
				}
			}
		}
	}

}
