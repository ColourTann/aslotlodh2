package game.screens.gameScreen.entity;

import java.util.HashMap;

import javax.swing.text.AbstractDocument.LeafElement;

import game.Main;
import game.particles.MinionShot;
import game.screens.gameScreen.GameScreen;
import game.util.Colours;
import game.util.Draw;
import game.util.Particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Minion extends Entity{




	public enum MinionType{Ranged, Melee}	
	static float speed=50;
	static float wiggleFactor=1.3f;
	static float wiggleRange=12;
	float offset=(float) (Math.random()*1000);
	public MinionType type;
	static int randomFactor=5;

	public Minion(MinionType type, Team team) {
		setSize(16, 16);
		attackPriority=AttackPriority.Minion;
		this.type=type;
		this.team=team;

		position=startPositions.get(team).cpy();

		switch(type){
		case Melee:
			damage=2;
			secondsPerShot=.5f;
			setHP(40);
			range=10;
			position.x+=team==Team.Left?50:-50;
			break;
		case Ranged:
			damage=4;
			secondsPerShot=2;
			setHP(30);
			range=70;
			break;
		default:
			break;

		}

		defaultAttackPosition=targetPositions.get(team);
		position.add(Particle.rand(-randomFactor, randomFactor), Particle.rand(-randomFactor, randomFactor));
		moveTowards(defaultAttackPosition, Order.AttackMove);
		secondsUntilShoot=secondsPerShot;
	}


	public void setPlayer(){
		player=true;
	}








	float ratio;
	@Override
	public void act(float delta) {
		super.act(delta);
		ratio = secondsUntilShoot/secondsPerShot;
		secondsUntilShoot-=delta;
		if(secondsUntilShoot<0)secondsUntilShoot=0;
		Entity friend = getNearbyEntity(true, wiggleRange, false, true);
		if(friend!=null&&friend instanceof Minion){
			Vector2 dv = friend.position.cpy().sub(position);
			position.mulAdd(dv, -delta*wiggleFactor);
			friend.position.mulAdd(dv, delta*wiggleFactor);

		}
		switch(order){

		case AttackMove:
		case Idle:
			Entity enemy = getNearbyEntity(false, aggroRange, false, true);
			if(enemy!=null){
				targetPosition=enemy.position;
				if(position.dst(enemy.position)<=range){


					if(secondsUntilShoot<=0){

						int multiplier = team==Team.Left?-1:1;

						secondsUntilShoot=secondsPerShot*Particle.rand(.9f, 1.1f);
						switch(type){
						case Melee:
							enemy.damage((int)damage);
							setRotation(.3f*multiplier);
							addAction(Actions.rotateTo(0, .2f));
							break;
						case Ranged:
							GameScreen.get().addParticle(new MinionShot((int)position.x, (int)position.y, enemy, (int)damage));
							setRotation(.3f*-multiplier);
							addAction(Actions.rotateTo(0, .2f));
							break;
						default:
							break;

						}
					}
					return;

				}
			}
			else{
				if(!player)targetPosition=defaultAttackPosition;
			}
			break;

		case Move:
			break;
		default:
			break;

		}

		if(targetPosition==null)return;

		Vector2 subtracted = targetPosition.cpy().sub(position);
		float toMoveThisTurn = delta*speed;
		if(subtracted.dst(0, 0)<toMoveThisTurn){
			position=targetPosition;
			targetPosition=null;
			order=Order.Idle;
			return;
		}
		Vector2 direction = targetPosition.cpy().sub(position).nor();
		position.mulAdd(direction, delta*speed);
	}




	static HashMap<Team, Color> colourMap = new HashMap<Team, Color>();
	static HashMap<MinionType, TextureRegion> minionRegions = new HashMap<Minion.MinionType, TextureRegion>();
	static{
		minionRegions.put(MinionType.Melee, Main.atlas.findRegion("sword"));
		minionRegions.put(MinionType.Ranged, Main.atlas.findRegion("bow"));

		colourMap.put(Team.Left, Colours.red);
		colourMap.put(Team.Right, Colours.blue);
	}
	static TextureRegion tabard = Main.atlas.findRegion("tabard");
	static TextureRegion arrow= Main.atlas.findRegion("arrow");
	static float arrowFreq=6, arrowAmp=3;
	@Override
	public void draw(Batch batch, float parentAlpha) {
		int xFlip = team==Team.Right?-1:1;

		batch.setColor(Colours.white);
		Draw.drawRotatedScaled(batch, minionRegions.get(type), position.x-(getWidth()/2)*xFlip, position.y-getHeight()/2, xFlip, 1, getRotation());



		batch.setColor(colourMap.get(team));
		Draw.drawRotatedScaled(batch, tabard, position.x-(getWidth()/2)*xFlip, position.y-getHeight()/2, xFlip, 1, getRotation());
		drawHPBar(batch, -getHeight()/2);

		if(player){
			batch.setColor(1,1,1,1);
			Draw.drawCentered(batch, arrow, position.x, (float)(position.y+15+Math.sin(Main.ticks*arrowFreq)*arrowAmp));
		}
	}


	@Override
	public void die() {
		
	}
}
