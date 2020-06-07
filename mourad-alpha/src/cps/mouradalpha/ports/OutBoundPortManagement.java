package cps.mouradalpha.ports;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import cps.mouradalpha.interfaces.ManagementCI;
import cps.mouradalpha.interfaces.MessageFilterI;

/**
 * The class <code>OutBoundPortManagement</code> implements the outbound port of
 * a component that requires Management service through the
 * <code>ManagementCI</code> interface.
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI 
 *
 */
public class OutBoundPortManagement extends AbstractOutboundPort implements ManagementCI {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the port with the given owner
	 * 
	 * @param owner The owner of the port
	 * @throws Exception
	 */
	public OutBoundPortManagement(ComponentI owner) throws Exception {
		super(ManagementCI.class, owner);
	}

	/**
	 * create the port with the given URI and the given owner.
	 * 
	 * @param uri   the URI of the port
	 * @param owner the owner of the port
	 * @throws Exception
	 */
	public OutBoundPortManagement(String uri, ComponentI owner) throws Exception {
		super(uri, ManagementCI.class, owner);
	}

	/**
	 * Subscribe to a specific topic by calling the server component (Broker)
	 * through the connector that implements the required interface.
	 * 
	 * @param topic          the topic
	 * @param inboundPortURI the inboundPortURI
	 */
	@Override
	public void subscribe(String topic, String inboundPortURI) throws Exception {
		((ManagementCI) this.connector).subscribe(topic, inboundPortURI);
	}

	/**
	 * Subscribe to a specific topics by calling the server component (Broker)
	 * through the connector that implements the required interface.
	 * 
	 * @param topics         the topics
	 * @param inboundPortURI the inboundPortURI
	 */
	@Override
	public void subscribe(String[] topics, String inboundPortURI) throws Exception {
		((ManagementCI) this.connector).subscribe(topics, inboundPortURI);
	}

	/**
	 * Subscribe to a specific topic with a filter by calling the server component
	 * (Broker) through the connector that implements the required interface.
	 * 
	 * @param topic          the topic
	 * @param filter         the filter
	 * @param inboundPortURI the inboundPortURI
	 */
	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) throws Exception {
		((ManagementCI) this.connector).subscribe(topic, filter, inboundPortURI);
	}

	/**
	 * Modify associated to a specific topic by calling the server component
	 * (Broker) through the connector that implements the required interface.
	 * 
	 * @param topic          the topic
	 * @param newFilter      the new Filter
	 * @param inboundPortURI the inboundPortURI
	 */
	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) throws Exception {
		((ManagementCI) this.connector).modifyFilter(topic, newFilter, inboundPortURI);
	}

	/**
	 * Unsubscribe to a specific topic by calling the server component (Broker)
	 * through the connector that implements the required interface.
	 * 
	 * @param topic          the topic
	 * @param inboundPortURI the inboundPortURI
	 */
	@Override
	public void unsubscribe(String topic, String inboundPortURI) throws Exception {
		((ManagementCI) this.connector).unsubscribe(topic, inboundPortURI);
	}

	/**
	 * create a specific topic by calling the server component (Broker) through the
	 * connector that implements the required interface.
	 * 
	 * @param topic the topic
	 */
	@Override
	public void createTopic(String topic) throws Exception {
		((ManagementCI) this.connector).createTopic(topic);
	}

	/**
	 * create specific topics by calling the server component (Broker) through the
	 * connector that implements the required interface.
	 * 
	 * @param topics the topics
	 */
	@Override
	public void createTopic(String[] topics) throws Exception {
		((ManagementCI) this.connector).createTopic(topics);
	}

	/**
	 * destroy a specific topic by calling the server component (Broker) through the
	 * connector that implements the required interface.
	 * 
	 * @param topic the topic
	 */
	@Override
	public void destroyTopic(String topic) throws Exception {
		((ManagementCI) this.connector).destroyTopic(topic);
	}

	/**
	 * Check whether a specific topic exist in the server component (Broker) through
	 * the connector that implements the required interface.
	 * 
	 * @param topic the topic
	 * @return true if the topic exist, false otherwise
	 */
	@Override
	public boolean isTopic(String topic) throws Exception {
		return ((ManagementCI) this.connector).isTopic(topic);
	}

	/**
	 * 
	 * get the topics by calling the server component (Broker) through the
	 * connector that implements the required interface.
	 * @return the list of topics 
	 */

	@Override
	public String[] getTopics() throws Exception {
		return ((ManagementCI) this.connector).getTopics();
	}

	/**
	 * 
	 */
	@Override
	public String getPublicationPortURI() throws Exception {
		return ((ManagementCI) this.connector).getPublicationPortURI();
	}
}