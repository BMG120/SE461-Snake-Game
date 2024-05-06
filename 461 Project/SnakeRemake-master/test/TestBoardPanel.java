import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.psnbtech.BoardPanel;
import org.psnbtech.Direction;
import org.psnbtech.SnakeGame;
import org.psnbtech.TileType;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.AWTException;
import java.awt.Graphics;

class TestBoardPanel {
	private static SnakeGame game;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		game = new SnakeGame();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		game = null;
	}

	@Test
	void testBoardPanel() {
		assertNotNull(game.board);
	}

	@Test
	void testClearBoard() {
		game.board.clearBoard();
		for(int i = 0;i<game.board.tiles.length;i++) {
			assertNull(game.board.tiles[i]);
		}
	}

	@Test
	void testSetTilePointTileType() {
		Point p = new Point(1, 10);
		game.board.setTile(p, TileType.Fruit );
		assertEquals(game.board.tiles[p.y * game.board.ROW_COUNT + p.x], TileType.Fruit);
	}

	@Test
	void testSetTileIntIntTileType() {
		int x = 1;
		int y = 4;
		game.board.setTile(x, y, TileType.Fruit );
		assertEquals(game.board.tiles[y * game.board.ROW_COUNT + x], TileType.Fruit);
		game.board.clearBoard();
	}

	@Test
	void testGetTile() {
		int x = 5;
		int y = 15;
		game.board.setTile(x, y, TileType.Fruit);
		assertEquals(game.board.getTile(x, y), TileType.Fruit);
		game.board.clearBoard();
	}
	//All paintComponent tests satisfy 3,4,6,3 loop, 3,4,5,6 loop, 10,11,12 loop,
	//and 19,20,28,26,29,30,31 path
	
	/*Satisfies 13,31
	 */
	@Test
	void testPaintComponentGraphics1() throws IOException,
    AWTException, InterruptedException{
		Robot rob = new Robot();
		Thread gameThread = new Thread(() ->{
			game.startGame();
		});
		gameThread.start();
		rob.keyPress(KeyEvent.VK_ENTER);
		rob.delay(100);
		assertTrue(game.board.tileGridDrawn);
		assertTrue(game.board.tileTypeDrawn);
		rob.keyPress(KeyEvent.VK_P);
		game.setPaused(false);
		game.setPaused(true);
	}
	/*Satisfies  13,14,15,16,17,18,19,23,25,27,26,29,30,31
	 */
	@Test
	void testPaintComponentGraphics2() throws IOException,
    AWTException, InterruptedException{
		Robot rob = new Robot();
		Thread gameThread = new Thread(() ->{
			game.startGame();
		});
		gameThread.start();
		rob.keyPress(KeyEvent.VK_ENTER);
		rob.delay(2000);

		assertSame("Press Enter to Restart", game.board.messages[1]);
	}
	
	/*Satisfies  13,14,15,16,17,18,19,23,22,21,24,26,29,30,31
	 */
	@Test
	void testPaintComponentGraphics3() throws IOException,
    AWTException, InterruptedException{
		Robot rob = new Robot();
		Thread gameThread = new Thread(() ->{
			game.startGame();
		});
		gameThread.start();
		rob.keyPress(KeyEvent.VK_ENTER);
		rob.keyPress(KeyEvent.VK_P);
		rob.delay(100);
		assertSame("Press P to Resume", game.board.messages[1]);
	}
	
	/*Satisfies 0,1,4,7,22
	 */
	@Test
	void testDrawTileFruit() {
		Graphics g = game.board.getGraphics();
		game.board.drawTile(0, 0, TileType.Fruit, g);
		assertTrue(game.board.drawnFruit);
	}

	/*Satisfies 0,1,3,6,22
	 */
	@Test
	void testDrawTileBody() {
		Graphics g = game.board.getGraphics();
		game.board.drawTile(0, 0, TileType.SnakeBody, g);
		assertTrue(game.board.drawnBody);
	}
	
	/*Satisfies 0,1,2,5,8,9,13,17,21,22
	 */
	@Test
	void testDrawTileNorthHead() {
		Graphics g = game.board.getGraphics();
		game.directions = new LinkedList<>();
		game.directions.add(Direction.North);
		game.board.drawTile(0, 0, TileType.SnakeHead, g);
		assertTrue(game.board.drawnHead[0]);
	}
	
	/*Satisfies 0,1,2,5,8,9,12,16,20,22
	 */
	@Test
	void testDrawTileSouthHead() {
		Graphics g = game.board.getGraphics();
		game.directions = new LinkedList<>();
		game.directions.add(Direction.South);
		game.board.drawTile(0, 0, TileType.SnakeHead, g);
		assertTrue(game.board.drawnHead[1]);
	}
	
	/*Satisfies 0,1,2,5,8,9,11,15,19,22
	 */
	@Test
	void testDrawTileWestHead() {
		Graphics g = game.board.getGraphics();
		game.directions = new LinkedList<>();
		game.directions.add(Direction.West);
		game.board.drawTile(0, 0, TileType.SnakeHead, g);
		assertTrue(game.board.drawnHead[0]);
	}
	
	/*Satisfies 0,1,2,5,8,9,10,14,18,22
	 */
	@Test
	void testDrawTileEastHead() {
		Graphics g = game.board.getGraphics();
		game.directions = new LinkedList<>();
		game.directions.add(Direction.East);
		game.board.drawTile(0, 0, TileType.SnakeHead, g);
		assertTrue(game.board.drawnHead[0]);
	}

}
