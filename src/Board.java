

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;

/**
 * Board
 * 
 * This class holds the primary game panel, which is an 4 by 4 array
 */
@SuppressWarnings("serial")
public class Board extends JPanel {

	// the state of the game logic
	private int[][] values; //a 2D array that store the value of all tiles on the board
	private Tile[][] tiles; //a 2D array that store all tiles on the board

	//linked list that store the values of each move
	private LinkedList<int[][]> moves;
	
	public boolean playing = false; // whether the game is running 
	private JLabel status; // Current status text showing how many moves, i.e. "Moves: 0"
	// Game constants
	public static final int COURT_WIDTH = 410;
	public static final int COURT_HEIGHT = 410;

	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;



	public Board(JLabel status) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// The timer is an object which triggers an action periodically with the given INTERVAL. We
		// register an ActionListener with this timer, whose actionPerformed() method is called each
		// time the timer triggers. We define a helper method called tick() that actually does
		// everything that should be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key events are handled by its key listener.
		setFocusable(true);


		//initialize values
		initializeValues();
		//show instruction window
		showInstruction();


		// This key listener allows the tiles to move as an arrow key is pressed, by
		// moving the method according to the key. (The tick method below actually moves the
		// tiles.)
		//since the canvas display is different than the printed order, 
		//moving left is actually moving up in the GUI, and other keys change
		//accordingly
		addKeyListener(new KeyAdapter() {
				boolean moved = false;
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					//moveLeft();
					//left key=up
					moved = moveUp();

				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					//moveRight();
					//right key=down
					moved= moveDown();

				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					//down key=right
					//moveDown();
					moved = moveRight();

				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					//up key = left
					//moveUp();
					moved = moveLeft();
				}
				//after each move, generate new tile and store the current value array into the move list
				if(moved) {
				addValues();
				}
				generateTile();
				status.setText("Moves: " + (moves.size()-1));
				convertToTiles();
				
			}

		});

		this.status = status;

	}

	//construct and initialize a board for test state
	public Board () {
		values = new int[4][4];
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				values[i][j]= 0 ; 
			}
		}
		tiles = new Tile[4][4];
	}

	//initialize values
	public void initializeValues() {
		values = new int[4][4];
		generateTile();
		convertToTiles();
		moves = new LinkedList<int[][]>();
		addValues();
	}

	//construct a board with a given array
	public void setValues (int[][] v) {
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				values[i][j]= v[i][j] ; 
			}
		}
	}


	//add values to the list of moves
	public void addValues() {
		int[][] temp = new int[4][4];
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				temp[i][j] = values[i][j];
			}
		}
		moves.addFirst(temp);
	}

	//return a 2d array mapping the tile values
	public int[][] getValues() {
		return values;

	}

	public boolean arrayEquals() {
		for(int i=0; i<4; i++) {
			for(int j = 0; j<4; j++) {
				if(values[i][j] != moves.getFirst()[i][j]) {
					return false;
				}
			}

		}
		return true;
	}


	//initialize values
	public void convertToTiles() {
		tiles = new Tile[4][4];
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				tiles[i][j] = new Tile(i*100+10, j*100+10, values[i][j]);
			}
		}
	}

	//randomly generate tile during the start of the game and each time after move
	public void generateTile() {
		//only generate if there is empty tiles
		if(checkEmpty()) {
			int x = (int)(Math.random()*4);
			int y = (int)(Math.random()*4);
			int val = 0;
			//if the tile already has a value, generate a new coordinate 
			while(values[x][y]!=0)
			{
				x = (int)(Math.random()*4);
				y = (int)(Math.random()*4);
			}
			//90 percent of getting a 2 and 10 percent of getting a 4
			if(Math.random()<0.9){
				val = 2;
			} else {
				val = 4;
			}

			values[x][y]=val; //either 2 or 4
			convertToTiles();
		}
	}

	//if there is empty tiles, return true
	public boolean checkEmpty() {
		for(int x=0; x<4; x++) {
			for(int y=0; y<4; y++) {
				if(values[x][y]==0) {
					return true;
				}
			}	
		}
		return false;
	}


	public boolean moveLeft() {
		boolean moved = false;
		if(canMove()) {
			for(int x=0; x<4; x++) {
				//start from second column since first one can't move left anymore
				for(int y=1; y<4; y++) {
					//if two pairs of equal values, merge them and move on to the next row
					if(values[x][0]== values[x][1] && values[x][2]== values[x][3]) {
						values[x][0]=values[x][0]*2;
						values[x][1]=values[x][2]*2;
						values[x][2] = 0;
						values[x][3] = 0;
						moved = true;
						break;
					}
					if(!moved) {
					moved = compute(values[x]);
					} else {
					compute(values[x]);
					}
					//else, combine the first two equal values(if any) and slide the rest 
					//if the tile value on the left is the same, merge the tiles
					if(values[x][y-1] == values[x][y]) {
						values[x][y-1]= values[x][y-1]*2;
						values[x][y] = 0;
						compute(values[x]);
						moved = true;
						break;
					}
				}
			}
		}
		return moved;
		
	}

	//slide all none zero values in the row to the left
	public boolean compute(int[] values2){
		boolean moved = false;
		for(int i = 1; i<4; i++) {
			//if current value is 0 and left value is none 0
			if (values2[i-1] == 0 && values2[i] != 0){
				values2[i-1]= values2[i];
				values2[i]=0;
				//if previous 2 are 0
				if(i >= 2 && values2[i-2] == 0){
					values2[i-2]= values2[i-1];
					values2[i-1]=0;
				}
				//if previous 3 are 0
				if(i == 3 && values2[i-3] == 0){
					values2[i-3]= values2[i-2];
					values2[i-2]=0;
				}
				moved = true;
			}
		}
		return moved;
	}


	//rotate the board left 90 degree so other direction move could be performed
	public void rotateRight () {
		int [][]temp = new int[4][4];
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				temp[i][j] = values[i][j]; 
			}
		}

		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				values[i][j] = temp[j][3-i];	
			}
		}

	}

	//rotate the board right 90 degree so other direction move could be performed
	public void rotateLeft () {
		int [][]temp = new int[4][4];
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				temp[i][j] = values[i][j]; 
			}
		}

		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				values[i][j] = temp[3-j][i];	
			}
		}

	}

	public boolean moveUp () {
		rotateRight();
		boolean m = moveLeft();
		rotateLeft();
		return m;

	}

	public boolean moveDown () {
		rotateLeft();
		boolean m = moveLeft();
		rotateRight();
		return m;

	}

	public boolean moveRight() {
		rotateRight();
		rotateRight();
		boolean m = moveLeft();
		rotateRight();
		rotateRight();
		return m;
	}

	//check if there are any possible moves
	public boolean canMove () {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				//check any adjacent tiles can be combined
				if(checkEmpty()||values[i][j]== values[i][j+1]||
						values[i+1][j]== values[i+1][j+1]||
						values[i][j]== values[i+1][j]||
						values[i][j+1]== values[i+1][j+1]) {
					return true;
				}
			}
		}
		return false;
	}

	//check if one wins
	public boolean checkForWin () {
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				if(values[i][j]== 2048 ) {
					return true;
				}
			}
		}
		return false;
	}


	//output text file of the current 16 tile values, separate by a space
	public void outputFile(){
		//initialize the file name
		String fileName = "output.txt";
		try {
			//Prompt the user to get file name
			fileName = JOptionPane.showInputDialog("Enter file name here:");

			PrintWriter out = new PrintWriter(new FileWriter(fileName+".txt"));
			for(int i=0; i<4; i++) {
				for(int j=0; j<4; j++) {
					out.print(values[i][j]+" "); 
				}
				out.println("");
			}
			JOptionPane.showMessageDialog(null, "The current game is saved as: "+ fileName +".txt");
			out.close();
		} catch  (IOException e) {
			//if catch an exception, print it out
			System.out.println("exception:" + e);
		} 
	}

	//output text file of the current 16 tile values, separate by a space
	public void inputFile(){
		try {
			//Prompt the user to enter the file name
			String fileName = JOptionPane.showInputDialog("Enter file name here:");
			// pass the path to the file as a parameter 
			File file = new File(fileName+".txt"); 
			Scanner sc = new Scanner(file); 

			for(int i=0; i<4; i++) {
				for(int j=0; j<4; j++) {
					values[i][j]=sc.nextInt(); 
				}
			}
			sc.close();
		} catch  (IOException e) {
			//if catch an exception, pop waning box
			JOptionPane.showMessageDialog(null,"File does not exist!");
		} 
		convertToTiles();
	}

	/**
	 * Undo the game to its previous state.
	 */
	public void undo() {
		if(moves.size() > 1) {
			moves.removeFirst();
			values = moves.getFirst();
			convertToTiles();
		} else {
			//show no avaliable moves
			JOptionPane.showMessageDialog(null,"No avaliable moves!");
		}
		//update moves
		status.setText("Moves: " + (moves.size()-1));
		playing = true;
		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	/**
	 * save the game to its current state.
	 */
	public void save() {
		outputFile();
		playing = true;
		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	/**
	 * read the game from a file.
	 */
	public void read() {
		inputFile();
		playing = true;
		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	public int getMoves() {
		return moves.size();
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {
		playing = true;
		status.setText("Moves: " + 0);
		initializeValues();
		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}


	/**
	 * show the instruction of the game. Do it as the game start and as a button
	 */
	public void showInstruction() {
		JOptionPane.showMessageDialog(null, "Hi Player! This is the game 2048 with some modifications.\n"
				+ "Like the traditional 2048 game, you can move the tiles in desired directions \n"
				+ "by pressing left, up, right, and down arrow on your key board.\n" 
				+ "A few differences:  \n"
				+ "*Undo a move by pressing the undo button* \n"
				+ "*Save your current game by pressing same, and name your file* \n"
				+ "*Read the game you played by enter the file name* \n"
				+ "*a tile is generated each time you move, so think carefully!*\n" + 
				"Get to 2048 to win!\n");
		requestFocusInWindow();
	}
	

	/**
	 * This method is called every time the timer defined in the constructor triggers.
	 */
	void tick() {
		if (playing) {
			convertToTiles();
			repaint();
			// check for the game end conditions
			if (!canMove()) {
				JOptionPane.showMessageDialog(null, "Game End! Total moves: " + (moves.size()-1));
				playing = false;
			} else if (checkForWin()) {
				//you can still play if you win
				playing = true;
				status.setText("You Win! But you can keep playin~" + " Moves: " + (moves.size()-1));
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				tiles[i][j].draw(g);
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}

}



