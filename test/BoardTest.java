import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;


//test the main alogorithm of 2048 such as when user move left, up, right, and down, and multiple moves. 
public class BoardTest {
	private Board board;
	
	private int[][] arr1= {
			{0,0,2,4},
			{0,2,0,4},
			{2,2,4,0},
			{4,0,0,4}};


	@Before
	public void setUp() {
		//initialize a fresh Board for each test
		board = new Board();
	}

	@Test
	public void initializeBoard() {
		int[][]va= new int[4][4];
		assertArrayEquals(board.getValues(),va);
	}


	@Test
	public void BoardSetValues() {
		board.setValues(arr1);
		assertArrayEquals(board.getValues(),arr1);
	}


	@Test
	public void BoardMoveRight() {
		board.setValues(arr1);
		board.moveRight();

		int[][] ans = {
				{0,0,2,4},
				{0,0,2,4},
				{0,0,4,4},
				{0,0,0,8}};
		assertArrayEquals(board.getValues(),ans);
	}

	@Test
	public void BoardMoveUp() {
		board.setValues(arr1);
		board.moveUp();

		int[][] ans= {
				{2,4,2,8},
				{4,0,4,4},
				{0,0,0,0},
				{0,0,0,0}};
		assertArrayEquals(board.getValues(),ans);
	}
	
	@Test
	public void BoardMoveUpRight() {
		board.setValues(arr1);
		board.moveUp();
		board.moveRight();
		int[][] ans= {
				{2,4,2,8},
				{0,0,4,8},
				{0,0,0,0},
				{0,0,0,0}};
		assertArrayEquals(board.getValues(),ans);
	}

	@Test
	public void BoardMoveLeft() {
		board.setValues(arr1);
		board.moveLeft();

		int [][] ans = {
				{2,4,0,0},
				{2,4,0,0},
				{4,4,0,0},
				{8,0,0,0}};
		assertArrayEquals(board.getValues(),ans);
	}
	
	@Test
	public void BoardMoveLeftFourSameValues() {
		board.setValues(arr1);
		board.moveLeft();

		int [][] ans = {
				{2,4,0,0},
				{2,4,0,0},
				{4,4,0,0},
				{8,0,0,0}};
		assertArrayEquals(board.getValues(),ans);
	}
	

	@Test
	public void BoardMultipleMoveLeft() {
		board.setValues(arr1);
		board.moveLeft();
		board.moveLeft();

		int [][] ans = {
				{2,4,0,0},
				{2,4,0,0},
				{8,0,0,0},
				{8,0,0,0}};
		assertArrayEquals(board.getValues(),ans);
	}
	
	
	@Test
	public void BoardMoveLeftRowFourSameValue() {
		int [][] arr = {
				{2,2,2,2},
				{4,4,4,4},
				{0,0,0,0},
				{0,0,0,0}};
		
		board.setValues(arr);
		board.moveLeft();

		int [][] ans = {
				{4,4,0,0},
				{8,8,0,0},
				{0,0,0,0},
				{0,0,0,0}};
		assertArrayEquals(board.getValues(),ans);
	}
	
	@Test
	public void BoardMoveLeftRowThreeSameValue() {
		int [][] arr = {
				{2,2,2,4},
				{4,4,4,4},
				{0,0,0,0},
				{0,0,0,0}};
		
		board.setValues(arr);
		board.moveLeft();

		int [][] ans = {
				{4,2,4,0},
				{8,8,0,0},
				{0,0,0,0},
				{0,0,0,0}};
		assertArrayEquals(board.getValues(),ans);
	}
	
	
	@Test
	public void BoardMoveDown() {
		board.setValues(arr1);
		board.moveDown();
	
		int [][] ans = {
				{0,0,0,0},
				{0,0,0,0},
				{2,0,2,4},
				{4,4,4,8}};
		assertArrayEquals(board.getValues(),ans);
	}
	
	@Test
	public void BoardMoveDownLeft() {
		board.setValues(arr1);
		board.moveDown();
		board.moveLeft();

		int [][] ans = {
				{0,0,0,0},
				{0,0,0,0},
				{4,4,0,0},
				{8,4,8,0}};
		assertArrayEquals(board.getValues(),ans);
	}
	
	@Test
	public void BoardMoveDownLeftLeft() {
		board.setValues(arr1);
		board.moveDown();
		board.moveLeft();
		board.moveLeft();
		
		int [][] ans = {
				{0,0,0,0},
				{0,0,0,0},
				{8,0,0,0},
				{8,4,8,0}};
		assertArrayEquals(board.getValues(),ans);
	}
	
	@Test
	public void BoardRotateLeft() {
		board.setValues(arr1);
		board.rotateLeft();
		
		int [][] ans = {
				{4,2,0,0},
				{0,2,2,0},
				{0,4,0,2},
				{4,0,4,4}};
		assertArrayEquals(board.getValues(),ans);
	}
	
	@Test
	public void BoardRotateRight() {
		board.setValues(arr1);
		board.rotateRight();
		
		int [][] ans = {
				{4,4,0,4},
				{2,0,4,0},
				{0,2,2,0},
				{0,0,2,4}};
		assertArrayEquals(board.getValues(),ans);
	}
	
	@Test
	public void BoardCanMove() {
		board.setValues(arr1);

		assertEquals(board.canMove(),true);
		
	}
	
	
	@Test
	public void BoardCanMoveFull() {
		int [][] ans = {
				{4,4,2,4},
				{2,4,4,8},
				{8,2,2,8},
				{8,8,2,4}};
		board.setValues(ans);

		assertEquals(board.canMove(),true);
	}
	
	@Test
	public void BoardCanMoveFalse() {
		int [][] ans = {
				{4,32,2,4},
				{2,8,4,8},
				{8,2,16,2},
				{2,4,2,4}};
		board.setValues(ans);

		assertEquals(board.canMove(),false);
	}
	
	@Test
	public void BoardNotWin() {
		int [][] ans = {
				{4,32,2,4},
				{2,8,0,8},
				{8,2,16,2},
				{2,4,2,4}};
		board.setValues(ans);

		assertEquals(board.checkForWin(),false);
	}
	
	@Test
	public void BoardWin() {
		int [][] ans = {
				{4,32,2048,4},
				{2,8,4,8},
				{8,2,16,2},
				{2,4,2,4}};
		board.setValues(ans);

		assertEquals(board.checkForWin(),true);
	}
	


}
