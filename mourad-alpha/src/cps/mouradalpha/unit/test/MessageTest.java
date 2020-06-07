package cps.mouradalpha.unit.test;

import static org.junit.Assert.*;

import org.junit.Test;

import cps.mouradalpha.Proprieties;
import cps.mouradalpha.TimeStamp;
import cps.mouradalpha.components.Message;
/**
 * Unit test for <code>Message</code> class
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
public class MessageTest {

	@Test
	public void init() throws Exception {
		TimeStamp t = new TimeStamp() ; 
		Proprieties p = new Proprieties() ; 
		
		Message m  = new Message("uri", "hello wordl",t, p) ;
		
		assertEquals(m.getURI(),"uri");
		assertEquals(m.getPlayload(),"hello wordl");
		assertEquals(m.getTimeStamp(), t);
		assertEquals(m.getProprieties(), p);
	}
	
	
	
	
	
}
