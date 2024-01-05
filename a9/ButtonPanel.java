package a9;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import a9.Game;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel implements ActionListener{
    private JToggleButton Plant1 = new JToggleButton("Cupcake");
    private JToggleButton Plant2 = new JToggleButton("Candy Bowl");
    private JPanel panel;
    private String currentSelection;
	
    public ButtonPanel(Game parent) {
    	super();
        
        panel = new JPanel();
        
        Plant1.addActionListener(this);
        Plant2.addActionListener(this);
        
        panel.add(Plant1);
        panel.add(Plant2);
        
        ButtonGroup group = new ButtonGroup();
        group.add(Plant1);
        group.add(Plant2);
        
        this.add(panel);
    }
    
    //Gets current button selection.
    public String getCurrentSelection() {
    	return currentSelection;
    }
    
    /**
     * Sets currentSelection to represent the button that was pressed.
     */
    @Override
	public void actionPerformed(ActionEvent e) {
    	if (Plant1.isSelected()) {
			currentSelection = "Plant1";
			
    	}
		if (Plant2.isSelected()) {
			currentSelection = "Plant2";
		}
    }
		
}
