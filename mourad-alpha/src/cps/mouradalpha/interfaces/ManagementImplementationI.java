package cps.mouradalpha.interfaces;

/**
 * The interface <code>ManagementImplementationI</code> defines the methods for
 * handling topics.
 * 
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 * @version 1.0
 *
 */
public interface ManagementImplementationI {

	/**
	 * create a single topic from a given URI
	 * 
	 * @param topic URI of the topic
	 * @throws Exception
	 */
	public void createTopic(String topic) throws Exception;

	/**
	 * create several topics from a given URIs at once
	 * 
	 * @param topics URIs of the topics
	 * @throws Exception
	 */
	public void createTopic(String[] topics) throws Exception;

	/**
	 * 
	 * @param topic URI of the topic
	 * @throws Exception
	 */
	public void destroyTopic(String topic) throws Exception;

	/**
	 * 
	 * @param topic URI of the topic
	 * @return true if the URI corresponding to the topic exists or false otherwise
	 * @throws Exception
	 */
	public boolean isTopic(String topic) throws Exception;

	/**
	 * 
	 * @return get the list of URI corresponding to the topics that exists in the
	 *         Broker
	 * @throws Exception
	 */
	public String[] getTopics() throws Exception;

	/**
	 * 
	 * @return get the URI of a port offering the interface PublicationCI which
	 *         allow the Publisher to connect and publish its messages
	 * @throws Exception
	 */
	public String getPublicationPortURI() throws Exception;

}
