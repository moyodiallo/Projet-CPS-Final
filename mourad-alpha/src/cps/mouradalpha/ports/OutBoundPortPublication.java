package cps.mouradalpha.ports;

import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.PublicationCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

/**
 * The class <code>OutBoundPortPublication</code> implements the outbound port
 * of a component that requires Publication service through the
 * <code>PublicationCI</code> interface.
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
public class OutBoundPortPublication extends AbstractOutboundPort implements PublicationCI {

    /**
     *
     */  
    private static final long serialVersionUID = 1L;
    
    /**
     * Create the port with the given owner
     * @param owner The owner of the port
     * @throws Exception
     */
    public OutBoundPortPublication(ComponentI owner) throws Exception {
        super(PublicationCI.class,owner);
    }
    /**
     * create the port with the given URI and the given owner.
     * @param uri the URI of the port
     * @param owner the owner of the port
     * @throws Exception
     */
    public OutBoundPortPublication(String uri, ComponentI owner) 
    throws Exception {
        super(uri, PublicationCI.class, owner);
    }
    /** 
     * publish a message for a specific topic by calling the server component (Broker) through the connector that
	 * implements the required interface.
     * @param m the message  
     * @param topic the topic
     */
    @Override
    public void publish(MessageI m, String topic)throws  Exception {
       ((PublicationCI)this.connector).publish(m, topic);
    }
    /**
     * 
     * publish a message for a specific topics by calling the server component (Broker) through the connector that
	 * implements the required interface.
     * @param m the message  
     * @param topics the topics
     */
    @Override
    public void publish(MessageI m, String[] topics)throws  Exception {
       ((PublicationCI)this.connector).publish(m, topics);
    }
    
    /**
     * publish messages for a specific topic by calling the server component (Broker) through the connector that
	 * implements the required interface.
     * @param ms the messages  
     * @param topic the topics
     */
    @Override
    public void publish(MessageI[] ms, String topic)throws  Exception {
       ((PublicationCI)this.connector).publish(ms, topic);
    }
    /**
     * publish messages for a specific topics by calling the server component (Broker) through the connector that
	 * implements the required interface.
     * @param ms the messages  
     * @param topics the topics
     */
    @Override
    public void publish(MessageI[] ms, String[] topics)throws  Exception {
       ((PublicationCI)this.connector).publish(ms, topics);
    }
}