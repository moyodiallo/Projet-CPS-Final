package cps.mouradalpha.interfaces;
/**
 * The interface <code>PublicationImplementationI</code> defines the methods that allows to a Publisher to publish messages for a specific topic.
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
public interface PublicationImplementationI {
	/**
	 * publish a message in a specific topic
	 * @param m the message that will be published
	 * @param topic the URI of the topic
	 * @throws Exception
	 */
	public void publish(MessageI m, String topic) throws Exception;
	/**
	 * publish a message in a specific topics
	 * @param m the message that will be published
	 * @param topics the URIs of the topics
	 * @throws Exception
	 */
	public void publish(MessageI m, String[] topics) throws Exception;
	/**
	 * publish messages in a specific topic
	 * @param ms the messages that will be published
	 * @param topic the URI of the topic
	 * @throws Exception
	 */
	public void publish(MessageI[] ms, String topic) throws Exception;
	/**
	 * publish messages in a specific topics
	 * @param ms the messages that will be published
	 * @param topics topics the URIs of the topics
	 * @throws Exception
	 */
	public void publish(MessageI[] ms, String[] topics) throws Exception;
}
