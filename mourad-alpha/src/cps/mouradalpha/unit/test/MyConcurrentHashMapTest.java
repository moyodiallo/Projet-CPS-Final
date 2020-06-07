package cps.mouradalpha.unit.test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cps.mouradalpha.util.MyConcurrentHashMap;
/**
 * unit test for <code>MyConcurrentHashMap</code> class
 * for each method, we perform basic test according to the specification listed in javadoc of the method
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
public class MyConcurrentHashMapTest {

	@Test
	public void putTest() {
		MyConcurrentHashMap<String, Integer> map = new MyConcurrentHashMap<String, Integer>();
		Integer temp = map.put("key", 123);
		assertEquals(temp, null);
		temp = map.put("key", 123);
		assertEquals(temp, (Integer) 123);
	}

	@Test
	public void containsKeyTest() {
		MyConcurrentHashMap<String, Integer> map = new MyConcurrentHashMap<String, Integer>();
		boolean temp = map.containsKey("key");
		assertEquals(temp, false);
		map.put("key", 123);
		temp = map.containsKey("key");
		assertEquals(temp, true);
	}

	@Test
	public void removeTest() {
		MyConcurrentHashMap<String, Integer> map = new MyConcurrentHashMap<String, Integer>();
		Integer temp = map.remove("key");
		assertEquals(temp, null);
		map.put("key", 123);
		temp = map.remove("key");
		assertEquals(temp, (Integer) 123);
		temp = map.remove("key");
		assertEquals(temp, null);
	}

	@Test
	public void getTest() {
		MyConcurrentHashMap<String, Integer> map = new MyConcurrentHashMap<String, Integer>();
		Integer temp = map.get("key");
		assertEquals(temp, null);
		map.put("key", 123);
		temp = map.get("key");
		assertEquals(temp, (Integer) 123);
	}

	@Test
	public void keySetTest() {
		MyConcurrentHashMap<String, Integer> map = new MyConcurrentHashMap<String, Integer>();
		Set<String> temp = map.keySet(); 
		Set<String> temp2 = new HashSet<String>() ;
		assertEquals(temp, temp2);
		map.put("key1", 123);
		map.put("key2", 456);
		map.put("key3", 789);
		temp = map.keySet(); 
		temp2.add("key1");
		temp2.add("key2");
		temp2.add("key3");
		assertEquals(temp,temp2); 
	}
	
	@Test
	public void sizeTest() {
		MyConcurrentHashMap<String, Integer> map = new MyConcurrentHashMap<String, Integer>();
		assertEquals(map.size(), 0);
		map.put("key1", 123);
		assertEquals(map.size(), 1);
		map.put("key2",456);
		assertEquals(map.size(), 2);
		map.remove("key2");
		assertEquals(map.size(), 1);
	}
}