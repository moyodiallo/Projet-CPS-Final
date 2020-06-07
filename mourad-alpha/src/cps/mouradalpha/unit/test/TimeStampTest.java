package cps.mouradalpha.unit.test;

import static org.junit.Assert.*;

import java.net.InetAddress;

import org.junit.Test;

import cps.mouradalpha.TimeStamp;
/**
 * unit test for <code>TimeStamp</code> class
 * for each method, we perform basic test according to the specification listed in javadoc of the method
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
public class TimeStampTest {

	@Test
	public void init() throws Exception {
		
		TimeStamp t = new TimeStamp() ; 
		assertTrue(t.getTime()!= 0);
		assertTrue(t.getTimestamper().equals(InetAddress.getLocalHost().getHostName()));
		
		long temp = System.currentTimeMillis()/1000L;
		TimeStamp t2 = new TimeStamp(temp, "192.168.43.197");
		assertEquals(temp, t2.getTime());
		assertEquals("192.168.43.197", "192.168.43.197");
	}
	
	@Test
	public void timeTest() throws Exception {
		TimeStamp t = new TimeStamp() ; 
		long temp = System.currentTimeMillis()/1000L;
		t.setTime(temp);
		assertEquals(temp, t.getTime());
	}

	@Test
	public void timesTamper() throws Exception {
		TimeStamp t = new TimeStamp() ; 
		t.setTimestamper("192.168.43.197");
		String temp =  t.getTimestamper() ; 
		assertEquals(temp, t.getTimestamper());
		
	}
	
	
}
