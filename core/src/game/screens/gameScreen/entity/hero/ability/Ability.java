package game.screens.gameScreen.entity.hero.ability;

import com.badlogic.gdx.math.Vector2;

import game.screens.gameScreen.entity.hero.Hero;

public abstract class Ability {
	int range;
	float currentCooldown;
	final float cooldown;
	Hero hero;
	public Ability(Hero hero, float cooldown) {
		this.hero=hero;
		this.cooldown=cooldown;
		currentCooldown=cooldown;
	}
	
	public void tick(float delta){
		currentCooldown-=delta;
	}
	public void use(){
		currentCooldown=cooldown;
		activate();
	}
	public boolean available(){
		return currentCooldown<=0;
	}
	abstract void activate();
}
