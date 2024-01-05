package a9;

import java.util.Random;

public class AlphaChadZombie extends Zombie{

	public AlphaChadZombie(int xPosition, int yPosition, int size, String imgPath, int health, int coolDown, int speed,
			int attackDamage) {
		super(xPosition, yPosition, 9*size/4, imgPath, health*2, coolDown, speed*3, 5*attackDamage/4);
	}
	
	/**
	 * Does twice as much damage to the plant objects as a normal zombie.
	 */
	@Override
	public void actOn(Plant other) {
		if (isColliding(other)) {
			if (isReadyForAction()) {
				other.changeHealth(-attackDamage*2);
				resetCoolDown();
			}
		}	
	}
	
	/**
	 * Creates a random y speed for the zombie, allowing it to run either straight or diagonally.
	 */
	Random generator = new Random();
	int ySpeed = generator.nextInt(-1, 2);
	
	/**
	 * Moves the zombie subclass in the new direction given by the y speed.
	 */
	@Override
	public void move() {
		shiftPosition(this.getSpeed(), ySpeed);
	}

}
