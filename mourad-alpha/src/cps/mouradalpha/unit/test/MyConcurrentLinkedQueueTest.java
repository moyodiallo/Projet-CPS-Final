package cps.mouradalpha.unit.test;

import static org.junit.Assert.*;

import org.junit.Test;

import cps.mouradalpha.util.MyConcurrentLinkedQueue;
/**
 * unit test for <code>MyConcurrentLinkedQueue</code> class
 * for each method, we perform basic test according to the specification listed in javadoc of the method
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
public class MyConcurrentLinkedQueueTest {

	@Test
	public void containsTest() {
		MyConcurrentLinkedQueue<String> queue = new MyConcurrentLinkedQueue<String>();
		boolean temp = queue.contains("ele");
		assertEquals(temp, false);
		queue.add("ele");
		temp = queue.contains("ele");
		assertEquals(temp, true);
	}

	@Test
	public void addTest() {
		MyConcurrentLinkedQueue<String> queue = new MyConcurrentLinkedQueue<String>();
		boolean temp = queue.add("ele");
		if (temp)
			assertTrue(queue.contains("ele"));
		else
			fail("error while inserting");
	}

	@Test
	public void removeTest() {
		MyConcurrentLinkedQueue<String> queue = new MyConcurrentLinkedQueue<String>();
		queue.add("ele");
		assertTrue(queue.contains("ele"));
		queue.remove("ele");
		assertTrue(!queue.contains("ele"));
	}

	@Test
	public void pollTest() {
		MyConcurrentLinkedQueue<String> queue = new MyConcurrentLinkedQueue<String>();
		String temp = queue.poll();
		assertEquals(temp, null);
		queue.add("key1");
		queue.add("key2");
		queue.add("key3");
		temp = queue.poll();
		assertEquals(temp, "key1");
		assertTrue(!queue.contains("key1"));

	}

	@Test
	public void sizeTest() {
		MyConcurrentLinkedQueue<String> queue = new MyConcurrentLinkedQueue<String>();
		assertTrue(queue.size() == 0);
		queue.add("ele1");
		queue.add("ele2");
		queue.add("ele3");
		assertTrue(queue.size() == 3);
		queue.remove("ele1");
		assertTrue(queue.size() == 2);
	}

	@Test
	public void getTest() {
		MyConcurrentLinkedQueue<String> queue = new MyConcurrentLinkedQueue<String>();
		
		queue.add("ele1");
		queue.add("ele2");
		queue.add("ele3");

		String temp = queue.get(0);
		assertEquals("ele1", queue.get(0));
		
		temp = queue.get(1);
		assertEquals("ele2", queue.get(1));
		
		temp = queue.get(2);
		assertEquals("ele3", queue.get(2));
	}

}
