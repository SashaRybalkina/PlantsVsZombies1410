package a9;

/**
 * A very basic Zombie actor. Variants on this class can be created
 * by varying the constructor parameters or by subclassing this.
 */
public class Zombie extends Actor {
    /**
     * Creates a zombie. For parameter descriptions, see Actor.
     */
	public Zombie(int xPosition, int yPosition, int size, String imgPath, int health,
	        int coolDown, int speed, int attackDamage) {
		super(xPosition, yPosition, size, imgPath, health, coolDown, speed, attackDamage);
	}
	
	/**
	 * An attack only happens when two hitboxes are overlapping and the
	 * Zombie is ready to attack again (based on its cooldown).
	 * 
	 * Zombies only attack Plants.
	 */
	
	@Override
	public void actOn(Plant other) {
		if (isColliding(other)) {
			if (isReadyForAction()) {
				other.changeHealth(-attackDamage);
				resetCoolDown();
			}
		}
	}
	
	@Override
	public void actOn(Bullet other) {
		if (isColliding(other)) {
			if (isReadyForAction()) {
				other.changeHealth(-attackDamage);
				resetCoolDown();
			}
		}
	}
	
	@Override
	public void actOn(Zombie other) {
	    // Do nothing
	}
	
	/**
	 * Overrides the sprite logic to allow Zombies to overlap
	 * with other zombies.
	 */
	@Override
	public boolean isColliding(Sprite other) {
	    return !(other instanceof Zombie) && super.isColliding(other);
	}
}