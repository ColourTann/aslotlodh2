package game.screens.gameScreen.entity.hero;


import game.screens.gameScreen.GameScreen;
import game.screens.gameScreen.entity.Entity;
import game.screens.gameScreen.entity.Minion;
import game.screens.gameScreen.entity.hero.ability.Ability;
import game.util.Draw;
import game.util.Noise;
import game.util.ScrollingText.Username;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;

public abstract class Hero extends Entity{
	public Username username;
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
	public TextureRegion texture;
	TextureRegion[] animation = new TextureRegion[3];
	public String heroName;
	static final float respawnTime=10;

	public Hero(Team team) {
		setTeam(team);
		secondsPerShot=1;
		attackPriority=AttackPriority.Hero;
//		respawn();
	}

	public void setTeam(Team team){
		defaultAttackPosition=targetPositions.get(team);		
		this.team=team;
	}

	public void respawn() {
		System.out.println("respawning");
		dead=false;
		moveTowards(defaultAttackPosition, Order.AttackMove);
		idealPosition=startPositions.get(team).cpy();
		position=idealPosition.cpy();
		setHP(60);
		GameScreen.self.addActor(this);
		GameScreen.self.entities.add(this);
		System.out.println(position+":"+idealPosition);
	}

	public void setEnemyHero(Hero h){
		enemyHero=h;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		currentGlobalCooldown-=delta;
		for(Ability a:abilities)a.tick(delta);
		noMove-=delta;
		if(noMove<=0) move(delta);
		attack();
	}



	private void attack() {
		if(currentGlobalCooldown>0)return;
		for(Ability a: abilities){

			if(useAbility(a))break;

		}
	}


	String[] deathMessage = new String[]{"lol noob", "thx for the gems, idiot", "gg ez ez"};
	public void die(){
		GameScreen.self.st.addText(enemyHero.username, deathMessage[(int) (Math.random()*deathMessage.length)]);
		GameScreen.self.addAction(Actions.delay(respawnTime,  Actions.run(new Runnable() {

			@Override
			public void run() {
				Hero.this.respawn();
			}
		})));
	}


	final float globalCooldown=2;
	float currentGlobalCooldown;
	public boolean useAbility(Ability a){

		boolean b = a.use();
		if(b){
			currentGlobalCooldown=globalCooldown;
			noMove=noMoveTime;
		}
		return b;

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
		for(Entity e:GameScreen.self.entities){
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
		Draw.drawScaled(batch, texture, (float)(position.x-getWidth()/2*xFlip), (float)position.y, xFlip, 1);
		if(noMove>0){
			
			int frame =(int) (Math.sin(noMove*Math.PI)*3);
			if(frame>2)frame=2; if(frame<0)frame=0;
		
			Draw.drawScaled(batch, animation[frame], (float)(position.x-getWidth()/2*xFlip), (float)position.y, xFlip, 1);
		}

		drawHPBar(batch,-2);
		if(targeted)drawReticule(batch, 0);
	}

	public void push(float x, float y, int magnitude) {
		Vector2 diff = position.cpy().sub(x, y).nor();
		idealPosition.add(diff.scl(magnitude));
	}

	public abstract void preDraw(Batch batch);

}

