package cps.mouradalpha.unit.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import cps.mouradalpha.Proprieties;
import cps.mouradalpha.TimeStamp;
import cps.mouradalpha.components.Message;
import cps.mouradalpha.interfaces.MessageFilterI;
import cps.mouradalpha.interfaces.MessageI;

/**
 * Test for the class <code>Filter</code>
 * 
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
public class FilterTest {
	List<MessageI> messages = new ArrayList<MessageI>();

	/**
	 * load the data used in test. it's a list the top 10 Best Football Clubs In The
	 * World 2020, we added two properties : country and number champions league won
	 * for each club.
	 * 
	 * @throws Exception
	 */
	@Before
	public void load() throws Exception {

		// fill the list : Top-10 Best Football Clubs In The World 2020
		messages.add(new Message("m1", "Barcelona", new TimeStamp(), new Proprieties()));
		messages.add(new Message("m2", "Real Madrid", new TimeStamp(), new Proprieties()));
		messages.add(new Message("m3", "Liverpool", new TimeStamp(), new Proprieties()));
		messages.add(new Message("m4", "Juventus", new TimeStamp(), new Proprieties()));
		messages.add(new Message("m5", "FC Bayern Munich", new TimeStamp(), new Proprieties()));
		messages.add(new Message("m6", "Manchester United", new TimeStamp(), new Proprieties()));
		messages.add(new Message("m7", "Paris Saint-Germain", new TimeStamp(), new Proprieties()));
		messages.add(new Message("m8", "Manchester City", new TimeStamp(), new Proprieties()));
		messages.add(new Message("m9", "Atlético Madrid", new TimeStamp(), new Proprieties()));
		messages.add(new Message("m10", "Borussia Dortmund", new TimeStamp(), new Proprieties()));
		// country property : String -> String
		messages.get(0).getProprieties().putProp("contry", "Spain");
		messages.get(1).getProprieties().putProp("contry", "Spain");
		messages.get(2).getProprieties().putProp("contry", "England");
		messages.get(3).getProprieties().putProp("contry", "Italy");
		messages.get(4).getProprieties().putProp("contry", "Germany");
		messages.get(5).getProprieties().putProp("contry", "England");
		messages.get(6).getProprieties().putProp("contry", "France");
		messages.get(7).getProprieties().putProp("contry", "England");
		messages.get(8).getProprieties().putProp("contry", "Spain");
		messages.get(9).getProprieties().putProp("contry", "Germany");
		// N° Champions League title won : String -> Integer
		messages.get(0).getProprieties().putProp("N° Champions League", 5);
		messages.get(1).getProprieties().putProp("N° Champions League", 13);
		messages.get(2).getProprieties().putProp("N° Champions League", 6);
		messages.get(3).getProprieties().putProp("N° Champions League", 2);
		messages.get(4).getProprieties().putProp("N° Champions League", 5);
		messages.get(5).getProprieties().putProp("N° Champions League", 5);
		messages.get(6).getProprieties().putProp("N° Champions League", 0);
		messages.get(7).getProprieties().putProp("N° Champions League", 0);
		messages.get(8).getProprieties().putProp("N° Champions League", 0);
		messages.get(9).getProprieties().putProp("N° Champions League", 1);
	}

	/**
	 * after loading the data, we perform 3 filters : 1st one = the message must
	 * start with B, 2nd : get the messages (clubs names) that are in Spain
	 * 3rd : get clubs that won more than 5 Champions League
	 */
	@Test
	public void test() {

		// filter1 : the message must start with B
		MessageFilterI filter1 = m -> m.getPlayload().toString().startsWith("B");
		List<MessageI> fitredMessages = messages.stream().filter(filter1::filtrer).collect(Collectors.toList());
		assertEquals(fitredMessages.size(), 2);
		// filter2 : get the messages (clubs names) that are in spain
		MessageFilterI filter2 = m -> m.getProprieties().getStringProp("contry").toString().equals("Spain");
		fitredMessages = messages.stream().filter(filter2::filtrer).collect(Collectors.toList());
		assertEquals(fitredMessages.size(), 3);
		// filter3 : get clubs that won more than 5 Champions League
		MessageFilterI filter3 = m -> m.getProprieties().getIntProp("N° Champions League") > 5;
		fitredMessages = messages.stream().filter(filter3::filtrer).collect(Collectors.toList());
		assertEquals(fitredMessages.size(), 2);

	}

}
