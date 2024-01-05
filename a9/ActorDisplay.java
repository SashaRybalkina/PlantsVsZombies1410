package a9;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import a9.Actor;

@SuppressWarnings("serial")
public class ActorDisplay extends JPanel implements MouseListener {
    /** Contains all plants and zombies in this game. */
	private ArrayList<Actor> actors = new ArrayList<>();
	private BufferedImage background;
	private Game parent;
	private int xPlacement;
	private int yPlacement;

	/**
	 * Creates a canvas upon which all actors will live.
	 * @param colPixels the number of pixels that this panel is wide
	 * @param rowPixels the number of pixels that this panel is high
	 */
	public ActorDisplay(int colPixels, int rowPixels, Game game) {
		setPreferredSize(new Dimension(colPixels, rowPixels));
		parent = game;
		addMouseListener(this);
		try {
			background = ImageIO.read(new File("src/a9/Sprites/sky.jpeg"));
		} catch (IOException e) {
			System.out.println("Could not load file");
			System.exit(0);
		}
	}
	
	/**
	 * Adds an actor to the master list of actors ONLY IF
	 * the provided actor is not colliding with any of the existing
	 * actors.
	 * @param actor the object to add
	 * @return false if something prevents the actor from being added, true otherwise
	 */
	public boolean addActor(Actor actor) {
	    if (actor.isCollidingAny(actors)) {
	        return false;
	    }
        actors.add(actor);
        return true;
	}

	/**
	 * This overrided method draws the details of this particular panel,
	 * including all actors that are contained within.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background,  0,  0,  null);
		for (Actor actor : actors) {
			actor.draw(g);
		}
	}

	/**
	 * Executes all of the actor logic that happens in one turn, including
	 * moving actors, checking for collisions, managing attacks, and more.
	 */
	public void step() {
		// Increment actor cooldowns.
		for (Actor actor : actors) {
			actor.update();
		}

		// Allow all actors to interact with all other actors.
		// This is where attacks, healing, etc happen.
		for (Actor actor : actors) {
			for (Actor other : actors) {
					actor.actOn(other);
			}
		}

		// Remove plants and zombies with low health
		ArrayList<Actor> nextTurnActors = new ArrayList<>();
		for (Actor actor : actors) {
			if (actor.isAlive())
				nextTurnActors.add(actor);
			else
				actor.removeAction(actors); // Execute any special effects for dead actors
		}
		actors = nextTurnActors;

		// Move the (alive) actors that are not colliding.
		for (Actor actor : actors) {
		    if (!actor.isCollidingAny(actors)) {
		        actor.move();
		        if (actor.getXPosition() < 1) {
					parent.gameOver(1);
				}
		    }
		}	
		
		// Redraw the scene.
		repaint();}

	/**
	 * Subtracts star amount from the star counter based on which plant was selected.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		xPlacement = parent.pixelToGrid(e.getX());
		yPlacement = parent.pixelToGrid(e.getY());
		
		parent.star -= parent.addPlant(xPlacement, yPlacement);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}}
		
