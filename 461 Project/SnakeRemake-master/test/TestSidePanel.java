import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.psnbtech.SidePanel;
import org.psnbtech.SnakeGame;

class TestSidePanel {
	private static SnakeGame game;
	private static SidePanel panel;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		game = new SnakeGame();
		panel = new SidePanel(game);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		game = null;
		panel = null;
	}

	@Test
	void testSidePanel() {
		assertNotNull(panel);
	}

	@Test
	void testPaintComponentGraphics() throws IOException,
    AWTException, InterruptedException {
		Robot rob = new Robot();
		Thread gameThread = new Thread(() ->{
			game.startGame();
		});
		gameThread.start();
		rob.keyPress(KeyEvent.VK_ENTER);
		assertTrue(game.side.painted);
	}

}
