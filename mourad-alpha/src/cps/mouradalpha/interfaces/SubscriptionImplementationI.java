package cps.mouradalpha.interfaces;
/**
 * The interface <code>SubscriptionImplementationI</code> defines the methods that allows to a Subscriber to subscribe for a topic.
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
public interface SubscriptionImplementationI {
	/**
	 * subscription for a specific topic 
	 * @param topic URI of the topic
	 * @param inboundPortURI the inbound port of the Broker
	 * @throws Exception
	 */
    public void subscribe(String topic, String inboundPortURI) throws Exception;
    /**
     * subscription for a specific topics 
     * @param topics URIs of the topics
     * @param inboundPortURI the inbound port of the Subscriber
     * @throws Exception
     */
    public void subscribe(String[] topics, String inboundPortURI) throws Exception;
    /**
     * subscription for a specific topics with a filter
     * @param topic URI of the topic
     * @param filter the filter that will be used to get only the messages that satisfy the filter
     * @param inboundPortURI  the inbound port of the Subscriber
     * @throws Exception
     */
    public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) throws Exception;
    /**
     * modify the filter that used in the subscription
     * @param topic URI of the topic
     * @param newFilter the new filter that will be used to get only the messages that satisfy the filter
     * @param inboundPortURI the inbound port of the Subscriber
     * @throws Exception
     */
    public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) throws Exception;
    /**
     * unsubscribe for a topic so that the subscriber don't receive the messages for this topic anymore
     * @param topic URI of the topic
     * @param inboundPortURI inbound port of the Subscriber
     * @throws Exception
     */
    public void unsubscribe(String topic, String inboundPortURI) throws Exception;
}
