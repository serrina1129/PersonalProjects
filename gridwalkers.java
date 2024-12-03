package GridWalker2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class gridwalkers implements ActionListener{
	
	private JButton button; //used to create all of the buttons on the JPanel
	private JButton[][] grid; // used to store the JButtons and keep track of them
	private int cols; //lets the program know how many columns to put in the grid
	private int rows; //lets the program know how many rows to put in the grid
	private ArrayList<JButton> path = new ArrayList<JButton>(); //used to keep track of all the buttons we visited on the path
	
	
	public gridwalkers() {
		// Ask user for number of rows
		String row = JOptionPane.showInputDialog(null, "Enter number of rows:");
        rows = Integer.parseInt(row);
        // Ask user for number of columns
        String col = JOptionPane.showInputDialog(null, "Enter number of columns:");
        cols = Integer.parseInt(col);
		//Create the JFrame 
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Grid Walker");

        //Created a JPanel size rows x columns and add it to the JFrame
		JPanel plane = new JPanel();
		plane.setLayout(new GridLayout(rows, cols));
		frame.getContentPane().add(plane);
		//create a 2d array of buttons to correspond to the JPanel
		grid = new JButton[rows][cols];
		int randrow = (int) (Math.random() * rows);
		int randcol = (int) (Math.random() * cols);
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < cols; c++) {
                if (r == randrow && c == randcol) {
                    //if the current row and column match the random generated row and column, place the target
                    button = new JButton("\u25CE");
                    button.addActionListener(this);
					button.setActionCommand("target");
					plane.add(button);
                    
                }
                else{
                    // Once the target button is placed the rest of the buttons are placed randomly based on a random generated number
    				int rand = (int) (Math.random()*4);	
                		//left arrow
                        if(rand==0) {
	                        button = new JButton("\u2190");
	                        button.addActionListener(this);
	                        button.setActionCommand("left");
	                        plane.add(button);
                        }
                       	//up arrow
                       	else if(rand==1) {
	                       	button = new JButton("\u2191");
	                        button.addActionListener(this);
	                        button.setActionCommand("up");
	                        plane.add(button);   
                       	}
                        //right arrow
                      	else if(rand==2) {
                      		button = new JButton("\u2192");
                        	button.addActionListener(this);
                        	button.setActionCommand("right");
                        	plane.add(button);
                       	}
                       	//down arrow
                       	else if(rand==3) {
                       		button = new JButton("\u2193");
                            button.addActionListener(this);
                            button.setActionCommand("down");
                            plane.add(button);
                        }
                }
                //fill the grid array with the random buttons
                grid[r][c] = button;
			}
		}
		plane.setVisible(true);
		frame.add(plane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int steps = 0;
		JButton click = (JButton) e.getSource();
		//set clicked button to pink
		Color pink = new Color(255, 0, 255);
		click.setBackground(pink);
		click.setBorderPainted(false);
		click.setOpaque(true);
		//set the start row and column to a default value 
	    int stRow=0;
	    int stCol=0;
	    // Find the clicked button's location in the grid
	    for (int r = 0; r< rows; r++) {
	        for (int c = 0; c < cols; c++) {
	            if (click == grid[r][c]) {
	                stRow = r;
	                stCol = c;
	                break;
	            }
	        }
	    }
	    //set boolean value to true to keep the while loop going
	    boolean playing = true;
	    while(playing) {
	    	//if you are at an up button move up a row
		    if (click.getActionCommand().equals("up")) {	
		    	stRow--;
		    } 
		    //if you are at a down button move down a row
		    else if (click.getActionCommand().equals("down")) {
		    	stRow++;
		    } 
		    //if you are at a left button move left a column
		    else if (click.getActionCommand().equals("left")) {
		    	stCol--;
		    } 
		    //if you are at a right button move right a column
		    else if (click.getActionCommand().equals("right")) {
		    	stCol++;
		    }
		    //if you reached the target button then you completed the path
		    else if (click.getActionCommand().equals("target")) {
		        click.setBackground(Color.CYAN);
		        JOptionPane.showMessageDialog(null, "You finished in " + steps + " steps.");
		        System.exit(0);
		    } 
		    //increase steps since you have taken a move
		    steps++;
		    //if you go out of bounds the print that you have left the grid
		    if( stRow == -1|| stRow == rows|| stCol == -1 || stCol == cols) {
		    	click.setBackground(Color.RED);
		    	JOptionPane.showMessageDialog(null, "You left the grid!");
		    }
		    //add the current button to the path list to keep track of all visited buttons
		    path.add(click);
		    // Update the click variable to the new button in the grid and set the color to pink
		    click = grid[stRow][stCol];
		    click.setBackground(pink);
		    click.setBorderPainted(false);
			click.setOpaque(true);
			//go through the path list to see if the button you are on was visited already. if it was then your path intersected itself
			for(JButton button: path) {
				if(button.equals(click)) {
					click.setBackground(Color.RED);
			    	JOptionPane.showMessageDialog(null, "Your path intersected itself!");
			    	playing = false;
				}
			}
	    }
	    System.exit(0);
	}
	
	public static void main(String[] args) {
		new gridwalkers();
	}
}