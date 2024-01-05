package a9;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A top-level panel for playing a game similar to Plants Vs Zombies.
 * 
 * This panel is primarily responsible for coordinating the various
 * aspects of the game, including:
 * - Running the game step-by-step using a timer
 * - Creating and displaying other components that make up the game
 * - Creating new plants and/or zombies, when necessary
 * 
 * @author Travis Martin and David Johnson
 */

/**
 * THEME OF THE GAME
 * 
 * The theme of the game is feeding animals with cupcakes and candy bowls, 
 * with the candy bowls shooting out candy pieces when placed. The health 
 * bars of the "zombies" represent their hunger levels, while the health 
 * bars of the "plants" represent how much of the treat is left. The rainbow
 * cats may be easy to please, but the alpha chad dogs pose a greater
 * challenge, not only eating the food faster and being satisfied at a
 * slower rate, but also randomly moving either straight or diagonally.
 *
 */

@SuppressWarnings("serial")
public class Game extends JPanel implements ActionListener, MouseListener {
	private static final int NUM_ROWS = 5;
	private static final int NUM_COLS = 7;
	private static final int GRID_BUFFER_PIXELS = 20;
	private static final int CELL_SIZE = 75;
	private static final int STEP_TIME= 20;
	private Random generator = new Random();
	private JPanel starPanel;
	private ButtonPanel buttonPanel;
	private JLabel starCount;
	protected int star = 0;
	private int count = 0;
	private Timer timer;
	
	
	/**
	 * This panel is responsible for displaying plants
	 * and zombies, and for managing their interactions.
	 */
	private ActorDisplay actorDisplay = new ActorDisplay(
	        NUM_COLS * CELL_SIZE + GRID_BUFFER_PIXELS * 2,
	        NUM_ROWS * CELL_SIZE + GRID_BUFFER_PIXELS * 2, this);

	private Game() {
		
		buttonPanel = new ButtonPanel(this);
		starPanel = new JPanel();
		starCount = new JLabel("Star Coins: " + star);
		
		starPanel.add(starCount);
		add(starPanel);
		add(buttonPanel);
	    add(actorDisplay);
	    addMouseListener(this);
	    
	    // This layout causes all elements to be stacked vertically
	    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	    
		// The timer calls the actionPerformed method every STEP_TIME milliseconds
		timer = new Timer(STEP_TIME, this);
		timer.start();
		
		// This adds a plant to every row
		for (int i = 0; i < NUM_ROWS; i++) {
			actorDisplay.addActor(new Plant(
		            gridToPixel(0), gridToPixel(i), CELL_SIZE, 
		            "src/a9/Sprites/cupcake.png", 150, 5, 1));
		}
	}

	/**
	 * Executes game logic every time the timer ticks.
	 */
    @Override
    public void actionPerformed(ActionEvent e) {
    	actorDisplay.step();
    	int row = generator.nextInt(NUM_ROWS);
    	if (generator.nextInt(100) > 45) {
    		addZombie(7, row);
    	}
        
        count++;
        	
        if (count%50 == 0) {
        	star += 100;
        	starCount.setText("Star Coins: " + star);
        }
    }
    
    /**
     * Randomly places either zombie1 or zombie2 to the official game grid & display panel.
     */
	private void addZombie(int col, int row) {
		
		if ((generator.nextInt(250) > 248)) {
	    	actorDisplay.addActor(new AlphaChadZombie(
	    		gridToPixel(col), gridToPixel(row), CELL_SIZE, 
	    		"src/a9/Sprites/doge.png",
	    		100, 50, -2, 10));
		}
		
		else if ((generator.nextInt(150) > 148)) {
	    	actorDisplay.addActor(new Zombie(
	    		gridToPixel(col), gridToPixel(row), CELL_SIZE, 
	    		"src/a9/Sprites/cat.png",
	    		100, 50, -2, 10));
	    }   
	}
	
	/**
	 * 
	 * @param col: the pixel x position.
	 * @param row: the pixel y position.
	 * @return: a number of either 500 or 1000, and subtarcts that amount from the star currency count.
	 */
	protected int addPlant(int col, int row) {
		
		if(buttonPanel.getCurrentSelection() != null && buttonPanel.getCurrentSelection().equals("Plant1") && star >= 500) {
	    	if (actorDisplay.addActor(new Plant(
	            gridToPixel(col), gridToPixel(row), CELL_SIZE, 
	            "src/a9/Sprites/cupcake.png", 150, 5, 1))) {
	    		return 500;
	    	}
		}
		
		if(buttonPanel.getCurrentSelection() != null && buttonPanel.getCurrentSelection().equals("Plant2") && star >= 1000) {
	    	if (actorDisplay.addActor(new Plant2(
		        gridToPixel(col), gridToPixel(row), CELL_SIZE, 
		         "src/a9/Sprites/CandyBowl.png", 150, 5, 1))) {
	    		
	    		actorDisplay.addActor(new Bullet(
	    			gridToPixel(col+1), gridToPixel(row), CELL_SIZE,
	    		    "src/a9/Sprites/candy.png", 150, 5, 1));
	    		
	    		actorDisplay.addActor(new Bullet(
		    			gridToPixel(col), gridToPixel(row+1), CELL_SIZE,
		    		    "src/a9/Sprites/candy.png", 150, 5, 1));
	    		
	    		actorDisplay.addActor(new Bullet(
		    			gridToPixel(col), gridToPixel(row-1), CELL_SIZE,
		    		    "src/a9/Sprites/candy.png", 150, 5, 1));

	    		return 1000;
	    	}
		}
		
		return 0;
	}
	
	/**
	 * Converts a row or column to its exact pixel location in the grid.
	 */
	private int gridToPixel(int rowOrCol) {
	    return rowOrCol * CELL_SIZE + GRID_BUFFER_PIXELS;
	}
	
	/**
	 * The inverse of gridToPixel
	 */
	protected int pixelToGrid(int xOrY) {
	    return (xOrY - GRID_BUFFER_PIXELS) / CELL_SIZE;
	}
	
	public void updateLabel(String update) {
		starCount.setText(update);
	}
	
	/**
	 * Create, start, and run the game.
	 */
	public static void main(String[] args) {
        JFrame app = new JFrame("Feed the fluffies!");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(new Game());
        app.pack();
        app.setVisible(true);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {	
	}

	/**
	 * Stops timer and displays a game over message
	 * @param command: command to stop the game
	 */
	public void gameOver(int command) {
		if(command == 1) {
			starCount.setText("Game Over");
			timer.stop();
		}
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
		
	}
}