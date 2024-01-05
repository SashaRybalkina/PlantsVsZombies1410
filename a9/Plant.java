package a9;

/**
 * A very basic Plant actor. Variants on this class can be created
 * by varying the constructor parameters or by subclassing this.
 */
public class Plant extends Actor {
	
    /**
     * Creates a plant. For parameter descriptions, see Actor.
     */
	public Plant(int xPosition, int yPosition, int size, String imgPath, int health, int coolDown, int attackDamage) {
		super(xPosition, yPosition, size * 4/5, imgPath, health, coolDown, 0, attackDamage);
	}

	/**
	 * An attack only happens when two hitboxes are overlapping and the
	 * Plant is ready to attack again (based on its cooldown).
	 * 
	 * Plants only attack Zombies.
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
	
	@Override
	public void actOn(Plant other) {
	    // Do nothing		
	}

	@Override
	protected void actOn(Bullet other) {
		// Do nothing
		
	}
}
