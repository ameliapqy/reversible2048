
=========================
=: Plan :=
=========================

  1. A standard 2048 board is 4 by 4. To represent the board, I will use a 2D array of integers that stores the current state of each square, which is 
  the value of the square displayed, and 0 will represent a blank square. 
  When the game starts,  all elements in the array will be 0, representing a blank board, except a 2 will be generated at a random position on the board.
  

  2.I will use a linked list to store each of the moves that occur during a game, since the order matter and I only need to access or remove the head. 
  It lets user undo a move, which remove the last move from of the list. The list will store an 2D array of values representing the current value of 
  each tile on the board for each move. 

  3.I will use I/O to store game state, so that the user can pause a game. There will be a save button that output a text file when the save button 
  is pressed. There will be a load button that will read the previously outputed text file and parse the data so it can be displayed. 

  4.The main state of my game will be the values (int 2D array), which could be tested with JUnit, and is converted to the displayable Tile 2D array for display. 
I will test the main alogorithm of 2048 such as when user move left, up, right, and down, and multiple moves. 
I will test edge cases, such as when everything on the board is filled, the game does not end unless no tiles could be combined. 
I will also test the case when four values in a row is the same, moving left would make 2 values combine, and when three same values are in a row, 
only the first two values are combined. 
  


=========================
=: Implementation :=
=========================

##classes and their functions:
Tiles: the tile class store the position, color, and font of each tile on the board.
It's function is to let each tiles draw on GUI so users could see it 
Board: This is the main class that hold the game panel. It has a 4 by 4 array to hold the tiles, 
an 2D int array to hold the scores, and a linked list to hold the moves. 
Game: main state of the game that has a specifies the frame and widgets of the GUI. It initiates the board, put buttons
to the panel such as undo, reset, read, and save. 
##difficulties
I stucked a long time on Algorithm for combining the tiles because there are so many
cases, and it's hard to have an algorithm for every none empty tile to move left. It's also hard to test the 
program because I have to write new methods for accessing and setting the values of each tile. 
It's also hard to keep track of moves since it need to be updated after each move, so I can't just create
a label in the game class. Instead, I let the board constructor take in the label so it
could be repainted after each move. 
##evaluation
It's encapsulated well by having a seperate Tile, Board, and Game class, so one can't 
modify a board or tile's state directly in the game class. Tile and Board class both have private variables, which 
encapsulate the state of the object nicely, and could only be modified by method inside the class. If I have the chance,
I might only hold the value in the tile class instead of having a new 2D int array, to improve code efficiency. 


========================
=: External Resources :=
========================

  Java Swing Library
  JOptionPane
  The game 2048(for rules)
