package game.screens.gameScreen.entity;

import com.badlogic.gdx.graphics.g2d.Batch;

import game.Main;
import game.particles.MinionShot;
import game.screens.gameScreen.GameScreen;
import game.screens.gameScreen.entity.Entity.AttackPriority;
import game.screens.gameScreen.entity.Entity.Team;
import game.util.Colours;
import game.util.Draw;

public class Tower extends Entity{

	public Tower(Team team) {
		attackPriority=AttackPriority.Tower;
		this.team=team;
		range=150;
		setHP(45);
		int gap=50;
		switch(team){
		case Left:
			position.x=gap;
			break;
		case Right:
			position.x=Main.width-gap;
			break;
		default:
			break;
		}
		position.y=Main.height/2;
	}



	@Override
	public void act(float delta) {
		super.act(delta);
		secondsUntilShoot-=delta;
		if(secondsUntilShoot<=0){
			Entity enemy = getNearbyEntity(false, range, true, true);
			if(enemy!=null){
				GameScreen.get().addParticle(new MinionShot((int)position.x, (int)position.y, enemy, 5));
				secondsUntilShoot=1;
			}
		}
	}



	@Override
	public void draw(Batch batch, float parentAlpha) {

		if(team==Team.Left)batch.setColor(Colours.blue);
		else batch.setColor(Colours.green);

		Draw.drawCenteredScaled(batch, Draw.getSq(), position.x, position.y, 45, 150);
		int hpSize=40;
		batch.setColor(Colours.orange);

		Draw.fillRectangle(batch, (int)(position.x-hpSize/2), (int)(position.y-10), hpSize*hp/(float)maxHp, 3);
	}
}
