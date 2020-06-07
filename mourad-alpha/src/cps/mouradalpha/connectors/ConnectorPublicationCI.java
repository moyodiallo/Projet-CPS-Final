package cps.mouradalpha.connectors;

import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.PublicationCI;
import fr.sorbonne_u.components.connectors.AbstractConnector;
/**
 * The class <code>ConnectorPublicationCI</code> implements a connector that links components  
 * which offer/require services that are present in <code>PublicationCI</code>
 * <p><strong>Description</strong></p>
 * a call of any method from the Publisher component will be transfered and execute by the Broker  
 * @author  Mourad TOUATI / Alpha Issiaga DIALLO
 *  
 */
public class ConnectorPublicationCI extends AbstractConnector implements 
PublicationCI {
	
	/**
	 * calling the inbound port with the corresponding offered method.
	 * @param m the message that will be published
	 * @param topic the URI of the topic
	 * @throws Exception
	 */
    @Override
    public void publish(MessageI m, String topic)throws  Exception {
       ((PublicationCI)this.offering).publish(m, topic);
    }

    /**
	 * calling the inbound port with the corresponding offered method.
	 * @param m the message that will be published
	 * @param topics the URIs of the topics
	 * @throws Exception
	 */
    @Override
    public void publish(MessageI m, String[] topics)throws  Exception {
       ((PublicationCI)this.offering).publish(m, topics);
    }
    /**
	 * calling the inbound port with the corresponding offered method.
	 * @param ms the messages that will be published
	 * @param topic the URI of the topic
	 * @throws Exception
	 */
    @Override
    public void publish(MessageI[] ms, String topic)throws  Exception {
       ((PublicationCI)this.offering).publish(ms, topic);
    }
    /**
	 * calling the inbound port with the corresponding offered method.
	 * @param ms the messages that will be published
	 * @param topics topics the URIs of the topics
	 * @throws Exception
	 */
    @Override
    public void publish(MessageI[] ms, String[] topics)throws  Exception {
       ((PublicationCI)this.offering).publish(ms, topics);
    }
}