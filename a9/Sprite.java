package a9;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * An object with an image representation that lives on a 2D grid.
 * 
 * This object supports moving around the grid, and can also check
 * whether it's overlapping with other sprites.
 * 
 * @author David Johnson and Travis Martin
 */
public class Sprite {

    // For details on these, see the constructor below.
	private int xPosition;
	private int yPosition;
	private int size;
	private BufferedImage image; // The display image for this sprite
	
	/**
	 * Creates a new sprite.
	 * 
	 * @param xPosition the starting x pixel (0 is the left)
	 * @param yPosition the starting y pixel (0 is the top)
	 * @param size the initial dimensions of this actor (width and height).
	 *             this is used both for the display size of this sprite
	 *             as well as its size for collision detection, unless 
	 *             isColliding is overridden.
	 * @param imgPath a path to the image file for this actor's picture
	 */
	public Sprite(int xPos, int yPos, int size, String imgPath) {
		setxPosition(xPos);
		yPosition = yPos;
		this.size = size;
		try {
			image = ImageIO.read(new File(imgPath));
		} catch (IOException e) {
			System.out.println("Could not load file: " + imgPath);
			System.exit(0);
		}
	}

	/**
	 * Returns this sprite's x pixel position on the 2D grid.
	 */
	public int getXPosition() {
		return getxPosition();
	}

	/**
	 * Returns this sprite's y pixel position on the 2D grid.
	 */
	public int getYPosition() {
		return yPosition;
	}
	
	/**
	 * Returns the width and height, in pixels, of this sprite.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Change the current position by offset amount, in pixels.
	 */
	public void shiftPosition(int xOffset, int yOffset) {
		setxPosition(getxPosition() + xOffset);
		yPosition += yOffset;
	}
	
	/**
	 * Draw the image at its position.
	 */
	public void draw(Graphics g) {
		g.drawImage(image, getxPosition(), yPosition, size, size, null);
	}
	
	
	/**
	 * See if point is within the rectangle from position to position + hitbox
	 * @param point
	 * @return true if the point is in the rectangle and false otherwise.
	 */
	public boolean isCollidingPoint(int x, int y) {
		return x >= getxPosition() && x <= getxPosition() + size && // within x range
			   y >= yPosition && y <= yPosition + size;   // within y range				
	}
	/**
	 * Checks if the hitbox of this sprite overlaps the hitbox of another sprite.
	 * The basic approach is to see if one is totally to the left, right, above, or 
	 * below the other sprite. If it is, it is not overlapping.
	 * @param other - the other sprite
	 * @return the collision status.
	 */
	public boolean isColliding(Sprite other) {
        if ((yPosition + size < other.yPosition) // See if this rectangle is above other
                || (yPosition > other.yPosition + other.size) // See if this rectangle is below other
                || (getxPosition() + size < other.getxPosition()) // See if this rectangle is left of other
                || (getxPosition() > other.getxPosition() + other.size)) // See if this rectangle is right of other
            return false;
		// If it is not above or below or left or right of the other, it is colliding.
		return true;
	}
	
	/**
	 * Checks if this sprite is colliding with any sprite in the provided list.
	 * This method will always use isColliding, so implementing custom collision detecting
	 * only requires overriding isColliding.
	 * @param others all other sprites to check
	 */
	public boolean isCollidingAny(ArrayList<? extends Sprite> others) {
	    // Follow the search loop pattern
	    for (Sprite other : others) {
	        if (other != this && isColliding(other)) {
	            return true;
	        }
	    }
	    return false;
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}
}
