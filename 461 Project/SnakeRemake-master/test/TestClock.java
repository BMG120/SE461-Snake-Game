import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.psnbtech.Clock;

class TestClock {
	private static Clock clock;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		clock = new Clock(100);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		clock = null;
	}

	@Test
	void testClock() {
		assertNotNull(clock);
	}

	@Test
	void testSetCyclesPerSecond() {
		clock.setCyclesPerSecond(500);
		float answer = (1.0f / 500) * 1000;
		assertEquals(answer, clock.millisPerCycle);
	}

	@Test
	void testReset() {
		clock.reset();
		assertEquals(0, clock.elapsedCycles);
		assertEquals(0.0f, clock.excessCycles);
		assertEquals(clock.getCurrentTime(), clock.lastUpdate);
	}

	/*
	 * Satisfies the path 0,1,2,3,6,7
	 */
	@Test
	void testUpdatePaused() {
		clock.setPaused(true);
		clock.update();
		assertTrue(clock.notPausedPath);
	}
	/*
	 * Satisfies the path 0,1,2,3,4,5,6,7
	 */
	@Test
	void testUpdateNotPaused() {
		boolean excesscycles0or1 = false;
		clock.setCyclesPerSecond(500);
		clock.setPaused(false);
		clock.update();		//excessCycles is either 0.0 or 1.0 due to inaccuracies and rounding of the clock
		if(clock.excessCyclesTesting == 1.0 || clock.excessCyclesTesting == 0.0){
			excesscycles0or1 = true;
		}
		assertTrue(excesscycles0or1);
	}

	@Test
	void testSetPaused() {
		clock.setPaused(false);
		assertFalse(clock.isPaused());
		clock.setPaused(true);
		assertTrue(clock.isPaused());
	}

	@Test
	void testIsPaused() {
		clock.setPaused(true);
		assertTrue(clock.isPaused());
	}

	/*
	 * Satisfies the path 0,1,2,3
	 */
	@Test
	void testHasElapsedCycleTrue() {
		clock.elapsedCycles = 10;
		assertTrue(clock.hasElapsedCycle());
	}
	
	/*
	 * Satisfies the path 0,1,3
	 */
	@Test
	void testHasElapsedCycleFalse() {
		clock.elapsedCycles = -1;
		assertFalse(clock.hasElapsedCycle());
	}

	@Test
	void testPeekElapsedCycle() {
		clock.elapsedCycles = 10;
		assertTrue(clock.peekElapsedCycle());
		clock.elapsedCycles = -1;
		assertFalse(clock.peekElapsedCycle());
	}

}
