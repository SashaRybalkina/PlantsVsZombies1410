package a9;

public class Plant2 extends Plant{

	public Plant2(int xPosition, int yPosition, int size, String imgPath, int health, int coolDown, int attackDamage) {
		super(xPosition, yPosition, size, imgPath, health, coolDown, attackDamage);	
	}
	// This subclass is empty for a reason. A Plant2 object is only supposed to
	// spawn three bullets near it, with the logic already done in Game.
}