package org.psnbtech;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class SnakeGameTest {

	private SnakeGame snakegame;
	private Robot robot;
	@Before
	public void setUp() throws Exception {
		snakegame = new SnakeGame();
		robot = new Robot();
	}

	@After
	public void tearDown() throws Exception {
		snakegame = null;
		robot = null;
	}

	@Test
	public void testConstructor() {
		assertNotNull(snakegame);
	}
	
	//
	
	//TsnakeGame {[1,2,3,6,28!], [1,7,8,11,28!], [1,12,13,16,28!], [1,17,18,21,28!]
	// 
	//, 

	
	
	

	
	
	@Test
	public void testGameOver() {
		// Drives snake straight north into the wall
		
		// Fulfills Test cases:
		// TsnakeGame {[1,25,27,26,28]}
		// TupdateGame {[1,3,4,7], [1,3,5,7]}
		// TupdateSnake {[1,2,6,7], [1,2,6,8,9,10,13], [1,2,6,8,9,10,11,13!]}
		
		Thread gameLoop = new Thread(()-> {
			snakegame.startGame();
		});
		
		gameLoop.start();
	
		snakegame.requestFocus();
		
		assertFalse(snakegame.isGameOver());
		
		robot.keyPress(KeyEvent.VK_ENTER);
		System.out.println("Enter pressed");
		
		robot.delay(1000);
		assertTrue(snakegame.getNextFruitScore() < 100);
		robot.delay(3000);
		
		assertTrue(snakegame.isGameOver());
		System.out.println("Assertion for Game Over resolved");
		
		
		
		//end game loop after tests
		try {
			gameLoop.join(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Test 
	public void testDirectionalControl() {
		// Checks all directional controls and directional conditionals,
		// such as going back in the opposite direction (shouldn't happen)
		// or moving directions while paused. (shouldn't happen)
		
		// Fulfills Test Cases:
		// TsnakeGame {[1,2,3,4,5,6,28!], [1,2,3,4,6,28!], [1,2,6,28!], [1,7,8,9,11,28!],  
		// 			[1,7,8,9,10,11,28!], [1,7,11,28!], [1,12,13,14,16,28!], 
		//			[1,12,13,14,15,16,28!], [1,12,16,28!], [1,17,18,19,20,21,28!],  
		//			[1,17,18,19,21,28!], [1,17,21,28!], [1,22,24,23,28!]}
		// TupdateSnake {[1,2,6,8,10,11,13!], [1,4,6,8,9,10,11,12,13!], 
		//			[1,3,6,8,9,10,11,12,13!], [1,5,6,8,9,10,11,12,13!],
		//			[1,2,6,8,9,10,11,12,13!]}
		// SnakeGame Logical Coverage
		// P1 = if(!isPaused && !isGameOver)
		// T1 = {[true,false], [false,true], [false,false]}
		// P2 = if(last != Direction.{SET OF ALL DIRECTIONS} 
		//			&& last != Direction.{SET OF OPPOSITE DIRECTIONS})
		// T2 = {[N,N], [N,S], [N,E], [N,W], [S,N], [S,S], [S,E], [S,W], [E,N], [E,S], 
		// 		[E,E], [E,W], [W,N], [W,S], [W,E], [W,W]}
		// Enter button:
		// P1 = if(isNewGame || isGameOver)
		// T1 = {[true,true]}
		// Directional/State Switch
		// P1 = switch(e.getKeyCode())
		// T0 = {VK_UP, VK_W, VK_DOWN, VK_S, VK_LEFT, VK_A, VK_RIGHT, VK_D, VK_ENTER, 
		//		VK_P, VK_X}
		
		Thread gameLoop = new Thread(()-> {
			snakegame.startGame();
		});
		
		gameLoop.start();
	
		snakegame.requestFocus();
		// enter game
		robot.keyPress(KeyEvent.VK_ENTER);
		
		// snake starts by going north, use button again to check conditional
		robot.keyPress(KeyEvent.VK_UP);
		robot.delay(500);
		assertEquals(Direction.North, snakegame.getDirection());
		
		// try VK_X, should not do anything
		robot.keyPress(KeyEvent.VK_X);
		robot.delay(100);
		assertEquals(Direction.North, snakegame.getDirection());
		
		//make sure snake cannot turn on itself
		robot.keyPress(KeyEvent.VK_S);
		robot.delay(500);
		assertEquals(Direction.North, snakegame.getDirection());
		
		// direct snake west by pressing A
		robot.keyPress(KeyEvent.VK_A);
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_LEFT);
		assertEquals(Direction.West, snakegame.getDirection());
		//make sure snake cannot turn on itself
		robot.keyPress(KeyEvent.VK_D);
		robot.delay(500);
		assertEquals(Direction.West, snakegame.getDirection());
		
		// direct snake south by pressing S
		robot.keyPress(KeyEvent.VK_S);
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_DOWN);
		assertEquals(Direction.South, snakegame.getDirection());
		//make sure snake cannot turn on itself
		robot.keyPress(KeyEvent.VK_W);
		robot.delay(500);
		assertEquals(Direction.South, snakegame.getDirection());
		
		// direct snake east by pressing D
		robot.keyPress(KeyEvent.VK_D);
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_RIGHT);
		assertEquals(Direction.East, snakegame.getDirection());
		//make sure snake cannot turn on itself
		robot.keyPress(KeyEvent.VK_A);
		robot.delay(500);
		assertEquals(Direction.East, snakegame.getDirection());
		
		// test up direction by pressing W
		robot.keyPress(KeyEvent.VK_W);
		robot.delay(500);
		assertEquals(Direction.North, snakegame.getDirection());
		// exhaust remaining conditional combinations
		// N->E, S->W, W->N, E->S
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.delay(100);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.delay(100);
		robot.keyPress(KeyEvent.VK_UP);
		
		robot.delay(500);
		
		// pause game and check if any directional works
		// direction should not change while paused
		robot.keyPress(KeyEvent.VK_P);
		// confirm game is paused
		robot.delay(30);
		assertTrue(snakegame.isPaused());
		robot.delay(500);
		//press each direction and check if direction has shifted
		robot.keyPress(KeyEvent.VK_A);
		robot.delay(500);
		assertEquals(Direction.North, snakegame.getDirection());
		robot.keyPress(KeyEvent.VK_S);
		robot.delay(500);
		assertEquals(Direction.North, snakegame.getDirection());
		robot.keyPress(KeyEvent.VK_D);
		robot.delay(500);
		assertEquals(Direction.North, snakegame.getDirection());
		robot.keyPress(KeyEvent.VK_UP);
		// try VK_X, should not do anything
		robot.keyPress(KeyEvent.VK_X);
		robot.delay(100);
		assertEquals(Direction.North, snakegame.getDirection());
		
		//unpause and allow snake to trigger game over
		robot.keyPress(KeyEvent.VK_P);
		robot.delay(100);
		// try VK_X, should not do anything
		robot.keyPress(KeyEvent.VK_X);
		robot.delay(100);
		assertEquals(Direction.North, snakegame.getDirection());
		assertFalse(snakegame.isPaused());
		robot.delay(2000);
		
		
		assertTrue(snakegame.isGameOver());
		robot.keyPress(KeyEvent.VK_A);
		robot.delay(500);
		assertEquals(Direction.North, snakegame.getDirection());
		robot.keyPress(KeyEvent.VK_S);
		robot.delay(500);
		assertEquals(Direction.North, snakegame.getDirection());
		robot.keyPress(KeyEvent.VK_D);
		robot.delay(500);
		assertEquals(Direction.North, snakegame.getDirection());
		robot.keyPress(KeyEvent.VK_UP);
		
		
		
		
		//end game loop after tests
		try {
			gameLoop.join(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testSnakeState() {
		// As game begins, tests if snake builds correctly if turned in any given 
		// direction at beginning... Also tests conditionals based on if a NewGame 
		// is played or if game is reset
		
		// Fulfills Test Cases:
		// TsnakeGame {[1,25,26,28!], [1,22,23,28!]}
		// TupdateSnake {[1,4,6,8,10,11,13!], [1,3,6,8,10,11,12,13!], 
		//			[1,5,6,8,10,11,13!], [1,2,6,8,10,11,12,13!]}
		// TstartGame {[1,2,3,4,5,6,2,3,5,6,2,3,4,5,6,2,3,4]}
		
		// SnakeGame Logical Coverage:
		// P2 = if(isNewGame || isGameOver)
		// T2 = { [true, false], [false,true], [false,false]}
		
		
		
		Thread gameLoop = new Thread(()-> {
			snakegame.startGame();
		});
		
		gameLoop.start();
	
		snakegame.requestFocus();
		// set game over to false, to satisfy condition newgame = true gameover = false
		snakegame.setGameOver(true);
		//confirm new game.
		assertTrue(snakegame.isGameOver());
		assertTrue(snakegame.isNewGame());
		robot.keyPress(KeyEvent.VK_ENTER);
		
		robot.delay(300);
		// reverse game over to satisfy game flow
		snakegame.setGameOver(false);
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.delay(30);
		robot.keyPress(KeyEvent.VK_DOWN);
		
		robot.delay(1000);
		
		// Press Enter while in game, should not change new game status
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(50);

		assertFalse(snakegame.isNewGame());
		
		//check size, allow for possibility of running into fruit.
		assertEquals(snakegame.getSnake().size(), 6 + snakegame.getFruitsEaten());
		
		//allow snake to run into wall
		robot.delay(2000);
		
		// check if pause works during game over (should not work)
		assertTrue(snakegame.isGameOver());
		robot.keyPress(KeyEvent.VK_P);
		robot.delay(30);
		assertFalse(snakegame.isPaused());
		
		
		robot.keyPress(KeyEvent.VK_ENTER);
		//check if still new game (should be false)
		robot.delay(10);
		assertFalse(snakegame.isNewGame());
		
		robot.delay(30);
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.delay(30);
		robot.keyPress(KeyEvent.VK_UP);
		
		robot.delay(2000);
		assertEquals(snakegame.getSnake().size(), 6 + snakegame.getFruitsEaten());
		
		//allow snake to run into wall
		robot.delay(2000);
		
		robot.keyPress(KeyEvent.VK_ENTER);
		
		robot.delay(500);
		
		
		
		//end game loop after tests
		try {
			gameLoop.join(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSnakeCollision() {
		// collide snake with itself in every direction, 
		// while body > MIN_SNAKE_LENGTH and also when body <  MIN_SNAKE_LENGTH 
		// (collide with wall for > and with itself for <)
		
		// Fulfills Test Cases:
		// TupdateGame  {[1,3,5,6,7!]}
		// TupdateSnake {[1,3,6,8,9,10,11,13!], [1,3,6,8,10,11,13!], [1,3,6,8,9,10,13!]
		// 				[1,3,6,8,10,13!], [1,3,6,7!], [1,2,6,8,10,13!], [1,4,6,7!],
		//				[1,4,6,8,9,10,11,13!], [1,4,6,8,10,11,12,13!], [1,4,6,8,9,10,13!], 
		//				[1,4,6,8,10,13!], [1,5,6,7!], [1,5,6,8,9,10,11,13!],
		//				[1,5,6,8,10,11,12,13!], [1,5,6,8,9,10,13!], [1,5,6,8,10,13!]}
		// updateGame Logical Coverage:
		// P2 = else if(collision == TileType.SnakeBody) 
		// T2 = {P2=true, P2=false}		
		
		Thread gameLoop = new Thread(()-> {
			snakegame.startGame();
		});
		
		gameLoop.start();
	
		snakegame.requestFocus();
		// enter game
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(500);
		
		// wrap around to collide with body from the right
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.delay(100);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.delay(100);
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.delay(2000);
		assertEquals(snakegame.getDirection(), Direction.East);
		
		assertTrue(snakegame.isGameOver());
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(500);
		
		// wrap around to collide with body from the left
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.delay(100);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.delay(100);
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.delay(2000);
		assertEquals(snakegame.getDirection(), Direction.West);
		assertTrue(snakegame.isGameOver());
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(500);
		
		// wrap around to collide with body from below
		robot.keyPress(KeyEvent.VK_RIGHT);
		// allow time for body to straighten out
		robot.delay(1000);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.delay(100);
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.delay(100);
		robot.keyPress(KeyEvent.VK_UP);
		robot.delay(2000);
		assertEquals(snakegame.getDirection(), Direction.North);
		assertTrue(snakegame.isGameOver());
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(500);
		
		// wrap around to collide with body from above
		robot.keyPress(KeyEvent.VK_LEFT);
		// allow time for body to straighten out
		robot.delay(1000);
		robot.keyPress(KeyEvent.VK_UP);
		robot.delay(100);
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.delay(100);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.delay(2000);
		assertEquals(snakegame.getDirection(), Direction.South);
		assertTrue(snakegame.isGameOver());
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(500);
		
		// allow collision against the wall on the right
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.delay(3000);
		assertEquals(snakegame.getDirection(), Direction.East);
		
		assertTrue(snakegame.isGameOver());
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(500);
		
		// allow collision against the wall on the left
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.delay(3000);
		assertEquals(snakegame.getDirection(), Direction.West);
		assertTrue(snakegame.isGameOver());
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(500);		
		
		// allow collision against the wall on the bottom
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.delay(100);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.delay(3000);
		assertEquals(snakegame.getDirection(), Direction.South);
				
		assertTrue(snakegame.isGameOver());
		
		//UP has been tested in testGameOver

		//end game loop after tests
		try {
			gameLoop.join(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMain() {
		Thread gameLoop = new Thread(()-> {
			SnakeGame.main(null);
		});
		
		gameLoop.start();
		
		
	
	}
	
	
	@Test
	public void testFruitCollision() {
		// Spawns a fruit ahead of the snake to collide with,
		// checks if collision updates fruits eaten and score
		// Makes snake go in circles to make next fruit score 
		// go down to 10 to make sure it does not go any lower
		
		// Fulfills the following Test Sets:
		
		// TupdateGame {[1,2,7!], [1,3,5,7!]}
		
		// updateGame Logical Coverage
		// P1 = if(collision == TileType.Fruit)
		// T1 = {P1=true, P1=false}
		// P3 = else if(nextFruitScore > 10)
		// T3 = {P3=true, P3=false}
		

		
		System.out.println(snakegame.getFruitsEaten());
		
		
		Thread gameLoop = new Thread(()-> {
			snakegame.startGame();
		});
		
		gameLoop.start();
	
		snakegame.requestFocus();
		
		snakegame.testing = true;
		
		// enter game
		robot.keyPress(KeyEvent.VK_ENTER);
		assertEquals(snakegame.getFruitsEaten(), 0);

		
		
		
		robot.delay(1000);
		System.out.println(snakegame.getFruitsEaten());
		// check that fruit was eaten and score updated
		assertEquals(snakegame.getFruitsEaten(), 1);
		robot.delay(100);
		assertTrue(snakegame.getScore() > 0);
		
		//do holding pattern until score reduces to < 10
		while (snakegame.getNextFruitScore() > 10) {
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.delay(700);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.delay(1000);
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.delay(700);
		robot.keyPress(KeyEvent.VK_UP);
		robot.delay(1000);
		
		}
		
		
		snakegame.testing = false;
		
		//end game loop after tests
		try {
			gameLoop.join(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void testDirectionsOverload() {
		// tests overloading directions to go larger or equal to MAX_DIRECTIONS
		
		////TsnakeGame {[1,2,3,6,28!], [1,7,8,11,28!], [1,12,13,16,28!], [1,17,18,21,28!]}
		
		// P3 = if(directions.size() < getMaxDirections())
		// T3 = {[True], [False]}
		
		Thread gameLoop = new Thread(()-> {
			snakegame.startGame();
		});
		
		gameLoop.start();
	
		snakegame.requestFocus();
		
		// enter game
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(500);
		
		
//		snakegame.getDirections().add(Direction.East);
//		snakegame.getDirections().add(Direction.West);
//		snakegame.getDirections().add(Direction.North);
//		snakegame.getDirections().add(Direction.South);
		robot.keyPress(KeyEvent.VK_UP);
		snakegame.getDirections().add(Direction.North);
		snakegame.getDirections().add(Direction.North);
		snakegame.getDirections().add(Direction.North);
		snakegame.getDirections().add(Direction.North);
		robot.keyPress(KeyEvent.VK_UP);
		assertTrue(snakegame.getDirections().size() >= SnakeGame.getMaxDirections());
		
		//assertTrue(snakegame.getDirections().size() >= SnakeGame.getMaxDirections());
		
		robot.delay(3000);
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(500);
		
//		snakegame.getDirections().add(Direction.South);
//		snakegame.getDirections().add(Direction.West);
//		snakegame.getDirections().add(Direction.North);
//		snakegame.getDirections().add(Direction.East);
		robot.keyPress(KeyEvent.VK_LEFT);
		snakegame.getDirections().add(Direction.West);
		snakegame.getDirections().add(Direction.West);
		snakegame.getDirections().add(Direction.West);
		snakegame.getDirections().add(Direction.West);
		robot.keyPress(KeyEvent.VK_LEFT);
		
		assertTrue(snakegame.getDirections().size() >= SnakeGame.getMaxDirections());
		
		robot.delay(3000);
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(500);
		
//		snakegame.getDirections().add(Direction.North);
//		snakegame.getDirections().add(Direction.East);
//		snakegame.getDirections().add(Direction.South);
//		snakegame.getDirections().add(Direction.West);
		robot.keyPress(KeyEvent.VK_RIGHT);
		snakegame.getDirections().add(Direction.East);
		snakegame.getDirections().add(Direction.East);
		snakegame.getDirections().add(Direction.East);
		snakegame.getDirections().add(Direction.East);
		robot.keyPress(KeyEvent.VK_RIGHT);
		
		assertTrue(snakegame.getDirections().size() >= SnakeGame.getMaxDirections());
		
		robot.delay(3000);
		
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(500);
		
//		snakegame.getDirections().add(Direction.East);
//		snakegame.getDirections().add(Direction.South);
//		snakegame.getDirections().add(Direction.West);
//		snakegame.getDirections().add(Direction.North);
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_DOWN);
		snakegame.getDirections().add(Direction.South);
		snakegame.getDirections().add(Direction.South);
		snakegame.getDirections().add(Direction.South);
		snakegame.getDirections().add(Direction.South);
		robot.keyPress(KeyEvent.VK_DOWN);
		
		
		
		assertTrue(snakegame.getDirections().size() >= SnakeGame.getMaxDirections());
		
		robot.delay(3000);
		
		
		
		//end game loop after tests
		try {
			gameLoop.join(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
