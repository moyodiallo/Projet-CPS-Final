package cps.mouradalpha.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

/**
 * The interface <code>NotificationCI</code> defines the methods that allows the
 * brokers to notify each other when a change is happened. A Broker get notified
 * by another when a topic is created, a message(s) is published or a topic is
 * destroyed
 * 
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
public interface NotificationCI extends OfferedI, RequiredI {
	/**
	 * notify a the broker that a topic was created
	 * 
	 * @param topic the topic
	 * @throws Exception
	 */
	void notifyTopic(String topic) throws Exception;

	/**
	 * notify a the broker that a message was sent
	 * 
	 * @param m     the message
	 * @param topic the topic
	 * @throws Exception
	 */
	void notifyPublish(MessageI m, String topic) throws Exception;

	/**
	 * notify a the broker that messages were sent
	 * 
	 * @param ms    the messages
	 * @param topic the topic
	 * @throws Exception
	 */

	void notifyPublish(MessageI ms[], String topic) throws Exception;

	/**
	 * notify a the broker that a topic was destroyed
	 * @param topic
	 * @throws Exception
	 */
	void notifyDestroyTopic(String topic) throws Exception;
}