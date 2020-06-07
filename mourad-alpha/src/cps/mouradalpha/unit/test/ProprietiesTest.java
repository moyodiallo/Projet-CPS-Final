package cps.mouradalpha.unit.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import cps.mouradalpha.Proprieties;
/**
 * unit test for <code>Proprieties</code> class
 * for each method, we perform basic test according to the specification listed in javadoc of the method
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
public class ProprietiesTest {

	@Test
	public void testInit() {

		Proprieties p = new Proprieties();
		assertTrue(p != null);
		assertTrue(p.getMap() != null);
		HashMap<String, Object> temp = new HashMap<String, Object>();
		p.setMap(temp);
		assertEquals(p.getMap(), temp);
	}

	@Test
	public void testBooleanProp() {
		Proprieties p = new Proprieties();
		p.putProp("myProp", true);
		Boolean temp = p.getBooleanProp("myProp");
		assertTrue(temp instanceof Boolean);
		Boolean temp2 = p.getBooleanProp("myProp");
		assertEquals(temp, temp2);
		p.putProp("myProp", false);
		assertNotEquals(temp2, p.getBooleanProp("myProp"));
	}

	@Test
	public void testByteProp() {
		Proprieties p = new Proprieties();
		p.putProp("myProp", new Byte("111"));
		Byte temp = p.getByteProp("myProp");
		assertTrue(temp instanceof Byte);
		Byte temp2 = p.getByteProp("myProp");
		assertEquals(temp, temp2);
		p.putProp("myProp", new Byte("112"));
		assertNotEquals(temp2, p.getByteProp("myProp"));
	}

	@Test
	public void testCharProp() {
		Proprieties p = new Proprieties();
		p.putProp("myProp", new Character('a'));
		Character temp = p.getCharProp("myProp");
		assertTrue(temp instanceof Character);
		Character temp2 = p.getCharProp("myProp");
		assertEquals(temp, temp2);
		p.putProp("myProp", new Character('b'));
		assertNotEquals(temp2, p.getCharProp("myProp"));
	}

	@Test
	public void testDoubleProp() {
		Proprieties p = new Proprieties();
		p.putProp("myProp", new Double("3.14"));
		Double temp = p.getDoubleProp("myProp");
		assertTrue(temp instanceof Double);
		Double temp2 = p.getDoubleProp("myProp");
		assertEquals(temp, temp2);
		p.putProp("myProp", new Double("3.15"));
		assertNotEquals(temp2, p.getDoubleProp("myProp"));
	}

	@Test
	public void testFloatProp() {
		Proprieties p = new Proprieties();
		p.putProp("myProp", new Float("3.14"));
		Float temp = p.getFloatProp("myProp");
		assertTrue(temp instanceof Float);
		Float temp2 = p.getFloatProp("myProp");
		assertEquals(temp, temp2);
		p.putProp("myProp", new Float("3.15"));
		assertNotEquals(temp2, p.getFloatProp("myProp"));
	}

	@Test
	public void testIntProp() {
		Proprieties p = new Proprieties();
		p.putProp("myProp", new Integer("111"));
		Integer temp = p.getIntProp("myProp");
		assertTrue(temp instanceof Integer);
		Integer temp2 = p.getIntProp("myProp");
		assertEquals(temp, temp2);
		p.putProp("myProp", new Integer("112"));
		assertNotEquals(temp2, p.getIntProp("myProp"));
	}

	@Test
	public void testLongProp() {
		Proprieties p = new Proprieties();
		p.putProp("myProp", new Long("111"));
		Long temp = p.getLongProp("myProp");
		assertTrue(temp instanceof Long);
		Long temp2 = p.getLongProp("myProp");
		assertEquals(temp, temp2);
		p.putProp("myProp", new Long("112"));
		assertNotEquals(temp2, p.getLongProp("myProp"));

	}

	@Test
	public void testShortProp() {
		Proprieties p = new Proprieties();
		p.putProp("myProp", new Short("111"));
		Short temp = p.getShortProp("myProp");
		assertTrue(temp instanceof Short);
		Short temp2 = p.getShortProp("myProp");
		assertEquals(temp, temp2);
		p.putProp("myProp", new Short("112"));
		assertNotEquals(temp2, p.getShortProp("myProp"));
	}
	@Test
	public void testStringProp() {
		Proprieties p = new Proprieties();
		p.putProp("myProp", new String("HI"));
		String temp = p.getStringProp("myProp");
		assertTrue(temp instanceof String);
		String temp2 = p.getStringProp("myProp");
		assertEquals(temp, temp2);
		p.putProp("myProp", new String("HELLO"));
		assertNotEquals(temp2, p.getStringProp("myProp"));
	}
	

}
