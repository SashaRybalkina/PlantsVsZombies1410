package a9;

public class Bullet extends Plant{

	public Bullet(int xPosition, int yPosition, int size, String imgPath, int health, int coolDown, int attackDamage) {
		super(xPosition, yPosition, size * 3/5, imgPath, health/2, coolDown, attackDamage);
	}

	/**
	 * Responsible for damaging any Zombie object.
	 */
	@Override
	public void actOn(Zombie other) {
		if (isColliding(other)) {
			if (isReadyForAction()) {
				other.changeHealth(-attackDamage);
				resetCoolDown();
			}
		}	
	}
	
	/**
	 * Allows for the bullet to move despite being a Plant subclass.
	 */
	@Override
	public void move() {
		shiftPosition(2, 0);
	}

}
