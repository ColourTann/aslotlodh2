package game.screens.gameScreen.entity;




import game.Main;
import game.screens.gameScreen.GameScreen;
import game.util.Colours;
import game.util.Draw;
import game.util.Functions;
import game.util.TextWisp;

import java.util.Comparator;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Entity extends Actor implements Comparable<Entity>{
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
	public boolean targeted;
	protected static HashMap<Team, Vector2> targetPositions = new HashMap<Entity.Team, Vector2>();
	protected static HashMap<Team, Vector2> startPositions = new HashMap<Entity.Team, Vector2>();
	static{
		targetPositions.put(Team.Left, new Vector2(Main.width+50, GameScreen.getMid()));
		targetPositions.put(Team.Right, new Vector2(-50, GameScreen.getMid()));
		
		startPositions.put(Team.Left, new Vector2(-50, GameScreen.getMid()));
		startPositions.put(Team.Right, new Vector2(Main.width+50, GameScreen.getMid()));
	}
	
	
	public int compareTo(Entity other) {
		return (int) (other.position.y*100-position.y*100);
	}
	
	public static Comparator<Entity> entityComparator 
    = new Comparator<Entity>() {




@Override
public int compare(Entity o1, Entity o2) {
	return o1.compareTo(o2);
}

};
	
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
		Functions.shuffle(GameScreen.self.entities);
		for(Entity e : GameScreen.self.entities){
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
		GameScreen.self.entities.remove(this);
	};
	
	public abstract void die();
	
	
	static TextureRegion reticule = Main.atlas.findRegion("target");
	public void drawReticule(Batch batch, float yOffset){
		batch.setColor(Colours.red);
		Draw.draw(batch, reticule,(position.x-reticule.getRegionWidth()/2), (position.y+getHeight()/2-10));
	}
	
	public void drawHPBar(Batch batch, float yOffset){
		int hpSize=10;
		batch.setColor(Colours.orange);
		Draw.fillRectangle(batch, (position.x-hpSize/2), (position.y+yOffset), hpSize, 1);
		batch.setColor(Colours.red);
		Draw.fillRectangle(batch, (position.x-hpSize/2), (position.y+yOffset), hpSize*hp/(float)maxHp, 1);
	}

	public void damage(int amount) {
		hp-=amount;
		if(hp<=0){
			cleanup();
			die();
		}
		
	}
	
	public void heal(int amount) {
		int hpLeft=maxHp-hp;
		amount=Math.min(hpLeft, amount);
		hp+=amount;
		if(amount==0)return;
		GameScreen.self.addParticle(new TextWisp(""+amount, Colours.green, (int)position.x, (int)(position.y+getHeight())));
	}

	Vector2 bonusPosition;
	public void push(float x, float y, int magnitude) {
		Vector2 diff = position.cpy().sub(x, y).nor();
		position.add(diff.scl(magnitude));
	}
	
	
}
