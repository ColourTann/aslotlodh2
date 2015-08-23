package game.screens.gameScreen.entity.hero;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import game.Main;
import game.screens.gameScreen.GameScreen;
import game.screens.gameScreen.entity.Entity;
import game.screens.gameScreen.entity.Minion;
import game.screens.gameScreen.entity.Entity.Team;
import game.screens.gameScreen.entity.hero.ability.Ability;
import game.util.Draw;
import game.util.Noise;

public abstract class Hero extends Entity{
	static final float speed=50;
	static final float range = 180;
	static float noiseFreq=1;
	static float noiseAmp=3;
	float noMove=0;
	float noiseOffset=(float) (Math.random()*1000);
	public Hero enemyHero;
	Vector2 idealPosition;
	static float noMoveTime=1f;
	float ticks;
	Array<Ability> abilities = new Array<Ability>();
	TextureRegion texture;
	TextureRegion[] animation = new TextureRegion[3];
	public Hero(Team team) {
		defaultAttackPosition=targetPositions.get(team);
		moveTowards(defaultAttackPosition, Order.AttackMove);
		idealPosition=startPositions.get(team).cpy();
		position=idealPosition.cpy();
		setHP(90);
		this.team=team;
		secondsPerShot=1;
		attackPriority=AttackPriority.Hero;
	}

	public void setEnemyHero(Hero h){
		enemyHero=h;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		for(Ability a:abilities)a.tick(delta);
		noMove-=delta;
		if(noMove<=0) move(delta);
		attack();
	}



	private void attack() {
		for(Ability a: abilities){
			if(a.available()){
				useAbility(a);
				break;
			}
		}



	}


	public void useAbility(Ability a){
		a.use();
		noMove=noMoveTime;
	}


	public void move(float delta){
		ticks+=delta;
		Vector2 targetPosition = findBestSpot();

		Vector2 subtracted = targetPosition.cpy().sub(idealPosition);
		float toMoveThisTurn = delta*speed;

		Vector2 direction = targetPosition.cpy().sub(idealPosition).nor();
		if(subtracted.len()<toMoveThisTurn){

		}
		else{
			idealPosition.mulAdd(direction, delta*speed);
		}
		position=idealPosition.cpy();
		juke();

		// if targeted by a tower, move back to middle
		// if no minions, retreat to towers 
	}



	private Vector2 findBestSpot() {

		Vector2 furthest = null;
		for(Entity e:GameScreen.get().entities){
			if(e instanceof Minion && e.team==team &&!e.player){

				if(furthest==null||
						(e.position.x>furthest.x)==(team==Team.Left)){
					furthest=e.position;
				}



			}
		}

		if(furthest==null) return startPositions.get(team);

		furthest=furthest.cpy();

		int multiplier = team==Team.Left?-1:1;
		furthest.x+=50*multiplier;

		return furthest;
	}

	private void juke() {
		position.x+=Noise.noise(ticks*noiseFreq+noiseOffset)*noiseAmp;
		position.y+=Noise.noise(ticks*noiseFreq+noiseOffset*2)*noiseAmp*3;
	}


	public void draw(Batch batch, float parentAlpha){
		preDraw(batch);
		
		batch.setColor(1,1,1,1);
		int xFlip =1;
		if(team==Team.Left)xFlip=-1;
		Draw.drawScaled(batch, texture, (int)(position.x-getWidth()/2*xFlip), (int)position.y, xFlip, 1);
		if(noMove>0){
			int frame =(int) (Math.sin(noMove*Math.PI)*3);
			Draw.drawScaled(batch, animation[frame], (int)(position.x-getWidth()/2*xFlip), (int)position.y, xFlip, 1);
		}
		
		drawHPBar(batch,-2);
	}

	public void push(float x, float y, int magnitude) {
		Vector2 diff = position.cpy().sub(x, y).nor();
		idealPosition.add(diff.scl(magnitude));
	}
	
	public abstract void preDraw(Batch batch);

}

