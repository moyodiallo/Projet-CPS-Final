package cps.mouradalpha.unit.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
/**
 * Test Suite the entire tests  
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ FilterTest.class, MessageTest.class, MyConcurrentHashMapTest.class, MyConcurrentLinkedQueueTest.class,
		ProprietiesTest.class, TimeStampTest.class })
public class AllTests {

}
