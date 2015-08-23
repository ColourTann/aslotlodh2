package game.screens.gameScreen.entity;



import java.util.Collections;
import java.util.HashMap;

import game.Main;
import game.particles.BasicShot;
import game.screens.gameScreen.GameScreen;







import game.util.Colours;
import game.util.Draw;
import game.util.TextWisp;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Entity extends Actor{
	public enum Team{Left, Right}
	public enum Order{Move, AttackMove, Attack, Idle}
	public enum AttackPriority{Hero(1), Minion(3), Tower(2);
	public int priority;
	private AttackPriority(int priority) {
		this.priority=priority;
	}
	}
	protected Vector2 targetPosition= new Vector2();
	public Vector2 position= new Vector2();
	protected static int aggroRange=150;
	protected int range;
	protected int maxHp;
	protected int hp;
	public Team team;
	protected float secondsPerShot=2;
	protected float secondsUntilShoot=0;
	public boolean dead;
	public boolean player;
	protected Order order=Order.AttackMove;
	protected Vector2 defaultAttackPosition;
	public AttackPriority attackPriority;
	float damage;
	protected static HashMap<Team, Vector2> targetPositions = new HashMap<Entity.Team, Vector2>();
	protected static HashMap<Team, Vector2> startPositions = new HashMap<Entity.Team, Vector2>();
	static{
		targetPositions.put(Team.Left, new Vector2(Main.width+50, Main.height/2));
		targetPositions.put(Team.Right, new Vector2(-50, Main.height/2));
		
		startPositions.put(Team.Left, new Vector2(0, Main.height/2));
		startPositions.put(Team.Right, new Vector2(Main.width, Main.height/2));
	}
	
	
	
	public Team getOpposingTeam(){
		switch(team){
		case Left: return Team.Right;
		case Right: return Team.Left;
		}
		return null;
	}
	
	public Entity getNearbyEntity(boolean friend, float maxDistance, boolean checkPriority, boolean closest){
		Entity best=null;
		float closestDistance=-1;
		Collections.shuffle(GameScreen.entities);
		for(Entity e : GameScreen.entities){
			if(e==this)continue;
			float distance =position.dst(e.position);
			
			if(checkPriority){
				if(best!=null&&best.attackPriority.priority<e.attackPriority.priority){
					best=e;
					closestDistance=distance;
					continue;
				}
			}
			
			if((e.team==this.team)==friend&&
					distance<maxDistance){
				
				if(closest&&!(distance<closestDistance||closestDistance==-1)) continue;
				
				
				closestDistance=distance;
				best=e;
			}
		}
		return best;
	}
	
	public void moveTowards(Vector2 target, Order order){
		this.targetPosition=target;
		this.order=order;
	}
	
	protected void setHP(int hp) {
		maxHp=hp;
		this.hp=hp;
	}

	protected void cleanup(){
		dead=true;
		remove();
		GameScreen.entities.remove(this);
	};
	
	public void drawHPBar(Batch batch, float yOffset){
		int hpSize=10;
		batch.setColor(Colours.orange);
		Draw.fillRectangle(batch, (int)(position.x-hpSize/2), (int)(position.y+yOffset), hpSize, 1);
		batch.setColor(Colours.red);
		Draw.fillRectangle(batch, (int)(position.x-hpSize/2), (int)(position.y+yOffset), hpSize*hp/(float)maxHp, 1);
	}

	public void damage(int amount) {
		hp-=amount;
//		GameScreen.get().addParticle(new TextWisp(""+amount, Colours.red, (int)position.x, (int)(position.y+getHeight())));
		if(hp<=0)cleanup();
	}
	
	public void heal(int amount) {
		int hpLeft=maxHp-hp;
		amount=Math.min(hpLeft, amount);
		hp+=amount;
		if(amount==0)return;
		GameScreen.get().addParticle(new TextWisp(""+amount, Colours.green, (int)position.x, (int)(position.y+getHeight())));
	}
	
	
}
