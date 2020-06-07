package cps.mouradalpha.ports;

import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.NotificationCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

/**
 * The class <code>OutBoundPortNotification</code> implements the outbound port
 * of a component that requires Notification service through the
 * <code>NotificationCI</code> interface.
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
public class OutBoundPortNotification extends AbstractOutboundPort implements NotificationCI {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * create the port with the given URI and the given owner.
	 * 
	 * @param uri   the URI of the port
	 * @param owner the owner of the port
	 * @throws Exception
	 */
	public OutBoundPortNotification(String uri, ComponentI owner) throws Exception {
		super(uri, NotificationCI.class, owner);
	}

	/**
	 * Notify the broker through the connector with the new topic created so that it
	 * update its list
	 * 
	 * @param topic the topic
	 */
	@Override
	public void notifyTopic(String topic) throws Exception {
		((NotificationCI) this.connector).notifyTopic(topic);
	}

	/**
	 * Notify the broker through the connector with the new message published so
	 * that it update its list
	 * 
	 * @param m the message
	 * @param topic the topic
	 */
	@Override
	public void notifyPublish(MessageI m, String topic) throws Exception {
		((NotificationCI) this.connector).notifyPublish(m, topic);
	}

	/**
	 * Notify the broker through the connector that a specific topic is destroyed so
	 * that it update its list
	 * @param topic
	 */
	@Override
	public void notifyDestroyTopic(String topic) throws Exception {
		((NotificationCI) this.connector).notifyDestroyTopic(topic);
    }
	/**
	 * Notify the broker through the connector with new messages published so
	 * that it update its list
	 * 
	 * @param ms the messages
	 * @param topic the topic
	 */
    @Override
    public void notifyPublish(MessageI[] ms, String topic) throws Exception {
        ((NotificationCI)this.connector).notifyPublish(ms, topic);
    }

}